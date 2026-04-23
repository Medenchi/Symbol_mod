package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PosterBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    // Форма плаката — тонкий прямоугольник на стене
    private static final VoxelShape SHAPE_NORTH =
        Block.createCuboidShape(0, 0, 14, 16, 16, 16);
    private static final VoxelShape SHAPE_SOUTH =
        Block.createCuboidShape(0, 0, 0, 16, 16, 2);
    private static final VoxelShape SHAPE_WEST =
        Block.createCuboidShape(14, 0, 0, 16, 16, 16);
    private static final VoxelShape SHAPE_EAST =
        Block.createCuboidShape(0, 0, 0, 2, 16, 16);

    public PosterBlock(Settings settings) {
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
        Direction facing = ctx.getSide();
        if (facing == Direction.UP || facing == Direction.DOWN) {
            facing = ctx.getHorizontalPlayerFacing();
        }
        return getDefaultState().with(FACING, facing);
    }

    @Override
    public VoxelShape getOutlineShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        return switch (state.get(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST  -> SHAPE_WEST;
            case EAST  -> SHAPE_EAST;
            default    -> SHAPE_NORTH;
        };
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
        // Показываем текст плаката при нажатии
        if (world.isClient()) {
            showPosterText(state, world, pos, player);
        }
        return ActionResult.SUCCESS;
    }

    protected void showPosterText(
        BlockState state,
        World world,
        BlockPos pos,
        PlayerEntity player
    ) {
        // Переопределяется в конкретных плакатах
        // Базовая реализация — просто название
        player.sendMessage(
            net.minecraft.text.Text.literal(
                "§8[Плакат]§7 " + this.getTranslationKey()
            ),
            false
        );
    }
}
