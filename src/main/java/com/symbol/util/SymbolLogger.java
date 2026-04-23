package com.symbol.util;

import com.symbol.SymbolMod;

public class SymbolLogger {
    
    public static void info(String message, Object... args) {
        SymbolMod.LOGGER.info(message, args);
    }
    
    public static void warn(String message, Object... args) {
        SymbolMod.LOGGER.warn(message, args);
    }
    
    public static void error(String message, Object... args) {
        SymbolMod.LOGGER.error(message, args);
    }
    
    public static void debug(String message, Object... args) {
        if (SymbolMod.CONFIG != null && SymbolMod.CONFIG.showDebugInfo) {
            SymbolMod.LOGGER.info("[DEBUG] " + message, args);
        }
    }
    
    public static void cutscene(String cutsceneName, String action) {
        info("Катсцена '{}': {}", cutsceneName, action);
    }
    
    public static void dialogue(String npcName, String dialogueId) {
        debug("Диалог с '{}': ID={}", npcName, dialogueId);
    }
}
