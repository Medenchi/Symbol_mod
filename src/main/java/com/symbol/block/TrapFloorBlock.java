package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.symbol.registry.ModSounds;
import com.symbol.system.CutsceneSystem;

public class TrapFloorBlock extends DecorativeBlock {

    // Сработала ли ловушка
    private static boolean triggered = false;

    public TrapFloorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(
        World world,
        BlockPos pos,
        BlockState state,
        Entity entity
    ) {
        super.onSteppedOn(world, pos, state, entity);

        // Срабатывает только для игрока
        if (entity instanceof ServerPlayerEntity player) {
            if (!triggered) {
                triggerTrap(world, pos, player);
            }
        }
    }

    private void triggerTrap(World world, BlockPos pos, ServerPlayerEntity player) {
        triggered = true;

        // Звук треска пола
        world.playSound(
            null, pos,
            ModSounds.SFX_TRAP_FLOOR_CRACK,
            SoundCategory.BLOCKS,
            1.0f, 0.8f
        );

        // Через 10 тиков — звук падения и телепортация
        world.getServer().execute(() -> {
            try {
                Thread.sleep(500); // 0.5 секунды задержки
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            world.playSound(
                null, pos,
                ModSounds.SFX_TRAP_FLOOR_FALL,
                SoundCategory.BLOCKS,
                1.0f, 0.9f
            );

            // Телепортируем игрока на точку возврата
            // (устанавливается через Палочку Режиссёра)
            TrapFloorBlock.resetTrap(player);
        });
    }

    // Сброс ловушки — телепорт игрока назад
    public static void resetTrap(ServerPlayerEntity player) {
        // Возвращаем игрока к точке входа в завод
        // Координаты задаются в конфиге карты
        triggered = false;

        // Сообщение игроку
        player.sendMessage(
            net.minecraft.text.Text.literal(
                "§c[!] §7Прогнивший пол не выдержал веса..."
            ),
            false
        );

        // Уведомление об отсутствии улик — они сохраняются
        player.sendMessage(
            net.minecraft.text.Text.literal(
                "§7Улики не потеряны."
            ),
            false
        );
    }
}
