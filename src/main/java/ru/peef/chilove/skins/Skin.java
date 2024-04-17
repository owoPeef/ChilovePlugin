package ru.peef.chilove.skins;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.entity.Player;

// TODO
public class Skin {
    public Skin(Player player) {
        WrappedGameProfile wrappedGameProfile = WrappedGameProfile.fromPlayer(player);
    }
}
