package com.github.voidSurvival2025.Features.Interact;

import com.github.voidSurvival2025.Manager.Others.SpawnPointManager;
import com.github.voidSurvival2025.VoidSurvival2025;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderEyePlace implements Listener {

    private final VoidSurvival2025 plugin;
    public EnderEyePlace(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void eyePlace(BlockPlaceEvent event) {

        Block block = event.getBlock();

        if (block.getType() != Material.END_PORTAL_FRAME) return;

        EndPortalFrame portalFrame = (EndPortalFrame) block.getBlockData();

        if (!portalFrame.hasEye()) return;

        Player player = event.getPlayer();

        if (player.getCooldown(Material.ENDER_EYE) > 0) {
            event.setCancelled(true);
            return;
        }

        player.setCooldown(Material.ENDER_EYE, 20 * 10);


        player.addPotionEffect(
                new PotionEffect(PotionEffectType.RESISTANCE, 20 * 5, 5, false, false, false)
        );
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1)
        );
        player.addPotionEffect(
                new PotionEffect(PotionEffectType.NAUSEA, 20 * 10, 1, false, false, false)
        );




        Location init = player.getLocation().clone();

        player.playSound(init, Sound.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);

        player.setPortalCooldown(20 * 10);

        Location whereTo = switch (player.getWorld().getName()) {
            case "world_the_end" -> SpawnPointManager.getWorldSpawn();
            default -> SpawnPointManager.getEndSpawn();
        };

        if (whereTo.getWorld().getName().equalsIgnoreCase("world")) {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (onlinePlayer.getName().equalsIgnoreCase(player.getName())) {
                    onlinePlayer.showTitle(Title.title(
                            MiniMessage.miniMessage().deserialize("<gray>You Return..."),
                            MiniMessage.miniMessage().deserialize("<gray>Home!")));
                    continue;
                }

                onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1, true));
                onlinePlayer.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<gray>A player returns..."), MiniMessage.miniMessage().deserialize("<gray>Home...")));
            }
        } else {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (onlinePlayer.getName().equalsIgnoreCase(player.getName())) {
                    onlinePlayer.showTitle(Title.title(
                            MiniMessage.miniMessage().deserialize("<gray>You enter..."),
                            MiniMessage.miniMessage().deserialize("<gray>The End...")));
                    continue;
                }

                onlinePlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1, true));
                onlinePlayer.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<gray>A player enters..."), MiniMessage.miniMessage().deserialize("<gray>The <obfuscated>abyss</obfuscated>...")));
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleportAsync(whereTo).thenRun(() -> {
                    for (Player online : plugin.getServer().getOnlinePlayers()) {
                        online.playSound(online, Sound.BLOCK_END_PORTAL_SPAWN, 1.0F, 0.5F);
                    }
                    block.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, block.getLocation(), 1);
                    block.getLocation().getWorld().createExplosion(block.getLocation(), 3);
                    block.breakNaturally(true);
                    player.playSound(player, Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 0.5F);
                    player.playSound(init, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.5F);
                });
            }
        }.runTaskLater(plugin, 20L * 4L);

    }
}
