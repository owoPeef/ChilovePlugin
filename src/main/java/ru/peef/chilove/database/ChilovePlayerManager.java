package ru.peef.chilove.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.peef.chilove.ConsoleColor;

import java.util.ArrayList;
import java.util.List;

public class ChilovePlayerManager {
    private static final String manager_prefix = "[PLAYERS MANAGER] ";
    private static final List<ChilovePlayer> players = new ArrayList<>();

    public static void addChilovePlayer(ChilovePlayer chilovePlayer) {
        Bukkit.getLogger().info(ConsoleColor.ANSI_GREEN + manager_prefix + "Add " + chilovePlayer.getPlayer().getName() + " to players..." + ConsoleColor.ANSI_RESET);
        players.add(chilovePlayer);
    }
    public static void removeChilovePlayer(ChilovePlayer chilovePlayer) {
        Bukkit.getLogger().info(ConsoleColor.ANSI_RED + manager_prefix + "Deleting " + chilovePlayer.getPlayer().getName() + " from players..." + ConsoleColor.ANSI_RESET);
        players.remove(chilovePlayer);
    }
    public static List<ChilovePlayer> getChilovePlayers() { return players; }
    public static ChilovePlayer getChilovePlayer(Player player) {
        for (ChilovePlayer cPlayer: players) {
            if (cPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return cPlayer;
            }
        }
        return null;
    }
    public static ChilovePlayer getChilovePlayerByName(String name) {
        for (ChilovePlayer cPlayer: players) {
            if (cPlayer.getPlayer().getName().equals(name)) return cPlayer;
        }
        return null;
    }

    public static Player getPlayer(Player player) {
        for (ChilovePlayer cPlayer: players) {
            if (cPlayer.getPlayer().equals(player)) return cPlayer.getPlayer();
        }
        return null;
    }
}
