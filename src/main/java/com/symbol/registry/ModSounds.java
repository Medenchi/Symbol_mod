package com.symbol.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;

public class ModSounds {

    // ============================================================
    // МУЗЫКА
    // ============================================================

    // Главное меню
    public static final SoundEvent MUSIC_MENU = register("music.menu");
    public static final SoundEvent MUSIC_MENU_AMBIENT = register("music.menu_ambient");

    // Офис Аргус
    public static final SoundEvent MUSIC_OFFICE_COLD = register("music.office_cold");
    public static final SoundEvent MUSIC_OFFICE_TENSE = register("music.office_tense");

    // Город / улица
    public static final SoundEvent MUSIC_CITY_RAIN = register("music.city_rain");
    public static final SoundEvent MUSIC_CITY_WALK = register("music.city_walk");

    // Ночной Дозор (бюро)
    public static final SoundEvent MUSIC_BUREAU = register("music.bureau");

    // Квартира детектива
    public static final SoundEvent MUSIC_APARTMENT = register("music.apartment");
    public static final SoundEvent MUSIC_APARTMENT_NIGHT = register("music.apartment_night");

    // Пятиэтажка / родители
    public static final SoundEvent MUSIC_PARENTS_HOME = register("music.parents_home");

    // Завод — снаружи
    public static final SoundEvent MUSIC_FACTORY_OUTSIDE = register("music.factory_outside");

    // Завод — внутри (нарастает напряжение)
    public static final SoundEvent MUSIC_FACTORY_YARD = register("music.factory_yard");
    public static final SoundEvent MUSIC_FACTORY_HALL = register("music.factory_hall");
    public static final SoundEvent MUSIC_FACTORY_DEEP = register("music.factory_deep");
    public static final SoundEvent MUSIC_FACTORY_BASEMENT = register("music.factory_basement");

    // Финальная комната завода
    public static final SoundEvent MUSIC_FINAL_ROOM = register("music.final_room");
    public static final SoundEvent MUSIC_FINAL_ROOM_STING = register("music.final_room_sting");

    // Деревня
    public static final SoundEvent MUSIC_VILLAGE_CALM = register("music.village_calm");
    public static final SoundEvent MUSIC_VILLAGE_UNEASY = register("music.village_uneasy");
    public static final SoundEvent MUSIC_VILLAGE_PARANOIA = register("music.village_paranoia");

    // Дом Громова
    public static final SoundEvent MUSIC_GROMOV_HOUSE = register("music.gromov_house");

    // Паранойя (нарастающая)
    public static final SoundEvent MUSIC_PARANOIA_LOW = register("music.paranoia_low");
    public static final SoundEvent MUSIC_PARANOIA_MID = register("music.paranoia_mid");
    public static final SoundEvent MUSIC_PARANOIA_HIGH = register("music.paranoia_high");
    public static final SoundEvent MUSIC_PARANOIA_MAX = register("music.paranoia_max");

    // Акт 3 и концовки
    public static final SoundEvent MUSIC_ACT3_TENSE = register("music.act3_tense");
    public static final SoundEvent MUSIC_ENDING_TRUTH = register("music.ending_truth");
    public static final SoundEvent MUSIC_ENDING_REPLACE = register("music.ending_replace");
    public static final SoundEvent MUSIC_ENDING_DENIAL = register("music.ending_denial");
    public static final SoundEvent MUSIC_ENDING_WATCHER = register("music.ending_watcher");

    // Титры
    public static final SoundEvent MUSIC_CREDITS = register("music.credits");

    // ============================================================
    // АМБИЕНТ (фоновые звуки)
    // ============================================================

    public static final SoundEvent AMBIENT_OFFICE = register("ambient.office");
    public static final SoundEvent AMBIENT_RAIN_CITY = register("ambient.rain_city");
    public static final SoundEvent AMBIENT_FACTORY_WIND = register("ambient.factory_wind");
    public static final SoundEvent AMBIENT_FACTORY_CREAK = register("ambient.factory_creak");
    public static final SoundEvent AMBIENT_FACTORY_DRIP = register("ambient.factory_drip");
    public static final SoundEvent AMBIENT_BASEMENT_HUM = register("ambient.basement_hum");
    public static final SoundEvent AMBIENT_VILLAGE_BIRDS = register("ambient.village_birds");
    public static final SoundEvent AMBIENT_VILLAGE_DOG = register("ambient.village_dog");
    public static final SoundEvent AMBIENT_VILLAGE_WIND = register("ambient.village_wind");
    public static final SoundEvent AMBIENT_APARTMENT_QUIET = register("ambient.apartment_quiet");

    // ============================================================
    // ЗВУКИ КАТСЦЕН
    // ============================================================

