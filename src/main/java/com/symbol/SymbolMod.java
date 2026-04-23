package com.symbol;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.registry.*;
import com.symbol.config.SymbolConfig;

public class SymbolMod implements ModInitializer {
    public static final String MOD_ID = "symbol";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    public static SymbolConfig CONFIG;

    @Override
    public void onInitialize() {
        LOGGER.info("=================================");
        LOGGER.info("Инициализация мода 'Символ'...");
        LOGGER.info("=================================");
        
        // Загрузка конфига
        CONFIG = SymbolConfig.load();
        LOGGER.info("✓ Конфигурация загружена");
        
        // Регистрация всех блоков
        ModBlocks.register();
        LOGGER.info("✓ Зарегистрировано {} декоративных блоков", ModBlocks.getBlockCount());
        
        // Регистрация всех предметов
        ModItems.register();
        LOGGER.info("✓ Зарегистрирована Палочка Режиссёра и инструменты");
        
        // Регистрация NPC
        ModEntities.register();
        LOGGER.info("✓ Зарегистрировано {} персонажей", ModEntities.getEntityCount());
        
        // Регистрация катсцен
        CutsceneRegistry.register();
        LOGGER.info("✓ Зарегистрировано {} катсцен", CutsceneRegistry.getCutsceneCount());
        
        // Регистрация 3D кнопок
        Button3DRegistry.register();
        LOGGER.info("✓ Система 3D кнопок инициализирована");
        
        // Регистрация звуков
        ModSounds.register();
        LOGGER.info("✓ Звуковая система готова");
        
        LOGGER.info("=================================");
        LOGGER.info("Мод 'Символ' успешно загружен!");
        LOGGER.info("=================================");
    }
}
