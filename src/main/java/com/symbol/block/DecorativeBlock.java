package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.symbol.item.DirectorWandItem;
import com.symbol.registry.ModItems;

public class DecorativeBlock extends Block {

    public DecorativeBlock(Settings settings) {
        super(settings);
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
        // Если в руке Палочка Режиссёра — открыть меню редактирования
        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.getItem() instanceof DirectorWandItem) {
            if (world.isClient()) {
                // Открываем GUI редактора блока
                DirectorWandItem.openBlockEditor(pos, state);
            }
            return ActionResult.SUCCESS;
        }

        // Обычное взаимодействие — показать название блока
        if (world.isClient()) {
            player.sendMessage(
                Text.literal("§7" + getTranslatedName(state)),
                true // показывать в actionbar
            );
        }

        return ActionResult.PASS;
    }

    // Получить читаемое имя блока
    protected String getTranslatedName(BlockState state) {
        return this.getTranslationKey();
    }
}