    public static final SoundEvent SFX_DOOR_OFFICE_OPEN = register("sfx.door_office_open");
    public static final SoundEvent SFX_DOOR_OFFICE_CLOSE = register("sfx.door_office_close");
    public static final SoundEvent SFX_DOOR_BOSS_SLAM = register("sfx.door_boss_slam");
    public static final SoundEvent SFX_FOLDER_THROW = register("sfx.folder_throw");
    public static final SoundEvent SFX_FOLDER_HIT_FACE = register("sfx.folder_hit_face");
    public static final SoundEvent SFX_BOX_PUT_DOWN = register("sfx.box_put_down");
    public static final SoundEvent SFX_BADGE_DROP = register("sfx.badge_drop");
    public static final SoundEvent SFX_FOOTSTEPS_OFFICE = register("sfx.footsteps_office");
    public static final SoundEvent SFX_FOOTSTEPS_STREET = register("sfx.footsteps_street");
    public static final SoundEvent SFX_FOOTSTEPS_FACTORY = register("sfx.footsteps_factory");
    public static final SoundEvent SFX_FOOTSTEPS_BASEMENT = register("sfx.footsteps_basement");
    public static final SoundEvent SFX_RAIN_START = register("sfx.rain_start");
    public static final SoundEvent SFX_BUS_STOP = register("sfx.bus_stop");
    public static final SoundEvent SFX_BUS_LEAVE = register("sfx.bus_leave");
    public static final SoundEvent SFX_FACTORY_GATE_OPEN = register("sfx.factory_gate_open");
    public static final SoundEvent SFX_FACTORY_GATE_CLOSE = register("sfx.factory_gate_close");
    public static final SoundEvent SFX_LOCK_CLICK = register("sfx.lock_click");
    public static final SoundEvent SFX_LOCK_OPEN = register("sfx.lock_open");
    public static final SoundEvent SFX_GLASS_CRUNCH = register("sfx.glass_crunch");
    public static final SoundEvent SFX_GLASS_BREAK = register("sfx.glass_break");
    public static final SoundEvent SFX_INTERCOM_BUZZ = register("sfx.intercom_buzz");
    public static final SoundEvent SFX_INTERCOM_CLICK = register("sfx.intercom_click");
    public static final SoundEvent SFX_LAPTOP_OPEN = register("sfx.laptop_open");
    public static final SoundEvent SFX_LAPTOP_TYPE = register("sfx.laptop_type");
    public static final SoundEvent SFX_LAPTOP_CLOSE = register("sfx.laptop_close");
    public static final SoundEvent SFX_TABLE_HIT = register("sfx.table_hit");
    public static final SoundEvent SFX_WATER_DRINK = register("sfx.water_drink");
    public static final SoundEvent SFX_DOOR_CREAK = register("sfx.door_creak");
    public static final SoundEvent SFX_METAL_DOOR_HEAVY = register("sfx.metal_door_heavy");
    public static final SoundEvent SFX_TRAP_FLOOR_CRACK = register("sfx.trap_floor_crack");
    public static final SoundEvent SFX_TRAP_FLOOR_FALL = register("sfx.trap_floor_fall");
    public static final SoundEvent SFX_TARP_PULL = register("sfx.tarp_pull");
    public static final SoundEvent SFX_PAPER_RUSTLE = register("sfx.paper_rustle");
    public static final SoundEvent SFX_BOOK_OPEN = register("sfx.book_open");
    public static final SoundEvent SFX_BOOK_CLOSE = register("sfx.book_close");
    public static final SoundEvent SFX_SAFE_OPEN = register("sfx.safe_open");
    public static final SoundEvent SFX_HUG = register("sfx.hug");
    public static final SoundEvent SFX_TEACUP = register("sfx.teacup");
    public static final SoundEvent SFX_CHAIR_SCRAPE = register("sfx.chair_scrape");
    public static final SoundEvent SFX_STINGER_HORROR = register("sfx.stinger_horror");
    public static final SoundEvent SFX_STINGER_DISCOVER = register("sfx.stinger_discover");
    public static final SoundEvent SFX_HEARTBEAT = register("sfx.heartbeat");
    public static final SoundEvent SFX_SCREEN_FADE = register("sfx.screen_fade");

    // ============================================================
    // ОЗВУЧКА — ДЕТЕКТИВ — КАТСЦЕНЫ
    // ============================================================

    // Нулевой акт
    public static final SoundEvent VOICE_DET_GLASSCOLLECTOR = register("voice.detective.cutscene.glass_collector");
    public static final SoundEvent VOICE_DET_NONSENSE = register("voice.detective.cutscene.nonsense");
    public static final SoundEvent VOICE_DET_WONT_TAKE = register("voice.detective.cutscene.wont_take_case");
    public static final SoundEvent VOICE_DET_LEAVING = register("voice.detective.cutscene.leaving_office");
    public static final SoundEvent VOICE_DET_SEARCH = register("voice.detective.cutscene.google_search");
    public static final SoundEvent VOICE_DET_NO_RESULTS = register("voice.detective.cutscene.no_results");
    public static final SoundEvent VOICE_DET_RAGE = register("voice.detective.cutscene.rage");
    public static final SoundEvent VOICE_DET_GOING_PARENTS = register("voice.detective.cutscene.going_parents");
    public static final SoundEvent VOICE_DET_DECISION = register("voice.detective.cutscene.decision_factory");

