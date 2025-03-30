package com.github.voidSurvival2025.Features.Player;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerSaddleRidePlayer implements Listener {

    private final VoidSurvival2025 plugin;
    public PlayerSaddleRidePlayer(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void interact(PlayerInteractAtEntityEvent event) { // mount player on clicked player

        Player player = event.getPlayer();

        if (!player.isSneaking()) return;

        Entity entity = event.getRightClicked();

        if (!(entity instanceof Player who)) return;

        ItemStack helmet = who.getInventory().getHelmet();

        if (helmet == null || helmet.getType() != Material.SADDLE) return;

        if (player.getCooldown(Material.SADDLE) > 0) return;

        player.setCooldown(Material.SADDLE, 20); // 1 second cooldown

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setSneaking(false);
                who.addPassenger(player);
                player.playSound(
                        player.getLocation(),
                        Sound.ITEM_ARMOR_EQUIP_GENERIC,
                        1.0F,
                        1.0F
                );
            }
        }.runTaskLater(plugin, 20L);
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent event) { // eject passengers riding player upon sneaking

        Player player = event.getPlayer();

        if (player.getCooldown(Material.SADDLE) > 0) return;

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }

    @EventHandler
    public void unequip(PlayerArmorChangeEvent event) { // eject passengers riding player upon unequipped saddle

        if (event.getSlot() != EquipmentSlot.HEAD) return;

        Player player = event.getPlayer();

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }

    @EventHandler
    public void swim(EntityToggleSwimEvent event) { // eject passengers riding player upon da wata

        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack helmet = player.getInventory().getHelmet();
        if (helmet == null || helmet.getType() != Material.SADDLE) return;

        if (!event.isSwimming() || !player.isInWater()) return;

        for (Entity passenger : player.getPassengers()) {
            player.removePassenger(passenger);
        }
    }
}
