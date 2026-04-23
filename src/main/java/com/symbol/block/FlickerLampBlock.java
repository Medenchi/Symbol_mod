package com.symbol.block;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FlickerLampBlock extends LampBlock {

    // Минимальное и максимальное время между миганиями (в тиках)
    private static final int FLICKER_MIN = 40;
    private static final int FLICKER_MAX = 200;

    // Длительность выключенного состояния (в тиках)
    private static final int OFF_DURATION_MIN = 2;
    private static final int OFF_DURATION_MAX = 15;

    public FlickerLampBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(
        BlockState state,
        World world,
        BlockPos pos,
        BlockState oldState,
        boolean notify
    ) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        // Запускаем случайный тик при появлении блока
        if (!world.isClient()) {
            world.scheduleBlockTick(pos, this, getRandomFlickerDelay(world.getRandom()));
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false; // Используем scheduled ticks
    }

    @Override
    public void scheduledTick(
        BlockState state,
        ServerWorld world,
        BlockPos pos,
        Random random
    ) {
        boolean isLit = state.get(LIT);

        if (isLit) {
            // Лампа горит — выключаем на короткое время
            world.setBlockState(pos, state.with(LIT, false));
            // Планируем включение обратно
            world.scheduleBlockTick(
                pos, this,
                OFF_DURATION_MIN + random.nextInt(OFF_DURATION_MAX - OFF_DURATION_MIN)
            );
        } else {
            // Лампа выключена — включаем обратно
            world.setBlockState(pos, state.with(LIT, true));
            // Планируем следующее мигание
            world.scheduleBlockTick(
                pos, this,
                getRandomFlickerDelay(random)
            );
        }

        // Звук мигания
        world.playSound(
            null, pos,
            net.minecraft.sound.SoundEvents.BLOCK_COMPARATOR_CLICK,
            net.minecraft.sound.SoundCategory.BLOCKS,
            0.05f,
            0.5f + random.nextFloat() * 0.5f
        );
    }

    private int getRandomFlickerDelay(Random random) {
        return FLICKER_MIN + random.nextInt(FLICKER_MAX - FLICKER_MIN);
    }
}
