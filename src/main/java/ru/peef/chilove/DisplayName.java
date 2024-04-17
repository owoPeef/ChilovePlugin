package ru.peef.chilove;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class DisplayName {
    Player player;
    public DisplayName(Player player) {
        this.player = player;
    }

    public void setPlayerPrefix(String message, ChatColor name_color) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team2 = scoreboard.getTeam(player.getName());
        if (team2 != null) {
            team2.addEntry(player.getName());
            team2.setPrefix(message);
            team2.setColor(name_color);
        } else {
            Team team = scoreboard.registerNewTeam(player.getName());
            team.addEntry(player.getName());
            team.setPrefix(message);
            team.setColor(name_color);
        }
    }
}
