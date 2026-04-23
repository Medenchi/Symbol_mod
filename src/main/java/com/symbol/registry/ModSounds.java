package com.symbol.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import com.symbol.SymbolMod;

public class ModSounds {

    // ==============================
    // МУЗЫКА
    // ==============================
    public static final SoundEvent МУЗЫКА_МЕНЮ = register("music.menu");
    public static final SoundEvent МУЗЫКА_ОФИС = register("music.office");
    public static final SoundEvent МУЗЫКА_ЗАВОД = register("music.factory");
    public static final SoundEvent МУЗЫКА_ДЕРЕВНЯ = register("music.village");
    public static final SoundEvent МУЗЫКА_НАПРЯЖЕНИЕ = register("music.tension");
    public static final SoundEvent МУЗЫКА_ПАРАНОЙЯ = register("music.paranoia");
    public static final SoundEvent МУЗЫКА_ФИНАЛ = register("music.finale");

    // ==============================
    // ЗВУКИ КАТСЦЕН
    // ==============================
    public static final SoundEvent ЗВУК_ДВЕРЬ_ОФИС = register("cutscene.door_office");
    public static final SoundEvent ЗВУК_ВОРОТА_ЗАВОД = register("cutscene.factory_gate");
    public static final SoundEvent ЗВУК_ДОМОФОН = register("cutscene.intercom");
    public static final SoundEvent ЗВУК_ШАГИ_ОФИС = register("cutscene.footsteps_office");
    public static final SoundEvent ЗВУК_ШАГИ_ЗАВОД = register("cutscene.footsteps_factory");
    public static final SoundEvent ЗВУК_УДАР_СТОЛ = register("cutscene.table_hit");
    public static final SoundEvent ЗВУК_ПАПКА_БРОСОК = register("cutscene.folder_throw");
    public static final SoundEvent ЗВУК_СТЕКЛО_ХРУ СТ = register("cutscene.glass_crunch");

    // ==============================
    // ОЗВУЧКА NPC
    // ==============================
    // Детектив
    public static final SoundEvent ДЕТЕКТИВ_УВОЛЕН = register("voice.detective.fired");
    public static final SoundEvent ДЕТЕКТИВ_ЗАМОК = register("voice.detective.new_lock");
    public static final SoundEvent ДЕТЕКТИВ_ФОРМА = register("voice.detective.human_form");
    public static final SoundEvent ДЕТЕКТИВ_ДЕРЕВНЯ = register("voice.detective.village_clue");
    public static final SoundEvent ДЕТЕКТИВ_ЕДЕМ = register("voice.detective.going_factory");

    // Начальник
    public static final SoundEvent НАЧАЛЬНИК_УВОЛЕН = register("voice.boss.youre_fired");

    // Валерия
    public static final SoundEvent ВАЛЕРИЯ_ПРИВЕТ = register("voice.valeria.greeting");
    public static final SoundEvent ВАЛЕРИЯ_ДЕЛО = register("voice.valeria.case_name");

    // Громов
    public static final SoundEvent ГРОМОВ_ЖДАЛ = register("voice.gromov.i_waited");
    public static final SoundEvent ГРОМОВ_ЭКСПЕРИМЕНТ = register("voice.gromov.experiment");
    public static final SoundEvent ГРОМОВ_НЕ_ЗАКОНЧИТСЯ = register("voice.gromov.wont_end");

    // Мама
    public static final SoundEvent МАМА_ЗАВОД = register("voice.mama.factory_closed");
    public static final SoundEvent МАМА_ОТЕЦ = register("voice.mama.about_father");

    // Отец
    public static final SoundEvent ОТЕЦ_НЕ_НАДО = register("voice.father.dont_go");

    // Нина
    public static final SoundEvent НИНА_ЧАЙ = register("voice.nina.tea");

    // Толя
    public static final SoundEvent ТОЛЯ_ВОСЬМОЙ = register("voice.tolya.eight_department");

    // ==============================
    // ЗВУКИ 3D КНОПОК
    // ==============================
    public static final SoundEvent КНОПКА_ПОЯВЛЕНИЕ = register("button3d.appear");
    public static final SoundEvent КНОПКА_НАВЕДЕНИЕ = register("button3d.hover");
    public static final SoundEvent КНОПКА_НАЖАТИЕ = register("button3d.click");
    public static final SoundEvent КНОПКА_ИСЧЕЗАНИЕ = register("button3d.disappear");

    // ==============================
    // ЗВУКИ ПАРАНОЙИ
    // ==============================
    public static final SoundEvent ПАРАНОЙЯ_ШОРОХ = register("paranoia.rustle");
    public static final SoundEvent ПАРАНОЙЯ_ШЁПОТ = register("paranoia.whisper");
    public static final SoundEvent ПАРАНОЙЯ_СТУК = register("paranoia.knock");

    // ==============================
    // ВСПОМОГАТЕЛЬНОЕ
    // ==============================
    private static SoundEvent register(String name) {
        Identifier id = new Identifier(SymbolMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        SymbolMod.LOGGER.info("Регистрация звуков...");
    }
}
