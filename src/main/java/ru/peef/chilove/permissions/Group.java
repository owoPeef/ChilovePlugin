package ru.peef.chilove.permissions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import ru.peef.chilove.database.Database;

import java.util.ArrayList;
import java.util.List;


public class Group {
    private final int group_id;
    private final int priority;
    private final String name;
    private final String prefix;
    private final String suffix;
    private final int cost;
    private final int is_donate;
    private final int inheritance_id;
    private final List<String> permissions = new ArrayList<>();
    private final ChatColor display_color;

    public Group(int group_id, int priority, String name, String prefix, String suffix, int cost, int is_donate, int inheritance_id, String display_color) {
        this.group_id = group_id;
        this.priority = priority;
        this.name = name;
        this.prefix = prefix.replace("&", "§");
        this.suffix = suffix.replace("&", "§");
        this.cost = cost;
        this.is_donate = is_donate;
        this.inheritance_id = inheritance_id;
        this.display_color = ChatColor.getByChar(display_color);
        setPermissions();
    }

    public void setPermissions() {
        JsonArray permissions_array = Database.getPermissionsOfGroup(this.group_id);
        for (int i = 0; i < permissions_array.size(); i++) {
            JsonObject object = permissions_array.get(i).getAsJsonObject();
            String permission = object.get("permission").getAsString();
            permissions.add(permission);
        }
    }

    public Boolean hasPermission(String permission) {
        for (String perm: permissions) {
            if (perm.equals(permission)) return true;
        }
        return false;
    }
    public List<String> getPermissions() { return this.permissions; }
    public String getName() { return this.name; }
    public Integer getId() { return this.group_id; }
    public Integer getPriority() { return this.priority; }
    public String getPrefix() { return this.prefix; }
    public String getSuffix() { return this.suffix; }
    public Integer getCost() { return this.cost; }
    public Integer getInheritanceId() { return this.inheritance_id; }
    public ChatColor getDisplayColor() { return this.display_color; }
    public Boolean isDonate() { return this.is_donate == 1; }
}
