package com.symbol.client.renderer;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class NpcRenderer<T extends Entity> extends EntityRenderer<T> {
    
    public NpcRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
    
    @Override
    public Identifier getTexture(T entity) {
        return new Identifier("symbol", "textures/entity/npc/placeholder.png");
    }
}
