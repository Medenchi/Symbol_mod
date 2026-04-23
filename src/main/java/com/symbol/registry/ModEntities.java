package com.symbol.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;
import com.symbol.entity.*;

public class ModEntities {

    // ==============================
    // ГЛАВНЫЕ ПЕРСОНАЖИ
    // ==============================
    public static final EntityType<НПЦEntity> ГРОМОВ = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "громов"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> ВАЛЕРИЯ = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "валерия"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> НИНА = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "нина"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.75f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> РАШИД = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "рашид"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> ТОЛЯ = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "толя"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> СЕМЁНЫЧ = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "семёныч"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.75f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> НАЧАЛЬНИК = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "начальник"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> МАМА = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "мама"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.7f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    public static final EntityType<НПЦEntity> ОТЕЦ = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(SymbolMod.MOD_ID, "отец"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, НПЦEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .trackRangeBlocks(64)
            .trackedUpdateRate(2)
            .build()
    );

    // ==============================
    // СЧЁТЧИК
    // ==============================
    private static final int ENTITY_COUNT = 9;

    public static int getEntityCount() {
        return ENTITY_COUNT;
    }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация сущностей NPC...");
    }
}
