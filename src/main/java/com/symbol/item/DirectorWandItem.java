package com.symbol.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.symbol.SymbolMod;
import com.symbol.client.gui.DirectorWandScreen;
import com.symbol.entity.NpcEntity;
import com.symbol.system.CutsceneSystem;

public class DirectorWandItem extends Item {

    // Режимы работы палочки
    public enum WandMode {
        SELECT,         // Выбор объектов
        CAMERA,         // Расстановка точек камеры
        NPC,            // Работа с NPC
        BUTTON,         // Создание 3D кнопок
        CUTSCENE,       // Управление катсценами
        BLOCK           // Работа с блоками
    }

    public DirectorWandItem(Settings settings) {
        super(settings);
    }

    // ============================================================
    // ГЛАВНЫЙ ОБРАБОТЧИК — ПКМ
    // ============================================================

    @Override
    public TypedActionResult<ItemStack> use(
        World world,
        PlayerEntity player,
        Hand hand
    ) {
        ItemStack stack = player.getStackInHand(hand);

        // Только для оператора карты
        if (!isMapMaker(player)) {
            player.sendMessage(
                Text.literal("§cПалочка Режиссёра доступна только для создателя карты."),
                false
            );
            return TypedActionResult.fail(stack);
        }

        // Shift + ПКМ — открыть главное меню
        if (player.isSneaking()) {
            if (world.isClient()) {
                openMainMenu(stack);
            }
            return TypedActionResult.success(stack);
        }

        // ПКМ в воздух — смотрим на что смотрит игрок
        HitResult hit = player.raycast(20.0, 0f, false);

        if (world.isClient()) {
            return TypedActionResult.success(stack);
        }

        // Сервер — обработка
        WandMode mode = getMode(stack);

        switch (mode) {
            case CAMERA -> handleCameraMode(
                player, hit, stack, world
            );
            case NPC -> handleNpcMode(
                player, hit, stack, world
            );
            case CUTSCENE -> handleCutsceneMode(
                player, hit, stack, world
            );
            default -> handleSelectMode(
                player, hit, stack, world
            );
        }

        return TypedActionResult.success(stack);
    }

    // ============================================================
    // ЛКМ — Дополнительные действия
    // ============================================================

    @Override
    public boolean onEntityHit(
        ItemStack stack,
        net.minecraft.entity.LivingEntity target,
        net.minecraft.entity.LivingEntity attacker
    ) {
        // Клик по NPC — открыть редактор NPC
        if (target instanceof NpcEntity npc &&
            attacker instanceof PlayerEntity player
        ) {
            if (isMapMaker(player)) {
                if (player.getWorld().isClient()) {
                    openNpcEditor(npc);
                }
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // РЕЖИМ ВЫБОРА
    // ============================================================

    private void handleSelectMode(
        PlayerEntity player,
        HitResult hit,
        ItemStack stack,
        World world
    ) {
        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult)hit).getBlockPos();
            BlockState state = world.getBlockState(pos);

            player.sendMessage(
                Text.literal(
                    "§7Блок: §f" + state.getBlock()
                        .getTranslationKey() +
                    "\n§7Позиция: §f" + pos.getX() +
                    " " + pos.getY() + " " + pos.getZ()
                ),
                false
            );
        }
    }

    // ============================================================
    // РЕЖИМ КАМЕРЫ
    // ============================================================

    private void handleCameraMode(
        PlayerEntity player,
        HitResult hit,
        ItemStack stack,
        World world
    ) {
        // Добавляем точку камеры на текущей позиции игрока
        double x = player.getX();
        double y = player.getEyeY();
        double z = player.getZ();
        float yaw = player.getYaw();
        float pitch = player.getPitch();

        // Сохраняем в NBT стека
        NbtCompound nbt = stack.getOrCreateNbt();
        int pointCount = nbt.getInt("camera_point_count");

        NbtCompound point = new NbtCompound();
        point.putDouble("x", x);
        point.putDouble("y", y);
        point.putDouble("z", z);
        point.putFloat("yaw", yaw);
        point.putFloat("pitch", pitch);
        point.putInt("duration", 60); // 3 секунды по умолчанию
        point.putString("easing", "SMOOTH");

        nbt.put("camera_point_" + pointCount, point);
        nbt.putInt("camera_point_count", pointCount + 1);

        player.sendMessage(
            Text.literal(
                "§a✓ §7Точка камеры " + (pointCount + 1) +
                " добавлена: §f" +
                String.format("%.1f", x) + " " +
                String.format("%.1f", y) + " " +
                String.format("%.1f", z)
            ),
            false
        );

        // Визуальная подсветка точки (частицы)
        if (world instanceof net.minecraft.server.world.ServerWorld sw) {
            sw.spawnParticles(
                net.minecraft.particle.ParticleTypes.END_ROD,
                x, y, z,
                5, 0.2, 0.2, 0.2, 0.05
            );
        }
    }

    // ============================================================
    // РЕЖИМ NPC
    // ============================================================

    private void handleNpcMode(
        PlayerEntity player,
        HitResult hit,
        ItemStack stack,
        World world
    ) {
        if (hit.getType() != HitResult.Type.BLOCK) {
            player.sendMessage(
                Text.literal("§7Кликните на блок чтобы заспавнить NPC"),
                true
            );
            return;
        }

        BlockPos pos = ((BlockHitResult)hit).getBlockPos().up();

        // Получаем тип NPC из NBT
        NbtCompound nbt = stack.getOrCreateNbt();
        String npcType = nbt.getString("selected_npc_type");

        if (npcType.isEmpty()) {
            player.sendMessage(
                Text.literal("§cСначала выберите тип NPC в меню (Shift+ПКМ)"),
                false
            );
            return;
        }

        // Спавним NPC
        spawnNpc(world, pos, npcType, player);
    }

