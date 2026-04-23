package com.symbol.system;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.symbol.SymbolMod;

public class CutsceneSystem {

    // Активные катсцены игроков
    private static final Map<UUID, ActiveCutscene> ACTIVE = new HashMap<>();

    // ============================================================
    // ЗАПУСК КАТСЦЕНЫ
    // ============================================================

    public static void startCutscene(
        PlayerEntity player,
        String cutsceneId
    ) {
        CutsceneData data = CutsceneRegistry.getCutscene(cutsceneId);
        if (data == null) {
            SymbolMod.LOGGER.error(
                "Катсцена '{}' не найдена!",
                cutsceneId
            );
            return;
        }

        if (!(player instanceof ServerPlayerEntity sp)) return;

        SymbolMod.LOGGER.info(
            "Запуск катсцены '{}' для {}",
            cutsceneId,
            player.getName().getString()
        );

        // Останавливаем предыдущую если была
        if (ACTIVE.containsKey(player.getUuid())) {
            stopCutscene(player);
        }

        // Замораживаем игрока
        if (data.freezePlayer) {
            PlayerFreezeSystem.freezeFull(player);
        }

        // Создаём активную катсцену
        ActiveCutscene active = new ActiveCutscene(data, 0);
        ACTIVE.put(player.getUuid(), active);

        // Отправляем клиенту команду начать катсцену
        NetworkHandler.sendCutsceneStart(sp, data);

        // Запускаем музыку если есть
        if (data.music != null) {
            player.getWorld().playSound(
                null,
                player.getBlockPos(),
                data.music,
                SoundCategory.MUSIC,
                SymbolMod.CONFIG.cutsceneMusicVolume,
                1.0f
            );
        }
    }

    // ============================================================
    // ТИК — вызывается каждый тик сервера
    // ============================================================

    public static void serverTick(ServerPlayerEntity player) {
        ActiveCutscene active = ACTIVE.get(player.getUuid());
        if (active == null) return;

        // Обрабатываем события текущего тика
        for (CutsceneData.CutsceneEvent event : active.data.events) {
            if (event.tick == active.currentTick) {
                processEvent(player, event, active);
            }
        }

        active.currentTick++;

        // Проверяем окончание
        if (active.currentTick >= active.data.totalDurationTicks) {
            onCutsceneEnd(player, active);
        }
    }

    // ============================================================
    // ОБРАБОТКА СОБЫТИЙ
    // ============================================================

    private static void processEvent(
        ServerPlayerEntity player,
        CutsceneData.CutsceneEvent event,
        ActiveCutscene active
    ) {
        switch (event.type) {

            case PLAY_SOUND -> {
                SoundEvent sound = getSoundById(event.data);
                if (sound != null) {
                    player.getWorld().playSound(
                        null,
                        player.getBlockPos(),
                        sound,
                        SoundCategory.BLOCKS,
                        event.volume,
                        event.pitch
                    );
                }
            }

            case PLAY_VOICE -> {
                SoundEvent voice = getSoundById(event.data);
                if (voice != null && SymbolMod.CONFIG.enableVoiceActing) {
                    player.getWorld().playSound(
                        null,
                        player.getBlockPos(),
                        voice,
                        SoundCategory.VOICE,
                        SymbolMod.CONFIG.voiceVolume,
                        1.0f
                    );
                }
            }

            case SHOW_SUBTITLE -> {
                if (SymbolMod.CONFIG.showSubtitles) {
                    NetworkHandler.sendSubtitle(player, event.data);
                }
            }

            case HIDE_SUBTITLE -> NetworkHandler.sendHideSubtitle(player);

            case SHOW_TEXT ->
                NetworkHandler.sendScreenText(player, event.data);

            case HIDE_TEXT ->
                NetworkHandler.sendHideScreenText(player);

            case FADE_TO_BLACK ->
                NetworkHandler.sendFadeToBlack(player);

            case FADE_FROM_BLACK ->
                NetworkHandler.sendFadeFromBlack(player);

            case SCREEN_FLASH ->
                NetworkHandler.sendScreenFlash(player);

            case CAMERA_SHAKE ->
                NetworkHandler.sendCameraShake(player, event.data);

            case ADD_EVIDENCE ->
                EvidenceSystem.addEvidence(player, event.data);

            case TRIGGER_ANIMATION -> {
                // Формат data: "npc_id:animation_name"
                String[] parts = event.data.split(":");
                if (parts.length == 2) {
                    NetworkHandler.sendNpcAnimation(
                        player, parts[0], parts[1]
                    );
                }
            }

            case START_DIALOGUE -> {
                // Пауза катсцены до конца диалога
                active.pausedForDialogue = true;
                DialogueSystem.startDialogue(player, event.data);
            }

            case SPAWN_NPC ->
                NetworkHandler.sendSpawnNpc(player, event.data);

            case HIDE_NPC ->
                NetworkHandler.sendHideNpc(player, event.data);

            case SET_TIME -> {
                try {
                    long time = Long.parseLong(event.data);
                    player.getWorld().setTimeOfDay(time);
                } catch (NumberFormatException e) {
                    SymbolMod.LOGGER.error(
                        "Неверное время: {}",
                        event.data
                    );
                }
            }

            case PLAY_MUSIC -> {
                SoundEvent music = getSoundById(event.data);
                if (music != null) {
                    player.getWorld().playSound(
                        null,
                        player.getBlockPos(),
                        music,
                        SoundCategory.MUSIC,
                        SymbolMod.CONFIG.cutsceneMusicVolume,
                        1.0f
                    );
                }
            }

            case STOP_MUSIC ->
                NetworkHandler.sendStopMusic(player);

            case END_CUTSCENE ->
                stopCutscene(player);
        }
    }