    // Акт 1 — прибытие
    public static final SoundEvent VOICE_DET_FACTORY_LOOK = register("voice.detective.cutscene.factory_look");
    public static final SoundEvent VOICE_DET_NEW_LOCK_GATES = register("voice.detective.cutscene.new_lock_gates");
    public static final SoundEvent VOICE_DET_GATES_CLOSE = register("voice.detective.cutscene.gates_close");
    public static final SoundEvent VOICE_DET_GREAT = register("voice.detective.cutscene.great_sarcasm");
    public static final SoundEvent VOICE_DET_45_YEARS = register("voice.detective.cutscene.45_years_nobody");

    // Акт 1 — финал
    public static final SoundEvent VOICE_DET_FINAL_WHAT_SAW = register("voice.detective.cutscene.what_did_you_see");
    public static final SoundEvent VOICE_DET_FINAL_INSCRIPTION = register("voice.detective.cutscene.village_inscription");
    public static final SoundEvent VOICE_DET_ACT1_END = register("voice.detective.cutscene.act1_end");

    // Акт 3 — внутренний монолог
    public static final SoundEvent VOICE_DET_MONOLOGUE_DONT_KNOW = register("voice.detective.monologue.they_dont_know");
    public static final SoundEvent VOICE_DET_MONOLOGUE_45_YEARS = register("voice.detective.monologue.45_years_watched");
    public static final SoundEvent VOICE_DET_MONOLOGUE_CHILDREN = register("voice.detective.monologue.children_watched");
    public static final SoundEvent VOICE_DET_MONOLOGUE_CANT_STOP = register("voice.detective.monologue.cant_stop");
    public static final SoundEvent VOICE_DET_MONOLOGUE_WHAT_DO = register("voice.detective.monologue.what_do_i_do");

    // ============================================================
    // ОЗВУЧКА — ДЕТЕКТИВ — УЛИКИ (ЗАВОД)
    // ============================================================

    // Двор
    public static final SoundEvent VOICE_DET_CLUE_BARRELS = register("voice.detective.clue.barrels");
    public static final SoundEvent VOICE_DET_CLUE_BARRELS_EMPTY = register("voice.detective.clue.barrels_empty");
    public static final SoundEvent VOICE_DET_CLUE_BARRELS_SMELL = register("voice.detective.clue.barrels_smell");
    public static final SoundEvent VOICE_DET_CLUE_LOADER = register("voice.detective.clue.loader");
    public static final SoundEvent VOICE_DET_CLUE_LOADER_KEY = register("voice.detective.clue.loader_key");
    public static final SoundEvent VOICE_DET_CLUE_LOADER_GRANDPA = register("voice.detective.clue.loader_grandpa");
    public static final SoundEvent VOICE_DET_CLUE_NOTICEBOARD = register("voice.detective.clue.notice_board");
    public static final SoundEvent VOICE_DET_CLUE_NOTICEBOARD_DATE = register("voice.detective.clue.notice_board_date");
    public static final SoundEvent VOICE_DET_CLUE_NOTICEBOARD_CLOSING = register("voice.detective.clue.notice_board_closing_day");

    // Проходная
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL = register("voice.detective.clue.visit_journal");
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL_DAILY = register("voice.detective.clue.visit_journal_daily");
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL_LAST = register("voice.detective.clue.visit_journal_last_entry");
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL_COUNT = register("voice.detective.clue.visit_journal_count");
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL_THREE = register("voice.detective.clue.visit_journal_three_missing");
    public static final SoundEvent VOICE_DET_CLUE_GUARD_PHOTO = register("voice.detective.clue.guard_photo");
    public static final SoundEvent VOICE_DET_CLUE_GUARD_YEARS = register("voice.detective.clue.guard_31_years");
    public static final SoundEvent VOICE_DET_CLUE_GUARD_QUESTION = register("voice.detective.clue.guard_did_he_leave");

    // Цех 1
    public static final SoundEvent VOICE_DET_CLUE_HALL_ENTER = register("voice.detective.clue.hall_enter");
    public static final SoundEvent VOICE_DET_CLUE_GLASS_CRUNCH = register("voice.detective.clue.glass_crunch_feet");
    public static final SoundEvent VOICE_DET_CLUE_SAFETY_POSTER = register("voice.detective.clue.safety_poster");
    public static final SoundEvent VOICE_DET_CLUE_SAFETY_POSTER_DATE = register("voice.detective.clue.safety_poster_date");
    public static final SoundEvent VOICE_DET_CLUE_SAFETY_POSTER_OTHER = register("voice.detective.clue.safety_poster_other_factories");
    public static final SoundEvent VOICE_DET_CLUE_LEVER = register("voice.detective.clue.lever");
    public static final SoundEvent VOICE_DET_CLUE_LEVER_NODUST = register("voice.detective.clue.lever_no_dust");
    public static final SoundEvent VOICE_DET_CLUE_LEVER_TOUCHED = register("voice.detective.clue.lever_recently_touched");
    public static final SoundEvent VOICE_DET_CLUE_GLASS_MOLD = register("voice.detective.clue.glass_mold");
    public static final SoundEvent VOICE_DET_CLUE_GLASS_MOLD_SIZE = register("voice.detective.clue.glass_mold_size");
    public static final SoundEvent VOICE_DET_CLUE_GLASS_MOLD_HUMAN = register("voice.detective.clue.glass_mold_human_shape");
    public static final SoundEvent VOICE_DET_CLUE_FRESH_TRACKS = register("voice.detective.clue.fresh_tracks");
    public static final SoundEvent VOICE_DET_CLUE_FRESH_TRACKS_CORRIDOR = register("voice.detective.clue.fresh_tracks_corridor");