    private void spawnNpc(
        World world,
        BlockPos pos,
        String npcType,
        PlayerEntity player
    ) {
        // Создаём NPC нужного типа
        com.symbol.registry.ModEntities.GROMOV
            .create(world);

        NpcEntity npc = switch (npcType) {
            case "gromov" ->
                com.symbol.registry.ModEntities.GROMOV.create(world);
            case "valeria" ->
                com.symbol.registry.ModEntities.VALERIA.create(world);
            case "nina" ->
                com.symbol.registry.ModEntities.NINA.create(world);
            case "rashid" ->
                com.symbol.registry.ModEntities.RASHID.create(world);
            case "tolya" ->
                com.symbol.registry.ModEntities.TOLYA.create(world);
            case "semonych" ->
                com.symbol.registry.ModEntities.SEMONYCH.create(world);
            case "boss" ->
                com.symbol.registry.ModEntities.BOSS.create(world);
            case "mom" ->
                com.symbol.registry.ModEntities.MOM.create(world);
            case "father" ->
                com.symbol.registry.ModEntities.FATHER.create(world);
            default -> null;
        };

        if (npc == null) {
            player.sendMessage(
                Text.literal("§cНеизвестный тип NPC: " + npcType),
                false
            );
            return;
        }

        // Настраиваем NPC
        npc.setNpcType(npcType);
        npc.setNpcName(getNpcDisplayName(npcType));
        npc.refreshPositionAndAngles(
            pos.getX() + 0.5,
            pos.getY(),
            pos.getZ() + 0.5,
            player.getYaw() + 180,
            0
        );

        world.spawnEntity(npc);

        player.sendMessage(
            Text.literal(
                "§a✓ §7NPC заспавнен: §f" +
                getNpcDisplayName(npcType)
            ),
            false
        );
    }

    // ============================================================
    // РЕЖИМ КАТСЦЕНЫ
    // ============================================================

    private void handleCutsceneMode(
        PlayerEntity player,
        HitResult hit,
        ItemStack stack,
        World world
    ) {
        NbtCompound nbt = stack.getOrCreateNbt();
        String selectedCutscene = nbt.getString("selected_cutscene");

        if (selectedCutscene.isEmpty()) {
            player.sendMessage(
                Text.literal("§7Выберите катсцену в меню (Shift+ПКМ)"),
                true
            );
            return;
        }

        // Предпросмотр катсцены
        if (player instanceof ServerPlayerEntity sp) {
            player.sendMessage(
                Text.literal("§7Запуск катсцены: §f" + selectedCutscene),
                false
            );
            CutsceneSystem.startCutscene(sp, selectedCutscene);
        }
    }

    // ============================================================
    // ОТКРЫТИЕ GUI
    // ============================================================

    public static void openMainMenu(ItemStack stack) {
        MinecraftClient.getInstance().setScreen(
            new DirectorWandScreen(stack)
        );
    }

    public static void openBlockEditor(BlockPos pos, BlockState state) {
        MinecraftClient.getInstance().setScreen(
            new DirectorWandScreen.BlockEditorScreen(pos, state)
        );
    }

    public static void openNpcEditor(NpcEntity npc) {
        MinecraftClient.getInstance().setScreen(
            new DirectorWandScreen.NpcEditorScreen(npc)
        );
    }

    // ============================================================
    // РЕЖИМ — ГЕТТЕР/СЕТТЕР
    // ============================================================

    public static WandMode getMode(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return WandMode.SELECT;

        String modeName = nbt.getString("wand_mode");
        try {
            return WandMode.valueOf(modeName);
        } catch (IllegalArgumentException e) {
            return WandMode.SELECT;
        }
    }

    public static void setMode(ItemStack stack, WandMode mode) {
        stack.getOrCreateNbt().putString(
            "wand_mode", mode.name()
        );
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private boolean isMapMaker(PlayerEntity player) {
        // Проверяем уровень оператора
        if (player instanceof ServerPlayerEntity sp) {
            return sp.hasPermissionLevel(2);
        }
        return SymbolMod.CONFIG.devMode;
    }

    private String getNpcDisplayName(String npcType) {
        return switch (npcType) {
            case "gromov"   -> "Громов";
            case "valeria"  -> "Валерия";
            case "nina"     -> "Нина";
            case "rashid"   -> "Рашид";
            case "tolya"    -> "Толя";
            case "semonych" -> "Семёныч";
            case "boss"     -> "Начальник";
            case "mom"      -> "Мама";
            case "father"   -> "Отец";
            default         -> npcType;
        };
    }

    // Экспортировать путь камеры в Java код
    public static String exportCameraPath(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return "// Нет точек камеры";

        int count = nbt.getInt("camera_point_count");
        if (count == 0) return "// Нет точек камеры";

        StringBuilder sb = new StringBuilder();
        sb.append("new CameraPath()\n");

        for (int i = 0; i < count; i++) {
            NbtCompound point = nbt.getCompound("camera_point_" + i);
            sb.append(String.format(
                "    .addKeyframe(%.2f, %.2f, %.2f, %.1ff, %.1ff, %d)\n",
                point.getDouble("x"),
                point.getDouble("y"),
                point.getDouble("z"),
                point.getFloat("yaw"),
                point.getFloat("pitch"),
                point.getInt("duration")
            ));
        }

        return sb.toString();
    }

    // Очистить все точки камеры
    public static void clearCameraPoints(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int count = nbt.getInt("camera_point_count");
        for (int i = 0; i < count; i++) {
            nbt.remove("camera_point_" + i);
        }
        nbt.putInt("camera_point_count", 0);
    }
}
