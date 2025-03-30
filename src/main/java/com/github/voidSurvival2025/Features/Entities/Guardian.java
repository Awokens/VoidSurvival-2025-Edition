package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Guardian implements Listener {

    @EventHandler
    public void elder(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof ElderGuardian)) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        Random random = new Random();
        int lootingLevel = player.getInventory()
                .getItemInMainHand().
                getEnchantmentLevel(Enchantment.LOOTING);
        double chance = 15 + (11.667 * lootingLevel);
        double randomValue = random.nextDouble() * 100;

        if (randomValue <= chance) {
            event.getDrops().add(new ItemStack(Material.HEART_OF_THE_SEA, 1));
        }

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.LUCK);

        if (potionEffect == null || potionEffect.getAmplifier() < 1) return;

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.LUCK,
                potionEffect.getDuration() + 20 * 60 * 4,
                2
        ));

    }

    @EventHandler
    public void guardian(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Guardian)) return;

        if (event.getEntity() instanceof ElderGuardian) return;

        Entity killer = event.getEntity().getKiller();

        if (!(killer instanceof Player player)) return;

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.LUCK);
        if (potionEffect == null || potionEffect.getAmplifier() > 1) return;

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.LUCK,
                potionEffect.getDuration() + 20 * 60 * 4,
                1
        ));

    }

}
