package com.symbol.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FurnitureBlock extends DecorativeBlock {

    // Направление — куда смотрит мебель
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FurnitureBlock(Settings settings) {
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
        // Мебель смотрит на игрока при размещении
        return getDefaultState().with(
            FACING,
            ctx.getHorizontalPlayerFacing().getOpposite()
        );
    }

    @Override
    public VoxelShape getOutlineShape(
        BlockState state,
        BlockView world,
        BlockPos pos,
        ShapeContext context
    ) {
        // Полный блок по умолчанию
        // Подклассы могут переопределить для кастомных форм
        return VoxelShapes.fullCube();
    }
}
