package ru.peef.chilove.hubs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Hub {
    boolean isMainHub = false;
    World world;
    Location spawnLocation;

    public Hub(World world, Location spawnLocation, Boolean isMainHub) {
        this.world = world;
        this.spawnLocation = spawnLocation;
        this.isMainHub = isMainHub;
    }

    public Hub(World world, Location spawnLocation) {
        this.world = world;
        this.spawnLocation = spawnLocation;
    }

    public void teleportToSpawn(Player player) {
        player.teleport(spawnLocation);
    }

    public void teleportToSpawn(Player player, float facing) {
        Location loc = spawnLocation;
        loc.setYaw(facing);
        player.teleport(loc);
    }

    public boolean isMain() { return isMainHub; }
    public World getWorld() { return world; }
    public Location getSpawnLocation() { return spawnLocation; }
}
