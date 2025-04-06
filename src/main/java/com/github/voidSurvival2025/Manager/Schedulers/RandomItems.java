package com.github.voidSurvival2025.Manager.Schedulers;

import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RandomItems implements Runnable{

    private final VoidSurvival2025 plugin;

    public RandomItems(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Material[] items = {
                Material.DIRT,
                Material.OAK_PLANKS,
                Material.COBBLESTONE,
                Material.IRON_NUGGET
        };

        Random random = new Random();

        int randomIndex = random.nextInt(items.length);

        Material randomElement = items[randomIndex];
        ItemStack item = new ItemStack(randomElement, 1);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (plugin.luckPermsUtils().hasToggledItems(player)) {
                player.getInventory().addItem(item);
            }
        }
    }
}
