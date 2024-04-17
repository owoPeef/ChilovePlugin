package ru.peef.chilove;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.database.Database;
import ru.peef.chilove.vk.Messages;

import java.util.List;

public class Utils {
    static Plugin plugin = ChiloveMain.getPlugin(ChiloveMain.class);

    public static void setPlayerTab(Player player, String text) { player.setPlayerListName(text); }
    public static void setPlayerDisplayName(Player player, String text) { player.setCustomName(text); }

    public static void sendConfirmationCode(Player player, long timestamp) {
        // hash = {playernick}:{timestamp}-fe9zx1fwl43m57
        String hash = player.getName() + ":" + timestamp + "-fe9zx1fwl43m57";
        String md5 = MD5(hash);
        Messages.send(621138817, md5);
        Database.addHash(621138817, md5, player);
    }

//    public static void setJoinMessageShow(boolean bool) { ChiloveMain.joinMessageEnabled = bool; }
//    public static void setQuitMessageShow(boolean bool) { ChiloveMain.quitMessageEnabled = bool; }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {}
        return null;
    }

    public static String format(String text) { return text.replace("&", "§"); }

    public static String getString(String format, Player player, World world) {
        ChilovePlayer permUser = new ChilovePlayer(player, false);
        return PlaceholderAPI.setPlaceholders(player, format.replace("&", "§")
                .replace("%player_prefix%", permUser.getGroup().getPrefix())
                .replace("%player_nick%", player.getName())
                .replace("%player_suffix%", permUser.getGroup().getSuffix())
                .replace("%world_size%", String.valueOf(world.getPlayers().size()))
                .replace("%world_size-1%", String.valueOf(player.getWorld().getPlayers().size()-1))
                .replace("%world_size+1%", String.valueOf(world.getPlayers().size()+1)));
    }

    public static String getString(String format, Player player, World world, String message) {
        ChilovePlayer permUser = new ChilovePlayer(player, false);
        return format.replace("&", "§")
                .replace("%player_prefix%", permUser.getGroup().getPrefix())
                .replace("%player_nick%", player.getName())
                .replace("%player_suffix%", permUser.getGroup().getSuffix())
                .replace("%world_size%", String.valueOf(world.getPlayers().size()))
                .replace("%world_size-1%", String.valueOf(player.getWorld().getPlayers().size()-1))
                .replace("%world_size+1%", String.valueOf(world.getPlayers().size()+1))
                .replace("%message%", message);
    }

    public static int calculateArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        int defense_points = 0;
        if (helmet != null) {
            switch (helmet.getType()) {
                case LEATHER_HELMET: {
                    defense_points += 1;
                    break;
                }
                case GOLDEN_HELMET, CHAINMAIL_HELMET, IRON_HELMET: {
                    defense_points += 2;
                    break;
                }
                case DIAMOND_HELMET, NETHERITE_HELMET: {
                    defense_points += 3;
                    break;
                }
            }
        }
        if (chestplate != null) {
            switch (chestplate.getType()) {
                case LEATHER_CHESTPLATE: {
                    defense_points += 3;
                    break;
                }
                case GOLDEN_CHESTPLATE, CHAINMAIL_CHESTPLATE: {
                    defense_points += 5;
                    break;
                }
                case IRON_CHESTPLATE: {
                    defense_points += 6;
                    break;
                }
                case DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE: {
                    defense_points += 8;
                    break;
                }
            }
        }
        if (leggings != null) {
            switch (leggings.getType()) {
                case LEATHER_LEGGINGS: {
                    defense_points += 2;
                    break;
                }
                case GOLDEN_LEGGINGS: {
                    defense_points += 3;
                    break;
                }
                case CHAINMAIL_LEGGINGS: {
                    defense_points += 4;
                    break;
                }
                case IRON_LEGGINGS: {
                    defense_points += 5;
                    break;
                }
                case DIAMOND_LEGGINGS, NETHERITE_LEGGINGS: {
                    defense_points += 6;
                    break;
                }
            }
        }
        if (boots != null) {
            switch (boots.getType()) {
                case LEATHER_BOOTS, GOLDEN_BOOTS, CHAINMAIL_BOOTS: {
                    defense_points += 1;
                    break;
                }
                case IRON_BOOTS: {
                    defense_points += 2;
                    break;
                }
                case DIAMOND_BOOTS, NETHERITE_BOOTS: {
                    defense_points += 3;
                    break;
                }
            }
        }

        return defense_points;
    }

    public static float calculateArmorPercent(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        return ((float) calculateArmor(helmet, chestplate, leggings, boots) / 20) * 100;
    }

    public static void enableDebug() { ChiloveMain.debugMode = true; }
    public static void disableDebug() { ChiloveMain.debugMode = false; }
    public static void setFlyEnabled(boolean is_enabled) { ChiloveMain.isFlyEnabled = is_enabled; }
    public static void setDisplayJoinMessage(boolean is_display) { ChiloveMain.joinMessageEnabled = is_display; }
    public static void setDisplayQuitMessage(boolean is_display) { ChiloveMain.quitMessageEnabled = is_display; }
    public static void setDisplayPlayerMessage(boolean is_display) { ChiloveMain.playerMessageEnabled = is_display; }

    public static void updateFullTab() {
        for (World world: Bukkit.getServer().getWorlds()) {
            for (Player player: world.getPlayers()) {
                ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
                if (chilovePlayer != null) chilovePlayer.updateTab();
            }
        }
    }

    /**
     * Задает значение при подключении игрока к серверу. Обычно, используется так как вся экосистема сервера
     * связана и само сообщение необходимо форматировать.
    */
    public static void setJoinMessage(String message) { ChiloveMain.joinFormat = message; }
    /**
     * Задает значение при выхода игрока с сервера. Обычно, используется так как вся экосистема сервера
     * связана и само сообщение необходимо форматировать.
     */
    public static void setQuitMessage(String message) { ChiloveMain.quitFormat = message; }
    /**
     * Задает значение формата сообщения в чат. Если setPlayersWhoCanHear не использовался,
     * тогда сообщение будет отправлено всем игрокам на сервере.
     */
    public static void setChatMessage(String message) { ChiloveMain.messageFormat = message; }
    /**
     * Задает значение отображение ника игрока в табе.
     */
    public static void setTabFormat(String message) { ChiloveMain.tabFormat = message; }
    /**
     * Задает значение отображение ника над головой игрока.
     */
    public static void setPlayerDisplayPrefix(String message) { ChiloveMain.displayPrefixFormat = message; }
    /**
     * Задает игроков, которые могут слышать сообщение. При каждой отправке сообщения список обнуляется.
     */
    public static void setPlayersWhoCanHear(List<Player> players) { ChiloveMain.playerHearMessage = players; }
}
