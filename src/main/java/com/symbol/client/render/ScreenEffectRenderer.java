package com.symbol.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class ScreenEffectRenderer {

    // ============================================================
    // СУБТИТРЫ
    // ============================================================

    private static String currentSubtitle = null;
    private static float subtitleAlpha = 0f;
    private static boolean showingSubtitle = false;

    public static void showSubtitle(String text) {
        currentSubtitle = text;
        showingSubtitle = true;
    }

    public static void hideSubtitle() {
        showingSubtitle = false;
    }

    // ============================================================
    // ТЕКСТ НА ЭКРАНЕ
    // ============================================================

    private static String currentText = null;
    private static float textAlpha = 0f;
    private static boolean showingText = false;

    public static void showText(String text) {
        currentText = text;
        showingText = true;
        textAlpha = 0f;
    }

    public static void hideText() {
        showingText = false;
    }

    // ============================================================
    // ЗАТЕМНЕНИЕ
    // ============================================================

    private static float fadeAlpha = 0f;
    private static boolean fadingToBlack = false;
    private static boolean fadingFromBlack = false;
    private static int fadeDuration = 40;
    private static int fadeTimer = 0;

    public static void fadeToBlack(int durationTicks) {
        fadingToBlack = true;
        fadingFromBlack = false;
        fadeDuration = durationTicks;
        fadeTimer = 0;
    }

    public static void fadeFromBlack(int durationTicks) {
        fadingFromBlack = true;
        fadingToBlack = false;
        fadeDuration = durationTicks;
        fadeTimer = 0;
        fadeAlpha = 1f;
    }

    // ============================================================
    // ВСПЫШКА
    // ============================================================

    private static float flashAlpha = 0f;
    private static boolean flashing = false;

    public static void flash() {
        flashAlpha = 1f;
        flashing = true;
    }

    // ============================================================
    // ТИК
    // ============================================================

    public static void tick() {
        // Субтитры — плавное появление
        if (showingSubtitle && subtitleAlpha < 1f) {
            subtitleAlpha = Math.min(subtitleAlpha + 0.05f, 1f);
        } else if (!showingSubtitle && subtitleAlpha > 0f) {
            subtitleAlpha = Math.max(subtitleAlpha - 0.05f, 0f);
        }

        // Текст — плавное появление
        if (showingText && textAlpha < 1f) {
            textAlpha = Math.min(textAlpha + 0.03f, 1f);
        } else if (!showingText && textAlpha > 0f) {
            textAlpha = Math.max(textAlpha - 0.03f, 0f);
        }

        // Затемнение к чёрному
        if (fadingToBlack) {
            fadeTimer++;
            fadeAlpha = Math.min((float)fadeTimer / fadeDuration, 1f);
            if (fadeTimer >= fadeDuration) {
                fadingToBlack = false;
                fadeAlpha = 1f;
            }
        }

        // Появление из чёрного
        if (fadingFromBlack) {
            fadeTimer++;
            fadeAlpha = Math.max(1f - (float)fadeTimer / fadeDuration, 0f);
            if (fadeTimer >= fadeDuration) {
                fadingFromBlack = false;
                fadeAlpha = 0f;
            }
        }

        // Вспышка — быстро гаснет
        if (flashing) {
            flashAlpha -= 0.08f;
            if (flashAlpha <= 0f) {
                flashAlpha = 0f;
                flashing = false;
            }
        }
    }

    // ============================================================
    // РЕНДЕРИНГ
    // ============================================================

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Затемнение
        if (fadeAlpha > 0f) {
            int alpha = (int)(fadeAlpha * 255);
            context.fill(
                0, 0,
                screenWidth, screenHeight,
                (alpha << 24)
            );
        }

        // Белая вспышка
        if (flashAlpha > 0f) {
            int alpha = (int)(flashAlpha * 255);
            context.fill(
                0, 0,
                screenWidth, screenHeight,
                (alpha << 24) | 0x00FFFFFF
            );
        }

        // Субтитры — снизу экрана над полосой
        if (subtitleAlpha > 0f && currentSubtitle != null) {
            renderSubtitle(context, client, screenWidth, screenHeight);
        }

        // Текст — по центру экрана
        if (textAlpha > 0f && currentText != null) {
            renderCenteredText(context, client, screenWidth, screenHeight);
        }
    }

    private static void renderSubtitle(
        DrawContext context,
        MinecraftClient client,
        int screenWidth,
        int screenHeight
    ) {
        TextRenderer font = client.textRenderer;
        int letterboxHeight = (int)(screenHeight *
            LetterboxRenderer.isVisible() ? 0.12f : 0f);

        // Позиция субтитров
        int y = screenHeight - letterboxHeight - 30;

        // Фон субтитров
        int textWidth = font.getWidth(currentSubtitle);
        int x = (screenWidth - textWidth) / 2;

        int bgAlpha = (int)(subtitleAlpha * 140);
        context.fill(
            x - 4, y - 2,
            x + textWidth + 4, y + font.fontHeight + 2,
            (bgAlpha << 24)
        );

        // Текст субтитров
        int textAlphaInt = (int)(subtitleAlpha * 255);
        context.drawText(
            font,
            Text.literal(currentSubtitle),
            x, y,
            (textAlphaInt << 24) | 0x00FFFFFF,
            false
        );
    }

    private static void renderCenteredText(
        DrawContext context,
        MinecraftClient client,
        int screenWidth,
        int screenHeight
    ) {
        TextRenderer font = client.textRenderer;
        String[] lines = currentText.split("\n");

        int totalHeight = lines.length * (font.fontHeight + 4);
        int startY = (screenHeight - totalHeight) / 2;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int textWidth = font.getWidth(line);
            int x = (screenWidth - textWidth) / 2;
            int y = startY + i * (font.fontHeight + 4);

            int alphaInt = (int)(textAlpha * 255);
            context.drawText(
                font,
                Text.literal(line),
                x, y,
                (alphaInt << 24) | 0x00FFFFFF,
                false
            );
        }
    }

    public static void clearAll() {
        showingSubtitle = false;
        showingText = false;
        fadingToBlack = false;
        fadingFromBlack = false;
        flashing = false;
        fadeAlpha = 0f;
        flashAlpha = 0f;
        subtitleAlpha = 0f;
        textAlpha = 0f;
    }
}
