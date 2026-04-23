package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.symbol.system.CutsceneSystem;
import com.symbol.registry.ModSounds;

// Большая кнопка НАЧАТЬ
public class Button3DBlock extends Block {

    // Активна ли кнопка
    public static final BooleanProperty ACTIVE =
        BooleanProperty.of("active");

    // Была ли уже нажата
    public static final BooleanProperty PRESSED =
        BooleanProperty.of("pressed");

    // Кнопка парит — нет коллизии
    private static final VoxelShape SHAPE =
        VoxelShapes.empty();

    public Button3DBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(ACTIVE, true)
            .with(PRESSED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
        builder.add(PRESSED);
    }

    @Override
    public VoxelShape getOutlineShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        // Игрок проходит сквозь кнопку
        return VoxelShapes.empty();
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
        // Проверяем что кнопка активна и не нажата
        if (!state.get(ACTIVE) || state.get(PRESSED)) {
            return ActionResult.PASS;
        }

        if (!world.isClient()) {
            // Помечаем как нажатую
            world.setBlockState(pos, state.with(PRESSED, true));

            // Звук нажатия
            world.playSound(
                null, pos,
                ModSounds.BUTTON_CLICK,
                net.minecraft.sound.SoundCategory.BLOCKS,
                1.0f, 1.0f
            );

            // Запускаем первую катсцену
            CutsceneSystem.startCutscene(player, "cutscene_act0_firing");

            // Исчезновение кнопки через 2 секунды
            scheduleButtonDisappear(world, pos, state);
        }

        return ActionResult.SUCCESS;
    }

    private void scheduleButtonDisappear(World world, BlockPos pos, BlockState state) {
        // Планируем удаление кнопки через 40 тиков (2 секунды)
        world.getServer().execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            world.removeBlock(pos, false);
        });
    }

    // Метод для Палочки Режиссёра — установить команду кнопки
    public static void setCommand(World world, BlockPos pos, String command) {
        // Сохраняем команду в NBT блока
        // Реализуется через BlockEntity
    }
}
