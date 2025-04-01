package com.github.voidSurvival2025.Features.Interact;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CorianRoot implements Listener {

    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {

        ItemStack item = event.getItem().asOne();

        if (item.getType() != Material.BEETROOT) return;

        if (!item.getItemMeta().hasCustomName()) return;

        if (!item.getItemMeta().customName().toString().contains("Corian Root")) return;

        Player consumer = event.getPlayer();

        double health = consumer.getAttribute(Attribute.MAX_HEALTH).getValue();

        if (health >= 40.0) {
            return;
        }

        health += 1;
        consumer.setHealthScale(health);
        consumer.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
        consumer.heal(health);


    }
}
