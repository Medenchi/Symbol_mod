package com.symbol.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;
import com.symbol.system.CutsceneData;
import com.symbol.system.EvidenceSystem;
import com.symbol.system.DialogueSystem;

import java.util.List;

public class NetworkHandler {

    // ============================================================
    // ID ПАКЕТОВ
    // ============================================================

    // Сервер → Клиент
    public static final Identifier CUTSCENE_START =
        new Identifier(SymbolMod.MOD_ID, "cutscene_start");
    public static final Identifier CUTSCENE_END =
        new Identifier(SymbolMod.MOD_ID, "cutscene_end");
    public static final Identifier FREEZE_PLAYER =
        new Identifier(SymbolMod.MOD_ID, "freeze_player");
    public static final Identifier DIALOGUE_LINE =
        new Identifier(SymbolMod.MOD_ID, "dialogue_line");
    public static final Identifier DIALOGUE_END =
        new Identifier(SymbolMod.MOD_ID, "dialogue_end");
    public static final Identifier CHOICE_BUTTONS =
        new Identifier(SymbolMod.MOD_ID, "choice_buttons");
    public static final Identifier WAIT_FOR_INPUT =
        new Identifier(SymbolMod.MOD_ID, "wait_for_input");
    public static final Identifier SUBTITLE =
        new Identifier(SymbolMod.MOD_ID, "subtitle");
    public static final Identifier HIDE_SUBTITLE =
        new Identifier(SymbolMod.MOD_ID, "hide_subtitle");
    public static final Identifier SCREEN_TEXT =
        new Identifier(SymbolMod.MOD_ID, "screen_text");
    public static final Identifier HIDE_SCREEN_TEXT =
        new Identifier(SymbolMod.MOD_ID, "hide_screen_text");
    public static final Identifier FADE_TO_BLACK =
        new Identifier(SymbolMod.MOD_ID, "fade_to_black");
    public static final Identifier FADE_FROM_BLACK =
        new Identifier(SymbolMod.MOD_ID, "fade_from_black");
    public static final Identifier SCREEN_FLASH =
        new Identifier(SymbolMod.MOD_ID, "screen_flash");
    public static final Identifier CAMERA_SHAKE =
        new Identifier(SymbolMod.MOD_ID, "camera_shake");
    public static final Identifier PARANOIA_UPDATE =
        new Identifier(SymbolMod.MOD_ID, "paranoia_update");
    public static final Identifier PARANOIA_EFFECT =
        new Identifier(SymbolMod.MOD_ID, "paranoia_effect");
    public static final Identifier EVIDENCE_ADDED =
        new Identifier(SymbolMod.MOD_ID, "evidence_added");
    public static final Identifier NPC_ANIMATION =
        new Identifier(SymbolMod.MOD_ID, "npc_animation");
    public static final Identifier SPAWN_NPC =
        new Identifier(SymbolMod.MOD_ID, "spawn_npc");
    public static final Identifier HIDE_NPC =
        new Identifier(SymbolMod.MOD_ID, "hide_npc");
    public static final Identifier STOP_MUSIC =
        new Identifier(SymbolMod.MOD_ID, "stop_music");
    public static final Identifier ENDING_UNLOCKED =
        new Identifier(SymbolMod.MOD_ID, "ending_unlocked");

    // Клиент → Сервер
    public static final Identifier ADVANCE_DIALOGUE =
        new Identifier(SymbolMod.MOD_ID, "advance_dialogue");
    public static final Identifier CHOICE_SELECTED =
        new Identifier(SymbolMod.MOD_ID, "choice_selected");

    // ============================================================
    // РЕГИСТРАЦИЯ
    // ============================================================

    public static void registerServer() {
        // Клиент → Сервер: продвинуть диалог
        ServerPlayNetworking.registerGlobalReceiver(
            ADVANCE_DIALOGUE,
            (server, player, handler, buf, responseSender) -> {
                server.execute(() ->
                    DialogueSystem.onPlayerAdvanceDialogue(player)
                );
            }
        );

        // Клиент → Сервер: выбор в диалоге
        ServerPlayNetworking.registerGlobalReceiver(
            CHOICE_SELECTED,
            (server, player, handler, buf, responseSender) -> {
                int choiceIndex = buf.readInt();
                server.execute(() ->
                    DialogueSystem.onChoiceSelected(player, choiceIndex)
                );
            }
        );

        SymbolMod.LOGGER.info("✓ Серверные сетевые обработчики зарегистрированы");
    }

