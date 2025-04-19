package com.github.voidSurvival2025.Features.Interact;

import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Date;
import java.util.List;

public class HandTradeSwap implements Listener {

    private final VoidSurvival2025 plugin;
    public HandTradeSwap(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void trade(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if ((!(event.getRightClicked() instanceof Player clickedPlayer))) return;

        if (!player.isSneaking()) return;
        if (!clickedPlayer.isSneaking()) return;


        Entity target = clickedPlayer.getTargetEntity(3);

        if (target == null || !target.equals(player)) return;

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (player.getCooldown(heldItem.getType()) > 0) return;

        ItemStack clickedPlayerHeldItem = clickedPlayer.getInventory().getItemInMainHand();

        if (clickedPlayer.getCooldown(clickedPlayerHeldItem.getType()) > 0) return;

        if (heldItem.isEmpty()) return;
        if (clickedPlayerHeldItem.isEmpty()) return;


        MetadataValue value = null;
        if (clickedPlayer.hasMetadata("mutual")) {
            value = clickedPlayer.getMetadata("mutual").getFirst();
        }


        if (value == null || value.equals(player)) {
            FixedMetadataValue mutual = new FixedMetadataValue(plugin, clickedPlayer);
            FixedMetadataValue timestamp = new FixedMetadataValue(plugin, new Date().getTime());
            player.setMetadata("mutual", mutual);
            player.setMetadata("clicked", timestamp);
            return;
        }
        double last = new Date().getTime();
        if (clickedPlayer.hasMetadata("clicked")) {
            last = clickedPlayer.getMetadata("clicked").getFirst().asDouble();
        }

        double diff = (new Date().getTime() - last) / 100;
        if (diff <= 1) {

            ItemStack first = player.getInventory().getItemInMainHand().clone();
            ItemStack second = clickedPlayer.getInventory().getItemInMainHand().clone();

            player.getInventory().setItemInMainHand(second);
            clickedPlayer.getInventory().setItemInMainHand(first);
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.0F);
            clickedPlayer.playSound(clickedPlayer, Sound.ENTITY_ITEM_PICKUP, 0.5F, 1.0F);

            player.setCooldown(first.getType(), 100);
            clickedPlayer.setCooldown(second.getType(), 100);

        }

        for (String key : List.of("mutual", "clicked")) {
            player.removeMetadata(key, plugin);
            clickedPlayer.removeMetadata(key, plugin);
        }
    }
}
