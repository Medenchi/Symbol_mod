package com.symbol.system;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFreezeSystem {

    // Хранит UUID игроков которые заморожены
    // + их позицию и направление взгляда
    private static final Map<UUID, FreezeData> FROZEN_PLAYERS = new HashMap<>();

    // ============================================================
    // ЗАМОРОЗКА
    // ============================================================

    // Полная заморозка — нельзя двигаться И поворачивать камеру
    public static void freezeFull(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity sp) {
            FROZEN_PLAYERS.put(
                player.getUuid(),
                new FreezeData(
                    player.getPos(),
                    player.getYaw(),
                    player.getPitch(),
                    FreezeType.FULL
                )
            );
            sendFreezePacket(sp, true, true);
        }
    }

    // Частичная заморозка — нельзя двигаться, но можно смотреть
    public static void freezeMovement(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity sp) {
            FROZEN_PLAYERS.put(
                player.getUuid(),
                new FreezeData(
                    player.getPos(),
                    player.getYaw(),
                    player.getPitch(),
                    FreezeType.MOVEMENT_ONLY
                )
            );
            sendFreezePacket(sp, true, false);
        }
    }

    // Заморозка для диалогов с кнопками выбора
    // Полная — нельзя ничего
    public static void freezeForChoice(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity sp) {
            FROZEN_PLAYERS.put(
                player.getUuid(),
                new FreezeData(
                    player.getPos(),
                    player.getYaw(),
                    player.getPitch(),
                    FreezeType.FULL
                )
            );
            sendFreezePacket(sp, true, true);

            // Сообщение подсказка
            player.sendMessage(
                net.minecraft.text.Text.literal(
                    "§8Выберите ответ..."
                ),
                true
            );
        }
    }

    // ============================================================
    // РАЗМОРОЗКА
    // ============================================================

    public static void unfreeze(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity sp) {
            FROZEN_PLAYERS.remove(player.getUuid());
            sendFreezePacket(sp, false, false);
        }
    }

    public static void unfreezeAll() {
        FROZEN_PLAYERS.clear();
        // Пакет всем игрокам
    }

    // ============================================================
    // ПРОВЕРКИ
    // ============================================================

    public static boolean isFrozen(PlayerEntity player) {
        return FROZEN_PLAYERS.containsKey(player.getUuid());
    }

    public static boolean isFullyFrozen(PlayerEntity player) {
        FreezeData data = FROZEN_PLAYERS.get(player.getUuid());
        return data != null && data.type == FreezeType.FULL;
    }

    // ============================================================
    // ТИК — вызывается каждый тик сервера
    // Удерживает игрока на месте
    // ============================================================

    public static void tick(ServerPlayerEntity player) {
        FreezeData data = FROZEN_PLAYERS.get(player.getUuid());
        if (data == null) return;

        // Телепортируем обратно если сдвинулся
        Vec3d currentPos = player.getPos();
        Vec3d frozenPos = data.position;

        double distanceMoved = currentPos.distanceTo(frozenPos);
        if (distanceMoved > 0.1) {
            player.teleport(
                frozenPos.x,
                frozenPos.y,
                frozenPos.z
            );
        }

        // Фиксируем взгляд если полная заморозка
        if (data.type == FreezeType.FULL) {
            if (Math.abs(player.getYaw() - data.yaw) > 1.0f ||
                Math.abs(player.getPitch() - data.pitch) > 1.0f) {
                player.setYaw(data.yaw);
                player.setPitch(data.pitch);
            }
        }
    }

    // ============================================================
    // ВНУТРЕННИЕ КЛАССЫ
    // ============================================================

    private static void sendFreezePacket(
        ServerPlayerEntity player,
        boolean frozen,
        boolean lockCamera
    ) {
        // Отправка пакета клиенту
        // Реализуется в сетевом слое
        NetworkHandler.sendFreezePacket(player, frozen, lockCamera);
    }

    public enum FreezeType {
        FULL,           // Нельзя ничего
        MOVEMENT_ONLY   // Нельзя двигаться, но можно смотреть
    }

    public static class FreezeData {
        public final Vec3d position;
        public final float yaw;
        public final float pitch;
        public final FreezeType type;

        public FreezeData(Vec3d position, float yaw, float pitch, FreezeType type) {
            this.position = position;
            this.yaw = yaw;
            this.pitch = pitch;
            this.type = type;
        }
    }
}