    // Склад
    public static final SoundEvent VOICE_DET_CLUE_INVOICE = register("voice.detective.clue.invoice_k7");
    public static final SoundEvent VOICE_DET_CLUE_INVOICE_LAB = register("voice.detective.clue.invoice_lab");
    public static final SoundEvent VOICE_DET_CLUE_INVOICE_WRONG = register("voice.detective.clue.invoice_not_glass");
    public static final SoundEvent VOICE_DET_CLUE_FLASHLIGHT = register("voice.detective.clue.new_flashlight");
    public static final SoundEvent VOICE_DET_CLUE_FLASHLIGHT_NEW = register("voice.detective.clue.flashlight_brand_new");
    public static final SoundEvent VOICE_DET_CLUE_FLASHLIGHT_WHO = register("voice.detective.clue.flashlight_who_left");

    // Коридор
    public static final SoundEvent VOICE_DET_CLUE_SIGN_DONOT = register("voice.detective.clue.sign_do_not");
    public static final SoundEvent VOICE_DET_CLUE_SIGN_DONOT_WHO = register("voice.detective.clue.sign_who_wrote");
    public static final SoundEvent VOICE_DET_CLUE_SIGN_GOING = register("voice.detective.clue.sign_going_anyway");

    // Кабинет директора
    public static final SoundEvent VOICE_DET_CLUE_PORTRAIT = register("voice.detective.clue.director_portrait");
    public static final SoundEvent VOICE_DET_CLUE_PORTRAIT_YEARS = register("voice.detective.clue.director_years");
    public static final SoundEvent VOICE_DET_CLUE_SAFE = register("voice.detective.clue.open_safe");
    public static final SoundEvent VOICE_DET_CLUE_SAFE_EMPTY = register("voice.detective.clue.safe_empty");
    public static final SoundEvent VOICE_DET_CLUE_SAFE_CODE = register("voice.detective.clue.safe_code_1979");
    public static final SoundEvent VOICE_DET_CLUE_NOTEBOOK = register("voice.detective.clue.director_notebook");
    public static final SoundEvent VOICE_DET_CLUE_NOTEBOOK_14 = register("voice.detective.clue.notebook_march_14");
    public static final SoundEvent VOICE_DET_CLUE_NOTEBOOK_PERMISSION = register("voice.detective.clue.notebook_permission");
    public static final SoundEvent VOICE_DET_CLUE_NOTEBOOK_15 = register("voice.detective.clue.notebook_march_15_empty");
    public static final SoundEvent VOICE_DET_CLUE_NOTEBOOK_EMPTY = register("voice.detective.clue.notebook_empty_page");

    // Цех 2
    public static final SoundEvent VOICE_DET_CLUE_SCHEME = register("voice.detective.clue.tech_scheme");
    public static final SoundEvent VOICE_DET_CLUE_SCHEME_K7 = register("voice.detective.clue.scheme_k7_again");
    public static final SoundEvent VOICE_DET_CLUE_SCHEME_NOTGLASS = register("voice.detective.clue.scheme_not_standard");
    public static final SoundEvent VOICE_DET_CLUE_ROBE = register("voice.detective.clue.aliev_robe");
    public static final SoundEvent VOICE_DET_CLUE_ROBE_NAME = register("voice.detective.clue.robe_aliev_name");
    public static final SoundEvent VOICE_DET_CLUE_ROBE_NOTE = register("voice.detective.clue.robe_note");
    public static final SoundEvent VOICE_DET_CLUE_ROBE_NOTE_READ = register("voice.detective.clue.robe_note_read");

    // Если знает про отца — дополнительные реплики
    public static final SoundEvent VOICE_DET_CLUE_JOURNAL_FATHER = register("voice.detective.clue.journal_father_question");
    public static final SoundEvent VOICE_DET_CLUE_ROBE_FATHER = register("voice.detective.clue.robe_father_worked_here");

