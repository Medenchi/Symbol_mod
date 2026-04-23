package com.symbol.system;

import net.minecraft.sound.SoundEvent;
import java.util.ArrayList;
import java.util.List;

// Данные одной катсцены
public class CutsceneData {

    public final String id;
    public final String title;
    public final int totalDurationTicks;
    public final boolean hasLetterbox;
    public final boolean freezePlayer;
    public final List<CutsceneEvent> events;
    public final CameraPath cameraPath;
    public final SoundEvent music;
    public final String nextCutsceneId;
    public final String nextDialogueId;

    private CutsceneData(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.totalDurationTicks = builder.totalDurationTicks;
        this.hasLetterbox = builder.hasLetterbox;
        this.freezePlayer = builder.freezePlayer;
        this.events = builder.events;
        this.cameraPath = builder.cameraPath;
        this.music = builder.music;
        this.nextCutsceneId = builder.nextCutsceneId;
        this.nextDialogueId = builder.nextDialogueId;
    }

    // ============================================================
    // СОБЫТИЯ КАТСЦЕНЫ
    // ============================================================

    // Событие — что происходит в конкретный тик
    public static class CutsceneEvent {
        public final int tick;           // Когда срабатывает
        public final EventType type;     // Тип события
        public final String data;        // Данные события
        public final float volume;       // Громкость (для звуков)
        public final float pitch;        // Тон (для звуков)

        public CutsceneEvent(
            int tick,
            EventType type,
            String data,
            float volume,
            float pitch
        ) {
            this.tick = tick;
            this.type = type;
            this.data = data;
            this.volume = volume;
            this.pitch = pitch;
        }

        // Упрощённый конструктор
        public CutsceneEvent(int tick, EventType type, String data) {
            this(tick, type, data, 1.0f, 1.0f);
        }
    }

    public enum EventType {
        PLAY_SOUND,         // Воспроизвести звук
        PLAY_VOICE,         // Воспроизвести озвучку
        SHOW_SUBTITLE,      // Показать субтитры
        HIDE_SUBTITLE,      // Скрыть субтитры
        SHOW_TEXT,          // Показать текст на экране
        HIDE_TEXT,          // Скрыть текст
        PLAY_MUSIC,         // Запустить музыку
        STOP_MUSIC,         // Остановить музыку
        FADE_TO_BLACK,      // Затемнение
        FADE_FROM_BLACK,    // Появление из темноты
        SCREEN_FLASH,       // Белая вспышка
        START_DIALOGUE,     // Запустить диалог
        TRIGGER_ANIMATION,  // Запустить анимацию NPC
        CAMERA_SHAKE,       // Тряска камеры
        ADD_EVIDENCE,       // Добавить улику
        SET_TIME,           // Установить время суток
        SPAWN_NPC,          // Заспавнить NPC
        HIDE_NPC,           // Скрыть NPC
        END_CUTSCENE        // Завершить катсцену
    }

    // ============================================================
    // СТРОИТЕЛЬ
    // ============================================================

    public static class Builder {
        private final String id;
        private String title = "";
        private int totalDurationTicks = 0;
        private boolean hasLetterbox = true;
        private boolean freezePlayer = true;
        private final List<CutsceneEvent> events = new ArrayList<>();
        private CameraPath cameraPath = new CameraPath();
        private SoundEvent music = null;
        private String nextCutsceneId = null;
        private String nextDialogueId = null;

        public Builder(String id) {
            this.id = id;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder duration(int ticks) {
            this.totalDurationTicks = ticks;
            return this;
        }

        public Builder noLetterbox() {
            this.hasLetterbox = false;
            return this;
        }

        public Builder dontFreezePlayer() {
            this.freezePlayer = false;
            return this;
        }

        public Builder camera(CameraPath path) {
            this.cameraPath = path;
            return this;
        }

        public Builder music(SoundEvent music) {
            this.music = music;
            return this;
        }

        public Builder then(String nextCutsceneId) {
            this.nextCutsceneId = nextCutsceneId;
            return this;
        }

        public Builder thenDialogue(String nextDialogueId) {
            this.nextDialogueId = nextDialogueId;
            return this;
        }

        // Добавить событие
        public Builder event(int tick, EventType type, String data) {
            events.add(new CutsceneEvent(tick, type, data));
            return this;
        }

        public Builder event(
            int tick, EventType type,
            String data, float volume, float pitch
        ) {
            events.add(new CutsceneEvent(tick, type, data,
                volume, pitch));
            return this;
        }

        // Быстрые методы для частых событий
        public Builder sound(int tick, String soundId) {
            return event(tick, EventType.PLAY_SOUND, soundId);
        }

        public Builder voice(int tick, String soundId) {
            return event(tick, EventType.PLAY_VOICE, soundId);
        }

        public Builder subtitle(int tick, String text) {
            return event(tick, EventType.SHOW_SUBTITLE, text);
        }

        public Builder hideSubtitle(int tick) {
            return event(tick, EventType.HIDE_SUBTITLE, "");
        }

        public Builder text(int tick, String text) {
            return event(tick, EventType.SHOW_TEXT, text);
        }

        public Builder hideText(int tick) {
            return event(tick, EventType.HIDE_TEXT, "");
        }

        public Builder fadeToBlack(int tick) {
            return event(tick, EventType.FADE_TO_BLACK, "");
        }

        public Builder fadeFromBlack(int tick) {
            return event(tick, EventType.FADE_FROM_BLACK, "");
        }

        public Builder flash(int tick) {
            return event(tick, EventType.SCREEN_FLASH, "");
        }

        public Builder shake(int tick, String intensity) {
            return event(tick, EventType.CAMERA_SHAKE, intensity);
        }

        public Builder evidence(int tick, String evidenceId) {
            return event(tick, EventType.ADD_EVIDENCE, evidenceId);
        }

        public Builder animation(int tick, String npcAndAnim) {
            return event(tick, EventType.TRIGGER_ANIMATION, npcAndAnim);
        }

        public Builder spawnNpc(int tick, String npcId) {
            return event(tick, EventType.SPAWN_NPC, npcId);
        }

        public Builder hideNpc(int tick, String npcId) {
            return event(tick, EventType.HIDE_NPC, npcId);
        }

        public CutsceneData build() {
            // Считаем длительность из пути камеры
            // если не задана явно
            if (totalDurationTicks == 0) {
                totalDurationTicks = cameraPath.getTotalDuration();
            }
            return new CutsceneData(this);
        }
    }
}
