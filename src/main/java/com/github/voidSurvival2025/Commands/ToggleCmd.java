package com.github.voidSurvival2025.Commands;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ToggleCmd {

    public ToggleCmd(VoidSurvival2025 plugin) {

        CommandAPICommand items = new CommandAPICommand("items")
                .executesPlayer((player, args) -> {

                    boolean toggled = plugin.luckPermsUtils().hasToggledItems(player);
                    toggled = !toggled;

                    plugin.luckPermsUtils().setToggledItems(player, toggled);

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "Toggled items to <gray>" + toggled
                    ));
                });

        CommandAPICommand bossbar = new CommandAPICommand("bossbar")
                .withFullDescription("Disable the World Map reset bossbar")
                .executesPlayer((player, args) -> {

                    boolean toggled = plugin.luckPermsUtils().hasBossBarToggled(player);

                    toggled = !toggled;

                    if (!toggled) {
                        plugin.worldResetManager().getMapResetBar().removePlayer(player);
                    } else {
                        plugin.worldResetManager().getMapResetBar().addPlayer(player);
                    }

                    plugin.luckPermsUtils().setBossBarToggled(player, toggled);

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "Toggled bossbar to <gray>" + toggled
                    ));
                });

        new CommandAPICommand("toggle")
                .withFullDescription("Toggle on or off certain features")
                .withUsage("/toggle <items, bossbar, stats>")
                .withSubcommands(items, bossbar)
                .register();

    }
}