    public static void registerClient() {
        // Все клиентские обработчики регистрируются
        // в ClientNetworkHandler.java
        SymbolMod.LOGGER.info("✓ Клиентские сетевые обработчики зарегистрированы");
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — КАТСЦЕНЫ
    // ============================================================

    public static void sendCutsceneStart(
        ServerPlayerEntity player,
        CutsceneData data
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(data.id);
        buf.writeInt(data.totalDurationTicks);
        buf.writeBoolean(data.hasLetterbox);

        // Записываем путь камеры
        List<com.symbol.system.CameraPath.CameraKeyframe> keyframes =
            data.cameraPath.getKeyframes();
        buf.writeInt(keyframes.size());
        for (var kf : keyframes) {
            buf.writeDouble(kf.position.x);
            buf.writeDouble(kf.position.y);
            buf.writeDouble(kf.position.z);
            buf.writeFloat(kf.yaw);
            buf.writeFloat(kf.pitch);
            buf.writeFloat(kf.roll);
            buf.writeInt(kf.durationTicks);
            buf.writeString(kf.easing.name());
        }

        ServerPlayNetworking.send(player, CUTSCENE_START, buf);
    }

    public static void sendCutsceneEnd(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            CUTSCENE_END,
            PacketByteBufs.empty()
        );
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — ЗАМОРОЗКА
    // ============================================================

    public static void sendFreezePacket(
        ServerPlayerEntity player,
        boolean frozen,
        boolean lockCamera
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(frozen);
        buf.writeBoolean(lockCamera);
        ServerPlayNetworking.send(player, FREEZE_PLAYER, buf);
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — ДИАЛОГ
    // ============================================================

    public static void sendDialogueLine(
        ServerPlayerEntity player,
        String speakerName,
        String text,
        String speakerSide,
        String npcId
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(speakerName);
        buf.writeString(text);
        buf.writeString(speakerSide);
        buf.writeString(npcId);
        ServerPlayNetworking.send(player, DIALOGUE_LINE, buf);
    }

    public static void sendDialogueEnd(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            DIALOGUE_END,
            PacketByteBufs.empty()
        );
    }

    public static void sendChoiceButtons(
        ServerPlayerEntity player,
        List<DialogueSystem.DialogueChoice> choices
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(choices.size());
        for (var choice : choices) {
            buf.writeString(choice.text);
            buf.writeInt(choice.effectValue);
        }
        ServerPlayNetworking.send(player, CHOICE_BUTTONS, buf);
    }

    public static void sendWaitForInput(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            WAIT_FOR_INPUT,
            PacketByteBufs.empty()
        );
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — ЭКРАННЫЕ ЭФФЕКТЫ
    // ============================================================

    public static void sendSubtitle(
        ServerPlayerEntity player,
        String text
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(text);
        ServerPlayNetworking.send(player, SUBTITLE, buf);
    }

    public static void sendHideSubtitle(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            HIDE_SUBTITLE,
            PacketByteBufs.empty()
        );
    }

    public static void sendScreenText(
        ServerPlayerEntity player,
        String text
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(text);
        ServerPlayNetworking.send(player, SCREEN_TEXT, buf);
    }

    public static void sendHideScreenText(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            HIDE_SCREEN_TEXT,
            PacketByteBufs.empty()
        );
    }

    public static void sendFadeToBlack(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            FADE_TO_BLACK,
            PacketByteBufs.empty()
        );
    }

    public static void sendFadeFromBlack(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            FADE_FROM_BLACK,
            PacketByteBufs.empty()
        );
    }

    public static void sendScreenFlash(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            SCREEN_FLASH,
            PacketByteBufs.empty()
        );
    }

    public static void sendCameraShake(
        ServerPlayerEntity player,
        String intensity
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(intensity);
        ServerPlayNetworking.send(player, CAMERA_SHAKE, buf);
    }

    public static void sendStopMusic(ServerPlayerEntity player) {
        ServerPlayNetworking.send(
            player,
            STOP_MUSIC,
            PacketByteBufs.empty()
        );
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — ПАРАНОЙЯ
    // ============================================================

    public static void sendParanoiaUpdate(
        ServerPlayerEntity player,
        int level
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(level);
        ServerPlayNetworking.send(player, PARANOIA_UPDATE, buf);
    }

    public static void sendParanoiaEffect(
        ServerPlayerEntity player,
        String effectType,
        int durationTicks
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(effectType);
        buf.writeInt(durationTicks);
        ServerPlayNetworking.send(player, PARANOIA_EFFECT, buf);
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — УЛИКИ
    // ============================================================

    public static void sendEvidenceAdded(
        ServerPlayerEntity player,
        EvidenceSystem.EvidenceData data
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(data.id);
        buf.writeString(data.title);
        buf.writeString(data.description);
        buf.writeString(data.category.name());
        buf.writeString(data.hint);
        ServerPlayNetworking.send(player, EVIDENCE_ADDED, buf);
    }

    // ============================================================
    // ОТПРАВКА ПАКЕТОВ — NPC
    // ============================================================

    public static void sendNpcAnimation(
        ServerPlayerEntity player,
        String npcId,
        String animationName
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(npcId);
        buf.writeString(animationName);
        ServerPlayNetworking.send(player, NPC_ANIMATION, buf);
    }

    public static void sendSpawnNpc(
        ServerPlayerEntity player,
        String npcId
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(npcId);
        ServerPlayNetworking.send(player, SPAWN_NPC, buf);
    }

    public static void sendHideNpc(
        ServerPlayerEntity player,
        String npcId
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(npcId);
        ServerPlayNetworking.send(player, HIDE_NPC, buf);
    }

    public static void sendEndingUnlocked(
        ServerPlayerEntity player,
        String endingId
    ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(endingId);
        ServerPlayNetworking.send(player, ENDING_UNLOCKED, buf);
    }
}
