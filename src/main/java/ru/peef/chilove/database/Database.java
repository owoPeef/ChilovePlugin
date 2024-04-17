package ru.peef.chilove.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.peef.chilove.ChiloveMain;
import ru.peef.chilove.ConsoleColor;
import ru.peef.chilove.permissions.Group;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO: Remake fully database system
public class Database {
    static String api_key = "21232f297a57a5a743894a0e4a801fc3";
    static String base_url = "http://188.120.241.107/api/";

    public static boolean isUserExist(Player player, String game) {
        try {
            String full_url = base_url + "v2/users/games/" + game + "/exist.php?api_key=" + api_key + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            return object.get("error") == null;
        } catch (Exception ignored) {}
        return false;
    }

    public static void updateUser(Player player, String game, String key, String value) {
        try {
            String full_url = base_url + "v2/users/games/" + game + "/update.php?api_key=" + api_key + "&key=" + key + "&value=" + value + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on updateUser(" + content + ")");
            }
        } catch (Exception ignored) {}
    }

    public static void addUser(Player player, String game_name, String key, String value) {
        try {
            String full_url = base_url + "v2/users/games/" + game_name + "/add.php?api_key=" + api_key + "&keys=" + key + "&values=" + value + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on addUser(" + content + ")");
            }
        } catch (Exception ignored) {}
    }

    public static JsonObject getUser(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            String full_url = base_url + "v2/users/get.php?api_key=" + api_key + "&uuid=" + uuid;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            return JsonParser.parseString(content.toString()).getAsJsonObject();
        } catch (Exception exception) {
            return JsonParser.parseString("{\"error\":\"" + exception.getMessage() + "\"}").getAsJsonObject();
        }
    }

    public static JsonObject getUserByUUID(UUID uuid) {
        try {
            String full_url = base_url + "users/get.php?api_key=" + api_key + "&uuid=" + uuid;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            return JsonParser.parseString(content.toString()).getAsJsonObject();
        } catch (Exception exception) {
            return JsonParser.parseString("{\"error\":\"" + exception.getMessage() + "\"}").getAsJsonObject();
        }
    }

    public static List<Group> getGroups() {
        try {
            String full_url = base_url + "groups/get_all.php?api_key=" + api_key;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonArray jp_groups = JsonParser.parseString(content.toString()).getAsJsonArray();

            List<Group> groups = new ArrayList<>();
            for (int i = 0; i < jp_groups.size(); i++) {
                JsonObject object = jp_groups.get(i).getAsJsonObject();
                int group_id = object.get("id").getAsInt();
                int priority = object.get("priority").getAsInt();
                String name = object.get("name").getAsString();
                String prefix = object.get("prefix").getAsString();
                String suffix = object.get("suffix").getAsString();
                int is_donate = object.get("is_donate").getAsInt();
                int cost = object.get("cost").getAsInt();
                int inheritance_id = object.get("inheritance_id").getAsInt();
                String display_color = object.get("name_color").getAsString();
                groups.add(new Group(group_id, priority, name, prefix, suffix, cost, is_donate, inheritance_id, display_color));
            }

            return groups;
        } catch (Exception exception) {
            return null;
        }
    }

    public static Group getGroupById(int id) {
        try {
            String full_url = base_url + "groups/get.php?api_key=" + api_key + "&group_id=" + id;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            int group_id = object.get("group_id").getAsInt();
            int priority = object.get("priority").getAsInt();
            String name = object.get("name").getAsString();
            String prefix = object.get("prefix").getAsString();
            String suffix = object.get("suffix").getAsString();
            int cost = object.get("cost").getAsInt();
            int is_donate = object.get("is_donate").getAsInt();
            int inheritance_id = object.get("inheritance_id").getAsInt();
            String display_color = object.get("name_color").getAsString();

            return new Group(group_id, priority, name, prefix, suffix, cost, is_donate, inheritance_id, display_color);
        } catch (Exception exception) {
            return null;
        }
    }

    public static Group getGroupByName(String name) {
        try {
            String full_url = base_url + "groups/get.php?api_key=" + api_key + "&group_name=" + name;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            int group_id = object.get("group_id").getAsInt();
            int priority = object.get("priority").getAsInt();
            String group_name = object.get("name").getAsString();
            String prefix = object.get("prefix").getAsString();
            String suffix = object.get("suffix").getAsString();
            int cost = object.get("cost").getAsInt();
            int is_donate = object.get("is_donate").getAsInt();
            int inheritance_id = object.get("inheritance_id").getAsInt();
            String display_color = object.get("name_color").getAsString();

            return new Group(group_id, priority, group_name, prefix, suffix, cost, is_donate, inheritance_id, display_color);
        } catch (Exception exception) {
            return null;
        }
    }

    public static String getUserGame(Player player, String game_name, String get_value) {
        try {
            String full_url = base_url + "users/get_game.php?api_key=" + api_key + "&game=" + game_name + "&value=" + get_value + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") == null) {
                return object.get(get_value).getAsString();
            } else {
                Bukkit.getLogger().info("Error on getUserGame(" + content + ")");
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static String getGamePlayerTop(Player player, String game_name) {
        try {
            String full_url = base_url + "users/get_game_top.php?api_key=" + api_key + "&game=" + game_name + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") == null) {
                return object.get("number").getAsString();
            } else {
                Bukkit.getLogger().info("Error on getGamePlayerTop(" + content + ")");
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static boolean hasPlayerSkywarsKit(Player player, String kit) {
        try {
            String full_url = base_url + "games/skywars/user_has_kit.php?api_key=" + api_key + "&kit=" + kit + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            Bukkit.getLogger().info(content.toString());

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on hasPlayerSkywarsKit(" + content + ")");
            } else {
                return object.get("has").getAsInt() == 1;
            }
        } catch (Exception ignored) {}
        return false;
    }

    public static void addPlayerSkywarsKit(Player player, String kit) {
        try {
            String full_url = base_url + "games/skywars/add_user_kit.php?api_key=" + api_key + "&kit=" + kit + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            Bukkit.getLogger().info(String.valueOf(content));
            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on addPlayerSkywarsKit(" + content + ")");
            }
        } catch (Exception ignored) {}
    }

    public static void addUserGame(Player player, String game_name) {
        try {
            String full_url = base_url + "users/add_game.php?api_key=" + api_key + "&game=" + game_name + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on addUserGame(" + content + ")");
            }
        } catch (Exception ignored) {}
    }

    public static void updateGameUser(Player player, String game_name, String key, String value) {
        try {
            String full_url = base_url + "users/set_game.php?api_key=" + api_key + "&game=" + game_name + "&key=" + key + "&value=" + value + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on updateGameUser(" + content + ")");
            }
        } catch (Exception ignored) {}
    }

    public static JsonArray getPermissionsOfGroup(int group_id) {
        try {
            String full_url = base_url + "groups/permissions/get_all.php?api_key=" + api_key + "&group_id=" + group_id;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            return JsonParser.parseString(content.toString()).getAsJsonArray();
        } catch (Exception exception) {
            return JsonParser.parseString("[{\"error\":\"" + exception.getMessage() + "\"}]").getAsJsonArray();
        }
    }

    public static void setUserGroup(Group group, Player player) {
        try {
            String full_url = base_url + "users/set_group.php?api_key=" + api_key + "&group_id=" + group.getId() + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[POST] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            Bukkit.getLogger().info(content.toString());
        } catch (Exception ignored) {}
    }

    public static void addUser(Player player) {
        try {
            String full_url = base_url + "users/add.php?api_key=" + api_key + "&table=users&nick=" + player.getName() + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);
        } catch (Exception ignored) {}
    }

    public static boolean addGroup(String name, String prefix, String suffix, int priority) {
        try {
            String full_url = base_url + "groups/add.php?api_key=" + api_key + "&name=" + name + "&prefix=" + prefix + "&suffix=" + suffix + "&priority=" + priority;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            return true;
        } catch (Exception ignored) {}
        return false;
    }

    public static void addHash(int vk_id, String hash, Player player) {
        try {
            UUID uuid = player.getUniqueId();
            String full_url = base_url + "hash/add.php?api_key=" + api_key + "&vk_id=" + vk_id + "&hash=" + hash + "&uuid=" + uuid;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);
        } catch (Exception ignored) {}
    }

    public static boolean isHashExists(Player player, String hash) {
        try {
            String full_url = base_url + "hash/get.php?api_key=" + api_key + "&code=" + hash + "&uuid=" + player.getUniqueId();
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject jo = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (jo.get("error") == null) {
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }

    public static void makeRequest(String path, String additions) {
        try {
            String full_url = base_url + path + "?api_key=" + api_key + additions;
            URL url = new URL(full_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            if (ChiloveMain.debugMode) Bukkit.getLogger().info(ConsoleColor.ANSI_PURPLE + "[GET] " + ConsoleColor.ANSI_BLUE + full_url + " => " + ConsoleColor.ANSI_GREEN + content + ConsoleColor.ANSI_RESET);

            JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();
            if (object.get("error") != null) {
                Bukkit.getLogger().info("Error on addPlayerSkywarsKit(" + content + ")");
            }
        } catch (Exception ignored) {}
    }
}