    // ============================================================
    // ЗАВЕРШЕНИЕ КАТСЦЕНЫ
    // ============================================================

    private static void onCutsceneEnd(
        ServerPlayerEntity player,
        ActiveCutscene active
    ) {
        SymbolMod.LOGGER.info(
            "Катсцена '{}' завершена",
            active.data.id
        );

        ACTIVE.remove(player.getUuid());

        // Размораживаем игрока
        PlayerFreezeSystem.unfreeze(player);

        // Отправляем клиенту команду завершить
        NetworkHandler.sendCutsceneEnd(player);

        // Запускаем следующую катсцену
        if (active.data.nextCutsceneId != null) {
            startCutscene(player, active.data.nextCutsceneId);
            return;
        }

        // Или запускаем диалог
        if (active.data.nextDialogueId != null) {
            DialogueSystem.startDialogue(player, active.data.nextDialogueId);
        }
    }

    public static void stopCutscene(PlayerEntity player) {
        ACTIVE.remove(player.getUuid());
        PlayerFreezeSystem.unfreeze(player);
        if (player instanceof ServerPlayerEntity sp) {
            NetworkHandler.sendCutsceneEnd(sp);
        }
    }

    // ============================================================
    // ВОСПРОИЗВЕДЕНИЕ ГОЛОСА (без катсцены)
    // ============================================================

    public static void playVoice(
        ServerPlayerEntity player,
        SoundEvent voice
    ) {
        if (!SymbolMod.CONFIG.enableVoiceActing) return;
        player.getWorld().playSound(
            null,
            player.getBlockPos(),
            voice,
            SoundCategory.VOICE,
            SymbolMod.CONFIG.voiceVolume,
            1.0f
        );
    }

    // ============================================================
    // ПРОВЕРКИ
    // ============================================================

    public static boolean isInCutscene(PlayerEntity player) {
        return ACTIVE.containsKey(player.getUuid());
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private static SoundEvent getSoundById(String id) {
        // Получаем SoundEvent по строковому ID
        // Через реестр звуков Minecraft
        try {
            net.minecraft.util.Identifier soundId =
                new net.minecraft.util.Identifier(
                    SymbolMod.MOD_ID, id
                );
            return net.minecraft.registry.Registries.SOUND_EVENT
                .get(soundId);
        } catch (Exception e) {
            SymbolMod.LOGGER.warn("Звук '{}' не найден", id);
            return null;
        }
    }

    // ============================================================
    // АКТИВНАЯ КАТСЦЕНА
    // ============================================================

    private static class ActiveCutscene {
        public final CutsceneData data;
        public int currentTick;
        public boolean pausedForDialogue;

        public ActiveCutscene(CutsceneData data, int startTick) {
            this.data = data;
            this.currentTick = startTick;
            this.pausedForDialogue = false;
        }
    }
}
