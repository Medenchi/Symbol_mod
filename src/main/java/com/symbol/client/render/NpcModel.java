package com.symbol.client.render;

import net.minecraft.util.Identifier;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import com.symbol.SymbolMod;
import com.symbol.entity.NpcEntity;

public class NpcModel<T extends NpcEntity & GeoAnimatable>
    extends GeoModel<T> {

    private final String npcTypeName;

    public NpcModel(String npcTypeName) {
        this.npcTypeName = npcTypeName;
    }

    // ============================================================
    // ПУТИ К ФАЙЛАМ
    // ============================================================

    @Override
    public Identifier getModelResource(T animatable) {
        String type = animatable.getNpcType();
        if (type == null || type.isEmpty()) type = npcTypeName;

        return new Identifier(
            SymbolMod.MOD_ID,
            "geo/npc/" + type + ".geo.json"
        );
    }

    @Override
    public Identifier getTextureResource(T animatable) {
        String type = animatable.getNpcType();
        if (type == null || type.isEmpty()) type = npcTypeName;

        return new Identifier(
            SymbolMod.MOD_ID,
            "textures/entity/npc/" + type + ".png"
        );
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        String type = animatable.getNpcType();
        if (type == null || type.isEmpty()) type = npcTypeName;

        return new Identifier(
            SymbolMod.MOD_ID,
            "animations/npc/" + type + ".animation.json"
        );
    }
}
