package com.symbol.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;

public class LetterboxRenderer {

    // Высота полосы (процент от экрана)
    private static final float TARGET_HEIGHT = 0.12f;

    // Текущая высота (анимируется)
    private static float currentHeight = 0f;

    // Целевая высота
    private static float targetHeight = 0f;

    // Скорость анимации
    private static final float ANIMATION_SPEED = 0.05f;

    // Видимы ли полосы
    private static boolean visible = false;

    // ============================================================
    // УПРАВЛЕНИЕ
    // ============================================================

    public static void show() {
        visible = true;
        targetHeight = TARGET_HEIGHT;
    }

    public static void hide() {
        visible = false;
        targetHeight = 0f;
    }

    public static boolean isVisible() {
        return currentHeight > 0.001f;
    }

    // ============================================================
    // ТИК (вызывается каждый кадр)
    // ============================================================

    public static void tick() {
        // Плавная анимация появления и исчезания
        if (currentHeight < targetHeight) {
            currentHeight = Math.min(
                currentHeight + ANIMATION_SPEED,
                targetHeight
            );
        } else if (currentHeight > targetHeight) {
            currentHeight = Math.max(
                currentHeight - ANIMATION_SPEED,
                targetHeight
            );
        }
    }

    // ============================================================
    // РЕНДЕРИНГ
    // ============================================================

    public static void render(DrawContext context) {
        if (currentHeight <= 0.001f) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Высота одной полосы в пикселях
        int barHeight = (int)(screenHeight * currentHeight);

        // Чёрный цвет — полностью непрозрачный
        int BLACK = 0xFF000000;

        // Верхняя полоса
        context.fill(
            0, 0,
            screenWidth, barHeight,
            BLACK
        );

        // Нижняя полоса
        context.fill(
            0, screenHeight - barHeight,
            screenWidth, screenHeight,
            BLACK
        );
    }
}
