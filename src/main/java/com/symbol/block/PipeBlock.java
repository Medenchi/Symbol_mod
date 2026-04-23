package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class PipeBlock extends DecorativeBlock {

    // Ориентация трубы
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public PipeBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(AXIS, Direction.Axis.Y));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        // Ориентируем трубу по направлению взгляда игрока
        Direction.Axis axis = ctx.getSide().getAxis();
        return getDefaultState().with(AXIS, axis);
    }
}
