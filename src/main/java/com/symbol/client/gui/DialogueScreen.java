package com.symbol.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import com.symbol.client.network.ClientNetworkHandler;
import com.symbol.registry.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class DialogueScreen extends Screen {

    // ============================================================
    // СОСТОЯНИЕ
    // ============================================================

    private static DialogueScreen INSTANCE = null;

    // Текущая реплика
    private static String speakerName = "";
    private static String dialogueText = "";
    private static String speakerSide = "left";
    private static String npcId = "";

    // Анимация текста (печатная машинка)
    private static int visibleChars = 0;
    private static int textTimer = 0;
    private static final int CHARS_PER_TICK = 2;
    private static boolean textComplete = false;

    // Кнопки выбора
    private static List<String> choices = new ArrayList<>();
    private static boolean showingChoices = false;
    private static int hoveredChoice = -1;

    // Подсказка продолжить
    private static boolean showContinuePrompt = false;
    private static int promptBlink = 0;

    // Анимация появления
    private static float slideY = 200f;
    private static final float TARGET_Y = 0f;

    // ============================================================
    // СТАТИЧЕСКИЕ МЕТОДЫ (вызываются из сети)
    // ============================================================

    public static void showLine(
        String speaker,
        String text,
        String side,
        String npc
    ) {
        speakerName = speaker;
        dialogueText = text;
        speakerSide = side;
        npcId = npc;
        visibleChars = 0;
        textTimer = 0;
        textComplete = false;
        showingChoices = false;
        showContinuePrompt = false;
        choices.clear();

        // Открываем экран если не открыт
        MinecraftClient client = MinecraftClient.getInstance();
        if (!(client.currentScreen instanceof DialogueScreen)) {
            INSTANCE = new DialogueScreen();
            client.setScreen(INSTANCE);
        }
    }

    public static void showChoices(List<String> choiceList) {
        choices = new ArrayList<>(choiceList);
        showingChoices = true;
        showContinuePrompt = false;
        textComplete = true;
        visibleChars = dialogueText.length();
    }

    public static void showContinuePrompt() {
        if (textComplete) {
            showContinuePrompt = true;
        }
    }

    public static void close() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof DialogueScreen) {
            client.setScreen(null);
        }
        INSTANCE = null;
        speakerName = "";
        dialogueText = "";
        choices.clear();
        showingChoices = false;
        showContinuePrompt = false;
    }

    // ============================================================
    // КОНСТРУКТОР
    // ============================================================

    public DialogueScreen() {
        super(Text.literal("Диалог"));
        slideY = 200f;
    }

    // ============================================================
    // ИНИЦИАЛИЗАЦИЯ
    // ============================================================

    @Override
    protected void init() {
        super.init();
    }

    // ============================================================
    // ТИК
    // ============================================================

    @Override
    public void tick() {
        // Анимация слайда
        if (slideY > TARGET_Y) {
            slideY = Math.max(slideY - 15f, TARGET_Y);
        }

        // Анимация текста — эффект печатной машинки
        if (!textComplete) {
            textTimer++;
            if (textTimer >= 1) {
                textTimer = 0;
                visibleChars = Math.min(
                    visibleChars + CHARS_PER_TICK,
                    dialogueText.length()
                );
                if (visibleChars >= dialogueText.length()) {
                    textComplete = true;
                }
            }
        }

        // Мигание подсказки продолжить
        promptBlink = (promptBlink + 1) % 40;
    }

    // ============================================================
    // РЕНДЕРИНГ
    // ============================================================

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer font = client.textRenderer;
        int w = this.width;
        int h = this.height;

        // Не затемняем фон
        // (игрок видит мир за диалогом)

        int panelY = (int)(h - 130 + slideY);

        // Фон диалогового окна
        renderDialoguePanel(context, w, h, panelY);

        // Портрет говорящего
        renderSpeakerPortrait(context, w, h, panelY, font);

        // Имя говорящего
        renderSpeakerName(context, w, panelY, font);

        // Текст диалога
        renderDialogueText(context, w, panelY, font);

        // Кнопки выбора
        if (showingChoices) {
            renderChoices(context, w, h, mouseX, mouseY, font);
        }

        // Подсказка — нажми чтобы продолжить
        if (showContinuePrompt && promptBlink < 20) {
            renderContinuePrompt(context, w, panelY, font);
        }
    }

    private void renderDialoguePanel(
        DrawContext context, int w, int h, int panelY
    ) {
        // Тёмный полупрозрачный фон
        context.fill(
            20, panelY,
            w - 20, panelY + 110,
            0xDD111111
        );

        // Рамка — тонкая серая линия
        // Верхняя
        context.fill(20, panelY, w - 20, panelY + 1, 0xFF444444);
        // Нижняя
        context.fill(20, panelY + 110, w - 20, panelY + 111, 0xFF444444);
        // Левая
        context.fill(20, panelY, 21, panelY + 111, 0xFF444444);
        // Правая
        context.fill(w - 21, panelY, w - 20, panelY + 111, 0xFF444444);

        // Акцентная линия сверху — тонкая яркая
        context.fill(20, panelY, w - 20, panelY + 2, 0xFF888888);
    }

    private void renderSpeakerPortrait(
        DrawContext context,
        int w, int h, int panelY,
        TextRenderer font
    ) {
        // Квадрат портрета
        boolean isLeft = speakerSide.equals("left");
        int portraitX = isLeft ? 30 : w - 110;
        int portraitY = panelY + 10;
        int portraitSize = 80;

        // Фон портрета
        context.fill(
            portraitX, portraitY,
            portraitX + portraitSize,
            portraitY + portraitSize,
            0xFF222222
        );

        // Рамка портрета
        context.fill(
            portraitX - 1, portraitY - 1,
            portraitX + portraitSize + 1,
            portraitY + portraitSize + 1,
            0xFF555555
        );

        // Имя NPC (заглушка — потом заменить на текстуру)
        String initials = npcId.isEmpty() ? "?" :
            String.valueOf(npcId.toUpperCase().charAt(0));

        int textX = portraitX + (portraitSize -
            font.getWidth(initials)) / 2;
        int textY = portraitY + (portraitSize - font.fontHeight) / 2;

        context.drawText(font,
            Text.literal(initials),
            textX, textY,
            0xFF888888, false
        );
    }

    private void renderSpeakerName(
        DrawContext context, int w, int panelY, TextRenderer font
    ) {
        boolean isLeft = speakerSide.equals("left");
        int nameX = isLeft ? 125 : 30;
        int nameY = panelY + 12;

        // Фон имени
        int nameWidth = font.getWidth(speakerName) + 16;
        context.fill(
            nameX - 4, nameY - 2,
            nameX + nameWidth, nameY + font.fontHeight + 2,
            0xFF1A1A1A
        );

        // Акцентная линия слева от имени
        context.fill(
            nameX - 4, nameY - 2,
            nameX - 2, nameY + font.fontHeight + 2,
            0xFFAAAAAA
        );

        // Текст имени
        context.drawText(font,
            Text.literal("§f§l" + speakerName),
            nameX + 4, nameY,
            0xFFFFFFFF, false
        );
    }

    private void renderDialogueText(
        DrawContext context, int w, int panelY, TextRenderer font
    ) {
        boolean isLeft = speakerSide.equals("left");
        int textStartX = isLeft ? 125 : 30;
        int textEndX = isLeft ? w - 35 : w - 120;
        int textY = panelY + 35;
        int maxWidth = textEndX - textStartX;

        // Получаем видимую часть текста
        String visible = dialogueText.substring(
            0, Math.min(visibleChars, dialogueText.length())
        );

        // Разбиваем на строки
        List<String> lines = wrapText(visible, font, maxWidth);

        for (int i = 0; i < lines.size() && i < 4; i++) {
            context.drawText(font,
                Text.literal("§7" + lines.get(i)),
                textStartX, textY + i * (font.fontHeight + 3),
                0xFFCCCCCC, false
            );
        }
    }

    private void renderChoices(
        DrawContext context,
        int w, int h,
        int mouseX, int mouseY,
        TextRenderer font
    ) {
        int choiceStartY = h - 200;
        int choiceWidth = 280;
        int choiceHeight = 28;
        int choiceX = (w - choiceWidth) / 2;

        hoveredChoice = -1;

        for (int i = 0; i < choices.size(); i++) {
            int choiceY = choiceStartY + i * (choiceHeight + 8);

            // Проверяем наведение
            boolean hovered =
                mouseX >= choiceX &&
                mouseX <= choiceX + choiceWidth &&
                mouseY >= choiceY &&
                mouseY <= choiceY + choiceHeight;

            if (hovered) hoveredChoice = i;

            // Фон кнопки
            int bgColor = hovered ? 0xFF2A2A2A : 0xFF1A1A1A;
            context.fill(
                choiceX, choiceY,
                choiceX + choiceWidth, choiceY + choiceHeight,
                bgColor
            );

            // Рамка кнопки
            int borderColor = hovered ? 0xFF888888 : 0xFF444444;
            // Верх
            context.fill(
                choiceX, choiceY,
                choiceX + choiceWidth, choiceY + 1,
                borderColor
            );
            // Низ
            context.fill(
                choiceX, choiceY + choiceHeight - 1,
                choiceX + choiceWidth, choiceY + choiceHeight,
                borderColor
            );
            // Лево
            context.fill(
                choiceX, choiceY,
                choiceX + 1, choiceY + choiceHeight,
                borderColor
            );
            // Право
            context.fill(
                choiceX + choiceWidth - 1, choiceY,
                choiceX + choiceWidth, choiceY + choiceHeight,
                borderColor
            );

            // Если наведено — акцентная полоска слева
            if (hovered) {
                context.fill(
                    choiceX, choiceY,
                    choiceX + 3, choiceY + choiceHeight,
                    0xFFAAAAAA
                );
            }

            // Номер варианта
            String number = (i + 1) + ".";
            context.drawText(font,
                Text.literal("§8" + number),
                choiceX + 8,
                choiceY + (choiceHeight - font.fontHeight) / 2,
                0xFF666666, false
            );

            // Текст варианта
            String choiceText = choices.get(i);
            int textColor = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;
            context.drawText(font,
                Text.literal(choiceText),
                choiceX + 25,
                choiceY + (choiceHeight - font.fontHeight) / 2,
                textColor, false
            );
        }
    }

    private void renderContinuePrompt(
        DrawContext context, int w, int panelY, TextRenderer font
    ) {
        String prompt = "▶ Далее";
        int x = w - 70;
        int y = panelY + 90;

        context.drawText(font,
            Text.literal("§8" + prompt),
            x, y,
            0xFF555555, false
        );
    }

    // ============================================================
    // ВВОД
    // ============================================================

    @Override
    public boolean mouseClicked(
        double mouseX, double mouseY, int button
    ) {
        if (button == 0) {
            // Если текст не дописан — пропускаем анимацию
            if (!textComplete) {
                visibleChars = dialogueText.length();
                textComplete = true;
                return true;
            }

            // Если показаны кнопки выбора — обрабатываем выбор
            if (showingChoices && hoveredChoice >= 0) {
                ClientNetworkHandler.sendChoiceSelected(hoveredChoice);
                showingChoices = false;
                return true;
            }

            // Иначе — продвигаем диалог
            if (showContinuePrompt) {
                ClientNetworkHandler.sendAdvanceDialogue();
                showContinuePrompt = false;
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Пробел или E — продвинуть диалог
        if (keyCode == 32 || keyCode == 69) {
            if (!textComplete) {
                visibleChars = dialogueText.length();
                textComplete = true;
                return true;
            }
            if (showContinuePrompt) {
                ClientNetworkHandler.sendAdvanceDialogue();
                showContinuePrompt = false;
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    // Не закрывать по Escape
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    // Не приостанавливать игру
    @Override
    public boolean shouldPause() {
        return false;
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private List<String> wrapText(
        String text, TextRenderer font, int maxWidth
    ) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();

        for (String word : words) {
            String test = current.isEmpty()
                ? word
                : current + " " + word;

            if (font.getWidth(test) > maxWidth) {
                if (!current.isEmpty()) {
                    lines.add(current.toString());
                    current = new StringBuilder(word);
                } else {
                    lines.add(word);
                }
            } else {
                if (!current.isEmpty()) current.append(" ");
                current.append(word);
            }
        }

        if (!current.isEmpty()) {
            lines.add(current.toString());
        }

        return lines;
    }
}
