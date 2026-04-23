package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.symbol.client.gui.DiaryScreen;

public class DiaryBlock extends Block {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    // ID дневника — задаётся через Палочку Режиссёра
    // Определяет какой текст показывать
    public static final StringProperty DIARY_ID = StringProperty.of(
        "diary_id",
        "worker_diary",
        "director_notebook",
        "basement_diary",
        "village_diary",
        "empty_house_diary"
    );

    // Форма — лежащая книга
    private static final VoxelShape SHAPE =
        Block.createCuboidShape(2, 0, 3, 14, 2, 13);

    public DiaryBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(DIARY_ID, "worker_diary"));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(DIARY_ID);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
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
        return SHAPE;
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
        if (world.isClient()) {
            String diaryId = state.get(DIARY_ID);
            // Открываем экран дневника
            DiaryScreen.open(diaryId);
        }
        return ActionResult.SUCCESS;
    }
}
