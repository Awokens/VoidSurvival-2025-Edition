package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.Manager.Others.SpawnPointManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class NetherPortalEnter implements Listener {

    @EventHandler
    public void portal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) return;

        event.setCancelled(true);
        Player player = event.getPlayer();

        Location teleportTo = switch (player.getWorld().getName()) {
            case "world_nether" -> SpawnPointManager.getWorldSpawn();
            case "world" -> SpawnPointManager.getNetherSpawn();
            default -> throw new IllegalStateException("Unexpected value: " + player.getWorld().getName());
        };
        player.teleportAsync(teleportTo);
    }

    @EventHandler
    public void portalCreate(PortalCreateEvent event) {

        if (event.getReason() == PortalCreateEvent.CreateReason.FIRE) return; // nether

        event.setCancelled(true);
    }
}
