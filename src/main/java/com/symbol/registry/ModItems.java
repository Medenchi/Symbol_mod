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
    // ИНСТРУМЕНТЫ
    // ==============================
    public static final Item DIRECTORS_WAND = register(
        "directors_wand",
        new DirectorWandItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.EPIC))
    );

    // ==============================
    // ПРЕДМЕТЫ СЮЖЕТА
    // ==============================
    public static final Item DIARY = register(
        "diary",
        new DiaryItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.UNCOMMON))
    );

    public static final Item CASE_FILE = register(
        "case_file",
        new CaseFileItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item FLASHLIGHT = register(
        "flashlight",
        new FlashlightItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item DETECTIVE_BADGE = register(
        "detective_badge",
        new Item(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.UNCOMMON))
    );

    public static final Item LIGHTER = register(
        "lighter",
        new Item(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item PARENTS_PHOTO = register(
        "parents_photo",
        new PhotoItem(new FabricItemSettings()
            .maxCount(1))
    );

    public static final Item BASEMENT_KEY = register(
        "basement_key",
        new Item(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.RARE))
    );

    public static final Item WORKER_DIARY = register(
        "worker_diary",
        new DiaryItem(new FabricItemSettings()
            .maxCount(1)
            .rarity(Rarity.RARE))
    );

    public static final Item SPARE_FLASHLIGHT = register(
        "spare_flashlight",
        new FlashlightItem(new FabricItemSettings()
            .maxCount(1))
    );

    // ==============================
    // УЛИКИ
    // ==============================
    public static final Item EVIDENCE_VISIT_JOURNAL = register(
        "evidence_visit_journal",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "visit_journal")
    );

    public static final Item EVIDENCE_INVOICE_K7 = register(
        "evidence_invoice_k7",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "invoice_k7")
    );

    public static final Item EVIDENCE_DIRECTOR_NOTEBOOK = register(
        "evidence_director_notebook",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "director_notebook")
    );

    public static final Item EVIDENCE_BASEMENT_DIARY = register(
        "evidence_basement_diary",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "basement_diary")
    );

    public static final Item EVIDENCE_ALIEV_ROBE = register(
        "evidence_aliev_robe",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "aliev_robe")
    );

    public static final Item EVIDENCE_GUARD_PHOTO = register(
        "evidence_guard_photo",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "guard_photo")
    );

    public static final Item EVIDENCE_TECH_SCHEME = register(
        "evidence_tech_scheme",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "tech_scheme")
    );

    public static final Item EVIDENCE_NOTICE_BOARD = register(
        "evidence_notice_board",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "notice_board")
    );

    public static final Item EVIDENCE_SAFETY_POSTER = register(
        "evidence_safety_poster",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "safety_poster")
    );

    public static final Item EVIDENCE_MATTRESSES = register(
        "evidence_mattresses",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "mattresses")
    );

    public static final Item EVIDENCE_CANS = register(
        "evidence_cans",
        new EvidenceItem(new FabricItemSettings().maxCount(1), "cans")
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
