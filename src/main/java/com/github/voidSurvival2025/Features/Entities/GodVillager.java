package com.github.voidSurvival2025.Features.Entities;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;


public class GodVillager implements Listener {

    @EventHandler
    public void career(VillagerCareerChangeEvent event) {

        if (event.getProfession().equals(Villager.Profession.ARMORER) ||
        event.getProfession().equals(Villager.Profession.LIBRARIAN)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof org.bukkit.entity.Villager villager)) return;
        if (villager.getProfession().equals(Villager.Profession.ARMORER) ||
                villager.getProfession().equals(Villager.Profession.LIBRARIAN)) {
            event.setCancelled(true);

            villager.setProfession(org.bukkit.entity.Villager.Profession.NONE);
        }
    }

    @EventHandler
    public void spawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof ZombieVillager villager)) return;
        villager.setVillagerProfession(org.bukkit.entity.Villager.Profession.NONE);
    }

    @EventHandler
    public void cure(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof ZombieVillager villager)) return;

        if (!villager.hasPotionEffect(PotionEffectType.WEAKNESS)) return;

        if (villager.isConverting()) return;

        Player player = event.getPlayer();

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem.isEmpty()) return;

        if (heldItem.getType() != Material.ENCHANTED_GOLDEN_APPLE) return;

        player.swingMainHand();
        heldItem.subtract(1);

        Random rand = new Random();

        int oxidizedCopper = rand.nextInt(24 - 16 + 1) + 16;
        int phantomMembrane = rand.nextInt(16 - 8 + 1) + 8;
        int nautilusShell = rand.nextInt(16 - 8 + 1) + 8;
        int emeraldBlock = rand.nextInt(32 - 16 + 1) + 16;

        String nbtString = "{VillagerData:{type:plain,profession:nitwit,level:99},Offers:{Recipes:[" +
                "{maxUses:1,buy:{id:oxidized_copper,count:" + oxidizedCopper +
                "},buyB:{id:heart_of_the_sea,count:1},sell:{id:trident,count:1}}," +
                "{maxUses:8,buy:{id:phantom_membrane,count:" + phantomMembrane +
                "},buyB:{id:nautilus_shell,count:" + nautilusShell +
                "},sell:{id:shulker_shell,count:1}}," +
                "{maxUses:4,buy:{id:emerald_block,count:" + emeraldBlock +
                "},buyB:{id:book,count:1},sell:{id:enchanted_book,count:1}}]}}";

        ReadWriteNBT writeNBT = NBT.parseNBT(nbtString);

        NBT.modify(villager, nbt -> {
            nbt.mergeCompound(writeNBT);
        });

        int min = 20;
        int max = 100;
        int time = rand.nextInt((max - min) + 1) + min;
        villager.setConversionTime(time);
    }
}