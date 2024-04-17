package ru.peef.chilove;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.peef.chilove.commands.AdminCommand;
import ru.peef.chilove.commands.NpcCommand;
import ru.peef.chilove.network.SocketServer;
import ru.peef.chilove.sounds.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ChiloveMain extends JavaPlugin {
    public static boolean debugMode = false;
    public static boolean needConfirmHash = false;

    public static String joinFormat = "%player_prefix%%player_nick%&a подключился.";

    public static boolean isFlyEnabled = true;
    public static boolean quitMessageEnabled = true;
    public static boolean joinMessageEnabled = true;
    public static boolean playerMessageEnabled = true;
    public static String quitFormat = "%player_prefix%%player_nick%&c отключился.";
    public static String messageFormat = "%player_prefix%%player_nick%%player_suffix%: %message%";
    public static String tabFormat = "%player_prefix%%player_nick%";
    public static String displayPrefixFormat = "%player_prefix%";
    public static List<Player> playerHearMessage = new ArrayList<>();

    public static String money_name = "байт";


    // TODO: On init log all received groups, his prefixes and suffixes. And also count of player, who has group[1], group[2], group[n]...
    @Override
    public void onEnable() {
        SoundManager.init();
        Config.loadConfig();
        loadCommands();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ChilovePlaceholder().register();
        }

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    @Override
    public void onDisable() {}

    public void loadCommands() {
        for (Map.Entry<String, Map<String, Object>> cmd : getDescription().getCommands().entrySet()) {
            String command = cmd.getKey();
            try {
                if (command.contains("admin")) Objects.requireNonNull(getCommand(command)).setExecutor(new AdminCommand());
                if (command.contains("cnpc")) Objects.requireNonNull(getCommand(command)).setExecutor(new NpcCommand());
            } catch (Exception exc) {
                this.getLogger().warning("CommandInit:Error(\"" + command + "\") => " + exc.getMessage());
            }
        }
    }

    public static JavaPlugin getInstance() { return getPlugin(ChiloveMain.class); }
}
