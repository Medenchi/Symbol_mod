package com.symbol.system;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;

import com.symbol.SymbolMod;

public class EvidenceSystem {

    // Улики каждого игрока
    private static final Map<UUID, List<Evidence>> PLAYER_EVIDENCE = new HashMap<>();

    // Все улики в игре
    private static final Map<String, EvidenceData> ALL_EVIDENCE = new HashMap<>();

    // ============================================================
    // РЕГИСТРАЦИЯ УЛИК
    // ============================================================

    public static void registerAll() {
        // Акт 0
        register("evidence_parents_factory",
            "Разговор с родителями",
            "Мама рассказала что завод закрылся в 1979 году. " +
            "Отец там работал. После закрытия он изменился.",
            EvidenceCategory.PEOPLE,
            "§7Связь: §fОтец — Завод №7"
        );

        // Акт 1 — двор
        register("evidence_notice_board",
            "Доска объявлений",
            "Внеплановое собрание 15 марта 1979 года. " +
            "Всем рабочим явка обязательна. " +
            "Это день закрытия завода.",
            EvidenceCategory.DOCUMENTS,
            "§7Дата: §f15.03.1979"
        );

        register("evidence_visit_journal",
            "Журнал посещений",
            "15 марта 1979. Вошли 18 человек. " +
            "Вышли 15. Трое не расписались на выход.",
            EvidenceCategory.DOCUMENTS,
            "§7Пропали: §f3 рабочих"
        );

        register("evidence_guard_photo",
            "Фото охранника",
            "Охранник по прозвищу Семёныч. " +
            "31 год службы на заводе.",
            EvidenceCategory.PEOPLE,
            "§7Найти: §fСемёныча"
        );

        // Акт 1 — цеха
        register("evidence_glass_mold",
            "Стеклянная форма",
            "Форма для литья стекла. " +
            "Размер: 1.8 метра на 0.5 метра. " +
            "Это форма для человека. Не для изделий.",
            EvidenceCategory.PHYSICAL,
            "§c[ВАЖНО] §fФорма под человека"
        );

        register("evidence_fresh_tracks",
            "Свежие следы",
            "На полу цеха свежие следы. " +
            "Кто-то здесь регулярно бывает.",
            EvidenceCategory.PHYSICAL,
            "§7Кто-то посещает завод"
        );

        register("evidence_invoice_k7",
            "Накладная на состав К-7",
            "Накладная на специальный состав К-7. " +
            "Получатель — Лаборатория. " +
            "На стекольном заводе не должно быть лаборатории.",
            EvidenceCategory.DOCUMENTS,
            "§7Состав К-7 — неизвестное вещество"
        );

        register("evidence_flashlight",
            "Новый фонарик",
            "Совсем новый фонарик на заброшенном заводе. " +
            "Батарейки свежие. Кто-то оставил намеренно.",
            EvidenceCategory.PHYSICAL,
            "§7Кто-то знал что ты придёшь?"
        );

        // Акт 1 — кабинет директора
        register("evidence_director_notebook",
            "Записная книжка Громова",
            "14 марта 1979: 'Получено разрешение. " +
            "Процедура начнётся завтра.' " +
            "15 марта — страница пустая.",
            EvidenceCategory.DOCUMENTS,
            "§7Громов знал что произойдёт"
        );

        register("evidence_safe_code",
            "Код сейфа",
            "Код сейфа директора — 1979. " +
            "Год закрытия завода. Сейф был открыт.",
            EvidenceCategory.PHYSICAL,
            "§7Код: §f1-9-7-9"
        );

        // Акт 1 — подвал
        register("evidence_mattresses",
            "Три матраса",
            "Трое людей жили в подвале долгое время. " +
            "На стенах царапины — считали дни.",
            EvidenceCategory.PHYSICAL,
            "§7Трое скрывались в подвале"
        );

        register("evidence_cans",
            "Советские консервы",
            "23 банки консервов марта 1979 года. " +
            "Трое человек провели около двух недель.",
            EvidenceCategory.PHYSICAL,
            "§7Прожили: §f~2 недели"
        );

        register("evidence_basement_diary",
            "Дневник рабочего",
            "Дневник одного из троих рабочих. " +
            "Последняя запись обрывается. " +
            "Они видели 'комнату' и не могли выйти.",
            EvidenceCategory.DOCUMENTS,
            "§c[ВАЖНО] §fОни видели комнату"
        );

        register("evidence_aliev_robe",
            "Роба Алиева",
            "Роба рабочего Алиева. " +
            "На спине карандашом: " +
            "'Если найдёшь — иди в подвал. Мы там.'",
            EvidenceCategory.PHYSICAL,
            "§7Рабочий: §fАлиев"
        );

        // Акт 1 — финал
        register("evidence_glass_corpses",
            "Тела в стекле",
            "Трое рабочих залиты составом К-7. " +
            "Застыли в вертикальных блоках стекла. " +
            "Убиты потому что видели 'комнату'.",
            EvidenceCategory.PHYSICAL,
            "§c[КЛЮЧЕВОЕ] §fТела найдены"
        );

        register("evidence_inscription",
            "Надпись в финальной комнате",
            "Выцарапано в стекле: " +
            "'МЫ ВИДЕЛИ ДЕРЕВНЮ'",
            EvidenceCategory.PHYSICAL,
            "§c[КЛЮЧЕВОЕ] §fСвязь с деревней"
        );

        // Акт 2 — деревня
        register("evidence_symbol_cup",
            "Символ на кружке",
            "Прищуренный глаз на донышке чайной кружки " +
            "в доме Нины.",
            EvidenceCategory.SYMBOL,
            "§7Символ №1"
        );

        register("evidence_symbol_battery",
            "Символ за батареей",
            "Прищуренный глаз за батареей " +
            "в доме сына Алиева — Рашида.",
            EvidenceCategory.SYMBOL,
            "§7Символ №2"
        );

        register("evidence_symbol_carpet",
            "Символ под ковром",
            "Прищуренный глаз под ковром в доме Толи. " +
            "Он искал но не там.",
            EvidenceCategory.SYMBOL,
            "§7Символ №3"
        );

        register("evidence_symbol_icon",
            "Символ за иконой",
            "Прищуренный глаз за иконой в доме Семёныча. " +
            "Дата рядом — 1981. " +
            "Маркировка продолжилась после закрытия завода.",
            EvidenceCategory.SYMBOL,
            "§7Символ №4 — §fДата: 1981"
        );

        register("evidence_symbol_empty_house",
            "Рисунок ребёнка",
            "Ребёнок из пустого дома нарисовал деревню " +
            "и символ над каждым домом. " +
            "Для него это было обычным.",
            EvidenceCategory.SYMBOL,
            "§7Символ №5 — §cДети видят"
        );

        register("evidence_tolya_story",
            "История Толи",
            "Восьмой отдел маркировал каждый дом " +
            "и каждую семью в деревне. " +
            "Пятеро сотрудников. Четверо умерли. " +
            "Толя остался.",
            EvidenceCategory.PEOPLE,
            "§7Восьмой отдел существует"
        );

        register("evidence_gromov_truth",
            "Правда Громова",
            "Завод был прикрытием. " +
            "Деревня — эксперимент по наблюдению. " +
            "Самое страшное: людям это безразлично.",
            EvidenceCategory.DOCUMENTS,
            "§c[ФИНАЛ] §fЭксперимент раскрыт"
        );

        SymbolMod.LOGGER.info(
            "Зарегистрировано {} улик",
            ALL_EVIDENCE.size()
        );
    }

