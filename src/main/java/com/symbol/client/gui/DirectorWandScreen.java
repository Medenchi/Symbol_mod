package com.symbol.client.gui;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import com.symbol.entity.NpcEntity;
import com.symbol.item.DirectorWandItem;
import com.symbol.item.DirectorWandItem.WandMode;

import java.util.ArrayList;
import java.util.List;

public class DirectorWandScreen extends Screen {

    // ============================================================
    // СОСТОЯНИЕ
    // ============================================================

    private final ItemStack wandStack;
    private WandMode currentMode;
    private Tab currentTab = Tab.MAIN;

    // Список кнопок
    private final List<WandButton> buttons = new ArrayList<>();

    // Выбранный NPC тип
    private String selectedNpcType = "gromov";

    // Выбранная катсцена
    private String selectedCutscene = "";

    // Поле ввода текста
    private String inputText = "";
    private boolean editingInput = false;

    // ============================================================
    // КОНСТРУКТОР
    // ============================================================

    public DirectorWandScreen(ItemStack stack) {
        super(Text.literal("Палочка Режиссёра"));
        this.wandStack = stack;
        this.currentMode = DirectorWandItem.getMode(stack);
    }

    // ============================================================
    // ИНИЦИАЛИЗАЦИЯ
    // ============================================================

    @Override
    protected void init() {
        super.init();
        buttons.clear();
        buildButtons();
    }

    private void buildButtons() {
        int w = this.width;
        int h = this.height;
        int panelW = 400;
        int panelH = 450;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        int btnW = 170;
        int btnH = 26;
        int col1X = panelX + 15;
        int col2X = panelX + 210;
        int startY = panelY + 85;
        int gap = 32;

        switch (currentTab) {

            // ==============================
            // ГЛАВНАЯ ВКЛАДКА
            // ==============================
            case MAIN -> {
                // Кнопки режимов
                String[] modeNames = {
                    "Выбор", "Камера", "NPC",
                    "Кнопки", "Катсцены", "Блоки"
                };
                WandMode[] modes = WandMode.values();

                for (int i = 0; i < modeNames.length; i++) {
                    final WandMode mode = modes[i];
                    final String name = modeNames[i];
                    int bx = (i % 2 == 0) ? col1X : col2X;
                    int by = startY + (i / 2) * gap;

                    buttons.add(new WandButton(
                        bx, by, btnW, btnH,
                        (mode == currentMode ? "§f▶ " : "§8  ") + name,
                        () -> {
                            currentMode = mode;
                            DirectorWandItem.setMode(wandStack, mode);
                            init();
                        }
                    ));
                }

                // Кнопка — очистить точки камеры
                buttons.add(new WandButton(
                    col1X, startY + 3 * gap + 10,
                    btnW * 2 + 15, btnH,
                    "§c✕ Очистить точки камеры",
                    () -> {
                        DirectorWandItem.clearCameraPoints(wandStack);
                        sendMessage("Точки камеры очищены");
                    }
                ));

                // Кнопка — экспорт пути камеры
                buttons.add(new WandButton(
                    col1X, startY + 4 * gap + 10,
                    btnW * 2 + 15, btnH,
                    "§e⬇ Экспорт пути камеры",
                    () -> {
                        String exported =
                            DirectorWandItem.exportCameraPath(wandStack);
                        sendMessage(exported);
                    }
                ));

                // Кнопка — закрыть
                buttons.add(new WandButton(
                    col1X, startY + 5 * gap + 10,
                    btnW * 2 + 15, btnH,
                    "§7Закрыть",
                    this::close
                ));
            }

            // ==============================
            // ВКЛАДКА NPC
            // ==============================
            case NPC -> {
                String[] npcTypes = {
                    "gromov", "valeria", "nina",
                    "rashid", "tolya", "semonych",
                    "boss", "mom", "father"
                };
                String[] npcNames = {
                    "Громов", "Валерия", "Нина",
                    "Рашид", "Толя", "Семёныч",
                    "Начальник", "Мама", "Отец"
                };

                for (int i = 0; i < npcTypes.length; i++) {
                    final String type = npcTypes[i];
                    final String name = npcNames[i];
                    int bx = (i % 2 == 0) ? col1X : col2X;
                    int by = startY + (i / 2) * gap;

                    buttons.add(new WandButton(
                        bx, by, btnW, btnH,
                        (type.equals(selectedNpcType) ?
                            "§f▶ " : "§8  ") + name,
                        () -> {
                            selectedNpcType = type;
                            wandStack.getOrCreateNbt()
                                .putString("selected_npc_type", type);
                            sendMessage("Выбран NPC: " + name);
                            init();
                        }
                    ));
                }

                // Кнопка назад
                buttons.add(new WandButton(
                    col1X, panelY + panelH - 50,
                    btnW * 2 + 15, btnH,
                    "§7← Назад",
                    () -> { currentTab = Tab.MAIN; init(); }
                ));
            }

            // ==============================
            // ВКЛАДКА КАТСЦЕНЫ
            // ==============================
            case CUTSCENE -> {
                String[] cutscenes = {
                    "cutscene_act0_building",
                    "cutscene_act0_firing",
                    "cutscene_act0_packing",
                    "cutscene_act0_city_walk",
                    "cutscene_act0_bureau",
                    "cutscene_act0_laptop",
                    "cutscene_act1_arrival",
                    "cutscene_act1_final_room",
                    "cutscene_act2_enter",
                    "cutscene_act2_end",
                    "cutscene_ending_a",
                    "cutscene_ending_b",
                    "cutscene_ending_c",
                    "cutscene_ending_d",
                    "cutscene_credits"
                };

                String[] labels = {
                    "Здание Аргус",
                    "Увольнение",
                    "Сборы",
                    "Блуждание",
                    "Бюро",
                    "Ноутбук",
                    "Прибытие",
                    "Финальная комната",
                    "Вход в деревню",
                    "Конец Акта 2",
                    "Концовка A",
                    "Концовка B",
                    "Концовка C",
                    "Концовка D",
                    "Титры"
                };

                int scrollY = startY;
                for (int i = 0; i < cutscenes.length; i++) {
                    final String id = cutscenes[i];
                    final String label = labels[i];
                    int bx = (i % 2 == 0) ? col1X : col2X;
                    int by = scrollY + (i / 2) * gap;

                    boolean selected = id.equals(selectedCutscene);
                    buttons.add(new WandButton(
                        bx, by, btnW, btnH,
                        (selected ? "§f▶ " : "§8  ") + label,
                        () -> {
                            selectedCutscene = id;
                            wandStack.getOrCreateNbt()
                                .putString("selected_cutscene", id);
                            sendMessage("Выбрана: " + label);
                            init();
                        }
                    ));
                }

                // Кнопка назад
                buttons.add(new WandButton(
                    col1X, panelY + panelH - 50,
                    btnW * 2 + 15, btnH,
                    "§7← Назад",
                    () -> { currentTab = Tab.MAIN; init(); }
                ));
            }
        }
    }

