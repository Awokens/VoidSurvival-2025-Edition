package com.github.voidSurvival2025.Features.Interact;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LightningRod implements Listener {

    @EventHandler
    public void rod(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if (!player.isInRain()) return;

        if (block == null || block.getType() != Material.LIGHTNING_ROD) return;
        Location location = block.getLocation();

        Block highestBlock = location.getWorld().getHighestBlockAt(location);

        if (highestBlock.getY() != block.getY()) return;
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.isEmpty() || heldItem.getType() != Material.COPPER_INGOT) return;
        if (player.getCooldown(Material.COPPER_INGOT) > 0) {
            return;
        }
        heldItem.subtract(1);

        player.setCooldown(Material.COPPER_INGOT, 100);

        block.getWorld().spawnEntity(
                location, EntityType.LIGHTNING_BOLT, CreatureSpawnEvent.SpawnReason.CUSTOM);


        player.playSound(player, Sound.BLOCK_GRINDSTONE_USE, 0.5F, 1.0F);
        player.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.5F);

    }

}
