package com.symbol.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import com.symbol.registry.ModSounds;
import com.symbol.system.CutsceneSystem;

public class GlassCorpseBlock extends Block {

    // Какое тело внутри (0=первый рабочий, 1=второй, 2=третий)
    public static final IntProperty BODY_INDEX = IntProperty.of("body_index", 0, 2);

    // Форма — высокий вертикальный блок
    private static final VoxelShape SHAPE =
        Block.createCuboidShape(3, 0, 3, 13, 16, 13);

    // Имена рабочих для отображения
    private static final String[] WORKER_NAMES = {
        "Неизвестный рабочий",
        "Алиев А.Р.",
        "Сергеев В.И."
    };

    public GlassCorpseBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager()
            .getDefaultState()
            .with(BODY_INDEX, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BODY_INDEX);
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
        // Жуткое мерцание частиц
        if (random.nextInt(10) == 0) {
            world.spawnParticles(
                ParticleTypes.SOUL,
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5,
                2,
                0.2, 0.5, 0.2,
                0.01
            );
        }
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
            int bodyIndex = state.get(BODY_INDEX);

            // Показываем информацию о теле
            player.sendMessage(
                net.minecraft.text.Text.literal(
                    "§8[Стеклянный блок]§7 Внутри виден силуэт человека."
                ),
                false
            );

            player.sendMessage(
                net.minecraft.text.Text.literal(
                    "§7На блоке выгравировано: §c" + WORKER_NAMES[bodyIndex]
                ),
                false
            );

            // Звук
            world.playSound(
                player,
                pos,
                ModSounds.SFX_STINGER_HORROR,
                SoundCategory.BLOCKS,
                0.3f, 1.0f
            );
        }

        return ActionResult.SUCCESS;
    }
}
