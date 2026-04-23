package com.symbol.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;
import com.symbol.entity.NpcEntity;

public class ModEntities {

    public static final EntityType<NpcEntity> GROMOV = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "gromov"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> VALERIA = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "valeria"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> NINA = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "nina"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.75f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> RASHID = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "rashid"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> TOLYA = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "tolya"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> SEMONYCH = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "semonych"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.75f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> BOSS = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "boss"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> MOM = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "mom"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.7f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<NpcEntity> FATHER = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "father"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    private static final int ENTITY_COUNT = 9;

    public static int getEntityCount() { return ENTITY_COUNT; }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация NPC сущностей...");
    }
}
