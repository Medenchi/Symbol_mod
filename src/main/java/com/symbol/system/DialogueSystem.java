package com.symbol.system;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.*;

import com.symbol.SymbolMod;
import com.symbol.registry.ModSounds;

public class DialogueSystem {

    // Текущий активный диалог для каждого игрока
    private static final Map<UUID, ActiveDialogue> ACTIVE_DIALOGUES = new HashMap<>();

    // ============================================================
    // ЗАПУСК ДИАЛОГА
    // ============================================================

    public static void startDialogue(
        ServerPlayerEntity player,
        String dialogueId
    ) {
        DialogueData data = DialogueRegistry.getDialogue(dialogueId);
        if (data == null) {
            SymbolMod.LOGGER.error(
                "Диалог '{}' не найден!",
                dialogueId
            );
            return;
        }

        // Создаём активный диалог
        ActiveDialogue active = new ActiveDialogue(data, 0);
        ACTIVE_DIALOGUES.put(player.getUuid(), active);

        SymbolMod.LOGGER.debug(
            "Запуск диалога '{}' для {}",
            dialogueId,
            player.getName().getString()
        );

        // Показываем первую реплику
        showCurrentLine(player, active);
    }

    // ============================================================
    // ПОКАЗ РЕПЛИКИ
    // ============================================================

    private static void showCurrentLine(
        ServerPlayerEntity player,
        ActiveDialogue active
    ) {
        DialogueLine line = active.getCurrentLine();
        if (line == null) {
            endDialogue(player);
            return;
        }

        // Отправляем текст клиенту (для GUI диалога)
        NetworkHandler.sendDialogueLine(
            player,
            line.speakerName,
            line.text,
            line.speakerSide,
            active.data.npcId
        );

        // Проигрываем озвучку если есть
        if (line.voiceSound != null) {
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                line.voiceSound,
                SoundCategory.VOICE,
                SymbolMod.CONFIG.voiceVolume,
                1.0f
            );
        }

