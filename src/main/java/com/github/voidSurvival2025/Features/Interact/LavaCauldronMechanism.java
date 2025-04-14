package com.github.voidSurvival2025.Features.Interact;

import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Campfire;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class LavaCauldronMechanism implements Listener {


    private final VoidSurvival2025 plugin;
    public LavaCauldronMechanism(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {

        Material type = event.getItemDrop().getItemStack().getType();

        if (type != Material.TUFF) return;

        Item item = event.getItemDrop();

        if (event.getItemDrop().getItemStack().getAmount() != 32) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (item.getItemStack().getAmount() != 32) {
                    this.cancel();
                    return;
                }

                if (item.isOnGround()) {
                    this.cancel();
                    return;
                }

                if (item.isInRain()) {
                    this.cancel();
                    return;
                }
                if (item.getTicksLived() > (20L * 60L)) {
                    this.cancel();
                    return;
                }
                Location position = item.getLocation();
                Block blockAt = position.getBlock();


                if (blockAt.getType() != Material.CAULDRON) return;

                Block below = blockAt.getRelative(BlockFace.DOWN);

                if (below.getType() != Material.CAMPFIRE) return;

                org.bukkit.block.data.type.Campfire  campfire = (org.bukkit.block.data.type.Campfire) below.getBlockData();

                if (!campfire.isLit()) return;

                item.remove();

                obsidianAnimate(event.getPlayer(), blockAt);
                this.cancel();

            }
        }.runTaskTimer(plugin, 0L, 5L);

    }

    public void obsidianAnimate(Player player, Block clickdBlock) {
        player.playSound(clickdBlock.getLocation(), Sound.ITEM_BUNDLE_INSERT, 1.0F, 1.0F);
        player.playSound(clickdBlock.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0F, 1.0F);


        final int[] counters = {200, 0};

        new BukkitRunnable() {
            @Override
            public void run() {

                Block block = clickdBlock.getLocation().getBlock();

                if (counters[0] < 1) {
                    //	set clicked block to lava cauldron
                    block.setType(Material.CAULDRON);
                    block.getLocation().getWorld().dropItem(block.getLocation().add(0, 1, 0), new ItemStack(Material.OBSIDIAN, 1));
                    this.cancel();
                    return;
                }

                if (block.getType() != Material.CAULDRON) {
                    this.cancel();
                }

                Block down = block.getRelative(BlockFace.DOWN);

                if (down.getType() != Material.CAMPFIRE) this.cancel();

                org.bukkit.block.data.type.Campfire campfire = (org.bukkit.block.data.type.Campfire) down.getBlockData();

                if (!campfire.isLit()) this.cancel();

                if (this.isCancelled()) return;

                if (counters[1] > 20) {
                    Particle.DustTransition transition = new Particle.DustTransition(
                            Color.ORANGE,
                            Color.RED,
                            5
                    );
                    Location location = block.getLocation().clone().toCenterLocation();
                    location.add(0, 0.2, 0);
                    location.offset(0, 1, 0);

                    location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 5, transition);
                    location.getWorld().playSound(location, Sound.BLOCK_LAVA_AMBIENT, 1.0F, 1.0F);

                    Random random = new Random();

                    // Generating a random number between 0 and 99
                    int randomNumber = random.nextInt(100);

                    if (randomNumber <= 25) {
                        location.getWorld().playSound(location, Sound.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
                    }
                    counters[1] = 0;
                }

                counters[1] += 1;
                counters[0] -= 1;

            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

//    @EventHandler
//    public void interact(PlayerInteractEvent event) {
//
//        Block clickdBlock = event.getClickedBlock();
//
//        if (event.getAction().isLeftClick()) return;
//
//        if (!event.getPlayer().isSneaking()) return;
//
//        if (clickdBlock == null || clickdBlock.getType() != Material.CAULDRON) return;
//
//        Block below = clickdBlock.getRelative(BlockFace.DOWN);
//
//        if (below.getType() != Material.CAMPFIRE) return;
//
//        org.bukkit.block.data.type.Campfire campfire = (org.bukkit.block.data.type.Campfire) below.getBlockData();
//
//        if (!campfire.isLit()) return;
//
//        Player player = event.getPlayer();
//        ItemStack heldItem = player.getInventory().getItemInMainHand();
//
//        if (heldItem.isEmpty()) return;
//
//        if (heldItem.getType() != Material.TUFF) return;
//
//        if (heldItem.getAmount() < 32) return;
//
//        if (player.getCooldown(Material.TUFF) > 0) return;
//
//        player.setCooldown(Material.TUFF, 20 * 181);
//
//        heldItem.subtract(32);
//        player.swingMainHand();
//        player.playSound(clickdBlock.getLocation(), Sound.ITEM_BUNDLE_INSERT, 1.0F, 1.0F);
//        player.playSound(clickdBlock.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0F, 1.0F);
//
//
//        final int[] counters = {200, 0};
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//
//                Block block = clickdBlock.getLocation().getBlock();
//
//                if (counters[0] < 1) {
//                    //	set clicked block to lava cauldron
//                    block.setType(Material.CAULDRON);
//                    block.getLocation().getWorld().dropItem(block.getLocation().add(0, 1, 0), new ItemStack(Material.OBSIDIAN, 1));
//                    this.cancel();
//                    return;
//                }
//
//                if (block.getType() != Material.CAULDRON) {
//                    this.cancel();
//                }
//
//                Block down = block.getRelative(BlockFace.DOWN);
//
//                if (down.getType() != Material.CAMPFIRE) this.cancel();
//
//                org.bukkit.block.data.type.Campfire campfire = (org.bukkit.block.data.type.Campfire) down.getBlockData();
//
//                if (!campfire.isLit()) this.cancel();
//
//                if (this.isCancelled()) return;
//
//                if (counters[1] > 20) {
//                    Particle.DustTransition transition = new Particle.DustTransition(
//                            Color.ORANGE,
//                            Color.RED,
//                            5
//                    );
//                    Location location = block.getLocation().clone().toCenterLocation();
//                    location.add(0, 0.2, 0);
//                    location.offset(0, 1, 0);
//
//                    location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 5, transition);
//                    location.getWorld().playSound(location, Sound.BLOCK_LAVA_AMBIENT, 1.0F, 1.0F);
//
//                    Random random = new Random();
//
//                    // Generating a random number between 0 and 99
//                    int randomNumber = random.nextInt(100);
//
//                    if (randomNumber <= 25) {
//                        location.getWorld().playSound(location, Sound.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
//                    }
//                    counters[1] = 0;
//                }
//
//                counters[1] += 1;
//                counters[0] -= 1;
//
//            }
//        }.runTaskTimer(plugin, 0L, 1L);

}