    // ============================================================
    // ДОБАВЛЕНИЕ УЛИКИ ИГРОКУ
    // ============================================================

    public static void addEvidence(PlayerEntity player, String evidenceId) {
        EvidenceData data = ALL_EVIDENCE.get(evidenceId);
        if (data == null) {
            SymbolMod.LOGGER.warn("Улика '{}' не найдена!", evidenceId);
            return;
        }

        UUID id = player.getUuid();
        List<Evidence> playerList = PLAYER_EVIDENCE
            .computeIfAbsent(id, k -> new ArrayList<>());

        // Не добавлять дубликаты
        boolean alreadyHas = playerList.stream()
            .anyMatch(e -> e.data.id.equals(evidenceId));

        if (!alreadyHas) {
            playerList.add(new Evidence(data, System.currentTimeMillis()));

            // Уведомление
            player.sendMessage(
                net.minecraft.text.Text.literal(
                    "§6[Дневник] §7Добавлена улика: §f" + data.title
                ),
                false
            );

            // Звук
            player.getWorld().playSound(
                player, player.getBlockPos(),
                com.symbol.registry.ModSounds.GUI_EVIDENCE_ADD,
                net.minecraft.sound.SoundCategory.MASTER,
                0.8f, 1.0f
            );

            // Отправляем клиенту для дневника
            if (player instanceof ServerPlayerEntity sp) {
                NetworkHandler.sendEvidenceAdded(sp, data);
            }

            SymbolMod.LOGGER.debug(
                "Улика '{}' добавлена игроку {}",
                evidenceId,
                player.getName().getString()
            );
        }
    }

    // ============================================================
    // ПОЛУЧЕНИЕ УЛИК
    // ============================================================

    public static List<Evidence> getPlayerEvidence(PlayerEntity player) {
        return PLAYER_EVIDENCE.getOrDefault(
            player.getUuid(),
            new ArrayList<>()
        );
    }

    public static boolean hasEvidence(PlayerEntity player, String evidenceId) {
        List<Evidence> list = PLAYER_EVIDENCE.get(player.getUuid());
        if (list == null) return false;
        return list.stream().anyMatch(e -> e.data.id.equals(evidenceId));
    }

    public static int getEvidenceCount(PlayerEntity player) {
        List<Evidence> list = PLAYER_EVIDENCE.get(player.getUuid());
        return list == null ? 0 : list.size();
    }

    // ============================================================
    // ДАННЫЕ
    // ============================================================

    private static void register(
        String id,
        String title,
        String description,
        EvidenceCategory category,
        String hint
    ) {
        ALL_EVIDENCE.put(id, new EvidenceData(
            id, title, description, category, hint
        ));
    }

    public static class EvidenceData {
        public final String id;
        public final String title;
        public final String description;
        public final EvidenceCategory category;
        public final String hint;

        public EvidenceData(
            String id,
            String title,
            String description,
            EvidenceCategory category,
            String hint
        ) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.category = category;
            this.hint = hint;
        }
    }

    public static class Evidence {
        public final EvidenceData data;
        public final long foundAt;

        public Evidence(EvidenceData data, long foundAt) {
            this.data = data;
            this.foundAt = foundAt;
        }
    }

    public enum EvidenceCategory {
        DOCUMENTS,  // Документы
        PHYSICAL,   // Физические улики
        PEOPLE,     // Люди и свидетели
        SYMBOL      // Символы
    }
}
