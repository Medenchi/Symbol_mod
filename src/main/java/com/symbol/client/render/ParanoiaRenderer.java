package com.symbol.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ParanoiaRenderer {

    // Текущий уровень паранойи (0-10)
    private static int paranoiaLevel = 0;

    // Активный эффект
    private static String activeEffect = null;
    private static int effectDuration = 0;
    private static int effectTimer = 0;

    // Статические помехи
    private static float staticIntensity = 0f;
    private static float[] staticLines = new float[20];

    // Виньетка (тёмные края)
    private static float vignetteIntensity = 0f;

    // Зернистость
    private static float grainIntensity = 0f;

    private static final Random RANDOM = new Random();

    // ============================================================
    // УПРАВЛЕНИЕ
    // ============================================================

    public static void setLevel(int level) {
        paranoiaLevel = MathHelper.clamp(level, 0, 10);
        updatePassiveEffects();
    }

    private static void updatePassiveEffects() {
        // Чем выше паранойя — тем сильнее пассивные эффекты
        float t = paranoiaLevel / 10f;

        // Виньетка нарастает
        vignetteIntensity = t * 0.6f;

        // Зернистость нарастает
        grainIntensity = t * 0.3f;

        // Статика появляется при уровне 6+
        staticIntensity = paranoiaLevel >= 6
            ? (paranoiaLevel - 6) / 4f * 0.4f
            : 0f;
    }

    public static void playEffect(String effectType, int duration) {
        activeEffect = effectType;
        effectDuration = duration;
        effectTimer = 0;
    }

    // ============================================================
    // ТИК
    // ============================================================

    public static void tick() {
        // Тикаем активный эффект
        if (activeEffect != null) {
            effectTimer++;
            if (effectTimer >= effectDuration) {
                activeEffect = null;
            }
        }

        // Случайные линии статики
        if (staticIntensity > 0f) {
            for (int i = 0; i < staticLines.length; i++) {
                if (RANDOM.nextFloat() < staticIntensity * 0.1f) {
                    staticLines[i] = RANDOM.nextFloat();
                } else {
                    staticLines[i] = -1f; // Невидима
                }
            }
        }
    }

    // ============================================================
    // РЕНДЕРИНГ
    // ============================================================

    public static void render(DrawContext context) {
        if (paranoiaLevel == 0 && activeEffect == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        // Виньетка — тёмные края
        if (vignetteIntensity > 0f) {
            renderVignette(context, w, h);
        }

        // Статические помехи
        if (staticIntensity > 0f) {
            renderStatic(context, w, h);
        }

        // Зернистость
        if (grainIntensity > 0f) {
            renderGrain(context, w, h);
        }

        // Активные эффекты
        if (activeEffect != null) {
            renderActiveEffect(context, w, h);
        }
    }

    private static void renderVignette(
        DrawContext context, int w, int h
    ) {
        int alpha = (int)(vignetteIntensity * 200);
        int color = (alpha << 24);

        // Четыре края
        int edgeWidth = (int)(w * 0.15f);
        int edgeHeight = (int)(h * 0.15f);

        // Левый край
        context.fill(0, 0, edgeWidth, h, color);
        // Правый край
        context.fill(w - edgeWidth, 0, w, h, color);
        // Верхний край
        context.fill(0, 0, w, edgeHeight, color);
        // Нижний край
        context.fill(0, h - edgeHeight, w, h, color);
    }

    private static void renderStatic(
        DrawContext context, int w, int h
    ) {
        for (float lineY : staticLines) {
            if (lineY < 0) continue;

            int y = (int)(lineY * h);
            int lineHeight = 1 + RANDOM.nextInt(3);
            int alpha = (int)(staticIntensity * 120);

            context.fill(
                0, y,
                w, y + lineHeight,
                (alpha << 24) | 0x00FFFFFF
            );
        }
    }

    private static void renderGrain(
        DrawContext context, int w, int h
    ) {
        // Зернистость — случайные пиксели
        int grainCount = (int)(grainIntensity * 500);
        for (int i = 0; i < grainCount; i++) {
            int x = RANDOM.nextInt(w);
            int y = RANDOM.nextInt(h);
            int alpha = (int)(RANDOM.nextFloat() * grainIntensity * 100);
            int brightness = RANDOM.nextInt(256);

            context.fill(
                x, y, x + 1, y + 1,
                (alpha << 24) |
                (brightness << 16) |
                (brightness << 8) |
                brightness
            );
        }
    }

    private static void renderActiveEffect(
        DrawContext context, int w, int h
    ) {
        float t = (float)effectTimer / effectDuration;

        switch (activeEffect) {
            case "static" -> {
                // Усиленная статика на короткое время
                int alpha = (int)((1f - t) * 80);
                renderHorizontalLines(context, w, h, alpha);
            }

            case "heavy_distortion" -> {
                // Сильное искажение
                int alpha = (int)((1f - t) * 150);
                context.fill(0, 0, w, h,
                    (alpha << 24) | 0x00111111);
                renderHorizontalLines(context, w, h, alpha / 2);
            }

            case "max_paranoia" -> {
                // Максимальная паранойя — всё вместе
                float pulse = (float)Math.sin(
                    effectTimer * 0.3f
                ) * 0.5f + 0.5f;
                int alpha = (int)(pulse * 100);
                context.fill(0, 0, w, h,
                    (alpha << 24) | 0x00000011);
            }
        }
    }

    private static void renderHorizontalLines(
        DrawContext context, int w, int h, int alpha
    ) {
        for (int y = 0; y < h; y += 4) {
            if (RANDOM.nextFloat() < 0.3f) {
                context.fill(0, y, w, y + 1,
                    (alpha << 24) | 0x00FFFFFF);
            }
        }
    }
}
