package com.symbol.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.symbol.registry.ModSounds;
import com.symbol.system.DialogueSystem.*;

public class DialogueRegistry {

    private static final Map<String, DialogueData> DIALOGUES = new HashMap<>();

    // ============================================================
    // РЕГИСТРАЦИЯ ВСЕХ ДИАЛОГОВ
    // ============================================================

    public static void registerAll() {
        registerValeriaFirstMeeting();
        registerNinaDialogue();
        registerRashidDialogue();
        registerTolyaDialogue();
        registerSemonychDialogue();
        registerGromovDialogue();
        registerMomDialogue();
        registerParentsDialogue();
    }

    // ============================================================
    // ДИАЛОГ — ВАЛЕРИЯ (первая встреча)
    // ============================================================

    private static void registerValeriaFirstMeeting() {
        List<DialogueLine> lines = new ArrayList<>();

        // Реплика 1
        lines.add(new DialogueLine(
            "Валерия",
            "Значит ты и есть тот самый «лучший детектив Аргуса»...",
            "left",
            ModSounds.VOICE_VAL_SO_YOU_ARE,
            0,
            null
        ));

        // Реплика 2
        lines.add(new DialogueLine(
            "Валерия",
            "...которого все выгнали?",
            "left",
            ModSounds.VOICE_VAL_BEST_DETECTIVE,
            0,
            null
        ));

        // Реплика 3
        lines.add(new DialogueLine(
            "Валерия",
            "Нам всё равно кого брать. Дело тяжёлое. " +
            "Никто не хочет за него браться.",
            "left",
            ModSounds.VOICE_VAL_HARD_CASE,
            0,
            null
        ));

        // Реплика 4 — с выбором
        lines.add(new DialogueLine(
            "Валерия",
            "Справляешься — остаёшься. Не справляешься — до свидания. " +
            "Вопросы есть?",
            "left",
            ModSounds.VOICE_VAL_QUESTIONS,
            0,
            List.of(
                new DialogueChoice(
                    "Что за дело?",
                    "valeria_answer_case",
                    ChoiceEffectType.NONE,
                    0, null
                ),
                new DialogueChoice(
                    "Почему никто не хочет его брать?",
                    "valeria_answer_why",
                    ChoiceEffectType.NONE,
                    0, null
                ),
                new DialogueChoice(
                    "Какая зарплата?",
                    "valeria_answer_salary",
                    ChoiceEffectType.NONE,
                    0, null
                ),
                new DialogueChoice(
                    "(Молча кивнуть)",
                    "valeria_end",
                    ChoiceEffectType.NONE,
                    0, null
                )
            )
        ));

        // Ответ про дело
        lines.add(new DialogueLine(
            "Валерия",
            "Стекольный завод. Заброшен с 79-го. " +
            "Там нашли кое-что интересное. Подробности в деле.",
            "left",
            ModSounds.VOICE_VAL_ANSWER_CASE,
            0, null
        ));

        // Ответ почему никто не берётся
        lines.add(new DialogueLine(
            "Валерия",
            "Трое детективов уже отказались. Один уволился " +
            "сразу после ознакомления с материалами дела.",
            "left",
            ModSounds.VOICE_VAL_ANSWER_WHY,
            0, null
        ));

        // Ответ про зарплату
        lines.add(new DialogueLine(
            "Валерия",
            "Двойная ставка. Премия за закрытие дела. " +
            "Устраивает?",
            "left",
            ModSounds.VOICE_VAL_ANSWER_SALARY,
            0, null
        ));

        // Завершающая реплика
        lines.add(new DialogueLine(
            "Валерия",
            "Хорошо. Тогда не будем терять времени. " +
            "Дело называется «Стекло».",
            "left",
            ModSounds.VOICE_VAL_CASE_NAME,
            0, null
        ));

        DIALOGUES.put("dialogue_valeria_first", new DialogueData(
            "dialogue_valeria_first",
            "valeria",
            lines,
            "cutscene_apartment_return"
        ));
    }

