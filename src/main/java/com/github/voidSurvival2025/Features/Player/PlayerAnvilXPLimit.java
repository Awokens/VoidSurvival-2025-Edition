package com.github.voidSurvival2025.Features.Player;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class PlayerAnvilXPLimit implements Listener {

    /**
     * EASY PEASY ANVIL XP LEVEL BYPASS
     * THANKS PAPERMC
     * @param event
     */

    @EventHandler
    public void anvilUse(PrepareAnvilEvent event) {
        event.getView().setMaximumRepairCost(10000000);

        if (event.getView().getRepairCost() < 39) {
            return;
        }

        for (HumanEntity viewer : event.getViewers()) {
            viewer.sendMessage(MiniMessage.miniMessage().deserialize(
                    " <red>Since you are over the XP limit, VISUALLY you cannot see it. <red>So here's the cost here:<newline>"
                            + "<white> " + event.getView().getRepairCost()));
        }

    }
}
