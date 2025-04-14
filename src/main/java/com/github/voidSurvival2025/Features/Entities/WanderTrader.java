package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class WanderTrader implements Listener {

    @EventHandler
    public void traderDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.WanderingTrader)) return;
        event.getDrops().add(new ItemStack(Material.EMERALD, 1));
    }
}
