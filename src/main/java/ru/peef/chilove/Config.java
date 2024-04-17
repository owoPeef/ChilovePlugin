package ru.peef.chilove;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Config {
    public static Plugin plugin = JavaPlugin.getPlugin(ChiloveMain.class);
    public static String pluginName = plugin.getDescription().getName();

    public static void loadConfig()
    {
        File currentFile = new File(System.getProperty("user.dir") + "\\plugins\\"+pluginName+"\\config.yml");
        if (!currentFile.exists())
        {
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
        }
    }

    public static void reloadConfig() throws IOException, InvalidConfigurationException {
        File config = new File(System.getProperty("user.dir") + "\\plugins\\"+pluginName+"\\config.yml");
        plugin.getConfig().load(config);
    }

    public static String readConfig(String path) {
        try {
            reloadConfig();
        } catch (Exception ignored) {}
        return Objects.requireNonNull(plugin.getConfig().get(path)).toString();
    }
}
