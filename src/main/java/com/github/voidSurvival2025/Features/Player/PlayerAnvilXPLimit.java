package com.github.voidSurvival2025.Features.Player;

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
        event.getView().bypassEnchantmentLevelRestriction(true);
    }
}
