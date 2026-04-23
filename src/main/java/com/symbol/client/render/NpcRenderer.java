package com.symbol.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import com.symbol.SymbolMod;
import com.symbol.entity.NpcEntity;

public class NpcRenderer<T extends NpcEntity>
    extends GeoEntityRenderer<T> {

    public NpcRenderer(EntityRendererFactory.Context context) {
        // Передаём одну модель для всех
        super(context, new NpcModel<>());
        this.shadowRadius = 0.3f;
    }

    @Override
    public Identifier getTextureLocation(T entity) {
        String type = entity.getNpcType();

        if (type == null || type.isEmpty()) {
            return new Identifier(
                SymbolMod.MOD_ID,
                "textures/entity/npc/placeholder.png"
            );
        }

        return new Identifier(
            SymbolMod.MOD_ID,
            "textures/entity/npc/" + type + ".png"
        );
    }
}
