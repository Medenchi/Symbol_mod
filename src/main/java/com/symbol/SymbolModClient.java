package com.symbol;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import com.symbol.client.gui.*;
import com.symbol.client.render.*;
import com.symbol.registry.ModEntities;

public class SymbolModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        SymbolMod.LOGGER.info("Инициализация клиентской части...");
        
        // Регистрация рендереров NPC
        registerEntityRenderers();
        
        // Регистрация GUI экранов
        registerScreens();
        
        // Инициализация системы катсцен
        CutsceneRenderer.init();
        
        // Инициализация 3D кнопок
        Button3DRenderer.init();
        
        // Регистрация клавиш управления
        KeyBindings.register();
        
        SymbolMod.LOGGER.info("✓ Клиентская часть загружена");
    }
    
    private void registerEntityRenderers() {
        // Громов
        EntityRendererRegistry.register(ModEntities.ГРОМОВ, 
            ctx -> new NPCRenderer<>(ctx, "громов"));
        
        // Валерия
        EntityRendererRegistry.register(ModEntities.ВАЛЕРИЯ, 
            ctx -> new NPCRenderer<>(ctx, "валерия"));
        
        // Нина
        EntityRendererRegistry.register(ModEntities.НИНА, 
            ctx -> new NPCRenderer<>(ctx, "нина"));
        
        // Рашид
        EntityRendererRegistry.register(ModEntities.РАШИД, 
            ctx -> new NPCRenderer<>(ctx, "рашид"));
        
        // Толя
        EntityRendererRegistry.register(ModEntities.ТОЛЯ, 
            ctx -> new NPCRenderer<>(ctx, "толя"));
        
        // Семёныч
        EntityRendererRegistry.register(ModEntities.СЕМЁНЫЧ, 
            ctx -> new NPCRenderer<>(ctx, "семёныч"));
        
        // Начальник
        EntityRendererRegistry.register(ModEntities.НАЧАЛЬНИК, 
            ctx -> new NPCRenderer<>(ctx, "начальник"));
        
        // Мама
        EntityRendererRegistry.register(ModEntities.МАМА, 
            ctx -> new NPCRenderer<>(ctx, "мама"));
        
        // Отец
        EntityRendererRegistry.register(ModEntities.ОТЕЦ, 
            ctx -> new NPCRenderer<>(ctx, "отец"));
        
        SymbolMod.LOGGER.info("✓ Зарегистрировано {} рендереров персонажей", 9);
    }
    
    private void registerScreens() {
        // Экраны будут регистрироваться через HandledScreens.register
        // когда создадим GUI классы
    }
}
