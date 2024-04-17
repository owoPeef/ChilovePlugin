package ru.peef.chilove.sounds;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    public static final HashMap<String, String> SOUNDS = new HashMap<>();

    public static void init() {
        SOUNDS.put("coin", "chilovemod:coin_sound");
        SOUNDS.put("win", "chilovemod:win_sound");
        SOUNDS.put("wind_charge", "chilovemod:wind_charge");
        SOUNDS.put("mace_smash", "chilovemod:mace_smash");
    }

    public static String getSound(String name) {
        name = name.toLowerCase();
        for (Map.Entry<String, String> sound: SOUNDS.entrySet()) {
            String soundName = sound.getKey().toLowerCase();
            if (soundName.equals(name)) return sound.getValue();
        }
        return null;
    }
}