    // Подвал
    public static final SoundEvent VOICE_DET_CLUE_BASEMENT_ENTER = register("voice.detective.clue.basement_enter");
    public static final SoundEvent VOICE_DET_CLUE_BASEMENT_DARK = register("voice.detective.clue.basement_so_dark");
    public static final SoundEvent VOICE_DET_CLUE_MATTRESSES = register("voice.detective.clue.mattresses");
    public static final SoundEvent VOICE_DET_CLUE_MATTRESSES_THREE = register("voice.detective.clue.mattresses_three");
    public static final SoundEvent VOICE_DET_CLUE_MATTRESSES_LIVED = register("voice.detective.clue.mattresses_lived_long");
    public static final SoundEvent VOICE_DET_CLUE_SCRATCHES = register("voice.detective.clue.wall_scratches");
    public static final SoundEvent VOICE_DET_CLUE_SCRATCHES_COUNT = register("voice.detective.clue.scratches_counting_days");
    public static final SoundEvent VOICE_DET_CLUE_CANS = register("voice.detective.clue.cans");
    public static final SoundEvent VOICE_DET_CLUE_CANS_DATE = register("voice.detective.clue.cans_march_1979");
    public static final SoundEvent VOICE_DET_CLUE_CANS_COUNT = register("voice.detective.clue.cans_count_days");
    public static final SoundEvent VOICE_DET_CLUE_CANS_MATH = register("voice.detective.clue.cans_three_people_weeks");
    public static final SoundEvent VOICE_DET_CLUE_DIARY_FOUND = register("voice.detective.clue.diary_found");
    public static final SoundEvent VOICE_DET_CLUE_DIARY_UNDER_MATTRESS = register("voice.detective.clue.diary_under_mattress");

    // Чтение дневника (каждая запись отдельно)
    public static final SoundEvent VOICE_DET_DIARY_READ_1 = register("voice.detective.diary.entry_march15");
    public static final SoundEvent VOICE_DET_DIARY_READ_2 = register("voice.detective.diary.entry_march17");
    public static final SoundEvent VOICE_DET_DIARY_READ_3 = register("voice.detective.diary.entry_march21");
    public static final SoundEvent VOICE_DET_DIARY_READ_4 = register("voice.detective.diary.entry_march25");
    public static final SoundEvent VOICE_DET_DIARY_READ_5 = register("voice.detective.diary.entry_final");
    public static final SoundEvent VOICE_DET_DIARY_CLOSE = register("voice.detective.diary.close_reaction");
    public static final SoundEvent VOICE_DET_DIARY_CONCLUSION = register("voice.detective.diary.what_room");

    // Финальная комната
    public static final SoundEvent VOICE_DET_FINAL_NEW_LOCK = register("voice.detective.final_room.new_lock");
    public static final SoundEvent VOICE_DET_FINAL_LOCK_MEANING = register("voice.detective.final_room.lock_meaning");
    public static final SoundEvent VOICE_DET_FINAL_GLASS_BLOCKS = register("voice.detective.final_room.glass_blocks");
    public static final SoundEvent VOICE_DET_FINAL_PEOPLE = register("voice.detective.final_room.its_people");
    public static final SoundEvent VOICE_DET_FINAL_THREE = register("voice.detective.final_room.three_workers");
    public static final SoundEvent VOICE_DET_FINAL_K7 = register("voice.detective.final_room.k7_purpose");
    public static final SoundEvent VOICE_DET_FINAL_FACE = register("voice.detective.final_room.face_in_glass");
    public static final SoundEvent VOICE_DET_FINAL_QUESTION = register("voice.detective.final_room.what_saw");
    public static final SoundEvent VOICE_DET_FINAL_INSCRIPTION_SEE = register("voice.detective.final_room.sees_inscription");
    public static final SoundEvent VOICE_DET_FINAL_VILLAGE = register("voice.detective.final_room.we_saw_village");

    // ============================================================
    // ОЗВУЧКА — ДЕТЕКТИВ — ДЕРЕВНЯ (символы и осмотр)
    // ============================================================

    public static final SoundEvent VOICE_DET_VILLAGE_ARRIVE = register("voice.detective.village.arrive");
    public static final SoundEvent VOICE_DET_VILLAGE_NORMAL = register("voice.detective.village.looks_normal");
    public static final SoundEvent VOICE_DET_VILLAGE_COLD = register("voice.detective.village.cold_feeling");

    // Символы
    public static final SoundEvent VOICE_DET_SYMBOL_FIRST = register("voice.detective.symbol.first_found");
    public static final SoundEvent VOICE_DET_SYMBOL_CUP = register("voice.detective.symbol.on_cup");
    public static final SoundEvent VOICE_DET_SYMBOL_CUP_NINA = register("voice.detective.symbol.nina_knows");
    public static final SoundEvent VOICE_DET_SYMBOL_BATTERY = register("voice.detective.symbol.behind_battery");
    public static final SoundEvent VOICE_DET_SYMBOL_BATTERY_ALIEV = register("voice.detective.symbol.aliev_son_house");
    public static final SoundEvent VOICE_DET_SYMBOL_CARPET = register("voice.detective.symbol.under_carpet");
    public static final SoundEvent VOICE_DET_SYMBOL_CARPET_TOLYA = register("voice.detective.symbol.tolya_was_close");
    public static final SoundEvent VOICE_DET_SYMBOL_ICON = register("voice.detective.symbol.behind_icon");
    public static final SoundEvent VOICE_DET_SYMBOL_ICON_DATE = register("voice.detective.symbol.icon_date_1981");
    public static final SoundEvent VOICE_DET_SYMBOL_PATTERN = register("voice.detective.symbol.seeing_pattern");
    public static final SoundEvent VOICE_DET_SYMBOL_EVERYWHERE = register("voice.detective.symbol.its_everywhere");
    public static final SoundEvent VOICE_DET_SYMBOL_ALL_FOUND = register("voice.detective.symbol.all_found");

