package com.symbol.client.gui;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

import com.symbol.client.render.CutsceneRenderer;
import com.symbol.client.render.LetterboxRenderer;
import com.symbol.client.render.ScreenEffectRenderer;
import com.symbol.client.render.ParanoiaRenderer;

public class HudRenderer {

    // Уровень паранойи для отображения
    private static int paranoiaLevel = 0;

    // Показывать ли HUD паранойи
    private static boolean showParanoiaHud = false;

    public static void init() {
        HudRenderCallback.EVENT.register(
            HudRenderer::render
        );
    }

    // ============================================================
    // ГЛАВНЫЙ РЕНДЕР
    // ============================================================

    private static void render(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // 1. Эффекты паранойи (под всем остальным)
        ParanoiaRenderer.render(context);

        // 2. Затемнение и вспышки
        ScreenEffectRenderer.render(context);

        // 3. Чёрные полосы (поверх мира, под интерфейсом)
        LetterboxRenderer.render(context);

        // 4. Субтитры и текст
        // (рендерятся внутри ScreenEffectRenderer)

        // 5. HUD паранойи (если включён)
        if (showParanoiaHud && paranoiaLevel > 0) {
            renderParanoiaIndicator(context, client);
        }

        // 6. Тик анимаций
        LetterboxRenderer.tick();
        ScreenEffectRenderer.tick();
        ParanoiaRenderer.tick();
        CutsceneRenderer.tick();
    }

    // ============================================================
    // ИНДИКАТОР ПАРАНОЙИ
    // ============================================================

    private static void renderParanoiaIndicator(
        DrawContext context,
        MinecraftClient client
    ) {
        TextRenderer font = client.textRenderer;
        int w = client.getWindow().getScaledWidth();

        int barW = 80;
        int barH = 4;
        int barX = w - barW - 10;
        int barY = 10;

        // Фон полосы
        context.fill(barX, barY,
            barX + barW, barY + barH, 0x88000000);

        // Заполнение
        int fillW = (int)(barW * (paranoiaLevel / 10f));
        int fillColor = paranoiaLevel >= 8 ? 0xFFAA3333 :
            paranoiaLevel >= 5 ? 0xFFAA8833 : 0xFF338833;

        context.fill(barX, barY,
            barX + fillW, barY + barH, fillColor);

        // Текст
        context.drawText(font,
            Text.literal("§8Паранойя"),
            barX, barY - 10,
            0xFF444444, false
        );
    }

    public static void setParanoiaLevel(int level) {
        paranoiaLevel = level;
    }

    public static void setShowParanoiaHud(boolean show) {
        showParanoiaHud = show;
    }
}
