package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final VoidSurvival2025 plugin;
    public PlayerQuit(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

//        plugin.worldResetManager().getMapResetBar().removePlayer(player);

        Entity vehicle = player.getVehicle();

        if (vehicle == null) return;

        vehicle.eject();
    }
}
