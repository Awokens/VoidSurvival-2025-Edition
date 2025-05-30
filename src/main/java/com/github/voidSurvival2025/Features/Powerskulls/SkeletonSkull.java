package com.github.voidSurvival2025.Features.Powerskulls;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SkeletonSkull implements Listener {

    @EventHandler
    public void change(PlayerArmorChangeEvent event) {

        if (event.getSlotType() != PlayerArmorChangeEvent.SlotType.HEAD) return;

        ItemStack newItem = event.getNewItem();
        Player player = event.getPlayer();

        if (newItem.getType() != Material.SKELETON_SKULL) return;


        Location position = player.getLocation();
        World world = position.getWorld();
        world.playSound(
                position, Sound.ENTITY_SKELETON_CONVERTED_TO_STRAY, 1.0F, 1.0F);

    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {

        if (event.isCancelled()) return;

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack helmet = player.getInventory().getHelmet();

        if (helmet == null || helmet.getType() != Material.SKELETON_SKULL) return;

        if (event.getDamager().getType() != EntityType.ARROW) return;

        if (player.getCooldown(Material.SKELETON_SKULL) > 0) return;

        player.setCooldown(Material.SKELETON_SKULL, 20 * 3);
        Location position = player.getLocation();
        World world = position.getWorld();

        world.playSound(position, Sound.ENTITY_BREEZE_DEFLECT, 1.0F, 1.0F);

        event.setCancelled(true);


        event.setDamage(1);
    }
}
