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
    public static final Block ГНИЛАЯ_ДОСКА = register("гнилая_доска",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.5f, 0.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block ГНИЛАЯ_ДОСКА_ПОТРЕПАННАЯ = register("гнилая_доска_потрепанная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.3f, 0.3f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block ГНИЛАЯ_ДОСКА_РАЗРУШЕННАЯ = register("гнилая_доска_разрушенная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.1f, 0.1f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block СОВЕТСКАЯ_ДОСКА = register("советская_доска",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    // ==============================
    // ПЛИТКА
    // ==============================
    public static final Block СОВЕТСКАЯ_ПЛИТКА = register("советская_плитка",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block СОВЕТСКАЯ_ПЛИТКА_ПОТРЕПАННАЯ = register("советская_плитка_потрепанная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.2f, 4.0f)));

    public static final Block СОВЕТСКАЯ_ПЛИТКА_РАЗРУШЕННАЯ = register("советская_плитка_разрушенная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(0.8f, 2.0f)));

    public static final Block СОВЕТСКАЯ_ПЛИТКА_БЕЛАЯ = register("советская_плитка_белая",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block СОВЕТСКАЯ_ПЛИТКА_ЗЕЛЕНАЯ = register("советская_плитка_зеленая",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block СОВЕТСКАЯ_ПЛИТКА_ЖЕЛТАЯ = register("советская_плитка_желтая",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    // ==============================
    // СТЕКЛО
    // ==============================
    public static final Block СТАРОЕ_СТЕКЛО = register("старое_стекло",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.3f, 0.3f).nonOpaque()));

    public static final Block ТРЕСНУТОЕ_СТЕКЛО = register("треснутое_стекло",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block МУТНОЕ_СТЕКЛО = register("мутное_стекло",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(0.3f, 0.3f).nonOpaque()));

    // ==============================
    // МЕТАЛЛ / РЖАВЧИНА
    // ==============================
    public static final Block РЖАВАЯ_ПАНЕЛЬ = register("ржавая_панель",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block РЖАВАЯ_ПАНЕЛЬ_ПОТРЕПАННАЯ = register("ржавая_панель_потрепанная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(2.0f, 4.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block РЖАВАЯ_ПАНЕЛЬ_РАЗРУШЕННАЯ = register("ржавая_панель_разрушенная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(1.0f, 2.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block МЕТАЛЛИЧЕСКАЯ_ПЛИТА = register("металлическая_плита",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(4.0f, 8.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block ПЕРФОРИРОВАННЫЙ_МЕТАЛЛ = register("перфорированный_металл",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ТРУБЫ
    // ==============================
    public static final Block ТРУБА_ВЕРТИКАЛЬНАЯ = register("труба_вертикальная",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block ТРУБА_ГОРИЗОНТАЛЬНАЯ = register("труба_горизонтальная",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block ТРУБА_РЖАВАЯ = register("труба_ржавая",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(2.0f, 4.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block ТРУБА_УГОЛ = register("труба_угол",
        new PipeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ЛАМПЫ
    // ==============================
    public static final Block ЛАМПА_СОВЕТСКАЯ = register("лампа_советская",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f).luminance(state ->
                state.get(LampBlock.LIT) ? 15 : 0).nonOpaque()));

    public static final Block ЛАМПА_СОВЕТСКАЯ_МИГАЮЩАЯ = register("лампа_советская_мигающая",
        new FlickerLampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f).luminance(state ->
                state.get(LampBlock.LIT) ? 12 : 0).nonOpaque()));

    public static final Block ЛАМПА_АВАРИЙНАЯ = register("лампа_аварийная",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f).luminance(state ->
                state.get(LampBlock.LIT) ? 8 : 0).nonOpaque()));

    public static final Block ЛАМПА_КРАСНАЯ = register("лампа_красная",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f).luminance(state ->
                state.get(LampBlock.LIT) ? 10 : 0).nonOpaque()));

    public static final Block ЛЮМИНЕСЦЕНТНАЯ_ЛАМПА = register("люминесцентная_лампа",
        new LampBlock(FabricBlockSettings.create()
            .strength(0.3f, 0.3f).luminance(state ->
                state.get(LampBlock.LIT) ? 15 : 0).nonOpaque()));

    // ==============================
    // СТЕНЫ
    // ==============================
    public static final Block ОБШАРПАННАЯ_СТЕНА = register("обшарпанная_стена",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block ОБШАРПАННАЯ_СТЕНА_ПОТРЕПАННАЯ = register("обшарпанная_стена_потрепанная",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.0f, 4.0f)));

    public static final Block СТЕНА_СО_ШТУКАТУРКОЙ = register("стена_со_штукатуркой",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 6.0f)));

    public static final Block СТЕНА_КИРПИЧ_СОВЕТСКИЙ = register("стена_кирпич_советский",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.BRICK_BLOCK)
            .strength(2.0f, 6.0f)));

    public static final Block СТЕНА_КИРПИЧ_ТРЕСНУТЫЙ = register("стена_кирпич_треснутый",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.BRICK_BLOCK)
            .strength(1.5f, 4.0f)));

    // ==============================
    // ПОЛЫ
    // ==============================
    public static final Block ЛИНОЛЕУМ = register("линолеум",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(0.5f, 0.5f)));

    public static final Block ЛИНОЛЕУМ_ПОТРЕПАННЫЙ = register("линолеум_потрепанный",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(0.3f, 0.3f)));

    public static final Block БЕТОННАЯ_ПЛИТА = register("бетонная_плита",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 6.0f)));

    public static final Block БЕТОННАЯ_ПЛИТА_ТРЕСНУТАЯ = register("бетонная_плита_треснутая",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(1.5f, 4.0f)));

    // ==============================
    // МЕБЕЛЬ
    // ==============================
    public static final Block СОВЕТСКИЙ_СТОЛ = register("советский_стол",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD).nonOpaque()));

    public static final Block СОВЕТСКИЙ_СТУЛ = register("советский_стул",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.0f, 1.0f).sounds(net.minecraft.block.BlockSoundGroup.WOOD).nonOpaque()));

    public static final Block СОВЕТСКИЙ_ШКАФ = register("советский_шкаф",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(2.0f, 2.0f).sounds(net.minecraft.block.BlockSoundGroup.WOOD).nonOpaque()));

    public static final Block СОВЕТСКАЯ_КРОВАТЬ_ДЕКОР = register("советская_кровать_декор",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD).nonOpaque()));

    public static final Block СОВЕТСКИЙ_ДИВАН = register("советский_диван",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOL).nonOpaque()));

    public static final Block СОВЕТСКИЙ_ХОЛОДИЛЬНИК = register("советский_холодильник",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(3.0f, 3.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block СОВЕТСКАЯ_ПЛИТА = register("советская_плита",
        new FurnitureBlock(FabricBlockSettings.create()
            .strength(3.0f, 3.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ЯЩИКИ И КОНТЕЙНЕРЫ
    // ==============================
    public static final Block ДЕРЕВЯННЫЙ_ЯЩИК = register("деревянный_ящик",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.5f, 1.5f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block МЕТАЛЛИЧЕСКИЙ_ЯЩИК = register("металлический_ящик",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(3.0f, 6.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL)));

    public static final Block СОВЕТСКАЯ_БОЧКА = register("советская_бочка",
        new DecorativeBlock(FabricBlockSettings.create()
            .strength(2.0f, 2.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    // ==============================
    // ПЛАКАТЫ
    // ==============================
    public static final Block СОВЕТСКИЙ_ПЛАКАТ_1 = register("советский_плакат_1",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block СОВЕТСКИЙ_ПЛАКАТ_2 = register("советский_плакат_2",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block СОВЕТСКИЙ_ПЛАКАТ_3 = register("советский_плакат_3",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block ПЛАКАТ_РОЗЫСК = register("плакат_розыск",
        new PosterBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    public static final Block ДОСКА_ОБЪЯВЛЕНИЙ = register("доска_объявлений",
        new PosterBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(1.0f, 1.0f).nonOpaque()));

    // ==============================
    // ЗАВОДСКИЕ БЛОКИ
    // ==============================
    public static final Block ЗАВОДСКОЙ_ПОЛ = register("заводской_пол",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 6.0f)));

    public static final Block ЗАВОДСКАЯ_СТЕНА = register("заводская_стена",
        new DecorativeBlock(FabricBlockSettings.copyOf(Blocks.STONE)
            .strength(2.0f, 8.0f)));

    public static final Block ПРОГНИВШИЙ_НАСТИЛ = register("прогнивший_настил",
        new TrapFloorBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .strength(0.1f, 0.1f).sounds(net.minecraft.block.BlockSoundGroup.WOOD)));

    public static final Block ЗАВОДСКОЙ_СТАНОК = register("заводской_станок",
        new FurnitureBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)
            .strength(5.0f, 10.0f).sounds(net.minecraft.block.BlockSoundGroup.METAL).nonOpaque()));

    public static final Block СТЕКЛЯННАЯ_ФОРМА = register("стеклянная_форма",
        new KeyPropBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(1.0f, 1.0f).nonOpaque()));

    // ==============================
    // СПЕЦИАЛЬНЫЕ БЛОКИ СЮЖЕТА
    // ==============================
    public static final Block СИМВОЛ_НА_СТЕНЕ = register("символ_на_стене",
        new SymbolBlock(FabricBlockSettings.create()
            .strength(0.1f, 0.1f).nonOpaque().luminance(s -> 2)));

    public static final Block СИМВОЛ_НА_ПОЛУ = register("символ_на_полу",
        new SymbolBlock(FabricBlockSettings.create()
            .strength(0.1f, 0.1f).nonOpaque()));

    public static final Block СТЕКЛО_С_ЧЕЛОВЕКОМ = register("стекло_с_человеком",
        new GlassCorpseBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .strength(2.0f, 2.0f).nonOpaque().luminance(s -> 3)));

    public static final Block ДНЕВНИК_БЛОК = register("дневник_блок",
        new DiaryBlock(FabricBlockSettings.create()
            .strength(0.2f, 0.2f).nonOpaque()));

    // ==============================
    // КНОПКИ СЮЖЕТНЫЕ
    // ==============================
    public static final Block КНОПКА_3D_СТАРТ = register("кнопка_3d_старт",
        new Button3DBlock(FabricBlockSettings.create()
            .strength(-1.0f, 3600000.0f).nonOpaque().luminance(s -> 8)));

    public static final Block КНОПКА_3D_ВЫБОР = register("кнопка_3d_выбор",
        new Button3DChoiceBlock(FabricBlockSettings.create()
            .strength(-1.0f, 3600000.0f).nonOpaque().luminance(s -> 6)));

    // ==============================
    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    // ==============================
    private static int blockCount = 0;

    private static Block register(String name, Block block) {
        blockCount++;
        Registry.register(Registries.BLOCK, new Identifier(SymbolMod.MOD_ID, name), block);
        Registry.register(Registries.ITEM,
            new Identifier(SymbolMod.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings()));
        return block;
    }

    public static int getBlockCount() {
        return blockCount;
    }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация декоративных блоков...");
    }
}
