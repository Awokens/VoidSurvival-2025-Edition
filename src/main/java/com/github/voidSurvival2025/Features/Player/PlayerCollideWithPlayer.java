package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.VoidSurvival2025;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerCollideWithPlayer implements Listener {

    private final VoidSurvival2025 plugin;

    public PlayerCollideWithPlayer(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.setCollidable(false);
        }
    }
}