    // ============================================================
    // РЕНДЕРИНГ
    // ============================================================

    @Override
    public void render(
        DrawContext context,
        int mouseX, int mouseY,
        float delta
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer font = client.textRenderer;
        int w = this.width;
        int h = this.height;

        // Полупрозрачный фон
        context.fill(0, 0, w, h, 0x99000000);

        int panelW = 400;
        int panelH = 450;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        // Фон панели
        context.fill(panelX, panelY,
            panelX + panelW, panelY + panelH, 0xFF0D0D0D);

        // Рамка
        context.fill(panelX - 1, panelY - 1,
            panelX + panelW + 1, panelY,
            0xFF444444);
        context.fill(panelX - 1, panelY + panelH,
            panelX + panelW + 1, panelY + panelH + 1,
            0xFF444444);
        context.fill(panelX - 1, panelY,
            panelX, panelY + panelH,
            0xFF444444);
        context.fill(panelX + panelW, panelY,
            panelX + panelW + 1, panelY + panelH,
            0xFF444444);

        // Заголовок
        context.fill(panelX, panelY,
            panelX + panelW, panelY + 35, 0xFF111111);
        context.fill(panelX, panelY + 35,
            panelX + panelW, panelY + 36, 0xFF333333);

        // Символ режиссёра
        context.drawText(font,
            Text.literal("§8✦"),
            panelX + 12, panelY + 12,
            0xFF555555, false
        );

        // Заголовок
        String title = "ПАЛОЧКА РЕЖИССЁРА";
        context.drawText(font,
            Text.literal("§7§l" + title),
            panelX + (panelW - font.getWidth(title)) / 2,
            panelY + 13,
            0xFF888888, false
        );

        // Текущий режим
        String modeText = "Режим: §f" + getModeDisplayName(currentMode);
        context.drawText(font,
            Text.literal("§8" + modeText),
            panelX + 12, panelY + 50,
            0xFF555555, false
        );

        // Количество точек камеры
        int camPoints = wandStack.getOrCreateNbt()
            .getInt("camera_point_count");
        context.drawText(font,
            Text.literal("§8Точек камеры: §7" + camPoints),
            panelX + 200, panelY + 50,
            0xFF555555, false
        );

        // Разделитель
        context.fill(panelX, panelY + 65,
            panelX + panelW, panelY + 66, 0xFF222222);

        // Кнопки вкладок
        renderTabs(context, font, panelX, panelY, panelW);

        // Кнопки
        for (WandButton btn : buttons) {
            btn.render(context, font, mouseX, mouseY);
        }

        // Подсказки внизу
        renderHints(context, font, panelX, panelY, panelW, panelH);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderTabs(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW
    ) {
        Tab[] tabs = Tab.values();
        int tabW = panelW / tabs.length;

        for (int i = 0; i < tabs.length; i++) {
            Tab tab = tabs[i];
            int tabX = panelX + i * tabW;
            int tabY = panelY + 66;
            boolean selected = tab == currentTab;

            context.fill(tabX, tabY,
                tabX + tabW, tabY + 18,
                selected ? 0xFF1A1A1A : 0xFF111111);

            if (selected) {
                context.fill(tabX, tabY + 16,
                    tabX + tabW, tabY + 18, 0xFF666666);
            }

            context.drawText(font,
                Text.literal(selected ?
                    "§f" + tab.label :
                    "§8" + tab.label),
                tabX + (tabW - font.getWidth(tab.label)) / 2,
                tabY + 5,
                0xFFFFFFFF, false
            );
        }
    }

    private void renderHints(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW, int panelH
    ) {
        int hintY = panelY + panelH - 25;
        context.fill(panelX, hintY - 5,
            panelX + panelW, panelY + panelH, 0xFF0A0A0A);
        context.fill(panelX, hintY - 5,
            panelX + panelW, hintY - 4, 0xFF222222);

        String hint = switch (currentMode) {
            case SELECT  -> "ПКМ — информация о блоке";
            case CAMERA  -> "ПКМ — добавить точку камеры · Shift+ПКМ — меню";
            case NPC     -> "ПКМ — заспавнить NPC · ЛКМ по NPC — редактор";
            case BUTTON  -> "ПКМ — создать 3D кнопку";
            case CUTSCENE -> "ПКМ — запустить выбранную катсцену";
            case BLOCK   -> "ПКМ — редактор блока";
        };

        context.drawText(font,
            Text.literal("§8" + hint),
            panelX + 10, hintY + 2,
            0xFF444444, false
        );
    }

    // ============================================================
    // ВВОД
    // ============================================================

    @Override
    public boolean mouseClicked(
        double mouseX, double mouseY, int button
    ) {
        // Проверяем вкладки
        int w = this.width;
        int h = this.height;
        int panelW = 400;
        int panelH = 450;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        Tab[] tabs = Tab.values();
        int tabW = panelW / tabs.length;

        for (int i = 0; i < tabs.length; i++) {
            Tab tab = tabs[i];
            int tabX = panelX + i * tabW;
            int tabY = panelY + 66;

            if (mouseX >= tabX && mouseX < tabX + tabW &&
                mouseY >= tabY && mouseY < tabY + 18) {
                currentTab = tab;
                init();
                return true;
            }
        }

        // Проверяем кнопки
        for (WandButton btn : buttons) {
            if (btn.isHovered(mouseX, mouseY)) {
                btn.onClick();
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    // Не закрывать на Escape
    @Override
    public boolean shouldCloseOnEsc() { return true; }

    // Не паузить игру
    @Override
    public boolean shouldPause() { return false; }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private String getModeDisplayName(WandMode mode) {
        return switch (mode) {
            case SELECT   -> "Выбор";
            case CAMERA   -> "Камера";
            case NPC      -> "NPC";
            case BUTTON   -> "Кнопки";
            case CUTSCENE -> "Катсцены";
            case BLOCK    -> "Блоки";
        };
    }

    private void sendMessage(String text) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(
                Text.literal("§8[Режиссёр] §7" + text),
                false
            );
        }
    }

    // ============================================================
    // ВНУТРЕННИЕ КЛАССЫ
    // ============================================================

    // Кнопка в меню палочки
    private static class WandButton {
        private final int x, y, w, h;
        private final String label;
        private final Runnable onClick;

        public WandButton(
            int x, int y, int w, int h,
            String label, Runnable onClick
        ) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.label = label;
            this.onClick = onClick;
        }

        public void render(
            DrawContext context,
            TextRenderer font,
            int mouseX, int mouseY
        ) {
            boolean hovered = isHovered(mouseX, mouseY);

            // Фон
            context.fill(x, y, x + w, y + h,
                hovered ? 0xFF222222 : 0xFF161616);

            // Рамка
            int borderColor = hovered ? 0xFF555555 : 0xFF2A2A2A;
            context.fill(x, y, x + w, y + 1, borderColor);
            context.fill(x, y + h - 1, x + w, y + h, borderColor);
            context.fill(x, y, x + 1, y + h, borderColor);
            context.fill(x + w - 1, y, x + w, y + h, borderColor);

            // Акцент слева при наведении
            if (hovered) {
                context.fill(x, y, x + 2, y + h, 0xFF888888);
            }

            // Текст
            context.drawText(font,
                Text.literal(label),
                x + 8,
                y + (h - font.fontHeight) / 2,
                0xFFAAAAAA, false
            );
        }

        public boolean isHovered(double mouseX, double mouseY) {
            return mouseX >= x && mouseX < x + w &&
                mouseY >= y && mouseY < y + h;
        }

        public void onClick() { onClick.run(); }
    }

    // Вкладки меню
    public enum Tab {
        MAIN("Главное"),
        NPC("NPC"),
        CUTSCENE("Катсцены");

        public final String label;
        Tab(String label) { this.label = label; }
    }

    // ============================================================
    // РЕДАКТОР БЛОКА
    // ============================================================

    public static class BlockEditorScreen extends Screen {

        private final BlockPos pos;
        private final BlockState state;

        public BlockEditorScreen(BlockPos pos, BlockState state) {
            super(Text.literal("Редактор блока"));
            this.pos = pos;
            this.state = state;
        }

        @Override
        public void render(
            DrawContext context,
            int mouseX, int mouseY, float delta
        ) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer font = client.textRenderer;
            int w = this.width;
            int h = this.height;

            context.fill(0, 0, w, h, 0x99000000);

            int panelW = 300;
            int panelH = 200;
            int panelX = (w - panelW) / 2;
            int panelY = (h - panelH) / 2;

            context.fill(panelX, panelY,
                panelX + panelW, panelY + panelH, 0xFF0D0D0D);

            context.drawText(font,
                Text.literal("§7§lРЕДАКТОР БЛОКА"),
                panelX + 15, panelY + 15,
                0xFF888888, false
            );

            context.drawText(font,
                Text.literal("§8Позиция: §7" +
                    pos.getX() + " " + pos.getY() + " " + pos.getZ()),
                panelX + 15, panelY + 40,
                0xFF555555, false
            );

            context.drawText(font,
                Text.literal("§8Блок: §7" +
                    state.getBlock().getTranslationKey()),
                panelX + 15, panelY + 55,
                0xFF555555, false
            );

            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() { return false; }
    }

    // ============================================================
    // РЕДАКТОР NPC
    // ============================================================

    public static class NpcEditorScreen extends Screen {

        private final NpcEntity npc;
        private String editDialogueId;
        private String editCutsceneId;
        private boolean editingDialogue = false;

        public NpcEditorScreen(NpcEntity npc) {
            super(Text.literal("Редактор NPC"));
            this.npc = npc;
            this.editDialogueId = npc.getDialogueId();
            this.editCutsceneId = npc.getCutsceneId();
        }

        @Override
        public void render(
            DrawContext context,
            int mouseX, int mouseY, float delta
        ) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer font = client.textRenderer;
            int w = this.width;
            int h = this.height;

            context.fill(0, 0, w, h, 0x99000000);

            int panelW = 350;
            int panelH = 280;
            int panelX = (w - panelW) / 2;
            int panelY = (h - panelH) / 2;

            // Фон
            context.fill(panelX, panelY,
                panelX + panelW, panelY + panelH, 0xFF0D0D0D);

            // Рамка
            context.fill(panelX - 1, panelY - 1,
                panelX + panelW + 1, panelY,
                0xFF444444);

            // Заголовок
            context.fill(panelX, panelY,
                panelX + panelW, panelY + 30, 0xFF111111);

            context.drawText(font,
                Text.literal("§7§lРЕДАКТОР NPC — §f" +
                    npc.getNpcName()),
                panelX + 15, panelY + 10,
                0xFF888888, false
            );

            // Тип NPC
            context.drawText(font,
                Text.literal("§8Тип: §7" + npc.getNpcType()),
                panelX + 15, panelY + 45,
                0xFF555555, false
            );

            // ID диалога
            context.drawText(font,
                Text.literal("§8Диалог ID:"),
                panelX + 15, panelY + 70,
                0xFF555555, false
            );

            // Поле ввода диалога
            context.fill(panelX + 15, panelY + 83,
                panelX + panelW - 15, panelY + 100,
                0xFF1A1A1A);
            context.fill(panelX + 15, panelY + 83,
                panelX + panelW - 15, panelY + 84,
                editingDialogue ? 0xFF888888 : 0xFF333333);

            context.drawText(font,
                Text.literal("§7" + editDialogueId +
                    (editingDialogue ? "§f|" : "")),
                panelX + 20, panelY + 88,
                0xFFAAAAAA, false
            );

            // ID катсцены
            context.drawText(font,
                Text.literal("§8Катсцена ID:"),
                panelX + 15, panelY + 115,
                0xFF555555, false
            );

            context.fill(panelX + 15, panelY + 128,
                panelX + panelW - 15, panelY + 145,
                0xFF1A1A1A);

            context.drawText(font,
                Text.literal("§7" + editCutsceneId),
                panelX + 20, panelY + 133,
                0xFFAAAAAA, false
            );

            // Настройки
            context.drawText(font,
                Text.literal(
                    "§8Смотреть на игрока: " +
                    (npc.isLookAtPlayer() ? "§a✓" : "§c✗")
                ),
                panelX + 15, panelY + 160,
                0xFF555555, false
            );

            context.drawText(font,
                Text.literal(
                    "§8Интерактивный: " +
                    (npc.isInteractable() ? "§a✓" : "§c✗")
                ),
                panelX + 15, panelY + 175,
                0xFF555555, false
            );

            // Кнопка сохранить
            boolean savHov =
                mouseX >= panelX + 15 &&
                mouseX < panelX + 160 &&
                mouseY >= panelY + 210 &&
                mouseY < panelY + 235;

            context.fill(
                panelX + 15, panelY + 210,
                panelX + 160, panelY + 235,
                savHov ? 0xFF1A2A1A : 0xFF111A11
            );
            context.drawText(font,
                Text.literal("§a✓ Сохранить"),
                panelX + 40, panelY + 219,
                0xFF55AA55, false
            );

            // Кнопка закрыть
            boolean clsHov =
                mouseX >= panelX + 185 &&
                mouseX < panelX + panelW - 15 &&
                mouseY >= panelY + 210 &&
                mouseY < panelY + 235;

            context.fill(
                panelX + 185, panelY + 210,
                panelX + panelW - 15, panelY + 235,
                clsHov ? 0xFF2A1A1A : 0xFF1A1111
            );
            context.drawText(font,
                Text.literal("§c✕ Закрыть"),
                panelX + 210, panelY + 219,
                0xFFAA5555, false
            );

            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean mouseClicked(
            double mouseX, double mouseY, int button
        ) {
            int w = this.width;
            int h = this.height;
            int panelW = 350;
            int panelH = 280;
            int panelX = (w - panelW) / 2;
            int panelY = (h - panelH) / 2;

            // Клик по полю ввода диалога
            if (mouseX >= panelX + 15 && mouseX < panelX + panelW - 15 &&
                mouseY >= panelY + 83 && mouseY < panelY + 100) {
                editingDialogue = true;
                return true;
            }

            // Кнопка сохранить
            if (mouseX >= panelX + 15 && mouseX < panelX + 160 &&
                mouseY >= panelY + 210 && mouseY < panelY + 235) {
                saveNpc();
                return true;
            }

            // Кнопка закрыть
            if (mouseX >= panelX + 185 && mouseX < panelX + panelW - 15 &&
                mouseY >= panelY + 210 && mouseY < panelY + 235) {
                close();
                return true;
            }

            editingDialogue = false;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean charTyped(char chr, int modifiers) {
            if (editingDialogue) {
                editDialogueId += chr;
                return true;
            }
            return super.charTyped(chr, modifiers);
        }

        @Override
        public boolean keyPressed(
            int keyCode, int scanCode, int modifiers
        ) {
            // Backspace
            if (editingDialogue && keyCode == 259) {
                if (!editDialogueId.isEmpty()) {
                    editDialogueId = editDialogueId
                        .substring(0, editDialogueId.length() - 1);
                }
                return true;
            }
            // Enter — сохранить
            if (keyCode == 257) {
                editingDialogue = false;
                return true;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        private void saveNpc() {
            // Отправляем пакет серверу с новыми данными NPC
            // Через NetworkHandler
            close();
        }

        @Override
        public boolean shouldPause() { return false; }
    }
}
