package com.symbol.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;
import com.symbol.block.*;

public class ModBlocks {

    // ==============================
    // ДЕРЕВО / ДОСКИ
    // ==============================
    public static final Block ROTTEN_PLANK = register("rotten_plank",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.5f, 0.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block ROTTEN_PLANK_WORN = register("rotten_plank_worn",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.3f, 0.3f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block ROTTEN_PLANK_DESTROYED = register("rotten_plank_destroyed",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.1f, 0.1f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block SOVIET_PLANK = register("soviet_plank",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    // ==============================
    // ПЛИТКА
    // ==============================
    public static final Block SOVIET_TILE = register("soviet_tile",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block SOVIET_TILE_WORN = register("soviet_tile_worn",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.2f, 4.0f)));

    public static final Block SOVIET_TILE_DESTROYED = register("soviet_tile_destroyed",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(0.8f, 2.0f)));

    public static final Block SOVIET_TILE_WHITE = register("soviet_tile_white",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block SOVIET_TILE_GREEN = register("soviet_tile_green",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block SOVIET_TILE_YELLOW = register("soviet_tile_yellow",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    // ==============================
    // СТЕКЛО
    // ==============================
    public static final Block OLD_GLASS = register("old_glass",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.3f, 0.3f).nonOpaque()));

    public static final Block CRACKED_GLASS = register("cracked_glass",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block MURKY_GLASS = register("murky_glass",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.3f, 0.3f).nonOpaque()));

    // ==============================
    // МЕТАЛЛ / РЖАВЧИНА
    // ==============================
    public static final Block RUSTY_PANEL = register("rusty_panel",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block RUSTY_PANEL_WORN = register("rusty_panel_worn",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(2.0f, 4.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block RUSTY_PANEL_DESTROYED = register("rusty_panel_destroyed",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(1.0f, 2.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block METAL_PLATE = register("metal_plate",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(4.0f, 8.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block PERFORATED_METAL = register("perforated_metal",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ТРУБЫ
    // ==============================
    public static final Block PIPE_VERTICAL = register("pipe_vertical",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block PIPE_HORIZONTAL = register("pipe_horizontal",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block PIPE_RUSTY = register("pipe_rusty",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(2.0f, 4.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block PIPE_CORNER = register("pipe_corner",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ЛАМПЫ
    // ==============================
    public static final Block LAMP_SOVIET = register("lamp_soviet",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)
            .luminance(state -> state.get(LampBlock.LIT) ? 15 : 0)
            .nonOpaque()));

    public static final Block LAMP_SOVIET_FLICKER = register("lamp_soviet_flicker",
        new FlickerLampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)
            .luminance(state -> state.get(LampBlock.LIT) ? 12 : 0)
            .nonOpaque()));

    public static final Block LAMP_EMERGENCY = register("lamp_emergency",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)
            .luminance(state -> state.get(LampBlock.LIT) ? 8 : 0)
            .nonOpaque()));

    public static final Block LAMP_RED = register("lamp_red",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)
            .luminance(state -> state.get(LampBlock.LIT) ? 10 : 0)
            .nonOpaque()));

    public static final Block LAMP_FLUORESCENT = register("lamp_fluorescent",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.3f, 0.3f)
            .luminance(state -> state.get(LampBlock.LIT) ? 15 : 0)
            .nonOpaque()));

    // ==============================
    // СТЕНЫ
    // ==============================
    public static final Block WORN_WALL = register("worn_wall",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block WORN_WALL_HEAVY = register("worn_wall_heavy",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.0f, 4.0f)));

    public static final Block PLASTER_WALL = register("plaster_wall",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block SOVIET_BRICK = register("soviet_brick",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.BRICK_BLOCK)
            .strength(2.0f, 6.0f)));

    public static final Block SOVIET_BRICK_CRACKED = register("soviet_brick_cracked",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.BRICK_BLOCK)
            .strength(1.5f, 4.0f)));

    // ==============================
    // ПОЛЫ
    // ==============================
    public static final Block LINOLEUM = register("linoleum",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)));

    public static final Block LINOLEUM_WORN = register("linoleum_worn",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(0.3f, 0.3f)));

    public static final Block CONCRETE_SLAB_DECO = register("concrete_slab_deco",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 6.0f)));

    public static final Block CONCRETE_SLAB_CRACKED = register("concrete_slab_cracked",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 4.0f)));

    // ==============================
    // МЕБЕЛЬ
    // ==============================
    public static final Block SOVIET_TABLE = register("soviet_table",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    public static final Block SOVIET_CHAIR = register("soviet_chair",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.0f, 1.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    public static final Block SOVIET_WARDROBE = register("soviet_wardrobe",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(2.0f, 2.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    public static final Block SOVIET_BED_DECO = register("soviet_bed_deco",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    public static final Block SOVIET_SOFA = register("soviet_sofa",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOL)
            .nonOpaque()));

    public static final Block SOVIET_FRIDGE = register("soviet_fridge",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(3.0f, 3.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.METAL)
            .nonOpaque()));

    public static final Block SOVIET_STOVE = register("soviet_stove",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(3.0f, 3.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.METAL)
            .nonOpaque()));

    public static final Block SOVIET_DESK = register("soviet_desk",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    public static final Block SOVIET_BOOKSHELF = register("soviet_bookshelf",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)
            .nonOpaque()));

    // ==============================
    // ЯЩИКИ И КОНТЕЙНЕРЫ
    // ==============================
    public static final Block WOODEN_CRATE = register("wooden_crate",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.5f, 1.5f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block METAL_CRATE = register("metal_crate",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block SOVIET_BARREL = register("soviet_barrel",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(2.0f, 2.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.METAL)
            .nonOpaque()));

    // ==============================
    // ПЛАКАТЫ
    // ==============================
    public static final Block SOVIET_POSTER_1 = register("soviet_poster_1",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block SOVIET_POSTER_2 = register("soviet_poster_2",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block SOVIET_POSTER_3 = register("soviet_poster_3",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block WANTED_POSTER = register("wanted_poster",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block NOTICE_BOARD = register("notice_board",
        new PosterBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.0f, 1.0f).nonOpaque()));

    // ==============================
    // ЗАВОДСКИЕ БЛОКИ
    // ==============================
    public static final Block FACTORY_FLOOR = register("factory_floor",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 6.0f)));

    public static final Block FACTORY_WALL = register("factory_wall",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 8.0f)));

    public static final Block ROTTEN_FLOOR = register("rotten_floor",
        new TrapFloorBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.1f, 0.1f)
            .sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block FACTORY_MACHINE = register("factory_machine",
        new FurnitureBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(5.0f, 10.0f)
            .sounds(net.minecraft.block.BlockSoundGroup.METAL)
            .nonOpaque()));

    public static final Block GLASS_MOLD = register("glass_mold",
        new KeyPropBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(1.0f, 1.0f).nonOpaque()));

    // ==============================
    // СПЕЦИАЛЬНЫЕ СЮЖЕТНЫЕ БЛОКИ
    // ==============================
    public static final Block SYMBOL_WALL = register("symbol_wall",
        new SymbolBlock(FabricBlockSettings.create()
            .strength(0.1f, 0.1f)
            .nonOpaque()
            .luminance(s -> 2)));

    public static final Block SYMBOL_FLOOR = register("symbol_floor",
        new SymbolBlock(FabricBlockSettings.create()
            .strength(0.1f, 0.1f)
            .nonOpaque()));

    public static final Block GLASS_CORPSE = register("glass_corpse",
        new GlassCorpseBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(2.0f, 2.0f)
            .nonOpaque()
            .luminance(s -> 3)));

    public static final Block DIARY_BLOCK = register("diary_block",
        new DiaryBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f)
            .nonOpaque()));

    // ==============================
    // 3D КНОПКИ
    // ==============================
    public static final Block BUTTON_3D_START = register("button_3d_start",
        new Button3DBlock(FabricBlockSettings.create()
            .strength(-1.0f, 3600000.0f)
            .nonOpaque()
            .luminance(s -> 8)));

    public static final Block BUTTON_3D_CHOICE = register("button_3d_choice",
        new Button3DChoiceBlock(FabricBlockSettings.create()
            .strength(-1.0f, 3600000.0f)
            .nonOpaque()
            .luminance(s -> 6)));

    // ==============================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ==============================
    private static int blockCount = 0;

    private static Block register(String name, Block block) {
        blockCount++;
        Registry.register(
            Registries.BLOCK,
            new Identifier(SymbolMod.MOD_ID, name),
            block
        );
        Registry.register(
            Registries.ITEM,
            new Identifier(SymbolMod.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings())
        );
        return block;
    }

    public static int getBlockCount() { return blockCount; }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация декоративных блоков...");
    }
}
