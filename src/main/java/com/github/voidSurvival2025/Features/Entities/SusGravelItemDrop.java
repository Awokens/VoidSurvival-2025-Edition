package com.github.voidSurvival2025.Features.Entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class SusGravelItemDrop implements Listener {

    @EventHandler
    public void onSusGravelItemDrop(ItemSpawnEvent event) {

        ItemStack item = event.getEntity().getItemStack();

        switch (item.getType()) {
            case SUSPICIOUS_GRAVEL, SUSPICIOUS_SAND:
                break;
            default:
                return;
        }
        Item entityItem = event.getEntity();

        entityItem.setCanPlayerPickup(false);
        entityItem.setCanMobPickup(false);
        entityItem.setPickupDelay(40);
        Location position = entityItem.getLocation();
        World world = position.getWorld();

        Particle.DustOptions options = new Particle.DustOptions(Color.GRAY, 2);
        world.spawnParticle(
                Particle.DUST,
                position,
                1,
                options
        );
        entityItem.remove();
    }
}