    // Осмотр домов
    public static final SoundEvent VOICE_DET_HOUSE1_ENTER = register("voice.detective.houses.house1_enter");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_ENTER = register("voice.detective.houses.empty_house_enter");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_CUP = register("voice.detective.houses.empty_house_cup");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_BOOK = register("voice.detective.houses.empty_house_book");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_DIARY = register("voice.detective.houses.empty_house_diary");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_DRAWING = register("voice.detective.houses.child_drawing");
    public static final SoundEvent VOICE_DET_HOUSE_EMPTY_DRAWING_CHILD = register("voice.detective.houses.child_saw_symbols");

    // Паранойя при исследовании деревни
    public static final SoundEvent VOICE_DET_PARANOIA_1 = register("voice.detective.paranoia.thought_1");
    public static final SoundEvent VOICE_DET_PARANOIA_2 = register("voice.detective.paranoia.thought_2");
    public static final SoundEvent VOICE_DET_PARANOIA_3 = register("voice.detective.paranoia.thought_3");
    public static final SoundEvent VOICE_DET_PARANOIA_4 = register("voice.detective.paranoia.thought_4");
    public static final SoundEvent VOICE_DET_PARANOIA_5 = register("voice.detective.paranoia.thought_5");
    public static final SoundEvent VOICE_DET_PARANOIA_WRIST = register("voice.detective.paranoia.symbol_on_wrist");

    // ============================================================
    // ОЗВУЧКА — НАЧАЛЬНИК
    // ============================================================

    public static final SoundEvent VOICE_BOSS_FIRED = register("voice.boss.youre_fired");
    public static final SoundEvent VOICE_BOSS_SIX_MONTHS = register("voice.boss.six_months_useless");
    public static final SoundEvent VOICE_BOSS_PARANOIA = register("voice.boss.your_paranoia");
    public static final SoundEvent VOICE_BOSS_GET_OUT = register("voice.boss.get_out");
    public static final SoundEvent VOICE_BOSS_MY_AGENCY = register("voice.boss.my_agency");

    // ============================================================
    // ОЗВУЧКА — ВАЛЕРИЯ
    // ============================================================

    public static final SoundEvent VOICE_VAL_GREETING = register("voice.valeria.greeting");
    public static final SoundEvent VOICE_VAL_SO_YOU_ARE = register("voice.valeria.so_you_are_detective");
    public static final SoundEvent VOICE_VAL_BEST_DETECTIVE = register("voice.valeria.best_detective_fired");
    public static final SoundEvent VOICE_VAL_WE_DONT_CARE = register("voice.valeria.we_dont_care_who");
    public static final SoundEvent VOICE_VAL_HARD_CASE = register("voice.valeria.case_is_hard");
    public static final SoundEvent VOICE_VAL_NOBODY_WANTS = register("voice.valeria.nobody_wants_it");
    public static final SoundEvent VOICE_VAL_SURVIVE_STAY = register("voice.valeria.survive_you_stay");
    public static final SoundEvent VOICE_VAL_QUESTIONS = register("voice.valeria.any_questions");
    public static final SoundEvent VOICE_VAL_CASE_NAME = register("voice.valeria.case_name_glass");
    public static final SoundEvent VOICE_VAL_CASE_CLOSED = register("voice.valeria.case_closed");
    public static final SoundEvent VOICE_VAL_WELL_DONE = register("voice.valeria.well_done");

    // Ответы Валерии на вопросы игрока
    public static final SoundEvent VOICE_VAL_ANSWER_CASE = register("voice.valeria.answer.what_case");
    public static final SoundEvent VOICE_VAL_ANSWER_WHY = register("voice.valeria.answer.why_nobody");
    public static final SoundEvent VOICE_VAL_ANSWER_SALARY = register("voice.valeria.answer.salary");
    public static final SoundEvent VOICE_VAL_ANSWER_NOD = register("voice.valeria.answer.silent_nod");

    // ============================================================
    // ОЗВУЧКА — МАМА
    // ============================================================

    public static final SoundEvent VOICE_MOM_OPEN_DOOR = register("voice.mom.open_door");
    public static final SoundEvent VOICE_MOM_HUG = register("voice.mom.hug");
    public static final SoundEvent VOICE_MOM_COME_IN = register("voice.mom.come_in");
    public static final SoundEvent VOICE_MOM_TEA = register("voice.mom.tea_offer");
    public static final SoundEvent VOICE_MOM_FACTORY_CLOSED = register("voice.mom.factory_closed_1979");
    public static final SoundEvent VOICE_MOM_WAS_18 = register("voice.mom.i_was_18");
    public static final SoundEvent VOICE_MOM_FATHER_WORKED = register("voice.mom.father_worked_there");
    public static final SoundEvent VOICE_MOM_LOOK_AT_FATHER = register("voice.mom.look_at_father");
    public static final SoundEvent VOICE_MOM_AFTER_FATHER = register("voice.mom.after_that_year");
    public static final SoundEvent VOICE_MOM_FATHER_CHANGED = register("voice.mom.father_changed");
    public static final SoundEvent VOICE_MOM_DONT_ASK = register("voice.mom.dont_ask_him");
    public static final SoundEvent VOICE_MOM_PLEASE = register("voice.mom.please_dont_go");
    public static final SoundEvent VOICE_MOM_INTERCOM = register("voice.mom.intercom_who");

