private void registerEntityRenderers() {
    // Один рендерер для всех NPC
    // Просто передаём ctx без имени типа

    EntityRendererRegistry.register(
        ModEntities.GROMOV,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.VALERIA,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.NINA,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.RASHID,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.TOLYA,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.SEMONYCH,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.BOSS,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.MOM,
        ctx -> new NpcRenderer<>(ctx)
    );
    EntityRendererRegistry.register(
        ModEntities.FATHER,
        ctx -> new NpcRenderer<>(ctx)
    );

    SymbolMod.LOGGER.info(
        "✓ Зарегистрировано {} рендереров NPC", 9
    );
}
