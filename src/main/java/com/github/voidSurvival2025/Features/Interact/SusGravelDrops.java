package com.github.voidSurvival2025.Features.Interact;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrushableBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SusGravelDrops implements Listener {

    @EventHandler
    public void brush(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();


        if (block == null) return;

        if (block.getType() != Material.SUSPICIOUS_GRAVEL) return;

        if (!(block.getState() instanceof BrushableBlock brushableBlock)) return;

        ItemStack item = brushableBlock.getItem();

        if (item.getType() != Material.AIR) return;

        LootTable lootTable = Bukkit.getLootTable(LootTables.ANCIENT_CITY.getKey());

        LootContext lootContext = new LootContext.Builder(block.getLocation())
                .lootedEntity(null)
                .build();

        if (lootTable == null) return;

        Random random = new Random();
        ItemStack randomItem;


        if (random.nextInt(100) <= 10) {
            int randomIndex = random.nextInt(common.length);
            randomItem = new ItemStack(common[randomIndex]);
        } else {
            Collection<ItemStack> lootItems = lootTable.populateLoot(new Random(), lootContext);
            lootItems.removeIf(itemStack -> switch (itemStack.getType()) {
                case ENCHANTED_GOLDEN_APPLE, GOLDEN_APPLE -> true;
                default -> false;
            });

            if (!lootItems.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(lootItems.size());
                randomItem = new ArrayList<>(lootItems).get(randomIndex);
            } else {
                // Handle the case where lootItems is empty, maybe set a default item or do something else
                randomItem = new ItemStack(Material.AIR); // Example: Setting AIR as default
            }
        }

        brushableBlock.setItem(randomItem);
        brushableBlock.update();
    }

    private final Material[] common = {
            Material.BOW,
            Material.NAUTILUS_SHELL,
            Material.FISHING_ROD,
            Material.BUNDLE,
            Material.CARROT,
            Material.CARROT,
            Material.POTATO,
            Material.POTATO,
            Material.DEAD_BUSH,
            Material.DEAD_BUSH,
            Material.GOLD_INGOT,
            Material.COAL,
            Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
            Material.NETHERITE_SCRAP,
            Material.GOLD_INGOT,
            Material.QUARTZ,
            Material.CHORUS_PLANT,
            Material.LAPIS_LAZULI,
            Material.LAPIS_LAZULI,
            Material.LAPIS_LAZULI,
    };
}