        // Если есть варианты выбора — показываем кнопки
        if (line.choices != null && !line.choices.isEmpty()) {
            showChoiceButtons(player, line.choices);
        } else {
            // Автоматическое продвижение через время
            scheduleNextLine(player, active, line.durationTicks);
        }
    }

    // ============================================================
    // КНОПКИ ВЫБОРА
    // ============================================================

    private static void showChoiceButtons(
        ServerPlayerEntity player,
        List<DialogueChoice> choices
    ) {
        // Замораживаем игрока
        PlayerFreezeSystem.freezeForChoice(player);

        // Отправляем клиенту список кнопок
        NetworkHandler.sendChoiceButtons(player, choices);
    }

    // Вызывается когда игрок нажал кнопку выбора
    public static void onChoiceSelected(
        PlayerEntity player,
        int choiceIndex
    ) {
        if (!(player instanceof ServerPlayerEntity sp)) return;

        ActiveDialogue active = ACTIVE_DIALOGUES.get(player.getUuid());
        if (active == null) return;

        DialogueLine currentLine = active.getCurrentLine();
        if (currentLine == null || currentLine.choices == null) return;

        if (choiceIndex >= currentLine.choices.size()) return;

        DialogueChoice choice = currentLine.choices.get(choiceIndex);

        SymbolMod.LOGGER.debug(
            "Игрок {} выбрал: '{}'",
            player.getName().getString(),
            choice.text
        );

        // Применяем эффект выбора
        applyChoiceEffect(sp, choice);

        // Переходим к следующей реплике по выбору
        if (choice.nextLineId != null) {
            active.jumpToLine(choice.nextLineId);
        } else {
            active.nextLine();
        }

        showCurrentLine(sp, active);
    }

    private static void applyChoiceEffect(
        ServerPlayerEntity player,
        DialogueChoice choice
    ) {
        switch (choice.effectType) {
            case ADD_PARANOIA -> ParanoiaSystem.increaseParanoia(
                player,
                choice.effectValue
            );
            case ADD_EVIDENCE -> {
                // Добавляем улику в дневник
                EvidenceSystem.addEvidence(
                    player,
                    choice.effectString
                );
            }
            case TRIGGER_CUTSCENE -> CutsceneSystem.startCutscene(
                player,
                choice.effectString
            );
            case NONE -> { /* Ничего */ }
        }
    }

    // ============================================================
    // ПРОДВИЖЕНИЕ ДИАЛОГА
    // ============================================================

    private static void scheduleNextLine(
        ServerPlayerEntity player,
        ActiveDialogue active,
        int delayTicks
    ) {
        // Через delayTicks тиков показываем следующую реплику
        // Если 0 — ждём нажатия пробела/клика
        if (delayTicks <= 0) {
            // Ждём ввода — клиент отправит пакет
            NetworkHandler.sendWaitForInput(player);
        } else {
            // Автоматически
            scheduleTask(delayTicks, () -> {
                active.nextLine();
                showCurrentLine(player, active);
            });
        }
    }

    // Вызывается когда игрок нажал пробел/клик для продолжения
    public static void onPlayerAdvanceDialogue(ServerPlayerEntity player) {
        ActiveDialogue active = ACTIVE_DIALOGUES.get(player.getUuid());
        if (active == null) return;

        active.nextLine();
        showCurrentLine(player, active);
    }

    // ============================================================
    // ЗАВЕРШЕНИЕ
    // ============================================================

    private static void endDialogue(ServerPlayerEntity player) {
        ActiveDialogue active = ACTIVE_DIALOGUES.remove(player.getUuid());
        if (active == null) return;

        SymbolMod.LOGGER.debug(
            "Диалог '{}' завершён",
            active.data.dialogueId
        );

        // Закрываем GUI диалога
        NetworkHandler.sendDialogueEnd(player);

        // Размораживаем игрока
        PlayerFreezeSystem.unfreeze(player);

        // Если у диалога есть продолжение — запускаем
        if (active.data.nextCutsceneId != null) {
            CutsceneSystem.startCutscene(
                player,
                active.data.nextCutsceneId
            );
        }
    }

    // ============================================================
    // ДАННЫЕ ДИАЛОГОВ
    // ============================================================

    public static class DialogueData {
        public final String dialogueId;
        public final String npcId;
        public final List<DialogueLine> lines;
        public final String nextCutsceneId;

        public DialogueData(
            String dialogueId,
            String npcId,
            List<DialogueLine> lines,
            String nextCutsceneId
        ) {
            this.dialogueId = dialogueId;
            this.npcId = npcId;
            this.lines = lines;
            this.nextCutsceneId = nextCutsceneId;
        }
    }

    public static class DialogueLine {
        public final String speakerName;
        public final String text;
        public final String speakerSide; // "left" или "right"
        public final net.minecraft.sound.SoundEvent voiceSound;
        public final int durationTicks;  // 0 = ждать ввода
        public final List<DialogueChoice> choices;

        public DialogueLine(
            String speakerName,
            String text,
            String speakerSide,
            net.minecraft.sound.SoundEvent voiceSound,
            int durationTicks,
            List<DialogueChoice> choices
        ) {
            this.speakerName = speakerName;
            this.text = text;
            this.speakerSide = speakerSide;
            this.voiceSound = voiceSound;
            this.durationTicks = durationTicks;
            this.choices = choices;
        }
    }

    public static class DialogueChoice {
        public final String text;
        public final String nextLineId;
        public final ChoiceEffectType effectType;
        public final int effectValue;
        public final String effectString;

        public DialogueChoice(
            String text,
            String nextLineId,
            ChoiceEffectType effectType,
            int effectValue,
            String effectString
        ) {
            this.text = text;
            this.nextLineId = nextLineId;
            this.effectType = effectType;
            this.effectValue = effectValue;
            this.effectString = effectString;
        }
    }

    public enum ChoiceEffectType {
        NONE,
        ADD_PARANOIA,
        ADD_EVIDENCE,
        TRIGGER_CUTSCENE
    }

    // ============================================================
    // АКТИВНЫЙ ДИАЛОГ
    // ============================================================

    private static class ActiveDialogue {
        public final DialogueData data;
        private int currentLineIndex;

        public ActiveDialogue(DialogueData data, int startIndex) {
            this.data = data;
            this.currentLineIndex = startIndex;
        }

        public DialogueLine getCurrentLine() {
            if (currentLineIndex >= data.lines.size()) return null;
            return data.lines.get(currentLineIndex);
        }

        public void nextLine() {
            currentLineIndex++;
        }

        public void jumpToLine(String lineId) {
            for (int i = 0; i < data.lines.size(); i++) {
                // Поиск по ID реплики
                // ID хранится в отдельном поле
                currentLineIndex = i;
                break;
            }
        }
    }

    private static void scheduleTask(int delayTicks, Runnable task) {
        // Планирование задачи через тики сервера
    }
}
