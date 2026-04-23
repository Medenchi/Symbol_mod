package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.symbol.system.ParanoiaSystem;

// Ключевой объект сцены (например стеклянная форма)
// При взаимодействии — триггерит сюжетные события
public class KeyPropBlock extends DecorativeBlock {

    // Был ли осмотрен
    public static final BooleanProperty EXAMINED =
        BooleanProperty.of("examined");

    // ID события которое триггерит этот объект
    private final String eventId;

    // Форма — лежащий прямоугольный объект
    private static final VoxelShape SHAPE =
        Block.createCuboidShape(1, 0, 4, 15, 6, 12);

    public KeyPropBlock(Settings settings) {
        this(settings, "default_event");
    }

    public KeyPropBlock(Settings settings, String eventId) {
        super(settings);
        this.eventId = eventId;
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(EXAMINED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(EXAMINED);
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
        if (!state.get(EXAMINED)) {
            if (!world.isClient()) {
                // Помечаем как осмотренный
                world.setBlockState(pos, state.with(EXAMINED, true));

                // Триггерим событие
                triggerEvent(world, pos, player);
            }
        } else {
            // Уже осмотрен — просто показываем подсказку
            if (world.isClient()) {
                player.sendMessage(
                    net.minecraft.text.Text.literal(
                        "§7Вы уже осмотрели это."
                    ),
                    true
                );
            }
        }

        return ActionResult.SUCCESS;
    }

    protected void triggerEvent(
        World world,
        BlockPos pos,
        PlayerEntity player
    ) {
        // Звук осмотра
        world.playSound(
            null, pos,
            net.minecraft.sound.SoundEvents.ITEM_BOOK_PAGE_TURN,
            net.minecraft.sound.SoundCategory.BLOCKS,
            0.5f, 1.0f
        );
    }
}
