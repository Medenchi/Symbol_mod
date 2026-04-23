package com.symbol.system;

import java.util.HashMap;
import java.util.Map;

import com.symbol.SymbolMod;
import com.symbol.registry.ModSounds;
import com.symbol.system.CameraPath.EasingType;
import com.symbol.system.CutsceneData.*;

public class CutsceneRegistry {

    private static final Map<String, CutsceneData> CUTSCENES =
        new HashMap<>();

    // ============================================================
    // РЕГИСТРАЦИЯ ВСЕХ КАТСЦЕН
    // ============================================================

    public static void register() {
        // Нулевой акт
        registerCutscene(buildAct0Building());
        registerCutscene(buildAct0Firing());
        registerCutscene(buildAct0PackingBox());
        registerCutscene(buildAct0CityWalk());
        registerCutscene(buildAct0BureauFind());
        registerCutscene(buildAct0Bureau());
        registerCutscene(buildAct0ApartmentReturn());
        registerCutscene(buildAct0Laptop());
        registerCutscene(buildAct0GoingParents());
        registerCutscene(buildAct0EnterParents());
        registerCutscene(buildAct0ParentsKitchen());
        registerCutscene(buildAct0Decision());

        // Акт 1
        registerCutscene(buildAct1Arrival());
        registerCutscene(buildAct1EnterFactory());
        registerCutscene(buildAct1FinalRoom());
        registerCutscene(buildAct1End());

        // Акт 2
        registerCutscene(buildAct2Enter());
        registerCutscene(buildAct2End());

        // Акт 3
        registerCutscene(buildAct3Choice());
        registerCutscene(buildAct3EndingA());
        registerCutscene(buildAct3EndingB());
        registerCutscene(buildAct3EndingC());
        registerCutscene(buildAct3EndingD());

        // Финал
        registerCutscene(buildCredits());

        SymbolMod.LOGGER.info(
            "Зарегистрировано {} катсцен",
            CUTSCENES.size()
        );
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — КАМЕРА ПО ЗДАНИЮ
    // ============================================================

    private static CutsceneData buildAct0Building() {
        return new CutsceneData.Builder("cutscene_act0_building")
            .title("Агентство Аргус")
            .duration(540) // 27 секунд
            .music(ModSounds.MUSIC_OFFICE_COLD)
            .camera(new CameraPath()
                // Снизу вверх по зданию
                .addKeyframe(
                    0, 2, 0,
                    0, 80,      // Смотрим вверх
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    0, 80, 0,
                    0, -10,     // Постепенно выравниваем
                    300, EasingType.CINEMATIC
                )
                // Проникаем внутрь
                .addKeyframe(
                    5, 65, -10,
                    -90, 0,
                    120, EasingType.SMOOTH
                )
                // Следуем за детективом
                .addKeyframe(
                    20, 64, -5,
                    -90, 15,
                    120, EasingType.CINEMATIC
                )
            )
            // Звук шагов при входе
            .sound(300, "sfx.footsteps_office")
            // Холодная атмосфера офиса
            .event(0, EventType.SET_TIME, "6000")
            .then("cutscene_act0_firing")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — УВОЛЬНЕНИЕ
    // ============================================================

    private static CutsceneData buildAct0Firing() {
        return new CutsceneData.Builder("cutscene_act0_firing")
            .title("Увольнение")
            .duration(720) // 36 секунд
            .music(ModSounds.MUSIC_OFFICE_TENSE)
            .camera(new CameraPath()
                // Детектив открывает папку
                .addKeyframe(
                    0, 64, 0,
                    0, 45,
                    0, EasingType.SMOOTH
                )
                // Крупный план папки
                .addKeyframe(
                    0.3, 63.5, 0.3,
                    0, 70,
                    80, EasingType.CINEMATIC
                )
                // Детектив идёт к начальнику
                .addKeyframe(
                    -3, 64, -2,
                    45, 10,
                    120, EasingType.SMOOTH
                )
                // Дверь кабинета
                .addKeyframe(
                    -8, 64, -5,
                    90, 5,
                    60, EasingType.EASE_IN
                )
                // Бросок папки — резкое движение камеры
                .addKeyframe(
                    -9, 64.5, -8,
                    85, -5,
                    20, EasingType.LINEAR
                )
                // Отдача — камера чуть дрожит
                .addKeyframe(
                    -9.2, 64.3, -8.1,
                    87, 0,
                    5, EasingType.LINEAR
                )
                // Начальник встаёт
                .addKeyframe(
                    -10, 64.5, -9,
                    80, 5,
                    80, EasingType.SMOOTH
                )
                // Детектив уходит
                .addKeyframe(
                    -8, 64, -6,
                    -90, 8,
                    100, EasingType.CINEMATIC
                )
                // Провожаем взглядом
                .addKeyframe(
                    -5, 64, -3,
                    -160, 10,
                    80, EasingType.SMOOTH
                )
            )

            // Детектив читает название дела
            .voice(20, "voice.detective.cutscene.glass_collector")
            .subtitle(20, "Стеклянный Собиратель… Опять эта хуйня.")
            .hideSubtitle(80)

            // Звук открытия двери
            .sound(90, "sfx.door_boss_slam")

            // Звук броска папки
            .sound(200, "sfx.folder_throw")
            .sound(205, "sfx.folder_hit_face")

            // Тряска камеры в момент броска
            .shake(200, "medium")

            // Реплика детектива
            .voice(210, "voice.detective.cutscene.wont_take_case")
            .subtitle(210,
                "Я не буду это вести. Это не дело. Это бред сумасшедшего."
            )
            .hideSubtitle(290)

            // Начальник встаёт (пауза)
            .animation(310, "boss:stand_up")

            // Реплика начальника
            .voice(340, "voice.boss.youre_fired")
            .subtitle(340, "Ты уволен.")
            .hideSubtitle(400)

            .voice(420, "voice.boss.six_months_useless")
            .subtitle(420,
                "Собирай свои вещи и проваливай из моего агентства."
            )
            .hideSubtitle(520)

            .voice(530, "voice.boss.get_out")
            .subtitle(530, "Убирайся.")
            .hideSubtitle(580)

            // Детектив уходит
            .animation(590, "detective:turn_and_leave")
            .sound(600, "sfx.door_office_close")

            .then("cutscene_act0_packing")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — СБОРЫ
    // ============================================================

    private static CutsceneData buildAct0PackingBox() {
        return new CutsceneData.Builder("cutscene_act0_packing")
            .title("Сборы")
            .duration(900) // 45 секунд
            .music(ModSounds.MUSIC_OFFICE_COLD)
            .camera(new CameraPath()
                // Детектив за столом
                .addKeyframe(
                    0, 64, 0,
                    0, 20,
                    0, EasingType.SMOOTH
                )
                // Крупный план рук — собирает вещи
                .addKeyframe(
                    0.5, 63.5, 0.2,
                    5, 60,
                    60, EasingType.CINEMATIC
                )
                // Фотография родителей
                .addKeyframe(
                    0.3, 63.8, 0.1,
                    -10, 70,
                    100, EasingType.SMOOTH
                )
                // Значок детектива
                .addKeyframe(
                    0.4, 63.7, 0.15,
                    15, 65,
                    80, EasingType.SMOOTH
                )
                // Детектив с коробкой идёт через офис
                .addKeyframe(
                    -5, 64, 3,
                    180, 10,
                    150, EasingType.CINEMATIC
                )
                // Сотрудники смотрят — камера показывает их
                .addKeyframe(
                    -8, 64.5, 0,
                    90, 5,
                    100, EasingType.SMOOTH
                )
                // Обратно на детектива
                .addKeyframe(
                    -10, 64, -5,
                    180, 12,
                    100, EasingType.SMOOTH
                )
                // Выход из здания
                .addKeyframe(
                    -15, 63, -10,
                    180, 8,
                    150, EasingType.CINEMATIC
                )
                // Дверь закрывается
                .addKeyframe(
                    -12, 63, -8,
                    0, 5,
                    60, EasingType.EASE_OUT
                )
            )

            // Крупный план фото
            .subtitle(100, "Старая фотография...")
            .hideSubtitle(160)

            // Звук падения значка в коробку
            .sound(200, "sfx.badge_drop")
            .subtitle(200, "...")
            .hideSubtitle(250)

            // Тяжёлая музыка нарастает
            .sound(300, "sfx.footsteps_office")

            // Тяжёлый звук двери
            .sound(680, "sfx.door_office_close")

            // Затемнение
            .fadeToBlack(850)

            .then("cutscene_act0_city_walk")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — БЛУЖДАНИЕ ПО ГОРОДУ
    // ============================================================

    private static CutsceneData buildAct0CityWalk() {
        return new CutsceneData.Builder("cutscene_act0_city_walk")
            .title("Город")
            .duration(780) // 39 секунд
            .music(ModSounds.MUSIC_CITY_RAIN)
            .camera(new CameraPath()
                // Появление из темноты
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Детектив под дождём
                .addKeyframe(
                    -3, 65, -5,
                    180, 8,
                    200, EasingType.CINEMATIC
                )
                // Мокрый асфальт — камера низко
                .addKeyframe(
                    -5, 63.5, -8,
                    180, 25,
                    150, EasingType.SMOOTH
                )
                // Неоновые вывески — камера смотрит в сторону
                .addKeyframe(
                    -8, 65, -12,
                    210, 0,
                    120, EasingType.SMOOTH
                )
                // Детектив останавливается у стены
                .addKeyframe(
                    -10, 65, -15,
                    180, 5,
                    100, EasingType.EASE_OUT
                )
                // Крупный план объявления
                .addKeyframe(
                    -10.5, 65, -15.8,
                    180, 0,
                    80, EasingType.CINEMATIC
                )
            )

            // Звук дождя
            .sound(0, "ambient.rain_city")

            // Появление из темноты
            .fadeFromBlack(0)

            // Детектив стоит у стены
            .subtitle(400,
                "«Требуется детектив. Бюро 'Ночной Дозор'. Большая зарплата.»"
            )
            .hideSubtitle(560)

            // Вздыхает
            .animation(570, "detective:sigh")

            .then("cutscene_act0_bureau_find")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — ПУТЬ В БЮРО
    // ============================================================

    private static CutsceneData buildAct0BureauFind() {
        return new CutsceneData.Builder("cutscene_act0_bureau_find")
            .title("Ночной Дозор")
            .duration(360)
            .music(ModSounds.MUSIC_CITY_WALK)
            .camera(new CameraPath()
                // Детектив идёт по улице
                .addKeyframe(
                    0, 65, 0,
                    180, 8,
                    0, EasingType.SMOOTH
                )
                // Спускается в подвал
                .addKeyframe(
                    -5, 63, -8,
                    200, 20,
                    180, EasingType.CINEMATIC
                )
                // Дверь подвала
                .addKeyframe(
                    -8, 62, -10,
                    180, 10,
                    100, EasingType.SMOOTH
                )
                // Открывается дверь
                .addKeyframe(
                    -9, 62, -11,
                    175, 8,
                    80, EasingType.EASE_IN
                )
            )

            .sound(0, "sfx.footsteps_street")
            .sound(200, "sfx.door_creak")

            .then("cutscene_act0_bureau")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — В БЮРО (встреча с Валерией)
    // ============================================================

    private static CutsceneData buildAct0Bureau() {
        return new CutsceneData.Builder("cutscene_act0_bureau")
            .title("Валерия")
            .duration(480)
            .music(ModSounds.MUSIC_BUREAU)
            .camera(new CameraPath()
                // Внутри подвала
                .addKeyframe(
                    0, 62, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Валерия за столом
                .addKeyframe(
                    -2, 62.5, -3,
                    5, 10,
                    120, EasingType.CINEMATIC
                )
                // Крупный план лица Валерии
                .addKeyframe(
                    -3, 63, -4,
                    8, 0,
                    60, EasingType.SMOOTH
                )
                // Она смотрит на детектива
                .addKeyframe(
                    -2.5, 62.8, -3.5,
                    0, 5,
                    80, EasingType.SMOOTH
                )
            )

            // Валерия поднимает взгляд
            .animation(60, "valeria:look_up")

            // Пауза — смотрит
            .sound(80, "sfx.chair_scrape")

            .thenDialogue("dialogue_valeria_first")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — ВОЗВРАЩЕНИЕ В КВАРТИРУ
    // ============================================================

    private static CutsceneData buildAct0ApartmentReturn() {
        return new CutsceneData.Builder("cutscene_act0_apartment_return")
            .title("")
            .duration(300)
            .music(ModSounds.MUSIC_APARTMENT)
            .camera(new CameraPath()
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    -2, 65, -3,
                    10, 5,
                    200, EasingType.CINEMATIC
                )
                .addKeyframe(
                    -4, 64.8, -5,
                    5, 10,
                    100, EasingType.SMOOTH
                )
            )

            .sound(0, "sfx.footsteps_street")
            .fadeFromBlack(0)

            .then("cutscene_act0_laptop")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — НОУТБУК / ПОИСК / ЯРОСТЬ
    // ============================================================

    private static CutsceneData buildAct0Laptop() {
        return new CutsceneData.Builder("cutscene_act0_laptop")
            .title("Поиск")
            .duration(600)
            .music(ModSounds.MUSIC_APARTMENT_NIGHT)
            .camera(new CameraPath()
                // Детектив за ноутбуком
                .addKeyframe(
                    0, 64, 0,
                    0, 20,
                    0, EasingType.SMOOTH
                )
                // Крупный план экрана
                .addKeyframe(
                    0.2, 64, -0.5,
                    0, 30,
                    80, EasingType.CINEMATIC
                )
                // Печатает
                .addKeyframe(
                    0.1, 63.9, -0.4,
                    2, 35,
                    60, EasingType.SMOOTH
                )
                // Результат — нет результатов
                .addKeyframe(
                    0.15, 63.95, -0.45,
                    0, 32,
                    40, EasingType.LINEAR
                )
                // КАМЕРА РЕЗКО УХОДИТ ВВЕРХ
                .addKeyframe(
                    0, 70, 0,
                    0, -30,
                    20, EasingType.LINEAR
                )
                // Детектив бьёт по столу
                .addKeyframe(
                    0, 66, -1,
                    0, 15,
                    40, EasingType.EASE_OUT
                )
                // Медленно успокаивается
                .addKeyframe(
                    -1, 65, -2,
                    10, 10,
                    150, EasingType.CINEMATIC
                )
                // Окно — смотрит в дождь
                .addKeyframe(
                    -3, 65, 0,
                    -90, 5,
                    150, EasingType.SMOOTH
                )
            )

            // Звук открытия ноутбука
            .sound(0, "sfx.laptop_open")

            // Печатает запрос
            .sound(60, "sfx.laptop_type")
            .voice(60, "voice.detective.cutscene.google_search")
            .subtitle(60, "стеклофабрика красново...")
            .hideSubtitle(130)

            // Результат
            .text(160, "Результатов не найдено")

            // Удар по столу
            .sound(220, "sfx.table_hit")
            .shake(220, "heavy")

            // Крик детектива
            .voice(225, "voice.detective.cutscene.rage")
            .subtitle(225,
                "Да что вообще происходит?! Это же не может быть совпадением!"
            )
            .hideSubtitle(340)

            // Закрывает ноутбук
            .sound(360, "sfx.laptop_close")
            .hideText(360)

            // Пьёт воду
            .sound(420, "sfx.water_drink")

            // Тихая реплика
            .voice(500, "voice.detective.cutscene.going_parents")
            .subtitle(500, "Пойду к родителям...")
            .hideSubtitle(580)

            .then("cutscene_act0_going_parents")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — ДОРОГА К РОДИТЕЛЯМ
    // ============================================================

    private static CutsceneData buildAct0GoingParents() {
        return new CutsceneData.Builder("cutscene_act0_going_parents")
            .title("")
            .duration(360)
            .music(ModSounds.MUSIC_CITY_RAIN)
            .camera(new CameraPath()
                // Идёт по улице среди пятиэтажек
                .addKeyframe(
                    0, 65, 0,
                    180, 5,
                    0, EasingType.SMOOTH
                )
                // Советские пятиэтажки в вечернем свете
                .addKeyframe(
                    -5, 65, -10,
                    200, 3,
                    200, EasingType.CINEMATIC
                )
                // Фонари отражаются в лужах
                .addKeyframe(
                    -8, 63.5, -15,
                    190, 20,
                    120, EasingType.SMOOTH
                )
                // Темнеет экран
                .addKeyframe(
                    -10, 65, -18,
                    180, 8,
                    40, EasingType.EASE_IN
                )
            )

            .sound(0, "sfx.footsteps_street")
            .sound(0, "ambient.rain_city")

            // Вечер
            .event(0, EventType.SET_TIME, "13000")

            // Затемнение
            .fadeToBlack(320)

            .then("cutscene_act0_enter_parents")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — ЗВОНОК В ДОМОФОН
    // ============================================================

    private static CutsceneData buildAct0EnterParents() {
        return new CutsceneData.Builder("cutscene_act0_enter_parents")
            .title("")
            .duration(480)
            .music(ModSounds.MUSIC_PARENTS_HOME)
            .camera(new CameraPath()
                // У подъезда
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Крупный план домофона
                .addKeyframe(
                    0.3, 65.2, -0.8,
                    5, 15,
                    80, EasingType.CINEMATIC
                )
                // После ответа — смотрит вверх
                .addKeyframe(
                    0.2, 65, -0.5,
                    3, -20,
                    80, EasingType.SMOOTH
                )
                // Дверь открывается
                .addKeyframe(
                    0.5, 65, -1.5,
                    0, 5,
                    120, EasingType.EASE_OUT
                )
                // Заходит
                .addKeyframe(
                    1, 65, -3,
                    0, 8,
                    120, EasingType.SMOOTH
                )
            )

            // Появление из темноты
            .fadeFromBlack(0)

            // Детектив нажимает домофон
            .sound(60, "sfx.intercom_buzz")
            .animation(60, "detective:press_button")

            // Легендарный звук домофона
            .sound(80, "sfx.intercom_click")

            // Мама отвечает
            .voice(100, "voice.mom.intercom_who")
            .subtitle(100, "Мама: Кто?")
            .hideSubtitle(160)

            // Детектив отвечает
            .subtitle(180, "— Это я, мам.")
            .hideSubtitle(240)

            // Мама
            .voice(260, "voice.mom.come_in")
            .subtitle(260, "Мама: Сынок! Заходи!")
            .hideSubtitle(320)

            // Щелчок двери
            .sound(330, "sfx.door_office_open")

            .thenDialogue("dialogue_mom_intercom")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — НА КУХНЕ У РОДИТЕЛЕЙ
    // ============================================================

    private static CutsceneData buildAct0ParentsKitchen() {
        return new CutsceneData.Builder("cutscene_act0_parents_kitchen")
            .title("Родители")
            .duration(300)
            .music(ModSounds.MUSIC_PARENTS_HOME)
            .camera(new CameraPath()
                // Советская кухня
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    0, EasingType.SMOOTH
                )
                // Мама ставит чай
                .addKeyframe(
                    -2, 65, -2,
                    20, 10,
                    100, EasingType.CINEMATIC
                )
                // Отец за столом — молчит
                .addKeyframe(
                    -4, 65, -1,
                    -30, 8,
                    100, EasingType.SMOOTH
                )
                // Садятся пить чай
                .addKeyframe(
                    -2, 65, -2,
                    10, 12,
                    100, EasingType.SMOOTH
                )
            )

            .sound(0, "sfx.teacup")
            .sound(60, "sfx.chair_scrape")
            .animation(0, "mom:pour_tea")
            .animation(0, "father:sit_silent")

            .thenDialogue("dialogue_parents")
            .build();
    }

    // ============================================================
    // НУЛЕВОЙ АКТ — РЕШЕНИЕ ЕХАТЬ
    // ============================================================

    private static CutsceneData buildAct0Decision() {
        return new CutsceneData.Builder("cutscene_act0_decision")
            .title("")
            .duration(480)
            .music(ModSounds.MUSIC_APARTMENT_NIGHT)
            .camera(new CameraPath()
                // Детектив на лестнице
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    0, EasingType.SMOOTH
                )
                // Резко останавливается
                .addKeyframe(
                    -2, 65, -3,
                    0, 5,
                    100, EasingType.EASE_OUT
                )
                // Разворачивается
                .addKeyframe(
                    -2, 65, -3,
                    180, 5,
                    40, EasingType.LINEAR
                )
                // Быстро спускается
                .addKeyframe(
                    0, 63, 0,
                    180, 15,
                    100, EasingType.EASE_IN
                )
            )

            // Детектив останавливается
            .animation(100, "detective:stop_suddenly")

            // Финальная фраза
            .voice(200, "voice.detective.cutscene.decision_factory")
            .subtitle(200, "Я еду на этот завод завтра.")
            .hideSubtitle(320)

            // Затемнение
            .fadeToBlack(380)

            // Надпись АКТ 1
            .text(420, "§0§lАКТ 1 — СТЕКЛО")
            .hideText(480)

            .build();
    }

    // ============================================================
    // АКТ 1 — ПРИБЫТИЕ НА ЗАВОД
    // ============================================================

    private static CutsceneData buildAct1Arrival() {
        return new CutsceneData.Builder("cutscene_act1_arrival")
            .title("Стеклозавод №7")
            .duration(900) // 45 секунд
            .music(ModSounds.MUSIC_FACTORY_OUTSIDE)
            .camera(new CameraPath()
                // Утро — автобус
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Детектив выходит
                .addKeyframe(
                    -3, 65, -5,
                    180, 8,
                    120, EasingType.EASE_OUT
                )
                // Просёлочная дорога
                .addKeyframe(
                    -10, 65, -20,
                    180, 5,
                    200, EasingType.CINEMATIC
                )
                // ВЕЛИЧЕСТВЕННЫЙ ПОДЪЁМ по зданию завода
                .addKeyframe(
                    -15, 63, -30,
                    180, 80,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    -15, 100, -30,
                    180, -10,
                    300, EasingType.CINEMATIC
                )
                // Опускаемся — детектив у ворот
                .addKeyframe(
                    -15, 65, -30,
                    180, 5,
                    100, EasingType.SMOOTH
                )
                // Крупный план нового замка
                .addKeyframe(
                    -15.5, 64.8, -30.8,
                    175, 15,
                    80, EasingType.CINEMATIC
                )
                // Ворота открываются
                .addKeyframe(
                    -16, 65, -32,
                    180, 5,
                    80, EasingType.EASE_IN
                )
                // Детектив заходит
                .addKeyframe(
                    -16, 65, -35,
                    180, 8,
                    120, EasingType.CINEMATIC
                )
            )

            // Утро
            .event(0, EventType.SET_TIME, "1000")

            // Звук автобуса
            .sound(0, "sfx.bus_stop")
            .fadeFromBlack(0)

            // Звук шагов
            .sound(130, "sfx.footsteps_street")

            // Детектив смотрит на завод
            .voice(340, "voice.detective.cutscene.factory_look")
            .subtitle(340,
                "Значит вот ты какой… Закрыт с 1979 года. Сорок пять лет никого."
            )
            .hideSubtitle(500)

            .voice(520, "voice.detective.cutscene.new_lock_gates")
            .subtitle(520, "Но замок новый.")
            .hideSubtitle(600)

            // Звук открытия замка
            .sound(640, "sfx.lock_open")

            // СТРАШНЫЙ скрип ворот
            .sound(680, "sfx.factory_gate_open")
            .shake(680, "light")

            // Детектив заходит — ворота закрываются
            .sound(820, "sfx.factory_gate_close")
            .sound(840, "sfx.lock_click")

            // Сарказм
            .voice(860, "voice.detective.cutscene.great_sarcasm")
            .subtitle(860, "Отлично.")
            .hideSubtitle(900)

            .build();
    }

    // ============================================================
    // АКТ 1 — ВХОД В ЗАВОД (игрок получает управление)
    // ============================================================

    private static CutsceneData buildAct1EnterFactory() {
        return new CutsceneData.Builder("cutscene_act1_enter_factory")
            .title("")
            .duration(60)
            .camera(new CameraPath()
                .addKeyframe(
                    0, 65, 0,
                    180, 8,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    -1, 65, -2,
                    180, 8,
                    60, EasingType.EASE_OUT
                )
            )
            // Даём управление игроку
            .dontFreezePlayer()
            .build();
    }

    // ============================================================
    // АКТ 1 — ФИНАЛЬНАЯ КОМНАТА
    // ============================================================

    private static CutsceneData buildAct1FinalRoom() {
        return new CutsceneData.Builder("cutscene_act1_final_room")
            .title("Комната")
            .duration(1200) // 60 секунд
            .music(ModSounds.MUSIC_FINAL_ROOM)
            .camera(new CameraPath()
                // Детектив открывает дверь
                .addKeyframe(
                    0, 62, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Луч фонарика — темнота
                .addKeyframe(
                    -1, 62, -2,
                    0, 8,
                    60, EasingType.LINEAR
                )
                // Фонарик скользит по стенам
                .addKeyframe(
                    -2, 62, -4,
                    -30, 10,
                    80, EasingType.SMOOTH
                )
                .addKeyframe(
                    -2, 62, -4,
                    30, 5,
                    60, EasingType.SMOOTH
                )
                // Останавливается на стеклянных блоках
                .addKeyframe(
                    -3, 62, -6,
                    0, 5,
                    100, EasingType.EASE_OUT
                )
                // УЖАС — детектив отшатывается
                .addKeyframe(
                    -2, 62, -4,
                    0, 10,
                    20, EasingType.LINEAR
                )
                // Сползает по стене
                .addKeyframe(
                    -1.5, 61, -3,
                    0, 20,
                    60, EasingType.EASE_OUT
                )
                // Долгая пауза на полу
                .addKeyframe(
                    -1.5, 61, -3,
                    0, 20,
                    200, EasingType.LINEAR
                )
                // Встаёт — подходит снова
                .addKeyframe(
                    -3, 62, -6,
                    0, 5,
                    200, EasingType.CINEMATIC
                )
                // Крупный план лица в стекле
                .addKeyframe(
                    -3.5, 62, -6.8,
                    0, 2,
                    100, EasingType.SMOOTH
                )
                // Детектив видит надпись
                .addKeyframe(
                    -4, 62, -5,
                    -90, 5,
                    150, EasingType.CINEMATIC
                )
                // Разворачивается к деревне
                .addKeyframe(
                    -4, 62, -5,
                    90, 5,
                    80, EasingType.SMOOTH
                )
                // КАМЕРА УХОДИТ ВВЕРХ ЧЕРЕЗ ВСЁ ЗДАНИЕ
                .addKeyframe(
                    -4, 150, -5,
                    90, -30,
                    200, EasingType.CINEMATIC
                )
            )

            // Стингер ужаса
            .sound(0, "sfx.metal_door_heavy")
            .event(0, EventType.PLAY_MUSIC,
                "music.final_room")

            // Фонарик по стенам — звуки шагов
            .sound(20, "sfx.footsteps_basement")

            // Детектив видит блоки
            .sound(380, "sfx.stinger_horror")
            .shake(380, "heavy")

            .voice(400, "voice.detective.final_room.glass_blocks")
            .subtitle(400, "...")
            .hideSubtitle(460)

            // Долгая пауза
            .voice(680, "voice.detective.final_room.its_people")
            .subtitle(680,
                "Это люди… Они залиты стеклом."
            )
            .hideSubtitle(780)

            .voice(800, "voice.detective.final_room.three_workers")
            .subtitle(800,
                "Трое. Как трое рабочих из журнала."
            )
            .hideSubtitle(880)

            .voice(900, "voice.detective.final_room.k7_purpose")
            .subtitle(900,
                "Состав К-7… Вот для чего он был."
            )
            .hideSubtitle(980)

            .voice(1000, "voice.detective.final_room.what_saw")
            .subtitle(1000,
                "Вас убили потому что вы что-то видели. Что вы видели?"
            )
            .hideSubtitle(1100)

            // Видит надпись — стингер открытия
            .sound(1120, "sfx.stinger_discover")
            .voice(1130, "voice.detective.final_room.we_saw_village")
            .subtitle(1130, "«МЫ ВИДЕЛИ ДЕРЕВНЮ»")
            .hideSubtitle(1200)

            // Добавляем ключевую улику
            .evidence(1120, "evidence_inscription")

            // Музыка нарастает
            .event(1150, EventType.PLAY_MUSIC, "music.paranoia_high")

            // Затемнение
            .fadeToBlack(1180)

            .then("cutscene_act1_end")
            .build();
    }

    // ============================================================
    // АКТ 1 — КОНЕЦ
    // ============================================================

    private static CutsceneData buildAct1End() {
        return new CutsceneData.Builder("cutscene_act1_end")
            .title("")
            .duration(300)
            .camera(new CameraPath()
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
            )

            .fadeFromBlack(0)
            .text(60, "§0§lАКТ 2 — ДЕРЕВНЯ")
            .hideText(240)
            .fadeToBlack(260)

            .build();
    }

    // ============================================================
    // АКТ 2 — ВХОД В ДЕРЕВНЮ
    // ============================================================

    private static CutsceneData buildAct2Enter() {
        return new CutsceneData.Builder("cutscene_act2_enter")
            .title("Деревня")
            .duration(540)
            .music(ModSounds.MUSIC_VILLAGE_CALM)
            .camera(new CameraPath()
                // Камера поднимается сквозь завод
                .addKeyframe(
                    0, 62, 0,
                    90, 5,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    0, 120, 0,
                    90, -20,
                    200, EasingType.CINEMATIC
                )
                // Вид сверху — деревня за забором
                .addKeyframe(
                    20, 120, 0,
                    90, -80,
                    150, EasingType.SMOOTH
                )
                // Спускаемся к детективу
                .addKeyframe(
                    20, 65, -30,
                    180, 8,
                    190, EasingType.CINEMATIC
                )
            )

            .fadeFromBlack(0)
            .sound(0, "ambient.village_birds")

            .voice(250, "voice.detective.village.arrive")
            .subtitle(250,
                "Деревня… «Мы видели деревню.»"
            )
            .hideSubtitle(380)

            .voice(400, "voice.detective.village.cold_feeling")
            .subtitle(400,
                "Что здесь такого страшного, чтобы умирать за это знание?"
            )
            .hideSubtitle(520)

            .build();
    }

    // ============================================================
    // АКТ 2 — ФИНАЛ (после Громова)
    // ============================================================

    private static CutsceneData buildAct2End() {
        return new CutsceneData.Builder("cutscene_act2_end")
            .title("")
            .duration(780)
            .music(ModSounds.MUSIC_VILLAGE_PARANOIA)
            .camera(new CameraPath()
                // Детектив стоит посреди деревни
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Камера медленно кружит
                .addKeyframe(
                    3, 65.5, 0,
                    90, 8,
                    200, EasingType.CINEMATIC
                )
                .addKeyframe(
                    0, 65.5, 3,
                    180, 8,
                    200, EasingType.CINEMATIC
                )
                .addKeyframe(
                    -3, 65, 0,
                    270, 8,
                    200, EasingType.CINEMATIC
                )
                // Поднимаемся вверх
                .addKeyframe(
                    0, 120, 0,
                    0, -80,
                    180, EasingType.SMOOTH
                )
            )

            // Внутренний монолог
            .voice(0, "voice.detective.monologue.they_dont_know")
            .subtitle(0, "Они не знают. Никто из них не знает.")
            .hideSubtitle(120)

            .voice(140, "voice.detective.monologue.45_years_watched")
            .subtitle(140, "Сорок пять лет под наблюдением.")
            .hideSubtitle(260)

            .voice(280, "voice.detective.monologue.children_watched")
            .subtitle(280, "Дети под наблюдением. Дети детей.")
            .hideSubtitle(380)

            .voice(400, "voice.detective.monologue.cant_stop")
            .subtitle(400, "И остановить это нельзя.")
            .hideSubtitle(500)

            // Галлюцинация — символ на запястье
            .event(520, EventType.SCREEN_FLASH, "")
            .voice(530, "voice.detective.paranoia.symbol_on_wrist")

            // Новый человек на дороге
            .subtitle(600,
                "По дороге идёт новый человек с рюкзаком..."
            )
            .hideSubtitle(720)

            .fadeToBlack(740)
            .text(760, "§0§lАКТ 3 — НАБЛЮДАТЕЛИ")
            .hideText(780)

            .then("cutscene_act3_choice")
            .build();
    }

    // ============================================================
    // АКТ 3 — ВЫБОР КОНЦОВКИ
    // ============================================================

    private static CutsceneData buildAct3Choice() {
        return new CutsceneData.Builder("cutscene_act3_choice")
            .title("Решение")
            .duration(200)
            .music(ModSounds.MUSIC_ACT3_TENSE)
            .camera(new CameraPath()
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    0, 65.5, 0,
                    0, 5,
                    200, EasingType.LINEAR
                )
            )

            .fadeFromBlack(0)

            // Вопрос — что делать?
            .voice(100, "voice.detective.monologue.what_do_i_do")
            .subtitle(100, "Так что мне делать?")
            .hideSubtitle(180)

            // Показываем кнопки выбора
            // (через систему Button3D)
            .event(200, EventType.START_DIALOGUE, "dialogue_act3_choice")

            .build();
    }

    // ============================================================
    // КОНЦОВКА A — ПРАВДА
    // ============================================================

    private static CutsceneData buildAct3EndingA() {
        return new CutsceneData.Builder("cutscene_ending_a")
            .title("Правда")
            .duration(1200)
            .music(ModSounds.MUSIC_ENDING_TRUTH)
            .camera(new CameraPath()
                // Детектив звонит
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    0, EasingType.SMOOTH
                )
                // Приезжают машины
                .addKeyframe(
                    -10, 65, -20,
                    180, 5,
                    300, EasingType.CINEMATIC
                )
                // Жители у домов
                .addKeyframe(
                    -5, 65.5, -15,
                    90, 5,
                    200, EasingType.SMOOTH
                )
                // Громова выводят
                .addKeyframe(
                    -8, 65, -18,
                    160, 8,
                    200, EasingType.SMOOTH
                )
                // Он проходит мимо детектива
                .addKeyframe(
                    -6, 65, -14,
                    180, 5,
                    100, EasingType.CINEMATIC
                )
                // Кабинет Ночного Дозора — несколько месяцев спустя
                .addKeyframe(
                    0, 62, 0,
                    0, 10,
                    200, EasingType.SMOOTH
                )
                // Окно — город
                .addKeyframe(
                    -3, 62, -2,
                    -90, 5,
                    100, EasingType.SMOOTH
                )
                // Камера отъезжает
                .addKeyframe(
                    0, 150, 0,
                    0, -80,
                    200, EasingType.CINEMATIC
                )
            )

            .fadeFromBlack(0)

            // Нина плачет
            .animation(300, "nina:cry")

            // Рашид держит жену
            .animation(300, "rashid:hold_wife")

            // Толя сидит — не удивлён
            .animation(300, "tolya:sit_not_surprised")

            // Громов проходит мимо
            .voice(680, "voice.gromov.wont_end")
            .subtitle(680, "Громов: Эксперимент не закончится.")
            .hideSubtitle(760)

            .subtitle(780, "— Я знаю.")
            .hideSubtitle(840)

            .voice(860, "voice.gromov.still_called")
            .subtitle(860, "Громов: И всё равно позвонил.")
            .hideSubtitle(940)

            .animation(960, "gromov:respect_nod")

            // Кабинет — несколько месяцев спустя
            .fadeToBlack(980)
            .fadeFromBlack(1000)

            .voice(1020, "voice.valeria.case_closed")
            .subtitle(1020, "Валерия: Дело закрыто.")
            .hideSubtitle(1080)

            .subtitle(1090, "— Нет. Формально — закрыто.")
            .hideSubtitle(1140)

            // Финальный текст
            .text(1150,
                "§7Папа позвонил сегодня. Первый раз сам.\n" +
                "§7Спросил, как дела.\n" +
                "§7Я сказал — хорошо.\n" +
                "§7Мы оба знали, что говорим не только об этом."
            )
            .hideText(1200)

            .fadeToBlack(1180)

            .then("cutscene_credits")
            .build();
    }

    // ============================================================
    // КОНЦОВКА B — ЗАМЕНА
    // ============================================================

    private static CutsceneData buildAct3EndingB() {
        return new CutsceneData.Builder("cutscene_ending_b")
            .title("Замена")
            .duration(900)
            .music(ModSounds.MUSIC_ENDING_REPLACE)
            .camera(new CameraPath()
                // Детектив идёт к Нине
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // У дома Нины
                .addKeyframe(
                    -5, 65, -8,
                    180, 8,
                    200, EasingType.CINEMATIC
                )
                // Нина улыбается
                .addKeyframe(
                    -6, 65.5, -9,
                    175, 5,
                    80, EasingType.SMOOTH
                )
                // За столом — кружка
                .addKeyframe(
                    -6, 65, -9,
                    175, 25,
                    100, EasingType.SMOOTH
                )
                // Крупный план дна кружки
                .addKeyframe(
                    -6.1, 64.5, -9.2,
                    175, 70,
                    80, EasingType.CINEMATIC
                )
                // Квартира детектива — потом
                .addKeyframe(
                    0, 65, 0,
                    0, 10,
                    200, EasingType.SMOOTH
                )
                // Рисует символы
                .addKeyframe(
                    0.5, 64.8, -0.3,
                    5, 40,
                    100, EasingType.SMOOTH
                )
                // Пустая улыбка
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    100, EasingType.EASE_OUT
                )
            )

            .fadeFromBlack(0)
            .sound(0, "ambient.village_wind")

            // Нина открывает дверь
            .voice(200, "voice.nina.stay_for_night")
            .subtitle(200, "Нина: Куда ж вы на ночь глядя? Оставайтесь.")
            .hideSubtitle(320)

            // За столом — кружка
            .sound(400, "sfx.teacup")

            // Видит символ на кружке
            .voice(480, "voice.detective.symbol.on_cup")
            .subtitle(480, "— Давно это здесь?")
            .hideSubtitle(540)

            // Нина улыбается — не отвечает
            .animation(550, "nina:just_smile")

            // Квартира — рисует символы
            .fadeToBlack(680)
            .fadeFromBlack(700)

            // Пустая улыбка — финал
            .text(820,
                "§7Дело №47 закрыто.\n" +
                "§7Детектив вышел на пенсию через три месяца.\n" +
                "§7Переехал в деревню Стеклово."
            )
            .hideText(900)
            .fadeToBlack(880)

            .then("cutscene_credits")
            .build();
    }

    // ============================================================
    // КОНЦОВКА C — ОТРИЦАНИЕ
    // ============================================================

    private static CutsceneData buildAct3EndingC() {
        return new CutsceneData.Builder("cutscene_ending_c")
            .title("Отрицание")
            .duration(720)
            .music(ModSounds.MUSIC_ENDING_DENIAL)
            .camera(new CameraPath()
                // Детектив садится в автобус
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    -5, 65, -10,
                    180, 8,
                    200, EasingType.CINEMATIC
                )
                // Квартира — пишет отчёт
                .addKeyframe(
                    0, 65, 0,
                    0, 15,
                    200, EasingType.SMOOTH
                )
                // Ставит чайник
                .addKeyframe(
                    -2, 65, -1,
                    -30, 10,
                    100, EasingType.SMOOTH
                )
                // КРУПНЫЙ ПЛАН ДНА КРУЖКИ
                .addKeyframe(
                    -2.1, 64.5, -1.2,
                    -30, 80,
                    80, EasingType.CINEMATIC
                )
            )

            .fadeFromBlack(0)
            .sound(0, "sfx.bus_leave")

            // В бюро
            .subtitle(220, "«Серийный убийца — Громов В.С. Дело закрыто.»")
            .hideSubtitle(340)

            .voice(360, "voice.valeria.well_done")
            .subtitle(360, "Валерия: Молодец. Быстро управился.")
            .hideSubtitle(440)

            // Дома — ставит чайник
            .sound(480, "sfx.teacup")

            // СИМВОЛ НА ДНЕ КРУЖКИ
            .sound(640, "sfx.stinger_horror")
            .fadeToBlack(660)

            .text(680,
                "§7Восьмой отдел учёл новое\n" +
                "§7местонахождение субъекта для наблюдения."
            )
            .hideText(720)

            .then("cutscene_credits")
            .build();
    }

    // ============================================================
    // КОНЦОВКА D — НАБЛЮДАТЕЛЬ
    // ============================================================

    private static CutsceneData buildAct3EndingD() {
        return new CutsceneData.Builder("cutscene_ending_d")
            .title("Наблюдатель")
            .duration(960)
            .music(ModSounds.MUSIC_ENDING_WATCHER)
            .camera(new CameraPath()
                // Темнеет — жители выходят
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                // Окружают детектива
                .addKeyframe(
                    0, 65.5, 0,
                    0, 8,
                    200, EasingType.CINEMATIC
                )
                // Громов последним
                .addKeyframe(
                    -1, 65, -1,
                    0, 5,
                    100, EasingType.SMOOTH
                )
                // Детектив встаёт в ряд
                .addKeyframe(
                    0, 65, 0,
                    0, 8,
                    100, EasingType.EASE_OUT
                )
                // КАМЕРА УХОДИТ ВВЕРХ
                .addKeyframe(
                    0, 200, 0,
                    0, -80,
                    300, EasingType.CINEMATIC
                )
            )

            .fadeFromBlack(0)

            // Жители выходят
            .animation(60, "nina:walk_out")
            .animation(80, "rashid:walk_out")
            .animation(100, "tolya:walk_out")
            .animation(140, "semonych:walk_out")
            .animation(180, "gromov:walk_out_last")

            // Громов подходит
            .voice(400, "voice.gromov.i_know_you_know")
            .subtitle(400, "Громов: Ты нашёл всё.")
            .hideSubtitle(480)

            .subtitle(500, "— Всё.")
            .hideSubtitle(560)

            .voice(580, "voice.gromov.respect_nod")
            .subtitle(580, "Громов: И что ты решил?")
            .hideSubtitle(640)

            // Детектив молчит — встаёт в ряд
            .animation(680, "detective:join_watchers")

            // Вид сверху — новый человек идёт
            .subtitle(820,
                "По дороге идёт новый человек с рюкзаком."
            )
            .hideSubtitle(900)

            .text(920, "§0§lНаблюдение продолжается.")
            .hideText(960)

            .fadeToBlack(940)
            .then("cutscene_credits")
            .build();
    }

    // ============================================================
    // ФИНАЛЬНЫЕ ТИТРЫ
    // ============================================================

    private static CutsceneData buildCredits() {
        return new CutsceneData.Builder("cutscene_credits")
            .title("Конец")
            .duration(1800) // 90 секунд
            .music(ModSounds.MUSIC_CREDITS)
            .camera(new CameraPath()
                // Медленный проход по офису Аргус
                .addKeyframe(
                    0, 65, 0,
                    0, 5,
                    0, EasingType.SMOOTH
                )
                .addKeyframe(
                    -20, 65, -10,
                    180, 5,
                    600, EasingType.CINEMATIC
                )
                // Стена с царапиной — символ
                .addKeyframe(
                    -18, 65.5, -8,
                    -90, 5,
                    200, EasingType.SMOOTH
                )
                // Крупный план царапины
                .addKeyframe(
                    -18.5, 65.3, -8.5,
                    -90, 2,
                    100, EasingType.CINEMATIC
                )
            )

            .fadeFromBlack(0)

            // Текст титров
            .text(100, "§7Стеклозавод №7 был закрыт.")
            .hideText(300)
            .text(320, "§7Стеклозаводы №1–6 не были найдены.")
            .hideText(520)

            // Авторы
            .text(600, "§f§lСОЗДАНО")
            .hideText(700)
            .text(720, "§7Разработка карты и мода:\n§fYourName")
            .hideText(900)
            .text(920, "§7Сценарий:\n§fYourName")
            .hideText(1100)

            // Последний кадр — символ на стене офиса
            .text(1300, "§8...")
            .hideText(1500)

            .text(1520,
                "§0§lСимвол"
            )
            .hideText(1700)

            .fadeToBlack(1750)

            .build();
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЕ
    // ============================================================

    private static void registerCutscene(CutsceneData data) {
        CUTSCENES.put(data.id, data);
    }

    public static CutsceneData getCutscene(String id) {
        return CUTSCENES.get(id);
    }

    public static int getCutsceneCount() {
        return CUTSCENES.size();
    }
}
