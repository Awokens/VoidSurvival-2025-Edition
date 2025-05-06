package com.github.voidSurvival2025.Commands.Default;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;

public class RespawnCmd {

    public RespawnCmd() {

        new CommandAPICommand("respawn")
                .withAliases("suicide", "commitarson", "itseverydaybro")
                .withFullDescription("Low on hunger? Die where you stand!")
                .executesPlayer((player, args) -> {

                    if (player.getCooldown(Material.BARRIER) > 0) { // cheat cheat for cooldown
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>You must 5 seconds from force respawning"
                        ));
                        return;
                    }

                    player.setCooldown(Material.BARRIER, 20 * 5);
                    player.setHealth(0);
                }).register();

    }
}
