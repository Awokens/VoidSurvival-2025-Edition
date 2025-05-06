package com.github.voidSurvival2025.Manager.Others;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SpawnPointManager {
    public static Location getWorldSpawn() {
        return new Location(Bukkit.getWorld("world"),0.5, -63, 0.5);
    }

    public static Location getNetherSpawn() {
        return new Location(Bukkit.getWorld("world_nether"),0.5, 1, 0.5);
    }

    public static Location getEndSpawn() {
        return new Location(Bukkit.getWorld("world_the_end"),0.5, 1, 0.5);
    }
}
