package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EndermanChorusFruit implements Listener {

    @EventHandler
    public void witch(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Enderman)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        int lootingLevel = player.getInventory()
                .getItemInMainHand()
                .getEnchantmentLevel(Enchantment.LOOTING);


        if (lootingLevel < 1) {
            lootingLevel = 1;
        }

        double chance = 10 + (5 * lootingLevel);

        double randomValue = ThreadLocalRandom.current().nextInt(1, 100);

        if (randomValue <= chance) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.CHORUS_FRUIT, 1));
        }
    }
}
