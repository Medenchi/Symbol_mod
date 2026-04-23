package com.symbol.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

import com.symbol.SymbolMod;
import com.symbol.client.render.CutsceneRenderer;
import com.symbol.client.render.LetterboxRenderer;
import com.symbol.client.render.ParanoiaRenderer;
import com.symbol.client.render.ScreenEffectRenderer;
import com.symbol.client.gui.DialogueScreen;
import com.symbol.client.gui.DiaryScreen;
import com.symbol.network.NetworkHandler;
import com.symbol.system.CameraPath;
import com.symbol.system.EvidenceSystem;

import java.util.ArrayList;
import java.util.List;

public class ClientNetworkHandler {

    public static void register() {

        // ============================================================
        // КАТСЦЕНЫ
        // ============================================================

        // Начало катсцены
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.CUTSCENE_START,
            (client, handler, buf, responseSender) -> {
                String cutsceneId = buf.readString();
                int duration = buf.readInt();
                boolean hasLetterbox = buf.readBoolean();

                // Читаем путь камеры
                int keyframeCount = buf.readInt();
                CameraPath path = new CameraPath();
                for (int i = 0; i < keyframeCount; i++) {
                    double x = buf.readDouble();
                    double y = buf.readDouble();
                    double z = buf.readDouble();
                    float yaw = buf.readFloat();
                    float pitch = buf.readFloat();
                    float roll = buf.readFloat();
                    int ticksDuration = buf.readInt();
                    String easingName = buf.readString();
                    CameraPath.EasingType easing =
                        CameraPath.EasingType.valueOf(easingName);

                    path.addKeyframe(
                        x, y, z,
                        yaw, pitch, roll,
                        ticksDuration, easing
                    );
                }

                client.execute(() -> {
                    CutsceneRenderer.startCutscene(
                        cutsceneId, duration, path
                    );
                    if (hasLetterbox) {
                        LetterboxRenderer.show();
                    }
                });
            }
        );

