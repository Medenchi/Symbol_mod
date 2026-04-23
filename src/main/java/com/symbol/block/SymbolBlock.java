package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.symbol.registry.ModSounds;
import com.symbol.system.ParanoiaSystem;

public class SymbolBlock extends Block {

    public static final DirectionProperty FACING = Properties.FACING;

    // Форма символа — очень тонкая накладка
    private static final VoxelShape SHAPE_WALL_NORTH =
        Block.createCuboidShape(2, 2, 15, 14, 14, 16);
    private static final VoxelShape SHAPE_WALL_SOUTH =
        Block.createCuboidShape(2, 2, 0, 14, 14, 1);
    private static final VoxelShape SHAPE_WALL_WEST =
        Block.createCuboidShape(15, 2, 2, 16, 14, 14);
    private static final VoxelShape SHAPE_WALL_EAST =
        Block.createCuboidShape(0, 2, 2, 1, 14, 14);
    private static final VoxelShape SHAPE_FLOOR =
        Block.createCuboidShape(2, 0, 2, 14, 1, 14);
    private static final VoxelShape SHAPE_CEILING =
        Block.createCuboidShape(2, 15, 2, 14, 16, 14);

    public SymbolBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getSide().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        return switch (state.get(FACING)) {
            case NORTH -> SHAPE_WALL_NORTH;
            case SOUTH -> SHAPE_WALL_SOUTH;
            case WEST  -> SHAPE_WALL_WEST;
            case EAST  -> SHAPE_WALL_EAST;
            case UP    -> SHAPE_CEILING;
            case DOWN  -> SHAPE_FLOOR;
        };
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(
        BlockState state,
        ServerWorld world,
        BlockPos pos,
        Random random
    ) {
        // Редкие частицы вокруг символа
        if (random.nextInt(20) == 0) {
            spawnParticles(world, pos);
        }
    }

    private void spawnParticles(ServerWorld world, BlockPos pos) {
        world.spawnParticles(
            ParticleTypes.END_ROD,
            pos.getX() + 0.5,
            pos.getY() + 0.5,
            pos.getZ() + 0.5,
            3,   // количество
            0.3, // разброс X
            0.3, // разброс Y
            0.3, // разброс Z
            0.01 // скорость
        );
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
        // Игрок нашёл символ!
        if (!world.isClient()) {
            // Увеличиваем уровень паранойи
            ParanoiaSystem.increaseParanoia(player, 1);

            // Отмечаем символ как найденный
            ParanoiaSystem.markSymbolFound(player, pos);

            // Звук обнаружения
            world.playSound(
                null, pos,
                ModSounds.SFX_STINGER_DISCOVER,
                SoundCategory.BLOCKS,
                0.5f, 1.0f
            );
        }

        return ActionResult.SUCCESS;
    }
}
