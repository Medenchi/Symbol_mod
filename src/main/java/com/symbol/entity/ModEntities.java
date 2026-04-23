package com.symbol.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.symbol.SymbolMod;

public class ModEntities {
    
    public static final EntityType<NpcEntity> GROMOV = register(
        "gromov",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> VALERIA = register(
        "valeria",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> NINA = register(
        "nina",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> RASHID = register(
        "rashid",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> TOLYA = register(
        "tolya",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> SEMONYCH = register(
        "semonych",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> BOSS = register(
        "boss",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> MOM = register(
        "mom",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    public static final EntityType<NpcEntity> FATHER = register(
        "father",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NpcEntity::new)
            .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
            .build()
    );
    
    private static <T extends NpcEntity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(SymbolMod.MOD_ID, name), type);
    }
    
    public static void registerEntities() {
        SymbolMod.LOGGER.info("Зарегистрировано {} NPC типов", 9);
    }
}
