package ru.peef.chilove.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.npcs.NPC;

public class NpcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);

            if (chilovePlayer.getGroup().hasPermission("chilove.commands.cnpc")) {
                if (args.length == 2) {
                    if (args[0].equals("create_npc")) {
                        NPC npc = new NPC(player, args[1]);
                    }
                }
            }
        }
        return true;
    }
}
