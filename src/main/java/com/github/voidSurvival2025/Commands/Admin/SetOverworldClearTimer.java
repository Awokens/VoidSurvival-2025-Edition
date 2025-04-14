package com.github.voidSurvival2025.Commands.Admin;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.TimeArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class SetOverworldClearTimer {

    private VoidSurvival2025 plugin;

    public SetOverworldClearTimer(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        final int MAX_SECOND = 60 * 60 * 24 * 5;
        final int MIN_SECOND = 60;

        new CommandAPICommand("setoverworldcleartimer")
                .withPermission(CommandPermission.OP)
                .withArguments(new IntegerArgument("target"))
                .executesPlayer((player, args) -> {
                    int target = (int) args.get("target");

                    if (target < MIN_SECOND || target > MAX_SECOND) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>You must enter a value from <white>" + MIN_SECOND + "<red> and <white>" + MAX_SECOND + "<red> SECONDs"
                        ));
                        return;
                    }
                    plugin.configManager()
                            .getVoidConfig()
                            .set("world_reset_timer", target);
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<green>Changed the overworld clear timer to <white> " + target + " seconds"
                    ));
                }).register();
    }
}
