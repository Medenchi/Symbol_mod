package com.symbol.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import com.symbol.system.CutsceneSystem;
import com.symbol.system.DialogueSystem;
import com.symbol.system.EvidenceSystem;
import com.symbol.registry.ModSounds;

public class NpcEntity extends PassiveEntity implements GeoEntity {

    // ============================================================
    // GECKOLIB
    // ============================================================

    private final AnimatableInstanceCache cache =
        GeckoLibUtil.createInstanceCache(this);

    // Имя анимации которая сейчас играет
    private String currentAnimation = "idle";

    // ID диалога этого NPC
    private String dialogueId = "";

    // ID катсцены при взаимодействии
    private String cutsceneId = "";

    // Имя NPC (отображается над головой)
    private String npcName = "NPC";

    // Тип NPC (определяет модель)
    private String npcType = "default";

    // Смотреть ли на игрока
    private boolean lookAtPlayer = true;

    // Можно ли взаимодействовать
    private boolean interactable = true;

    // Была ли уже первая встреча
    private boolean firstMeetingDone = false;

    // ============================================================
    // КОНСТРУКТОР
    // ============================================================

    public NpcEntity(EntityType<? extends PassiveEntity> type, World world) {
        super(type, world);
        this.setInvulnerable(true);
        this.setAiDisabled(false);
    }

    // ============================================================
    // АТРИБУТЫ
    // ============================================================

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
            // NPC не двигаются сами
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    // ============================================================
    // AI ЦЕЛИ
    // ============================================================

    @Override
    protected void initGoals() {
        // Только смотрение на игрока
        // NPC стоят неподвижно
        this.goalSelector.add(1,
            new LookAtEntityGoal(this, PlayerEntity.class, 8.0f)
        );
        this.goalSelector.add(2,
            new LookAroundGoal(this)
        );
    }

    // ============================================================
    // ВЗАИМОДЕЙСТВИЕ
    // ============================================================

    @Override
    protected ActionResult interactMob(
        PlayerEntity player,
        Hand hand
    ) {
        if (!interactable) return ActionResult.PASS;
        if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

        if (!this.getWorld().isClient()) {
            // Сначала смотрим на игрока
            this.faceTowards(player);

            // Если есть катсцена при первой встрече
            if (!firstMeetingDone && !cutsceneId.isEmpty()) {
                firstMeetingDone = true;
                CutsceneSystem.startCutscene(player, cutsceneId);
                return ActionResult.SUCCESS;
            }

            // Если есть диалог — запускаем
            if (!dialogueId.isEmpty()) {
                DialogueSystem.startDialogue(
                    (net.minecraft.server.network.ServerPlayerEntity) player,
                    dialogueId
                );
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.SUCCESS;
    }

    // NPC смотрит на игрока
    private void faceTowards(PlayerEntity player) {
        double dx = player.getX() - this.getX();
        double dz = player.getZ() - this.getZ();
        float yaw = (float)(Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90f;
        this.setYaw(yaw);
        this.setHeadYaw(yaw);
    }

    // ============================================================
    // GECKOLIB АНИМАЦИИ
    // ============================================================

    @Override
    public void registerControllers(
        AnimatableManager.ControllerRegistrar controllers
    ) {
        controllers.add(new AnimationController<>(
            this,
            "main_controller",
            0,
            this::mainAnimationPredicate
        ));
    }

    private PlayState mainAnimationPredicate(
        AnimationState<NpcEntity> state
    ) {
        // Играем текущую анимацию
        state.getController().setAnimation(
            RawAnimation.begin()
                .thenLoop(currentAnimation)
        );
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // Установить анимацию (вызывается из катсцен)
    public void playAnimation(String animationName) {
        this.currentAnimation = animationName;
    }

    // ============================================================
    // NBT (сохранение/загрузка)
    // ============================================================

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("dialogue_id", dialogueId);
        nbt.putString("cutscene_id", cutsceneId);
        nbt.putString("npc_name", npcName);
        nbt.putString("npc_type", npcType);
        nbt.putBoolean("look_at_player", lookAtPlayer);
        nbt.putBoolean("interactable", interactable);
        nbt.putBoolean("first_meeting_done", firstMeetingDone);
        nbt.putString("current_animation", currentAnimation);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dialogueId = nbt.getString("dialogue_id");
        cutsceneId = nbt.getString("cutscene_id");
        npcName = nbt.getString("npc_name");
        npcType = nbt.getString("npc_type");
        lookAtPlayer = nbt.getBoolean("look_at_player");
        interactable = nbt.getBoolean("interactable");
        firstMeetingDone = nbt.getBoolean("first_meeting_done");
        currentAnimation = nbt.getString("current_animation");
        if (currentAnimation.isEmpty()) currentAnimation = "idle";
    }

    // ============================================================
    // ДОЧЕРНИЕ NPC (не размножаются)
    // ============================================================

    @Override
    public PassiveEntity createChild(
        ServerWorld world,
        PassiveEntity entity
    ) {
        return null; // NPC не размножаются
    }

    // ============================================================
    // ГЕТТЕРЫ И СЕТТЕРЫ
    // ============================================================

    public String getDialogueId() { return dialogueId; }
    public void setDialogueId(String id) { this.dialogueId = id; }

    public String getCutsceneId() { return cutsceneId; }
    public void setCutsceneId(String id) { this.cutsceneId = id; }

    public String getNpcName() { return npcName; }
    public void setNpcName(String name) { this.npcName = name; }

    public String getNpcType() { return npcType; }
    public void setNpcType(String type) { this.npcType = type; }

    public boolean isInteractable() { return interactable; }
    public void setInteractable(boolean v) { this.interactable = v; }

    public boolean isLookAtPlayer() { return lookAtPlayer; }
    public void setLookAtPlayer(boolean v) { this.lookAtPlayer = v; }

    public void resetFirstMeeting() { this.firstMeetingDone = false; }
}
