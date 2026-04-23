package com.symbol.system;

import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;

public class CameraPath {

    // Список точек пути камеры
    private final List<CameraKeyframe> keyframes = new ArrayList<>();

    // ============================================================
    // ДОБАВЛЕНИЕ ТОЧЕК
    // ============================================================

    // Добавить точку пути
    // x, y, z — позиция камеры
    // yaw — поворот влево/вправо (-180 до 180)
    // pitch — наклон вверх/вниз (-90 до 90)
    // roll — крен камеры (обычно 0)
    // durationTicks — сколько тиков двигаться до этой точки
    // easing — тип плавности движения
    public CameraPath addKeyframe(
        double x, double y, double z,
        float yaw, float pitch, float roll,
        int durationTicks,
        EasingType easing
    ) {
        keyframes.add(new CameraKeyframe(
            new Vec3d(x, y, z),
            yaw, pitch, roll,
            durationTicks,
            easing
        ));
        return this; // для цепочки вызовов
    }

    // Упрощённая версия без крена и с линейной плавностью
    public CameraPath addKeyframe(
        double x, double y, double z,
        float yaw, float pitch,
        int durationTicks
    ) {
        return addKeyframe(x, y, z, yaw, pitch, 0f,
            durationTicks, EasingType.SMOOTH);
    }

    // ============================================================
    // ИНТЕРПОЛЯЦИЯ (плавное движение между точками)
    // ============================================================

    // Получить позицию и угол камеры в момент времени t (0.0 - 1.0)
    public CameraState getStateAtTime(float t) {
        if (keyframes.isEmpty()) return CameraState.DEFAULT;
        if (keyframes.size() == 1) {
            CameraKeyframe kf = keyframes.get(0);
            return new CameraState(
                kf.position, kf.yaw, kf.pitch, kf.roll
            );
        }

        // Считаем общее время
        int totalTicks = getTotalDuration();
        int targetTick = (int)(t * totalTicks);

        // Находим между какими точками мы сейчас
        int accumulated = 0;
        for (int i = 0; i < keyframes.size() - 1; i++) {
            CameraKeyframe current = keyframes.get(i);
            CameraKeyframe next = keyframes.get(i + 1);

            accumulated += next.durationTicks;

            if (targetTick <= accumulated) {
                // Мы между current и next
                int segmentStart = accumulated - next.durationTicks;
                int segmentTick = targetTick - segmentStart;
                float segmentT = (float)segmentTick / next.durationTicks;

                // Применяем easing
                segmentT = applyEasing(segmentT, next.easing);

                // Интерполируем
                return interpolate(current, next, segmentT);
            }
        }

        // Конец пути
        CameraKeyframe last = keyframes.get(keyframes.size() - 1);
        return new CameraState(
            last.position, last.yaw, last.pitch, last.roll
        );
    }

    private CameraState interpolate(
        CameraKeyframe from,
        CameraKeyframe to,
        float t
    ) {
        // Плавная интерполяция позиции
        Vec3d pos = new Vec3d(
            lerp(from.position.x, to.position.x, t),
            lerp(from.position.y, to.position.y, t),
            lerp(from.position.z, to.position.z, t)
        );

        // Плавная интерполяция углов
        float yaw = lerpAngle(from.yaw, to.yaw, t);
        float pitch = lerp(from.pitch, to.pitch, t);
        float roll = lerp(from.roll, to.roll, t);

        return new CameraState(pos, yaw, pitch, roll);
    }

    // ============================================================
    // EASING ФУНКЦИИ (типы плавности)
    // ============================================================

    private float applyEasing(float t, EasingType type) {
        return switch (type) {
            // Линейное — без плавности
            case LINEAR -> t;

            // Плавное начало и конец
            case SMOOTH -> t * t * (3 - 2 * t);

            // Плавное начало
            case EASE_IN -> t * t * t;

            // Плавный конец
            case EASE_OUT -> 1 - (float)Math.pow(1 - t, 3);

            // Кинематографическое — резкое начало плавный конец
            case CINEMATIC -> {
                if (t < 0.5f) {
                    yield 4 * t * t * t;
                } else {
                    yield 1 - (float)Math.pow(-2 * t + 2, 3) / 2;
                }
            }

            // Упругое — немного пружинит в конце
            case ELASTIC -> {
                float c4 = (float)(2 * Math.PI) / 3;
                if (t == 0) yield 0;
                if (t == 1) yield 1;
                yield (float)(Math.pow(2, -10 * t)
                    * Math.sin((t * 10 - 0.75f) * c4) + 1);
            }
        };
    }

    // ============================================================
    // МАТЕМАТИКА
    // ============================================================

    private float lerp(double a, double b, float t) {
        return (float)(a + (b - a) * t);
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    // Интерполяция угла с учётом перехода через 360
    private float lerpAngle(float a, float b, float t) {
        float diff = b - a;
        // Нормализуем разницу
        while (diff > 180) diff -= 360;
        while (diff < -180) diff += 360;
        return a + diff * t;
    }

    public int getTotalDuration() {
        return keyframes.stream()
            .mapToInt(kf -> kf.durationTicks)
            .sum();
    }

    public List<CameraKeyframe> getKeyframes() {
        return keyframes;
    }

    // ============================================================
    // ВНУТРЕННИЕ КЛАССЫ
    // ============================================================

    public static class CameraKeyframe {
        public final Vec3d position;
        public final float yaw;
        public final float pitch;
        public final float roll;
        public final int durationTicks;
        public final EasingType easing;

        public CameraKeyframe(
            Vec3d position,
            float yaw, float pitch, float roll,
            int durationTicks,
            EasingType easing
        ) {
            this.position = position;
            this.yaw = yaw;
            this.pitch = pitch;
            this.roll = roll;
            this.durationTicks = durationTicks;
            this.easing = easing;
        }
    }

    public static class CameraState {
        public static final CameraState DEFAULT =
            new CameraState(Vec3d.ZERO, 0, 0, 0);

        public final Vec3d position;
        public final float yaw;
        public final float pitch;
        public final float roll;

        public CameraState(
            Vec3d position,
            float yaw, float pitch, float roll
        ) {
            this.position = position;
            this.yaw = yaw;
            this.pitch = pitch;
            this.roll = roll;
        }
    }

    public enum EasingType {
        LINEAR,
        SMOOTH,
        EASE_IN,
        EASE_OUT,
        CINEMATIC,
        ELASTIC
    }
}
