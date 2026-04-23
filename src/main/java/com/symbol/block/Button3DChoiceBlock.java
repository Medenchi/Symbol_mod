package com.symbol.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.symbol.registry.ModSounds;
import com.symbol.system.DialogueSystem;
import com.symbol.system.PlayerFreezeSystem;

// Кнопка выбора в диалогах
public class Button3DChoiceBlock extends Button3DBlock {

    // Индекс варианта ответа (0-3)
    public static final IntProperty CHOICE_INDEX =
        IntProperty.of("choice_index", 0, 3);

    public Button3DChoiceBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(ACTIVE, true)
            .with(PRESSED, false)
            .with(CHOICE_INDEX, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
        builder.add(PRESSED);
        builder.add(CHOICE_INDEX);
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
        if (!state.get(ACTIVE) || state.get(PRESSED)) {
            return ActionResult.PASS;
        }

        if (!world.isClient()) {
            int choiceIndex = state.get(CHOICE_INDEX);

            // Помечаем нажатой
            world.setBlockState(pos, state.with(PRESSED, true));

            // Звук выбора
            world.playSound(
                null, pos,
                ModSounds.BUTTON_CLICK,
                net.minecraft.sound.SoundCategory.BLOCKS,
                0.8f, 1.1f
            );

            // Передаём выбор в систему диалогов
            DialogueSystem.onChoiceSelected(player, choiceIndex);

            // Размораживаем игрока
            PlayerFreezeSystem.unfreeze(player);

            // Удаляем ВСЕ кнопки выбора рядом
            removeAllChoiceButtons(world, pos);
        }

        return ActionResult.SUCCESS;
    }

    // Удалить все кнопки выбора в радиусе 5 блоков
    private void removeAllChoiceButtons(World world, BlockPos centerPos) {
        BlockPos.iterate(
            centerPos.add(-5, -2, -5),
            centerPos.add(5, 2, 5)
        ).forEach(pos -> {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof Button3DChoiceBlock) {
                world.removeBlock(pos, false);
            }
        });
    }
}
