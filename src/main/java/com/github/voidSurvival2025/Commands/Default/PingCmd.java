package com.github.voidSurvival2025.Commands.Default;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class PingCmd {

    private VoidSurvival2025 plugin;
    public PingCmd(VoidSurvival2025 plugin) {
        new CommandAPICommand("ping")
                .withOptionalArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {


                    Player target = (Player) args.getOptional("target").orElse(player);

                    int ping = target.getPing();

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            target.getName() + "'s ping is <green>" + ping + " ms"
                    ));

                }).register();
    }
}
