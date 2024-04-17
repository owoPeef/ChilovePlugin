package ru.peef.chilove.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.peef.chilove.ChiloveMain;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.database.Database;
import ru.peef.chilove.permissions.Group;
import ru.peef.chilove.structures.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);

            if (chilovePlayer.getGroup().hasPermission("chilove.commands.admin")) {
                if (args.length == 1) {
                    if (Objects.equals(args[0], "sel") || Objects.equals(args[0], "selection") && player.hasPermission("chilove.selection_tool")) {
                        if (chilovePlayer.selectionToolEnabled) {
                            chilovePlayer.selectionToolEnabled = false;
                            player.sendMessage("§cВыключен режим выделения");
                        } else {
                            chilovePlayer.selectionToolEnabled = true;
                            player.sendMessage("§bВключен режим выделения");
                        }
                    }

                    if (args[0].equals("get_groups")) {
                        if (chilovePlayer.isConfirmed()) {
                            List<Group> groups = Database.getGroups();
                            if (groups != null) {
                                groups.forEach((group) -> {
                                    BaseComponent message = new TextComponent(ChatColor.AQUA + "Группа #" + (group.getPriority() + 1) + ": " + group.getPrefix() + player.getName());
                                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
                                            group.getDisplayColor() + group.getName() + "\n"
                                                    + ChatColor.AQUA + "Донат: " + (group.isDonate() ? ChatColor.GREEN + "да\n" + ChatColor.AQUA + "Стоимость: " + ChatColor.GOLD + group.getCost() + " " + ChiloveMain.money_name: ChatColor.RED + "нет")
                                    ).create()));
                                    player.spigot().sendMessage(message);
                                });
                            } else {
                                player.sendMessage(ChatColor.RED + "Произошла ошибка.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Вы не подтвердили сессию! Для получения повторного сессионого ключа необходимо перезайти.");
                        }
                    }
                }

                // enable debug
                if (args.length == 2) {
                    if (args[0].equals("enable") && args[1].equals("debug")) {
                        Utils.enableDebug();
                    }
                    if (args[0].equals("disable") && args[1].equals("debug")) {
                        Utils.disableDebug();
                    }

                    if (args[0].equals("teleport") || args[0].equals("tp")) {
                        World world = Bukkit.getWorld(args[1]);
                        if (world != null) {
                            player.teleport(world.getSpawnLocation());
                            player.sendMessage(Utils.format("&aВы были телепортированы в мир &b" + world.getName()));
                        } else {
                            player.sendMessage(Utils.format("&cМир не найден!"));
                        }
                    }
                }

                // add_group {name} {prefix} {suffix} {priority}
                if (args.length == 5) {
                    if (args[0].equals("add_group")) {
                        if (chilovePlayer.isConfirmed()) {
                            String group_name = args[1];
                            String group_prefix = args[2].replace("%s%", " ");
                            String group_suffix = args[3].replace("%s%", " ");
                            int group_priority = Integer.parseInt(args[4]);

                            if (Database.addGroup(group_name, group_prefix, group_suffix, group_priority)) {
                                player.sendMessage(ChatColor.GREEN + "Новая группа успешно создана!");
                            } else {
                                player.sendMessage(ChatColor.RED + "Произошла ошибка!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Вы не подтвердили сессию! Для получения повторного сессионого ключа необходимо перезайти.");
                        }
                    }
                }

                // confirm {hash}
                if (args.length == 2) {
                    if (args[0].equals("confirm")) {
                        boolean isUserConfirmedAlready = ChilovePlayerManager.getChilovePlayer(player).isConfirmed();

                        if (isUserConfirmedAlready) {
                            player.sendMessage(ChatColor.AQUA + "Ваша сессия уже подтверждена и не нуждается в повторном подтверждении!");
                        } else {
                            boolean md5 = Database.isHashExists(player, args[1]);
                            if (md5) {
                                ChilovePlayerManager.getChilovePlayer(player).setConfirmed(true);
                                player.sendMessage(ChatColor.GREEN + "Ваша сессия успешно подтверждена! Теперь вы имеете доступ к командам администрирования.");
                            } else {
                                player.sendMessage(ChatColor.RED + "Этот код недействителен!");
                            }
                        }
                    }
                }

                if (args.length == 3) {
                    if (Objects.equals(args[0], "save")) {
                        if (player.hasPermission("chilove.save.selection")) {
                            Build bld = new Build(
                                    ChiloveMain.getInstance(),
                                    chilovePlayer.selection.getFirstPositionX(),
                                    chilovePlayer.selection.getFirstPositionY(),
                                    chilovePlayer.selection.getFirstPositionZ(),
                                    chilovePlayer.selection.getSecondPositionX(),
                                    chilovePlayer.selection.getSecondPositionY(),
                                    chilovePlayer.selection.getSecondPositionZ()
                            );

                            String result = bld.save(args[2]);
                            player.sendMessage(result);
                        }
                    }

                    if (args[0].equals("set_user")) {
                        if (chilovePlayer.isConfirmed()) {
                            ChilovePlayer group_player = ChilovePlayerManager.getChilovePlayerByName(args[1]);
                            if (group_player == null) {
                                player.sendMessage(ChatColor.RED + "Игрок с таким ником не найден!");
                            } else {
                                Group group = Database.getGroupByName(args[2].toLowerCase());
                                if (group == null)
                                    player.sendMessage(ChatColor.RED + "Группа с таким названием не найдена!");
                                else {
                                    String message;
                                    message = group_player.getGroup().getPrefix() + group_player.getPlayer().getName() + ChatColor.GREEN + " изменен на " + group.getPrefix() + group_player.getPlayer().getName();
                                    Database.setUserGroup(group, group_player.getPlayer());
                                    Utils.setPlayerTab(group_player.getPlayer(), Utils.getString(ChiloveMain.tabFormat, group_player.getPlayer(), group_player.getPlayer().getWorld()));
                                    player.sendMessage(message);
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Вы не подтвердили сессию! Для получения повторного сессионого ключа необходимо перезайти.");
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Вы не можете выполнять данную команду!");
            }
        }
        return true;
    }

    public String[] subcommands = {"confirm", "selection", "sel", "save", "teleport", "tp", "enable", "set_user", "get_groups", "get_group", "add_group"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();
        Player player = Bukkit.getPlayer(sender.getName());
        if (player != null) {
            ChilovePlayer user = new ChilovePlayer(player, false);
            if (user.getGroup().hasPermission("chilove.subcommands.*")) {
                if (args.length == 1) {
                    try {
                        for (int i = 0; i < args[0].length(); i++) {
                            for (String subcommand: subcommands) {
                                if (args[0].charAt(i) == subcommand.charAt(i)) list.add(subcommand);
                            }
                        }
                    } catch (Exception ignored) {}
                }

                switch (args[0]) {
                    case "enable", "disable": { list.add("debug"); break; }
                }
                if (args[0].equals("save")) {
                    list.add("<build_name>");
                }
                if (args[0].equals("teleport") || args[0].equals("tp")) {
                    for (World world: Bukkit.getServer().getWorlds()) {
                        list.add(world.getName());
                    }
                }
                if (args[0].equals("add_group")) {
                    switch (args.length) {
                        case 2: list.add("<group_name>"); break;
                        case 3: list.add("<group_prefix>"); break;
                        case 4: list.add("<group_suffix>"); break;
                        case 5: list.add("<group_priority>"); break;
                    }
                }
                if (args[0].equals("set_user")) {
                    switch (args.length) {
                        case 2: Bukkit.getServer().getWorlds().get(0).getPlayers().forEach((pl) -> list.add(pl.getName())); break;
                        case 3: Objects.requireNonNull(Database.getGroups()).forEach((group) -> list.add(group.getName())); break;
                    }
                }
            }
        }
        return list;
    }
}