    // ============================================================
    // ОЗВУЧКА — ОТЕЦ
    // ============================================================

    public static final SoundEvent VOICE_FATHER_DONT_GO = register("voice.father.dont_go_there");
    // Отец говорит ТОЛЬКО одну фразу — это намеренно

    // ============================================================
    // ОЗВУЧКА — НИНА
    // ============================================================

    public static final SoundEvent VOICE_NINA_GREETING = register("voice.nina.greeting");
    public static final SoundEvent VOICE_NINA_FROM_CITY = register("voice.nina.from_city");
    public static final SoundEvent VOICE_NINA_FACTORY_DANGEROUS = register("voice.nina.factory_dangerous");
    public static final SoundEvent VOICE_NINA_TEA = register("voice.nina.tea");
    public static final SoundEvent VOICE_NINA_STAY = register("voice.nina.stay_for_night");
    public static final SoundEvent VOICE_NINA_SMILE = register("voice.nina.just_smiles");
    // Нина не отвечает на вопрос про символ — намеренно

    // ============================================================
    // ОЗВУЧКА — РАШИД
    // ============================================================

    public static final SoundEvent VOICE_RASHID_GREETING = register("voice.rashid.greeting");
    public static final SoundEvent VOICE_RASHID_FATHER_QUESTION = register("voice.rashid.father_question_reaction");
    public static final SoundEvent VOICE_RASHID_I_WAS_TEN = register("voice.rashid.i_was_ten");
    public static final SoundEvent VOICE_RASHID_ACCIDENT = register("voice.rashid.they_said_accident");
    public static final SoundEvent VOICE_RASHID_NO_BODY = register("voice.rashid.no_body_shown");
    public static final SoundEvent VOICE_RASHID_ROBE_REACTION = register("voice.rashid.robe_reaction");
    public static final SoundEvent VOICE_RASHID_WAITED = register("voice.rashid.waited_45_years");
    public static final SoundEvent VOICE_RASHID_THANK_YOU = register("voice.rashid.thank_you");

    // ============================================================
    // ОЗВУЧКА — ТОЛЯ
    // ============================================================

    public static final SoundEvent VOICE_TOLYA_GREETING = register("voice.tolya.from_factory");
    public static final SoundEvent VOICE_TOLYA_I_WORKED = register("voice.tolya.i_worked_there");
    public static final SoundEvent VOICE_TOLYA_YOUNG = register("voice.tolya.was_young_stupid");
    public static final SoundEvent VOICE_TOLYA_DEPT8 = register("voice.tolya.eighth_department");
    public static final SoundEvent VOICE_TOLYA_QUALITY = register("voice.tolya.they_said_quality");
    public static final SoundEvent VOICE_TOLYA_MARKING = register("voice.tolya.we_were_marking");
    public static final SoundEvent VOICE_TOLYA_EVERY_HOUSE = register("voice.tolya.every_house_family");
    public static final SoundEvent VOICE_TOLYA_40_YEARS = register("voice.tolya.forty_years");
    public static final SoundEvent VOICE_TOLYA_FIVE = register("voice.tolya.we_were_five");
    public static final SoundEvent VOICE_TOLYA_FOUR_DEAD = register("voice.tolya.four_already_dead");
    public static final SoundEvent VOICE_TOLYA_I_REMAIN = register("voice.tolya.i_remain");
    public static final SoundEvent VOICE_TOLYA_WATCHED = register("voice.tolya.left_to_watch_myself");
    public static final SoundEvent VOICE_TOLYA_SEARCHED = register("voice.tolya.i_searched_for_it");
    public static final SoundEvent VOICE_TOLYA_NOT_FOUND = register("voice.tolya.did_not_find");
    public static final SoundEvent VOICE_TOLYA_MEANS_NOTHING = register("voice.tolya.means_nothing");

    // ============================================================
    // ОЗВУЧКА — СЕМЁНЫЧ
    // ============================================================

    public static final SoundEvent VOICE_SEM_GREETING = register("voice.semonych.from_factory");
    public static final SoundEvent VOICE_SEM_COME_IN = register("voice.semonych.come_in");
    public static final SoundEvent VOICE_SEM_MARCH15 = register("voice.semonych.march15");
    public static final SoundEvent VOICE_SEM_I_WROTE = register("voice.semonych.i_wrote_that");
    public static final SoundEvent VOICE_SEM_18_IN = register("voice.semonych.18_entered");
    public static final SoundEvent VOICE_SEM_15_OUT = register("voice.semonych.15_left");
    public static final SoundEvent VOICE_SEM_I_COUNTED = register("voice.semonych.i_counted");
    public static final SoundEvent VOICE_SEM_SPECIAL_EXIT = register("voice.semonych.special_exit_they_said");
    public static final SoundEvent VOICE_SEM_I_BELIEVED = register("voice.semonych.i_believed");
    public static final SoundEvent VOICE_SEM_OR_PRETENDED = register("voice.semonych.or_pretended_to");