        // Конец катсцены
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.CUTSCENE_END,
            (client, handler, buf, responseSender) -> {
                client.execute(() -> {
                    CutsceneRenderer.stopCutscene();
                    LetterboxRenderer.hide();
                    ScreenEffectRenderer.clearAll();
                });
            }
        );

        // ============================================================
        // ЗАМОРОЗКА
        // ============================================================

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.FREEZE_PLAYER,
            (client, handler, buf, responseSender) -> {
                boolean frozen = buf.readBoolean();
                boolean lockCamera = buf.readBoolean();
                client.execute(() -> {
                    ClientFreezeHandler.setFrozen(frozen, lockCamera);
                });
            }
        );

        // ============================================================
        // ДИАЛОГ
        // ============================================================

        // Реплика диалога
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.DIALOGUE_LINE,
            (client, handler, buf, responseSender) -> {
                String speakerName = buf.readString();
                String text = buf.readString();
                String speakerSide = buf.readString();
                String npcId = buf.readString();

                client.execute(() -> {
                    DialogueScreen.showLine(
                        speakerName, text, speakerSide, npcId
                    );
                });
            }
        );

        // Конец диалога
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.DIALOGUE_END,
            (client, handler, buf, responseSender) -> {
                client.execute(DialogueScreen::close);
            }
        );

        // Кнопки выбора
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.CHOICE_BUTTONS,
            (client, handler, buf, responseSender) -> {
                int count = buf.readInt();
                List<String> choices = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    choices.add(buf.readString());
                    buf.readInt(); // effectValue (не нужен клиенту)
                }
                client.execute(() -> {
                    DialogueScreen.showChoices(choices);
                });
            }
        );

        // Ждать ввода
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.WAIT_FOR_INPUT,
            (client, handler, buf, responseSender) -> {
                client.execute(DialogueScreen::showContinuePrompt);
            }
        );

        // ============================================================
        // ЭКРАННЫЕ ЭФФЕКТЫ
        // ============================================================

        // Субтитры
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.SUBTITLE,
            (client, handler, buf, responseSender) -> {
                String text = buf.readString();
                client.execute(() ->
                    ScreenEffectRenderer.showSubtitle(text)
                );
            }
        );

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.HIDE_SUBTITLE,
            (client, handler, buf, responseSender) -> {
                client.execute(ScreenEffectRenderer::hideSubtitle);
            }
        );

        // Текст на экране
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.SCREEN_TEXT,
            (client, handler, buf, responseSender) -> {
                String text = buf.readString();
                client.execute(() ->
                    ScreenEffectRenderer.showText(text)
                );
            }
        );

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.HIDE_SCREEN_TEXT,
            (client, handler, buf, responseSender) -> {
                client.execute(ScreenEffectRenderer::hideText);
            }
        );

        // Затемнение
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.FADE_TO_BLACK,
            (client, handler, buf, responseSender) -> {
                client.execute(() ->
                    ScreenEffectRenderer.fadeToBlack(40)
                );
            }
        );

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.FADE_FROM_BLACK,
            (client, handler, buf, responseSender) -> {
                client.execute(() ->
                    ScreenEffectRenderer.fadeFromBlack(40)
                );
            }
        );

        // Вспышка
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.SCREEN_FLASH,
            (client, handler, buf, responseSender) -> {
                client.execute(ScreenEffectRenderer::flash);
            }
        );

        // Тряска камеры
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.CAMERA_SHAKE,
            (client, handler, buf, responseSender) -> {
                String intensity = buf.readString();
                client.execute(() ->
                    CutsceneRenderer.startShake(intensity)
                );
            }
        );

        // Стоп музыка
        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.STOP_MUSIC,
            (client, handler, buf, responseSender) -> {
                client.execute(() ->
                    client.getSoundManager().stopAll()
                );
            }
        );

        // ============================================================
        // ПАРАНОЙЯ
        // ============================================================

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.PARANOIA_UPDATE,
            (client, handler, buf, responseSender) -> {
                int level = buf.readInt();
                client.execute(() ->
                    ParanoiaRenderer.setLevel(level)
                );
            }
        );

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.PARANOIA_EFFECT,
            (client, handler, buf, responseSender) -> {
                String effectType = buf.readString();
                int duration = buf.readInt();
                client.execute(() ->
                    ParanoiaRenderer.playEffect(effectType, duration)
                );
            }
        );

        // ============================================================
        // УЛИКИ
        // ============================================================

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.EVIDENCE_ADDED,
            (client, handler, buf, responseSender) -> {
                String id = buf.readString();
                String title = buf.readString();
                String description = buf.readString();
                String categoryName = buf.readString();
                String hint = buf.readString();

                EvidenceSystem.EvidenceData data =
                    new EvidenceSystem.EvidenceData(
                        id, title, description,
                        EvidenceSystem.EvidenceCategory
                            .valueOf(categoryName),
                        hint
                    );

                client.execute(() ->
                    DiaryScreen.addEvidence(data)
                );
            }
        );

        // ============================================================
        // КОНЦОВКИ
        // ============================================================

        ClientPlayNetworking.registerGlobalReceiver(
            NetworkHandler.ENDING_UNLOCKED,
            (client, handler, buf, responseSender) -> {
                String endingId = buf.readString();
                client.execute(() -> {
                    SymbolMod.LOGGER.info(
                        "Концовка разблокирована: {}",
                        endingId
                    );
                    // Показываем уведомление
                    ScreenEffectRenderer.showText(
                        "§8[Новая концовка доступна]"
                    );
                });
            }
        );

        SymbolMod.LOGGER.info(
            "✓ Клиентские сетевые обработчики зарегистрированы"
        );
    }

    // ============================================================
    // ОТПРАВКА С КЛИЕНТА НА СЕРВЕР
    // ============================================================

    // Продвинуть диалог (пробел/клик)
    public static void sendAdvanceDialogue() {
        ClientPlayNetworking.send(
            NetworkHandler.ADVANCE_DIALOGUE,
            PacketByteBufs.empty()
        );
    }

    // Выбор в диалоге
    public static void sendChoiceSelected(int choiceIndex) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(choiceIndex);
        ClientPlayNetworking.send(NetworkHandler.CHOICE_SELECTED, buf);
    }
}
