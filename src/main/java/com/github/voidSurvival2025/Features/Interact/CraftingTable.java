package com.github.voidSurvival2025.Features.Interact;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftingTable implements Listener {

    @EventHandler
    public void table(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) return;
        if (!event.getAction().isRightClick()) return;

        if (player.getInventory().getItemInMainHand().getType() != Material.CRAFTING_TABLE) return;

        player.openWorkbench(player.getLocation(), true);
        player.swingMainHand();
        player.playSound(player, Sound.UI_LOOM_TAKE_RESULT, 0.5F, 1.0F);
    }
}
