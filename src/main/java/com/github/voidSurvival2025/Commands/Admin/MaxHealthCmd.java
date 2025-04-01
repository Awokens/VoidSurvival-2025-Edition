package com.github.voidSurvival2025.Commands.Admin;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.List;

public class MaxHealthCmd {

    private final VoidSurvival2025 plugin;
    public MaxHealthCmd(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        new CommandAPICommand("maxhealth")
                .withPermission(CommandPermission.OP)
                .withArguments(new PlayerArgument("target"), new DoubleArgument("health"))
                .executesPlayer((player, args) -> {


                    Player target = (Player) args.get("target");

                    double health;

                    try {
                        health = (double) args.get("health");
                    } catch (NullPointerException e) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>Invalid health scale number</red>"
                        ));
                        return;
                    }

                    if (health <= 0.0 || health > 20.0) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>Invalid health scale number</red>"
                        ));
                        return;
                    }

                    target.setHealthScale(health);
                    target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
                    target.heal(health);

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "Changed " + target.getName() + "'s max health to " + health
                    ));

                }).register();

    }
}
