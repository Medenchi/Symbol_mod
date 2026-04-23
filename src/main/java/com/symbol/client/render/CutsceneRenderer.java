package com.symbol.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;

import com.symbol.system.CameraPath;

public class CutsceneRenderer {

    // ============================================================
    // СОСТОЯНИЕ
    // ============================================================

    private static boolean active = false;
    private static String currentCutsceneId = null;
    private static int totalDuration = 0;
    private static int currentTick = 0;
    private static CameraPath cameraPath = null;

    // Тряска камеры
    private static float shakeIntensity = 0f;
    private static int shakeDuration = 0;
    private static float shakeOffsetX = 0f;
    private static float shakeOffsetY = 0f;

    // ============================================================
    // УПРАВЛЕНИЕ
    // ============================================================

    public static void startCutscene(
        String id,
        int duration,
        CameraPath path
    ) {
        active = true;
        currentCutsceneId = id;
        totalDuration = duration;
        currentTick = 0;
        cameraPath = path;
    }

    public static void stopCutscene() {
        active = false;
        currentCutsceneId = null;
        cameraPath = null;
        shakeIntensity = 0f;
    }

    public static boolean isActive() {
        return active;
    }

    // ============================================================
    // ТРЯСКА КАМЕРЫ
    // ============================================================

    public static void startShake(String intensity) {
        switch (intensity) {
            case "light" -> {
                shakeIntensity = 0.5f;
                shakeDuration = 20;
            }
            case "medium" -> {
                shakeIntensity = 1.5f;
                shakeDuration = 30;
            }
            case "heavy" -> {
                shakeIntensity = 3.0f;
                shakeDuration = 40;
            }
            default -> {
                shakeIntensity = 1.0f;
                shakeDuration = 20;
            }
        }
    }

    // ============================================================
    // ТИК (каждый кадр)
    // ============================================================

    public static void tick() {
        if (!active) return;

        currentTick++;

        // Тряска
        if (shakeDuration > 0) {
            shakeDuration--;
            float decay = (float)shakeDuration / 40f;
            java.util.Random rng = new java.util.Random();
            shakeOffsetX = (float)(rng.nextGaussian()
                * shakeIntensity * decay);
            shakeOffsetY = (float)(rng.nextGaussian()
                * shakeIntensity * decay);
        } else {
            shakeOffsetX = 0f;
            shakeOffsetY = 0f;
        }

        // Обновляем эффекты экрана
        LetterboxRenderer.tick();
        ScreenEffectRenderer.tick();
    }

    // ============================================================
    // ПОЛУЧЕНИЕ СОСТОЯНИЯ КАМЕРЫ
    // ============================================================

    // Вызывается из Mixin для переопределения позиции камеры
    public static CameraPath.CameraState getCurrentCameraState() {
        if (!active || cameraPath == null) {
            return CameraPath.CameraState.DEFAULT;
        }

        float t = totalDuration > 0
            ? (float)currentTick / totalDuration
            : 0f;

        t = Math.min(t, 1f);

        CameraPath.CameraState state = cameraPath.getStateAtTime(t);

        // Применяем тряску
        if (shakeIntensity > 0f && shakeDuration > 0) {
            return new CameraPath.CameraState(
                state.position,
                state.yaw + shakeOffsetX,
                state.pitch + shakeOffsetY,
                state.roll
            );
        }

        return state;
    }

    public static float getCurrentYaw() {
        return getCurrentCameraState().yaw;
    }

    public static float getCurrentPitch() {
        return getCurrentCameraState().pitch;
    }

    public static Vec3d getCurrentPosition() {
        return getCurrentCameraState().position;
    }

    public static void init() {
        // Инициализация при запуске клиента
    }
}
