package ru.peef.chilove;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;

public class ChilovePlaceholder extends PlaceholderExpansion {
    public ChilovePlaceholder() {}

    @Override
    public @NotNull String getAuthor() { return "owoPeef"; }

    @Override
    public @NotNull String getIdentifier() { return "chilove"; }

    @Override
    public @NotNull String getVersion() { return "1.0.0"; }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
        if (chilovePlayer != null) {
            if (params.equalsIgnoreCase("prefix")) {
                return chilovePlayer.getGroup().getPrefix();
            } else if (params.equalsIgnoreCase("suffix")) {
                return chilovePlayer.getGroup().getSuffix();
            }
        }

        return null;
    }
}