    // ============================================================
    // ОЗВУЧКА — ГРОМОВ
    // ============================================================

    public static final SoundEvent VOICE_GROMOV_I_WAITED = register("voice.gromov.i_waited");
    public static final SoundEvent VOICE_GROMOV_WAITED_LONG = register("voice.gromov.waited_long");
    public static final SoundEvent VOICE_GROMOV_COME_IN = register("voice.gromov.come_in");
    public static final SoundEvent VOICE_GROMOV_FACTORY_COVER = register("voice.gromov.factory_was_cover");
    public static final SoundEvent VOICE_GROMOV_VILLAGE_EXPERIMENT = register("voice.gromov.village_experiment");
    public static final SoundEvent VOICE_GROMOV_WATCHED = register("voice.gromov.how_people_live");
    public static final SoundEvent VOICE_GROMOV_DISCOVERY = register("voice.gromov.scariest_discovery");
    public static final SoundEvent VOICE_GROMOV_DONT_CARE = register("voice.gromov.people_dont_care");
    public static final SoundEvent VOICE_GROMOV_TATTOO = register("voice.gromov.shows_tattoo");
    public static final SoundEvent VOICE_GROMOV_BOTH = register("voice.gromov.i_am_both");
    public static final SoundEvent VOICE_GROMOV_SELF = register("voice.gromov.i_am_also_watched");
    public static final SoundEvent VOICE_GROMOV_REMINDER = register("voice.gromov.tattoo_reminder");
    public static final SoundEvent VOICE_GROMOV_WONT_END = register("voice.gromov.wont_end");
    public static final SoundEvent VOICE_GROMOV_I_KNOW = register("voice.gromov.i_know_you_know");
    public static final SoundEvent VOICE_GROMOV_STILL_CALLED = register("voice.gromov.still_called");
    public static final SoundEvent VOICE_GROMOV_RESPECT_NOD = register("voice.gromov.respect_nod");

    // ============================================================
    // ЗВУКИ ПАРАНОЙИ
    // ============================================================

    public static final SoundEvent PARANOIA_RUSTLE = register("paranoia.rustle");
    public static final SoundEvent PARANOIA_WHISPER_1 = register("paranoia.whisper_1");
    public static final SoundEvent PARANOIA_WHISPER_2 = register("paranoia.whisper_2");
    public static final SoundEvent PARANOIA_WHISPER_3 = register("paranoia.whisper_3");
    public static final SoundEvent PARANOIA_KNOCK = register("paranoia.knock");
    public static final SoundEvent PARANOIA_STEPS_BEHIND = register("paranoia.steps_behind");
    public static final SoundEvent PARANOIA_BREATHING = register("paranoia.breathing");
    public static final SoundEvent PARANOIA_GLITCH = register("paranoia.glitch");
    public static final SoundEvent PARANOIA_STATIC = register("paranoia.static");
    public static final SoundEvent PARANOIA_HEARTBEAT = register("paranoia.heartbeat");

    // ============================================================
    // ЗВУКИ 3D КНОПОК
    // ============================================================

    public static final SoundEvent BUTTON_APPEAR = register("button3d.appear");
    public static final SoundEvent BUTTON_HOVER = register("button3d.hover");
    public static final SoundEvent BUTTON_CLICK = register("button3d.click");
    public static final SoundEvent BUTTON_DISAPPEAR = register("button3d.disappear");
    public static final SoundEvent BUTTON_START_SPECIAL = register("button3d.start_special");

    // ============================================================
    // ЗВУКИ GUI / ДНЕВНИКА
    // ============================================================

    public static final SoundEvent GUI_DIARY_OPEN = register("gui.diary_open");
    public static final SoundEvent GUI_DIARY_CLOSE = register("gui.diary_close");
    public static final SoundEvent GUI_DIARY_PAGE_TURN = register("gui.diary_page_turn");
    public static final SoundEvent GUI_EVIDENCE_ADD = register("gui.evidence_added");
    public static final SoundEvent GUI_EVIDENCE_OPEN = register("gui.evidence_open");
    public static final SoundEvent GUI_MENU_OPEN = register("gui.menu_open");
    public static final SoundEvent GUI_MENU_CLICK = register("gui.menu_click");
    public static final SoundEvent GUI_MENU_HOVER = register("gui.menu_hover");

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД
    // ============================================================

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(SymbolMod.MOD_ID, name);
        return Registry.register(
            Registries.SOUND_EVENT,
            id,
            SoundEvent.of(id)
        );
    }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация звуков и озвучки...");
    }
}
