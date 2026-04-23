package com.symbol.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import com.symbol.system.EvidenceSystem;
import com.symbol.registry.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class DiaryScreen extends Screen {

    // ============================================================
    // УЛИКИ
    // ============================================================

    // Все улики на клиенте
    private static final List<EvidenceSystem.EvidenceData>
        CLIENT_EVIDENCE = new ArrayList<>();

    // Выбранная улика
    private int selectedIndex = -1;

    // Текущая вкладка
    private Tab currentTab = Tab.ALL;

    // Анимация
    private float openAnimation = 0f;
    private int scrollOffset = 0;

    // Максимум улик на экране
    private static final int ITEMS_PER_PAGE = 8;

    // ============================================================
    // ДОБАВЛЕНИЕ УЛИК (вызывается из сети)
    // ============================================================

    public static void addEvidence(EvidenceSystem.EvidenceData data) {
        // Не добавлять дубликаты
        boolean exists = CLIENT_EVIDENCE.stream()
            .anyMatch(e -> e.id.equals(data.id));
        if (!exists) {
            CLIENT_EVIDENCE.add(data);
        }
    }

    // Открыть дневник с конкретной уликой
    public static void open(String evidenceId) {
        MinecraftClient client = MinecraftClient.getInstance();
        DiaryScreen screen = new DiaryScreen();

        // Находим индекс улики
        for (int i = 0; i < CLIENT_EVIDENCE.size(); i++) {
            if (CLIENT_EVIDENCE.get(i).id.equals(evidenceId)) {
                screen.selectedIndex = i;
                break;
            }
        }

        client.setScreen(screen);

        // Звук открытия
        client.player.playSound(
            ModSounds.GUI_DIARY_OPEN, 0.8f, 1.0f
        );
    }

    // ============================================================
    // КОНСТРУКТОР
    // ============================================================

    public DiaryScreen() {
        super(Text.literal("Дневник"));
    }

    // ============================================================
    // ТИК
    // ============================================================

    @Override
    public void tick() {
        // Анимация открытия
        if (openAnimation < 1f) {
            openAnimation = Math.min(openAnimation + 0.08f, 1f);
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
        context.fill(0, 0, w, h, 0xBB000000);

        // Анимация масштаба
        float scale = 0.8f + openAnimation * 0.2f;
        int panelW = (int)(700 * scale);
        int panelH = (int)(500 * scale);
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        // Фон дневника — тёмная бумага
        context.fill(panelX, panelY,
            panelX + panelW, panelY + panelH,
            0xFF0D0D0D);

        // Рамка
        renderBorder(context, panelX, panelY, panelW, panelH);

        // Заголовок
        renderHeader(context, font, panelX, panelY, panelW);

        // Вкладки
        renderTabs(context, font, panelX, panelY, panelW, mouseX, mouseY);

        // Список улик (левая часть)
        renderEvidenceList(context, font,
            panelX, panelY, panelW, panelH,
            mouseX, mouseY);

        // Детали улики (правая часть)
        if (selectedIndex >= 0 &&
            selectedIndex < CLIENT_EVIDENCE.size()
        ) {
            renderEvidenceDetail(context, font,
                panelX, panelY, panelW, panelH);
        } else {
            renderEmptyDetail(context, font,
                panelX, panelY, panelW, panelH);
        }

        // Кнопка закрыть
        renderCloseButton(context, font,
            panelX + panelW - 30, panelY + 8,
            mouseX, mouseY);

        // Количество улик
        context.drawText(font,
            Text.literal("§8Улики: §7" + CLIENT_EVIDENCE.size()),
            panelX + 10, panelY + panelH - 18,
            0xFF666666, false
        );
    }

    private void renderBorder(
        DrawContext context,
        int x, int y, int w, int h
    ) {
        int borderColor = 0xFF333333;
        int accentColor = 0xFF555555;

        // Внешняя рамка
        context.fill(x - 1, y - 1, x + w + 1, y, accentColor);
        context.fill(x - 1, y + h, x + w + 1, y + h + 1, accentColor);
        context.fill(x - 1, y, x, y + h, accentColor);
        context.fill(x + w, y, x + w + 1, y + h, accentColor);
    }

    private void renderHeader(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW
    ) {
        // Фон заголовка
        context.fill(
            panelX, panelY,
            panelX + panelW, panelY + 30,
            0xFF111111
        );

        // Разделитель
        context.fill(
            panelX, panelY + 30,
            panelX + panelW, panelY + 31,
            0xFF333333
        );

        // Символ глаза слева
        context.drawText(font,
            Text.literal("§8◉"),
            panelX + 12, panelY + 10,
            0xFF555555, false
        );

        // Заголовок
        String title = "ДНЕВНИК УЛИК";
        int titleW = font.getWidth(title);
        context.drawText(font,
            Text.literal("§7§l" + title),
            panelX + (panelW - titleW) / 2,
            panelY + 11,
            0xFF999999, false
        );
    }

    private void renderTabs(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW,
        int mouseX, int mouseY
    ) {
        Tab[] tabs = Tab.values();
        int tabW = 80;
        int tabY = panelY + 31;

        for (int i = 0; i < tabs.length; i++) {
            Tab tab = tabs[i];
            int tabX = panelX + i * tabW;

            boolean selected = tab == currentTab;
            boolean hovered =
                mouseX >= tabX && mouseX < tabX + tabW &&
                mouseY >= tabY && mouseY < tabY + 20;

            int bgColor = selected ? 0xFF1A1A1A :
                hovered ? 0xFF161616 : 0xFF111111;

            context.fill(tabX, tabY,
                tabX + tabW, tabY + 20, bgColor);

            // Нижний акцент для выбранной
            if (selected) {
                context.fill(tabX, tabY + 18,
                    tabX + tabW, tabY + 20, 0xFF666666);
            }

            String label = tab.label;
            int lx = tabX + (tabW - font.getWidth(label)) / 2;
            context.drawText(font,
                Text.literal(selected ? "§f" + label : "§8" + label),
                lx, tabY + 6,
                0xFFFFFFFF, false
            );
        }
    }

    private void renderEvidenceList(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW, int panelH,
        int mouseX, int mouseY
    ) {
        int listX = panelX;
        int listY = panelY + 52;
        int listW = 220;
        int listH = panelH - 70;

        // Фон списка
        context.fill(listX, listY,
            listX + listW, listY + listH, 0xFF0A0A0A);

        // Разделитель справа
        context.fill(listX + listW, listY,
            listX + listW + 1, listY + listH, 0xFF222222);

        // Фильтруем улики по вкладке
        List<EvidenceSystem.EvidenceData> filtered =
            getFilteredEvidence();

        int itemH = 36;
        int visibleStart = scrollOffset;
        int visibleEnd = Math.min(
            visibleStart + ITEMS_PER_PAGE,
            filtered.size()
        );

        for (int i = visibleStart; i < visibleEnd; i++) {
            EvidenceSystem.EvidenceData ev = filtered.get(i);
            int itemY = listY + (i - visibleStart) * itemH;
            int realIndex = CLIENT_EVIDENCE.indexOf(ev);

            boolean selected = realIndex == selectedIndex;
            boolean hovered =
                mouseX >= listX && mouseX < listX + listW &&
                mouseY >= itemY && mouseY < itemY + itemH;

            // Фон элемента
            int bg = selected ? 0xFF1E1E1E :
                hovered ? 0xFF161616 : 0xFF0A0A0A;
            context.fill(listX, itemY,
                listX + listW, itemY + itemH, bg);

            // Разделитель
            context.fill(listX, itemY + itemH - 1,
                listX + listW, itemY + itemH, 0xFF1A1A1A);

            // Индикатор категории
            int catColor = getCategoryColor(ev.category);
            context.fill(listX, itemY,
                listX + 3, itemY + itemH, catColor);

            // Название улики
            context.drawText(font,
                Text.literal("§f" + ev.title),
                listX + 10, itemY + 8,
                0xFFCCCCCC, false
            );

            // Категория
            context.drawText(font,
                Text.literal("§8" + ev.category.name()),
                listX + 10, itemY + 22,
                0xFF555555, false
            );
        }

        // Пустой список
        if (filtered.isEmpty()) {
            String empty = "Нет улик";
            int ex = listX + (listW - font.getWidth(empty)) / 2;
            int ey = listY + listH / 2;
            context.drawText(font,
                Text.literal("§8" + empty),
                ex, ey, 0xFF444444, false
            );
        }
    }

    private void renderEvidenceDetail(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW, int panelH
    ) {
        EvidenceSystem.EvidenceData ev =
            CLIENT_EVIDENCE.get(selectedIndex);

        int detailX = panelX + 222;
        int detailY = panelY + 52;
        int detailW = panelW - 222;
        int detailH = panelH - 70;

        // Заголовок улики
        context.fill(detailX, detailY,
            detailX + detailW, detailY + 35, 0xFF0F0F0F);

        context.drawText(font,
            Text.literal("§f§l" + ev.title),
            detailX + 15, detailY + 10,
            0xFFFFFFFF, false
        );

        // Категория с цветом
        int catColor = getCategoryColor(ev.category);
        String catName = getCategoryName(ev.category);
        context.drawText(font,
            Text.literal("§8" + catName),
            detailX + 15, detailY + 23,
            catColor, false
        );

        // Разделитель
        context.fill(detailX, detailY + 35,
            detailX + detailW, detailY + 36, 0xFF222222);

        // Описание улики
        int descY = detailY + 50;
        List<String> descLines = wrapText(
            ev.description, font, detailW - 30
        );

        for (int i = 0; i < descLines.size(); i++) {
            context.drawText(font,
                Text.literal("§7" + descLines.get(i)),
                detailX + 15,
                descY + i * (font.fontHeight + 4),
                0xFFAAAAAA, false
            );
        }

        // Подсказка/связь
        if (ev.hint != null && !ev.hint.isEmpty()) {
            int hintY = detailY + detailH - 40;

            context.fill(detailX, hintY,
                detailX + detailW, hintY + 30, 0xFF0C0C0C);

            context.fill(detailX, hintY,
                detailX + detailW, hintY + 1, 0xFF222222);

            context.drawText(font,
                Text.literal(ev.hint),
                detailX + 15, hintY + 10,
                0xFF777777, false
            );
        }
    }

    private void renderEmptyDetail(
        DrawContext context, TextRenderer font,
        int panelX, int panelY, int panelW, int panelH
    ) {
        int detailX = panelX + 222;
        int detailY = panelY + 52;
        int detailW = panelW - 222;
        int detailH = panelH - 70;

        String hint = "Выберите улику";
        int hx = detailX + (detailW - font.getWidth(hint)) / 2;
        int hy = detailY + detailH / 2;

        context.drawText(font,
            Text.literal("§8" + hint),
            hx, hy, 0xFF333333, false
        );

        // Символ глаза в центре
        String eye = "◉";
        int ex = detailX + (detailW - font.getWidth(eye)) / 2;
        context.drawText(font,
            Text.literal("§8" + eye),
            ex, hy - 20, 0xFF222222, false
        );
    }

    private void renderCloseButton(
        DrawContext context, TextRenderer font,
        int x, int y, int mouseX, int mouseY
    ) {
        boolean hovered =
            mouseX >= x && mouseX < x + 22 &&
            mouseY >= y && mouseY < y + 14;

        context.fill(x, y, x + 22, y + 14,
            hovered ? 0xFF2A1010 : 0xFF1A1010);

        context.drawText(font,
            Text.literal("§c✕"),
            x + 7, y + 3,
            0xFFAA4444, false
        );
    }

    // ============================================================
    // ВВОД
    // ============================================================

    @Override
    public boolean mouseClicked(
        double mouseX, double mouseY, int button
    ) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer font = client.textRenderer;
        int w = this.width;
        int h = this.height;

        int panelW = 700;
        int panelH = 500;
        int panelX = (w - panelW) / 2;
        int panelY = (h - panelH) / 2;

        // Кнопка закрыть
        int closeX = panelX + panelW - 30;
        int closeY = panelY + 8;
        if (mouseX >= closeX && mouseX < closeX + 22 &&
            mouseY >= closeY && mouseY < closeY + 14) {
            onClose();
            return true;
        }

        // Вкладки
        Tab[] tabs = Tab.values();
        int tabY = panelY + 31;
        for (int i = 0; i < tabs.length; i++) {
            int tabX = panelX + i * 80;
            if (mouseX >= tabX && mouseX < tabX + 80 &&
                mouseY >= tabY && mouseY < tabY + 20) {
                currentTab = tabs[i];
                selectedIndex = -1;
                scrollOffset = 0;
                return true;
            }
        }

        // Клик по улике в списке
        int listX = panelX;
        int listY = panelY + 52;
        int listW = 220;
        int itemH = 36;

        List<EvidenceSystem.EvidenceData> filtered = getFilteredEvidence();

        for (int i = 0; i < Math.min(ITEMS_PER_PAGE, filtered.size()); i++) {
            int itemY = listY + i * itemH;
            if (mouseX >= listX && mouseX < listX + listW &&
                mouseY >= itemY && mouseY < itemY + itemH) {
                selectedIndex = CLIENT_EVIDENCE.indexOf(
                    filtered.get(i + scrollOffset)
                );
                // Звук
                client.player.playSound(
                    ModSounds.GUI_EVIDENCE_OPEN, 0.6f, 1.0f
                );
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(
        double mouseX, double mouseY, double amount
    ) {
        List<EvidenceSystem.EvidenceData> filtered = getFilteredEvidence();
        int maxScroll = Math.max(0, filtered.size() - ITEMS_PER_PAGE);

        scrollOffset = (int)Math.max(0,
            Math.min(scrollOffset - amount, maxScroll)
        );
        return true;
    }

    @Override
    public void close() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.playSound(
                ModSounds.GUI_DIARY_CLOSE, 0.8f, 1.0f
            );
        }
        super.close();
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private List<EvidenceSystem.EvidenceData> getFilteredEvidence() {
        if (currentTab == Tab.ALL) return CLIENT_EVIDENCE;

        List<EvidenceSystem.EvidenceData> filtered = new ArrayList<>();
        for (var ev : CLIENT_EVIDENCE) {
            boolean match = switch (currentTab) {
                case DOCUMENTS ->
                    ev.category ==
                        EvidenceSystem.EvidenceCategory.DOCUMENTS;
                case PHYSICAL ->
                    ev.category ==
                        EvidenceSystem.EvidenceCategory.PHYSICAL;
                case PEOPLE ->
                    ev.category ==
                        EvidenceSystem.EvidenceCategory.PEOPLE;
                case SYMBOLS ->
                    ev.category ==
                        EvidenceSystem.EvidenceCategory.SYMBOL;
                default -> true;
            };
            if (match) filtered.add(ev);
        }
        return filtered;
    }

    private int getCategoryColor(EvidenceSystem.EvidenceCategory cat) {
        return switch (cat) {
            case DOCUMENTS -> 0xFF5577AA;
            case PHYSICAL  -> 0xFF77AA55;
            case PEOPLE    -> 0xFFAA7755;
            case SYMBOL    -> 0xFF885599;
        };
    }

    private String getCategoryName(EvidenceSystem.EvidenceCategory cat) {
        return switch (cat) {
            case DOCUMENTS -> "Документ";
            case PHYSICAL  -> "Физическая улика";
            case PEOPLE    -> "Свидетель";
            case SYMBOL    -> "Символ";
        };
    }

    private List<String> wrapText(
        String text, TextRenderer font, int maxWidth
    ) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder current = new StringBuilder();

        for (String word : words) {
            String test = current.isEmpty()
                ? word : current + " " + word;
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
        if (!current.isEmpty()) lines.add(current.toString());
        return lines;
    }

    // ============================================================
    // ВКЛАДКИ
    // ============================================================

    public enum Tab {
        ALL("Все"),
        DOCUMENTS("Доки"),
        PHYSICAL("Улики"),
        PEOPLE("Люди"),
        SYMBOLS("Символы");

        public final String label;
        Tab(String label) { this.label = label; }
    }
}
