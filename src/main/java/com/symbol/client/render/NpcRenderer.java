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

    // Имя типа NPC (определяет какую модель/текстуру использовать)
    private final String npcTypeName;

    public NpcRenderer(
        EntityRendererFactory.Context context,
        String npcTypeName
    ) {
        super(context, new NpcModel<>(npcTypeName));
        this.npcTypeName = npcTypeName;
        // Тень под NPC
        this.shadowRadius = 0.3f;
    }

    // ============================================================
    // ТЕКСТУРА
    // ============================================================

    @Override
    public Identifier getTextureLocation(T entity) {
        // Текстура берётся по типу NPC
        String type = entity.getNpcType();
        if (type == null || type.isEmpty()) {
            type = npcTypeName;
        }
        return new Identifier(
            SymbolMod.MOD_ID,
            "textures/entity/npc/" + type + ".png"
        );
    }

    // ============================================================
    // РЕНДЕР
    // ============================================================

    @Override
    public void render(
        T entity,
        float entityYaw,
        float partialTick,
        MatrixStack poseStack,
        VertexConsumerProvider bufferSource,
        int packedLight
    ) {
        // Стандартный рендер GeckoLib
        super.render(
            entity, entityYaw, partialTick,
            poseStack, bufferSource, packedLight
        );

        // Имя над головой
        renderNameTag(entity, poseStack, bufferSource, packedLight);
    }

    private void renderNameTag(
        T entity,
        MatrixStack poseStack,
        VertexConsumerProvider bufferSource,
        int packedLight
    ) {
        String name = entity.getNpcName();
        if (name == null || name.isEmpty()) return;

        // Только если игрок близко
        double dist = entity.squaredDistanceTo(
            net.minecraft.client.MinecraftClient
                .getInstance().player
        );
        if (dist > 64) return; // Дальше 8 блоков — не показываем

        poseStack.push();
        poseStack.translate(0, entity.getHeight() + 0.3, 0);
        poseStack.multiply(
            net.minecraft.client.MinecraftClient
                .getInstance()
                .getEntityRenderDispatcher()
                .getRotation()
        );
        poseStack.scale(-0.025f, -0.025f, 0.025f);

        var matrix = poseStack.peek().getPositionMatrix();
        var font = net.minecraft.client.MinecraftClient
            .getInstance().textRenderer;

        float bgAlpha = 0.25f;
        int textW = font.getWidth(name);

        // Фон имени
        var vertexConsumer = bufferSource.getBuffer(
            net.minecraft.client.render.RenderLayer.getGui()
        );

        // Текст имени
        font.draw(
            name,
            -textW / 2f, 0,
            0xAAFFFFFF,
            false,
            matrix,
            bufferSource,
            net.minecraft.client.font.TextRenderer.TextLayerType.SEE_THROUGH,
            0x40000000,
            packedLight
        );

        poseStack.pop();
    }
}
