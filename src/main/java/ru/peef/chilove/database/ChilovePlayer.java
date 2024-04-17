package ru.peef.chilove.database;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.peef.chilove.ChiloveMain;
import ru.peef.chilove.Utils;
import ru.peef.chilove.network.SocketServer;
import ru.peef.chilove.permissions.Group;
import ru.peef.chilove.structures.Selection;

import java.util.UUID;

public class ChilovePlayer {
    private Player player;
    private Group group;

    private int user_id;
    private String nick;
    private UUID uuid;

    public boolean selectionToolEnabled = false;
    public Selection selection = new Selection();
    private boolean is_confirm = false;

    // TODO: Make all searches of Player from this class
    public ChilovePlayer(Player player, boolean addIfEmpty) {
        this.player = player;
        JsonObject jo = Database.getUserByUUID(this.player.getUniqueId());
        if (!setUser()) {
            if (addIfEmpty) {
                Database.addUser(player);
                Bukkit.getLogger().info("Added new player (" + player.getName() + ")");
            }
        }
        setUser();
    }

    public boolean setUser() {
        JsonObject jo = Database.getUserByUUID(this.player.getUniqueId());
        if (jo.get("error") != null) {
            return false;
        } else {
            JsonObject group_array = jo.getAsJsonObject("group");
            group = new Group(
                    group_array.get("id").getAsInt(), // id
                    group_array.get("priority").getAsInt(), // priority
                    group_array.get("name").getAsString(), // name
                    group_array.get("prefix").getAsString(), // prefix
                    group_array.get("suffix").getAsString(), // suffix
                    group_array.get("is_donate").getAsInt(), // is_donate
                    group_array.get("inheritance_id").getAsInt(), // inheritance_id
                    group_array.get("cost").getAsInt(), // cost
                    group_array.get("name_color").getAsString() // name_color
            );
            return true;
        }
    }

    public String getUserGame(String game_name, String get_value) {
        return Database.getUserGame(this.player, game_name, get_value);
    }

    public void addToGame(String game_name) { Database.addUserGame(this.player, game_name); }
    public void updateUserGame(String action, String game_name, int rate_points) {
        int currentRatePoints = Integer.parseInt(getUserGame(game_name, "rate_points"));
        if (action.equals("increase")) {
            currentRatePoints += rate_points;
        } else {
            int rp = Integer.parseInt(String.valueOf(rate_points).replace("-", ""));
            currentRatePoints = currentRatePoints - rp;
        }
        Database.updateGameUser(this.player, game_name, "rate_points", String.valueOf(currentRatePoints));
    }
    public void addSkywarsKit(String kit) { Database.addPlayerSkywarsKit(this.player, kit); }
    public boolean hasSkywarsKit(String kit) { return Database.hasPlayerSkywarsKit(this.player, kit); }

    public void updateTab() { player.setPlayerListName(Utils.getString(ChiloveMain.tabFormat, player, player.getWorld())); }
    public Group getGroup() { return this.group; }
    public Player getPlayer() { return this.player; }
    public void setPlayer(Player player) { this.player = player; }
    public void setConfirmed(Boolean confirm) { this.is_confirm = confirm; }
    public Boolean isConfirmed() { return this.is_confirm; }
}
