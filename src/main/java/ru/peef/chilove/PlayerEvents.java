package ru.peef.chilove;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;

import java.util.HashMap;
import java.util.UUID;

public class PlayerEvents implements Listener {
    HashMap<UUID, PermissionAttachment> perms = new HashMap<>();
    Plugin plugin = ChiloveMain.getPlugin(ChiloveMain.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        ChilovePlayer chilovePlayer = new ChilovePlayer(player, true);
        ChilovePlayerManager.addChilovePlayer(chilovePlayer);

        for (String permission: chilovePlayer.getGroup().getPermissions()) {
            PermissionAttachment attachment = player.addAttachment(plugin);
            perms.put(player.getUniqueId(), attachment);

            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission(permission, true);
        }

        if (chilovePlayer.getGroup().hasPermission("chilove.fly") && ChiloveMain.isFlyEnabled) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }

        if (ChiloveMain.needConfirmHash) Utils.sendConfirmationCode(player, System.currentTimeMillis());
        else ChilovePlayerManager.getChilovePlayer(player).setConfirmed(true);

        for (String permission: chilovePlayer.getGroup().getPermissions()) {
            PermissionAttachment attachment = player.addAttachment(plugin);
            perms.put(player.getUniqueId(), attachment);

            PermissionAttachment pperms = perms.get(player.getUniqueId());
            pperms.setPermission(permission, true);
        }


        String message = Utils.getString(ChiloveMain.joinFormat, player, world);
        chilovePlayer.updateTab();
        new DisplayName(player).setPlayerPrefix(Utils.getString(ChiloveMain.displayPrefixFormat, player, world), chilovePlayer.getGroup().getDisplayColor());

        event.setJoinMessage("");
        if (ChiloveMain.joinMessageEnabled) {
            for (Player world_player: world.getPlayers()) {
                world_player.sendMessage(message);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
        if (chilovePlayer != null) {
            for (String permission: chilovePlayer.getGroup().getPermissions()) {
                perms.get(player.getUniqueId()).unsetPermission(permission);
            }
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskLater(plugin, () -> {
                ChilovePlayerManager.removeChilovePlayer(chilovePlayer);
            }, 20 * 2); // <- (20 ticks * 2 seconds)
        }

        String message = Utils.getString(ChiloveMain.quitFormat, player, world);

        event.setQuitMessage("");
        if (ChiloveMain.quitMessageEnabled) {
            for (Player world_player: world.getPlayers()) {
                world_player.sendMessage(message);
            }
        }
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChilovePlayer permUser = ChilovePlayerManager.getChilovePlayer(player);
        event.setCancelled(true);

        String message = Utils.getString(ChiloveMain.messageFormat, player, player.getWorld(), event.getMessage());

        if (ChiloveMain.playerMessageEnabled) {
            if (ChiloveMain.playerHearMessage.isEmpty()) {
                for (Player p : player.getWorld().getPlayers()) {
                    p.sendMessage(message);
                }
            } else {
                for (Player p : ChiloveMain.playerHearMessage) {
                    p.sendMessage(message);
                }
            }
        }
        ChiloveMain.playerHearMessage.clear();
    }

    @EventHandler
    public void BlockClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        ChilovePlayer player = ChilovePlayerManager.getChilovePlayer(p);
        Action action = event.getAction();

        if (event.getClickedBlock() != null) {
            int clickX = event.getClickedBlock().getX();
            int clickY = event.getClickedBlock().getY();
            int clickZ = event.getClickedBlock().getZ();

            if (player != null && player.selectionToolEnabled && p.hasPermission("chilove.selection_tool")) {
                if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
                    if (action == Action.LEFT_CLICK_BLOCK
                            && (player.selection.getFirstPositionX() != clickX
                            || player.selection.getFirstPositionY() != clickY
                            || player.selection.getFirstPositionZ() != clickZ
                    )) {
                        player.selection.setPosition(true, "x", clickX);
                        player.selection.setPosition(true, "y", clickY);
                        player.selection.setPosition(true, "z", clickZ);

                        p.sendMessage("§aДля позиции 1 выбран блок " + clickX + " " + clickY + " " + clickZ);
                    }

                    if (action == Action.RIGHT_CLICK_BLOCK
                            && (player.selection.getSecondPositionX() != clickX
                            || player.selection.getSecondPositionY() != clickY
                            || player.selection.getSecondPositionZ() != clickZ
                    )) {
                        player.selection.setPosition(false, "x", clickX);
                        player.selection.setPosition(false, "y", clickY);
                        player.selection.setPosition(false, "z", clickZ);

                        p.sendMessage("§aДля позиции 2 выбран блок " + clickX + " " + event.getClickedBlock().getY() + " " + clickZ);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}
