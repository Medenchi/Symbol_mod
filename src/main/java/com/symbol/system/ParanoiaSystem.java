package com.symbol.system;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.symbol.SymbolMod;
import com.symbol.registry.ModSounds;

public class ParanoiaSystem {

    // Уровень паранойи каждого игрока (0-10)
    private static final Map<UUID, Integer> PARANOIA_LEVELS = new HashMap<>();

    // Найденные символы
    private static final Map<UUID, Set<BlockPos>> FOUND_SYMBOLS = new HashMap<>();

    // Всего символов в игре
    public static final int MAX_SYMBOLS = 5;

    // Максимальный уровень паранойи
    public static final int MAX_PARANOIA = 10;

    // ============================================================
    // УПРАВЛЕНИЕ УРОВНЕМ ПАРАНОЙИ
    // ============================================================

    public static void increaseParanoia(PlayerEntity player, int amount) {
        UUID id = player.getUuid();
        int current = PARANOIA_LEVELS.getOrDefault(id, 0);
        int newLevel = Math.min(current + amount, MAX_PARANOIA);
        PARANOIA_LEVELS.put(id, newLevel);

        SymbolMod.LOGGER.debug(
            "Паранойя игрока {}: {} -> {}",
            player.getName().getString(),
            current,
            newLevel
        );

        // Триггерим эффекты при достижении порогов
        checkParanoiaThresholds(player, current, newLevel);

        // Отправляем клиенту
        if (player instanceof ServerPlayerEntity sp) {
            NetworkHandler.sendParanoiaUpdate(sp, newLevel);
        }
    }

    public static int getParanoiaLevel(PlayerEntity player) {
        return PARANOIA_LEVELS.getOrDefault(player.getUuid(), 0);
    }

    public static boolean hasMaxParanoia(PlayerEntity player) {
        return getParanoiaLevel(player) >= MAX_PARANOIA;
    }

    // ============================================================
    // ПОРОГОВЫЕ ЭФФЕКТЫ
    // ============================================================

    private static void checkParanoiaThresholds(
        PlayerEntity player,
        int oldLevel,
        int newLevel
    ) {
        // Уровень 2 — первые шорохи
        if (oldLevel < 2 && newLevel >= 2) {
            onParanoiaLevel2(player);
        }

        // Уровень 4 — шёпот
        if (oldLevel < 4 && newLevel >= 4) {
            onParanoiaLevel4(player);
        }

        // Уровень 6 — галлюцинации
        if (oldLevel < 6 && newLevel >= 6) {
            onParanoiaLevel6(player);
        }

        // Уровень 8 — сильные эффекты
        if (oldLevel < 8 && newLevel >= 8) {
            onParanoiaLevel8(player);
        }

        // Уровень 10 — максимум
        if (oldLevel < 10 && newLevel >= 10) {
            onParanoiaMax(player);
        }
    }

    private static void onParanoiaLevel2(PlayerEntity player) {
        // Начинаем проигрывать случайные шорохи
        scheduleRandomSound(player, ModSounds.PARANOIA_RUSTLE, 200, 400);

        // Небольшое сообщение
        player.sendMessage(
            net.minecraft.text.Text.literal("§8..."),
            true
        );
    }

    private static void onParanoiaLevel4(PlayerEntity player) {
        // Шёпот за спиной
        scheduleRandomSound(player, ModSounds.PARANOIA_WHISPER_1, 100, 300);
        scheduleRandomSound(player, ModSounds.PARANOIA_WHISPER_2, 150, 350);
    }

    private static void onParanoiaLevel6(PlayerEntity player) {
        // Эффект статики на экране
        if (player instanceof ServerPlayerEntity sp) {
            NetworkHandler.sendParanoiaEffect(sp, "static", 60);
        }

        // Звук помех
        scheduleRandomSound(player, ModSounds.PARANOIA_STATIC, 80, 200);
    }

