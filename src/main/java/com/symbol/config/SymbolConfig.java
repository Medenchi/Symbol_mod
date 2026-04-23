package com.symbol.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.symbol.SymbolMod;

import java.io.*;
import java.nio.file.*;

public class SymbolConfig {
    
    // Настройки катсцен
    public boolean enableLetterbox = true;
    public float letterboxHeight = 0.15f;
    public boolean enableCutsceneMusic = true;
    public float cutsceneMusicVolume = 0.7f;
    
    // Настройки паранойи
    public boolean enableParanoiaEffects = true;
    public float paranoiaIntensity = 1.0f;
    public boolean enableSymbolGlitches = true;
    
    // Настройки 3D кнопок
    public boolean enableButton3DGlow = true;
    public float button3DScale = 1.0f;
    public boolean freezePlayerOnChoice = true;
    
    // Настройки озвучки
    public boolean enableVoiceActing = true;
    public float voiceVolume = 1.0f;
    public boolean showSubtitles = true;
    
    // Режим разработчика
    public boolean devMode = false;
    public boolean showDebugInfo = false;
    public boolean unlockAllCutscenes = false;
    
    private static final Path CONFIG_PATH = Paths.get("config/symbol-mod.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public static SymbolConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                SymbolMod.LOGGER.info("Загрузка конфигурации из {}", CONFIG_PATH);
                return GSON.fromJson(reader, SymbolConfig.class);
            } catch (Exception e) {
                SymbolMod.LOGGER.error("Ошибка загрузки конфига, создаём новый", e);
            }
        }
        
        SymbolConfig config = new SymbolConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
                SymbolMod.LOGGER.info("Конфигурация сохранена в {}", CONFIG_PATH);
            }
        } catch (Exception e) {
            SymbolMod.LOGGER.error("Ошибка сохранения конфига", e);
        }
    }
}
