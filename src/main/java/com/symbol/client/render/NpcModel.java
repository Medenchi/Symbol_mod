package com.symbol.client.render;

import net.minecraft.util.Identifier;

import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import com.symbol.SymbolMod;
import com.symbol.entity.NpcEntity;

public class NpcModel<T extends NpcEntity & GeoAnimatable>
    extends GeoModel<T> {

    // NPC у которых есть своя анимация
    private static final java.util.Set<String> OWN_ANIMATIONS =
        java.util.Set.of("gromov", "detective");

    public NpcModel() {
        // Пустой конструктор
        // Тип NPC берётся из самой сущности
    }

    // ============================================================
    // МОДЕЛЬ — ОДНА ДЛЯ ВСЕХ
    // ============================================================

    @Override
    public Identifier getModelResource(T animatable) {
        return new Identifier(
            SymbolMod.MOD_ID,
            "geo/npc/default_npc.geo.json"
        );
    }

    // ============================================================
    // ТЕКСТУРА — СВОЯ ДЛЯ КАЖДОГО
    // Если файла нет — Minecraft покажет фиолетовые квадраты
    // Это нормально на этапе разработки
    // ============================================================

    @Override
    public Identifier getTextureResource(T animatable) {
        String type = animatable.getNpcType();

        // Если тип не задан — заглушка
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

    // ============================================================
    // АНИМАЦИЯ — СВОЯ ИЛИ SHARED
    // ============================================================

    @Override
    public Identifier getAnimationResource(T animatable) {
        String type = animatable.getNpcType();

        // Если у NPC есть своя анимация — берём её
        // Иначе — общая для всех
        if (type != null && OWN_ANIMATIONS.contains(type)) {
            return new Identifier(
                SymbolMod.MOD_ID,
                "animations/npc/" + type + ".animation.json"
            );
        }

        return new Identifier(
            SymbolMod.MOD_ID,
            "animations/npc/shared.animation.json"
        );
    }
}
