package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.symbol.item.DirectorWandItem;
import com.symbol.registry.ModSounds;

public class LampBlock extends Block {

    // Свойство — включена/выключена
    public static final BooleanProperty LIT = Properties.LIT;

    public LampBlock(Settings settings) {
        super(settings);
        // По умолчанию лампа включена
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(LIT, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ActionResult onUse(
        BlockState state,
        World world,
        BlockPos pos,
        PlayerEntity player,
        Hand hand,
        BlockHitResult hit
    ) {
        ItemStack heldItem = player.getStackInHand(hand);

        // Палочка режиссёра — редактор
        if (heldItem.getItem() instanceof DirectorWandItem) {
            if (world.isClient()) {
                DirectorWandItem.openBlockEditor(pos, state);
            }
            return ActionResult.SUCCESS;
        }

        // Обычное нажатие — переключить лампу
        if (!world.isClient()) {
            boolean isLit = state.get(LIT);
            world.setBlockState(pos, state.with(LIT, !isLit));

            // Звук переключения
            if (isLit) {
                world.playSound(
                    null, pos,
                    net.minecraft.sound.SoundEvents.BLOCK_LEVER_CLICK,
                    net.minecraft.sound.SoundCategory.BLOCKS,
                    0.3f, 0.6f
                );
            } else {
                world.playSound(
                    null, pos,
                    net.minecraft.sound.SoundEvents.BLOCK_LEVER_CLICK,
                    net.minecraft.sound.SoundCategory.BLOCKS,
                    0.3f, 0.8f
                );
            }
        }

        return ActionResult.SUCCESS;
    }

    // Метод для карты — включить лампу
    public static void setLit(World world, BlockPos pos, boolean lit) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof LampBlock) {
            world.setBlockState(pos, state.with(LIT, lit));
        }
    }
}