    // ============================================================
    // ДИАЛОГ — НИНА
    // ============================================================

    private static void registerNinaDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Нина",
            "Ой, молодой человек! Вы к нам? Из города небось?",
            "left",
            ModSounds.VOICE_NINA_GREETING,
            0,
            List.of(
                new DialogueChoice(
                    "Да, турист. Красивые места.",
                    "nina_tourist",
                    ChoiceEffectType.NONE,
                    0, null
                ),
                new DialogueChoice(
                    "Работаю. Изучаю старые заводы.",
                    "nina_factory",
                    ChoiceEffectType.ADD_PARANOIA,
                    0, null
                ),
                new DialogueChoice(
                    "Ищу кое-кого. Не подскажете?",
                    "nina_search",
                    ChoiceEffectType.NONE,
                    0, null
                )
            )
        ));

        // Ветка — турист
        lines.add(new DialogueLine(
            "Нина",
            "Туристы! Надо же. У нас тут тихо, спокойно. " +
            "Чай будете?",
            "left",
            ModSounds.VOICE_NINA_TEA,
            0, null
        ));

        // Ветка — завод (с паузой)
        lines.add(new DialogueLine(
            "Нина",
            "Завод? Давно закрытый. Незачем туда ходить. " +
            "Опасно там. Чай будете?",
            "left",
            ModSounds.VOICE_NINA_FACTORY_DANGEROUS,
            0, null
        ));

        // Предложение чая — всегда в конце
        lines.add(new DialogueLine(
            "Нина",
            "Проходите, проходите! Я как раз пироги поставила.",
            "left",
            ModSounds.VOICE_NINA_COME_IN,
            0, null
        ));

        DIALOGUES.put("dialogue_nina", new DialogueData(
            "dialogue_nina",
            "nina",
            lines,
            null
        ));
    }

    // ============================================================
    // ДИАЛОГ — РАШИД
    // ============================================================

    private static void registerRashidDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Рашид",
            "Чем могу?",
            "left",
            ModSounds.VOICE_RASHID_GREETING,
            0,
            List.of(
                new DialogueChoice(
                    "Ваш отец работал на заводе?",
                    "rashid_father",
                    ChoiceEffectType.ADD_PARANOIA,
                    1, null
                ),
                new DialogueChoice(
                    "Давно живёте в деревне?",
                    "rashid_village",
                    ChoiceEffectType.NONE,
                    0, null
                ),
                new DialogueChoice(
                    "Ничего, ошибся домом.",
                    "rashid_end",
                    ChoiceEffectType.NONE,
                    0, null
                )
            )
        ));

        // Ветка — отец
        lines.add(new DialogueLine(
            "Рашид",
            "Мне было десять лет, когда отец исчез. " +
            "Сказали — несчастный случай. Тела не показали.",
            "left",
            ModSounds.VOICE_RASHID_I_WAS_TEN,
            0, null
        ));

        lines.add(new DialogueLine(
            "Рашид",
            "Сорок пять лет. Я ждал. Думал — может, ушёл сам. " +
            "Но нет... Спасибо вам. Правда.",
            "left",
            ModSounds.VOICE_RASHID_WAITED,
            0, null
        ));

        DIALOGUES.put("dialogue_rashid", new DialogueData(
            "dialogue_rashid",
            "rashid",
            lines,
            null
        ));
    }

    // ============================================================
    // ДИАЛОГ — ТОЛЯ
    // ============================================================

    private static void registerTolyaDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Толя",
            "С завода идёшь?",
            "left",
            ModSounds.VOICE_TOLYA_GREETING,
            0,
            List.of(
                new DialogueChoice(
                    "А что там можно увидеть?",
                    "tolya_factory",
                    ChoiceEffectType.ADD_PARANOIA,
                    2, null
                ),
                new DialogueChoice(
                    "Просто мимо проходил.",
                    "tolya_pass",
                    ChoiceEffectType.NONE,
                    0, null
                )
            )
        ));

        // Ветка — про завод (ключевой диалог)
        lines.add(new DialogueLine(
            "Толя",
            "Я сам там работал. Молодой был, дурной. " +
            "Восьмой отдел.",
            "left",
            ModSounds.VOICE_TOLYA_DEPT8,
            0, null
        ));

        lines.add(new DialogueLine(
            "Толя",
            "Нам говорили — контроль качества. " +
            "Мы маркировали вещи. Каждый дом. Каждую семью. " +
            "Сорок лет.",
            "left",
            ModSounds.VOICE_TOLYA_MARKING,
            0, null
        ));

        lines.add(new DialogueLine(
            "Толя",
            "Нас было пятеро. Четверо уже умерли. Я остался.",
            "left",
            ModSounds.VOICE_TOLYA_FOUR_DEAD,
            0, null
        ));

        lines.add(new DialogueLine(
            "Толя",
            "Думаешь у меня тоже есть? Я искал. Не нашёл. " +
            "Но это ничего не значит...",
            "left",
            ModSounds.VOICE_TOLYA_NOT_FOUND,
            0, null
        ));

        lines.add(new DialogueLine(
            "Толя",
            "Может они меня оставили — наблюдать за самим собой.",
            "left",
            ModSounds.VOICE_TOLYA_WATCHED,
            0, null
        ));

        DIALOGUES.put("dialogue_tolya", new DialogueData(
            "dialogue_tolya",
            "tolya",
            lines,
            null
        ));
    }

    // ============================================================
    // ДИАЛОГ — СЕМЁНЫЧ
    // ============================================================

    private static void registerSemonychDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Семёныч",
            "С завода.",
            "left",
            ModSounds.VOICE_SEM_GREETING,
            40,
            null
        ));

        lines.add(new DialogueLine(
            "Семёныч",
            "Заходи.",
            "left",
            ModSounds.VOICE_SEM_COME_IN,
            0, null
        ));

        lines.add(new DialogueLine(
            "Семёныч",
            "Ту запись я сам делал. Восемнадцать вошли. " +
            "Пятнадцать вышли. Я считал. Я видел.",
            "left",
            ModSounds.VOICE_SEM_I_COUNTED,
            0, null
        ));

        lines.add(new DialogueLine(
            "Семёныч",
            "Мне сказали — они вышли через специальный выход. " +
            "Я поверил.",
            "left",
            ModSounds.VOICE_SEM_SPECIAL_EXIT,
            0, null
        ));

        lines.add(new DialogueLine(
            "Семёныч",
            "Или сделал вид, что поверил.",
            "left",
            ModSounds.VOICE_SEM_OR_PRETENDED,
            0, null
        ));

        DIALOGUES.put("dialogue_semonych", new DialogueData(
            "dialogue_semonych",
            "semonych",
            lines,
            null
        ));
    }

    // ============================================================
    // ДИАЛОГ — ГРОМОВ (финальный)
    // ============================================================

    private static void registerGromovDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Громов",
            "Я ждал. Долго ждал. Заходи.",
            "left",
            ModSounds.VOICE_GROMOV_I_WAITED,
            0, null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "Завод был прикрытием. Деревня — эксперимент.",
            "left",
            ModSounds.VOICE_GROMOV_FACTORY_COVER,
            0, null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "Мы изучали, как люди живут, когда за ними наблюдают, " +
            "и как живут, когда не знают.",
            "left",
            ModSounds.VOICE_GROMOV_VILLAGE_EXPERIMENT,
            0, null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "Самое страшное открытие было не в том, " +
            "что за ними следят.",
            "left",
            ModSounds.VOICE_GROMOV_DISCOVERY,
            0, null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "А в том, что людям это безразлично.",
            "left",
            ModSounds.VOICE_GROMOV_DONT_CARE,
            80,
            null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "Я и то, и другое. Я знаю об эксперименте. " +
            "И я часть эксперимента.",
            "left",
            ModSounds.VOICE_GROMOV_BOTH,
            0, null
        ));

        lines.add(new DialogueLine(
            "Громов",
            "Это я сам сделал, чтобы не забывать, " +
            "что я тоже под наблюдением.",
            "left",
            ModSounds.VOICE_GROMOV_SELF,
            0, null
        ));

        DIALOGUES.put("dialogue_gromov", new DialogueData(
            "dialogue_gromov",
            "gromov",
            lines,
            "cutscene_act2_end"
        ));
    }

    // ============================================================
    // ДИАЛОГ — МАМА (домофон)
    // ============================================================

    private static void registerMomDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Мама",
            "Кто?",
            "left",
            ModSounds.VOICE_MOM_INTERCOM,
            0, null
        ));

        lines.add(new DialogueLine(
            "Детектив",
            "Это я, мам.",
            "right",
            null,
            40, null
        ));

        lines.add(new DialogueLine(
            "Мама",
            "Сынок! Заходи, заходи!",
            "left",
            ModSounds.VOICE_MOM_COME_IN,
            0, null
        ));

        DIALOGUES.put("dialogue_mom_intercom", new DialogueData(
            "dialogue_mom_intercom",
            "mom",
            lines,
            "cutscene_enter_parents_home"
        ));
    }

    // ============================================================
    // ДИАЛОГ — РОДИТЕЛИ (за чаем)
    // ============================================================

    private static void registerParentsDialogue() {
        List<DialogueLine> lines = new ArrayList<>();

        lines.add(new DialogueLine(
            "Мама",
            "Как дела, сынок? Ты выглядишь уставшим.",
            "left",
            ModSounds.VOICE_MOM_TEA,
            0,
            List.of(
                new DialogueChoice(
                    "Расскажи про стеклозавод.",
                    "parents_factory",
                    ChoiceEffectType.ADD_EVIDENCE,
                    0, "evidence_parents_factory"
                ),
                new DialogueChoice(
                    "Папа, ты там работал?",
                    "parents_father",
                    ChoiceEffectType.ADD_PARANOIA,
                    1, null
                ),
                new DialogueChoice(
                    "Всё нормально. Просто зашёл.",
                    "parents_casual",
                    ChoiceEffectType.NONE,
                    0, null
                )
            )
        ));

        // Ветка — про завод
        lines.add(new DialogueLine(
            "Мама",
            "Завод закрылся в 1979 году... " +
            "Мне тогда было 18. Твой отец там работал.",
            "left",
            ModSounds.VOICE_MOM_FACTORY_CLOSED,
            0, null
        ));

        // Пауза — смотрит на отца
        lines.add(new DialogueLine(
            "Мама",
            "...",
            "left",
            ModSounds.VOICE_MOM_LOOK_AT_FATHER,
            60, null
        ));

        // Отец произносит единственную реплику
        lines.add(new DialogueLine(
            "Отец",
            "Не надо тебе туда ходить.",
            "right",
            ModSounds.VOICE_FATHER_DONT_GO,
            0, null
        ));

        // Если продолжать давить
        lines.add(new DialogueLine(
            "Мама",
            "Лучше не спрашивай его об этом... " +
            "После того года он сильно изменился. " +
            "Стал... другим.",
            "left",
            ModSounds.VOICE_MOM_FATHER_CHANGED,
            0, null
        ));

        lines.add(new DialogueLine(
            "Мама",
            "Пожалуйста, не лезь туда, сынок.",
            "left",
            ModSounds.VOICE_MOM_PLEASE,
            0, null
        ));

        DIALOGUES.put("dialogue_parents", new DialogueData(
            "dialogue_parents",
            "mom",
            lines,
            null
        ));
    }

    // ============================================================
    // ПОЛУЧЕНИЕ ДИАЛОГА
    // ============================================================

    public static DialogueData getDialogue(String id) {
        return DIALOGUES.get(id);
    }

    public static void register() {
        registerAll();
        SymbolMod.LOGGER.info(
            "Зарегистрировано {} диалогов",
            DIALOGUES.size()
        );
    }
}
