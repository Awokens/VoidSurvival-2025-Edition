package com.github.voidSurvival2025.Features.Interact;

import com.github.voidSurvival2025.Manager.Others.TNTTrailManager;
import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

    private final VoidSurvival2025 plugin;
    public EntityExplode(VoidSurvival2025 plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void explode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed tnt)) return;

        int multiplier = 1;

        event.setCancelled(true);

        for (Block connectedBlock : TNTTrailManager.getConnectedBlocks(tnt.getLocation().getBlock())) {
            if (TNTTrailManager.isRelative(connectedBlock)) {
                new TNTTrailManager(plugin, connectedBlock, (75 * multiplier));
                break;
            }
        }
    }
}
