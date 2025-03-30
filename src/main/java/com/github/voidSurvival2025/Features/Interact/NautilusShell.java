package com.github.voidSurvival2025.Features.Interact;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class NautilusShell implements Listener {
    @EventHandler
    public void treasure(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().isRightClick()) return;
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.isEmpty()) return;
        if (heldItem.getType() != Material.NAUTILUS_SHELL) return;
        if (player.getCooldown(Material.NAUTILUS_SHELL) > 0) return;

        heldItem.subtract(1);

        player.setCooldown(Material.NAUTILUS_SHELL, 20);

        LootTable lootTable = Bukkit.getLootTable(LootTables.SHIPWRECK_SUPPLY.getKey());

        LootContext lootContext = new LootContext.Builder(player.getLocation())
                .lootedEntity(null)
                .build();

        if (lootTable == null) return;

        player.playSound(player, Sound.ITEM_BRUSH_BRUSHING_SAND, 0.5F, 1.0F);

        player.swingMainHand();

        Collection<ItemStack> lootItems = lootTable.populateLoot(ThreadLocalRandom.current(), lootContext);

        HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(lootItems.toArray(new ItemStack[0]));

        if (leftOver.isEmpty()) return;

        for (ItemStack leftOverItem : leftOver.values()) {
            player.getWorld().dropItem(player.getLocation(), leftOverItem);
        }
    }
}