    private static void onParanoiaLevel8(PlayerEntity player) {
        // Сильный эффект
        if (player instanceof ServerPlayerEntity sp) {
            NetworkHandler.sendParanoiaEffect(sp, "heavy_distortion", 80);
        }

        // Сердцебиение
        scheduleRandomSound(player, ModSounds.PARANOIA_HEARTBEAT, 60, 120);

        // Шаги за спиной
        scheduleRandomSound(player, ModSounds.PARANOIA_STEPS_BEHIND, 100, 250);
    }

    private static void onParanoiaMax(PlayerEntity player) {
        // Максимальная паранойя
        if (player instanceof ServerPlayerEntity sp) {
            NetworkHandler.sendParanoiaEffect(sp, "max_paranoia", 100);
        }

        // Все звуки вместе
        scheduleRandomSound(player, ModSounds.PARANOIA_BREATHING, 40, 100);

        // Разблокируем концовку D если выполнены условия
        checkEndingDCondition(player);
    }

    // ============================================================
    // СИМВОЛЫ
    // ============================================================

    public static void markSymbolFound(PlayerEntity player, BlockPos pos) {
        UUID id = player.getUuid();
        FOUND_SYMBOLS.computeIfAbsent(id, k -> new HashSet<>()).add(pos);

        int found = getFoundSymbolsCount(player);

        SymbolMod.LOGGER.debug(
            "Символов найдено: {}/{}",
            found,
            MAX_SYMBOLS
        );

        // Реакция детектива в зависимости от количества
        triggerSymbolVoice(player, found);
    }

    public static int getFoundSymbolsCount(PlayerEntity player) {
        Set<BlockPos> found = FOUND_SYMBOLS.get(player.getUuid());
        return found == null ? 0 : found.size();
    }

    public static boolean hasFoundAllSymbols(PlayerEntity player) {
        return getFoundSymbolsCount(player) >= MAX_SYMBOLS;
    }

    private static void triggerSymbolVoice(PlayerEntity player, int count) {
        if (player instanceof ServerPlayerEntity sp) {
            switch (count) {
                case 1 -> CutsceneSystem.playVoice(sp,
                    ModSounds.VOICE_DET_SYMBOL_FIRST);
                case 2 -> CutsceneSystem.playVoice(sp,
                    ModSounds.VOICE_DET_SYMBOL_PATTERN);
                case 3 -> CutsceneSystem.playVoice(sp,
                    ModSounds.VOICE_DET_SYMBOL_EVERYWHERE);
                case 5 -> CutsceneSystem.playVoice(sp,
                    ModSounds.VOICE_DET_SYMBOL_ALL_FOUND);
            }
        }
    }

    // ============================================================
    // КОНЦОВКИ
    // ============================================================

    private static void checkEndingDCondition(PlayerEntity player) {
        // Концовка D требует:
        // 1. Все символы найдены
        // 2. Максимальная паранойя
        if (hasFoundAllSymbols(player) && hasMaxParanoia(player)) {
            if (player instanceof ServerPlayerEntity sp) {
                // Помечаем что концовка D доступна
                NetworkHandler.sendEndingUnlocked(sp, "ending_d");
            }
        }
    }

    // ============================================================
    // СОХРАНЕНИЕ И ЗАГРУЗКА (NBT)
    // ============================================================

    public static void saveToNbt(
        PlayerEntity player,
        net.minecraft.nbt.NbtCompound nbt
    ) {
        nbt.putInt("paranoia_level",
            PARANOIA_LEVELS.getOrDefault(player.getUuid(), 0));
        nbt.putInt("symbols_found",
            getFoundSymbolsCount(player));
    }

    public static void loadFromNbt(
        PlayerEntity player,
        net.minecraft.nbt.NbtCompound nbt
    ) {
        PARANOIA_LEVELS.put(
            player.getUuid(),
            nbt.getInt("paranoia_level")
        );
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private static void scheduleRandomSound(
        PlayerEntity player,
        net.minecraft.sound.SoundEvent sound,
        int minDelay,
        int maxDelay
    ) {
        // Планируем случайный звук
        // Реализуется через сервер
    }
}
