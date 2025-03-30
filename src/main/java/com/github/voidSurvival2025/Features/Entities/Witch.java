package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Witch implements Listener {

    @EventHandler
    public void witch(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Witch)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        Random random = new Random();
        int lootingLevel = player.getInventory()
                .getItemInMainHand()
                .getEnchantmentLevel(Enchantment.LOOTING);
        double chance = 1 + ((9.0 / 3) * lootingLevel);
        double randomValue = random.nextDouble() * 100;

        if (randomValue <= chance) {
            event.getDrops().add(new ItemStack(Material.NETHER_WART, 1));
        }
    }
}
