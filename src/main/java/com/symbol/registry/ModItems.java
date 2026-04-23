package com.symbol.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import com.symbol.SymbolMod;
import com.symbol.item.*;

public class ModItems {

    // ==============================
    // ИНСТРУМЕНТЫ РЕЖИССЁРА
    // ==============================
    public static final Item ПАЛОЧКА_РЕЖИССЁРА = register(
        "палочка_режиссёра",
        new DirectorWandItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.EPIC))
    );

    // ==============================
    // ПРЕДМЕТЫ СЮЖЕТА
    // ==============================
    public static final Item ДНЕВНИК = register(
        "дневник",
        new DiaryItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.UNCOMMON))
    );

    public static final Item ПАПКА_ДЕЛА = register(
        "папка_дела",
        new CaseFileItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item ФОНАРИК = register(
        "фонарик",
        new FlashlightItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item ЗНАЧОК_ДЕТЕКТИВА = register(
        "значок_детектива",
        new Item(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.UNCOMMON))
    );

    public static final Item ЗАЖИГАЛКА = register(
        "зажигалка",
        new Item(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item ФОТОГРАФИЯ_РОДИТЕЛЕЙ = register(
        "фотография_родителей",
        new PhotoItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item КЛЮЧ_ПОДВАЛА = register(
        "ключ_подвала",
        new Item(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.RARE))
    );

    public static final Item ДНЕВНИК_РАБОЧЕГО = register(
        "дневник_рабочего",
        new DiaryItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.RARE))
    );

    // ==============================
    // УЛИКИ
    // ==============================
    public static final Item УЛИКА_ЖУРНАЛ_ПОСЕЩЕНИЙ = register(
        "улика_журнал_посещений",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "journal_1979")
    );

    public static final Item УЛИКА_НАКЛАДНАЯ_К7 = register(
        "улика_накладная_к7",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "invoice_k7")
    );

    public static final Item УЛИКА_ЗАПИСНАЯ_КНИЖКА = register(
        "улика_записная_книжка",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "director_notebook")
    );

    public static final Item УЛИКА_ДНЕВНИК_ПОДВАЛА = register(
        "улика_дневник_подвала",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "basement_diary")
    );

    public static final Item УЛИКА_РОБА_АЛИЕВА = register(
        "улика_роба_алиева",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "aliev_robe")
    );

    public static final Item УЛИКА_ФОТО_ОХРАННИКА = register(
        "улика_фото_охранника",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "guard_photo")
    );

    public static final Item УЛИКА_СХЕМА_ТЕХНОЛОГИЧЕСКАЯ = register(
        "улика_схема_технологическая",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "tech_scheme")
    );

    // ==============================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ==============================
    private static Item register(String name, Item item) {
        return Registry.register(
            Registries.ITEM,
            new Identifier(SymbolMod.MOD_ID, name),
            item
        );
    }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация предметов...");
    }
}
