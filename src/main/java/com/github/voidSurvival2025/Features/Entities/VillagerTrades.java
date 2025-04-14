package com.github.voidSurvival2025.Features.Entities;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;

import java.util.ArrayList;
import java.util.List;


public class VillagerTrades implements Listener {

    @EventHandler
    public void trade(VillagerAcquireTradeEvent event) {

        ItemStack result = event.getRecipe().getResult();

        if (result.getType() != Material.ENCHANTED_BOOK) return;

        event.getRecipe().setMaxUses(6);

        List<ItemStack> ingredients = new ArrayList<>();

        ItemStack emerald = event.getRecipe().getIngredients().getFirst();

        int count = emerald.getAmount();

        ItemStack prismarine = new ItemStack(Material.PRISMARINE_SHARD, count);
        ItemStack nautilus = new ItemStack(Material.NAUTILUS_SHELL, 1);

        ingredients.add(prismarine);
        ingredients.add(nautilus);

        event.getRecipe().setIngredients(ingredients);

    }
    @EventHandler
    public void replenish(VillagerReplenishTradeEvent event) {

        Villager villager = (Villager) event.getEntity();
        if (villager.getProfession() == Villager.Profession.LIBRARIAN) {
            event.setCancelled(true);
        }

    }

    //    @EventHandler
//    public void cure(PlayerInteractEntityEvent event) {
//
//        if (!(event.getRightClicked() instanceof ZombieVillager villager)) return;
//
//        if (!villager.hasPotionEffect(PotionEffectType.WEAKNESS)) return;
//
//        if (villager.isConverting()) return;
//
//        Player player = event.getPlayer();
//
//        ItemStack heldItem = player.getInventory().getItemInMainHand();
//
//        if (heldItem.isEmpty()) return;
//
//        if (heldItem.getType() != Material.ENCHANTED_GOLDEN_APPLE) return;
//
//        player.swingMainHand();
//        heldItem.subtract(1);
//
//        Random rand = new Random();
//
//        int oxidizedCopper = rand.nextInt(24 - 16 + 1) + 16;
//        int phantomMembrane = rand.nextInt(16 - 8 + 1) + 8;
//        int nautilusShell = rand.nextInt(16 - 8 + 1) + 8;
//        int emeraldBlock = rand.nextInt(32 - 16 + 1) + 16;
//
//        String nbtString = "{VillagerData:{type:plain,profession:nitwit,level:99},Offers:{Recipes:[" +
//                "{maxUses:1,buy:{id:oxidized_copper,count:" + oxidizedCopper +
//                "},buyB:{id:heart_of_the_sea,count:1},sell:{id:trident,count:1}}," +
//                "{maxUses:8,buy:{id:phantom_membrane,count:" + phantomMembrane +
//                "},buyB:{id:nautilus_shell,count:" + nautilusShell +
//                "},sell:{id:shulker_shell,count:1}}," +
//                "{maxUses:4,buy:{id:emerald_block,count:" + emeraldBlock +
//                "},buyB:{id:book,count:1},sell:{id:enchanted_book,count:1}}]}}";
//
//        ReadWriteNBT writeNBT = NBT.parseNBT(nbtString);
//
//        NBT.modify(villager, nbt -> {
//            nbt.mergeCompound(writeNBT);
//        });
//
//        int min = 20;
//        int max = 100;
//        int time = rand.nextInt((max - min) + 1) + min;
//        villager.setConversionTime(time);
//    }
}