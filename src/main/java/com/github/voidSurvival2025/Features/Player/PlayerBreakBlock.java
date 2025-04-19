package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.VoidSurvival2025;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.enginehub.linbus.stream.token.LinToken;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerBreakBlock implements Listener {

    private VoidSurvival2025 plugin;

    public PlayerBreakBlock(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    public void ironNugget(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.STONE)  return;

        if (ThreadLocalRandom.current().nextInt(100) <= 80) {
            return;
        }

        event.setDropItems(false);

        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
        int fortuneLevel = 1 + heldItem.getEnchantmentLevel(Enchantment.FORTUNE);

        int bonus = ThreadLocalRandom.current().nextInt(1, fortuneLevel);

        block.getLocation().getWorld().dropItemNaturally(block.getLocation().toBlockLocation(),
                new ItemStack(Material.IRON_NUGGET, bonus));

    }

    @EventHandler
    public void apple(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (!(block.getBlockData() instanceof Leaves)) return;

        if (ThreadLocalRandom.current().nextInt(100) <= 92) {
            return;
        }

        event.setDropItems(false);
        block.getLocation().getWorld().dropItemNaturally(block.getLocation().toBlockLocation(),
                new ItemStack(Material.APPLE));

    }

    @EventHandler
    public void suspiciousBlock(BlockBreakProgressUpdateEvent event) {

//        if (!event.getEntity().isOp()) return;

        if (!(event.getEntity() instanceof Player player)) return;

        Block block = event.getBlock();

        if (block.getType() != Material.COBBLED_DEEPSLATE) return;

        ItemStack heldItem = player.getInventory().getItemInMainHand();

        /*
        check if poo poo player has a hoe in the first place to start animation
         */

        switch (heldItem.getType()) {
            case Material.STONE_HOE, Material.IRON_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE:
                break;
            default: return;
        }

        if (player.getCooldown(heldItem.getType()) > 0) return;

        player.setCooldown(heldItem.getType(), 20);

        Location location = block.getLocation();

        location.getWorld().playSound(
                location, Sound.BLOCK_GRAVEL_BREAK, 0.5F, 0.5F);
        location.getWorld().playSound(
                location, Sound.ITEM_DYE_USE, 0.5F, 0.5F);
        MetadataValue metadataValue;


        if (!player.isSneaking()) {
            player.setMetadata("progress", new FixedMetadataValue(plugin, 0.0F));
            return;
        }

        if (player.hasMetadata("progress")) {

            metadataValue = player.getMetadata("progress").getFirst();
        } else {
            metadataValue = new FixedMetadataValue(plugin, 0.0F);
        }

        if (metadataValue == null) return;

        float value = metadataValue.asFloat();

        if (value > 0.9F) {
            block.setType(Material.SUSPICIOUS_GRAVEL);
            for (Player other : plugin.getServer().getOnlinePlayers()) {
                other.sendBlockDamage(location, 0.0F, player.getUniqueId().hashCode());
            }
            player.removeMetadata("progress", plugin);
            return;
        } else {
            value += 0.1F;
        }

        for (Player other : plugin.getServer().getOnlinePlayers()) {
            other.sendBlockDamage(location, value, player.getUniqueId().hashCode());
        }

        player.setMetadata("progress", new FixedMetadataValue(plugin, value));
        player.swingMainHand();

    }
}
