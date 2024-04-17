package ru.peef.chilove.hubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HubManager {
    public static List<Hub> hubs = new ArrayList<>();

    public static void addHub(Hub hub) {
        hubs.add(hub);
    }

    public static Hub getMainHub() {
        for (Hub hub: hubs) {
            if (hub.isMainHub) return hub;
        }
        return null;
    }

    public static Hub getRandomHub() {
        for (Hub hub: hubs) {
            if (hub.world.getPlayers().size() <= 100) return hub;
        }
        Random rand = new Random();
        return hubs.get(rand.nextInt(hubs.size()));
    }
}
