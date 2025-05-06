package com.github.voidSurvival2025.Commands.Admin;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.PlayerArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class EnderchestSpy {

    private VoidSurvival2025 plugin;

    public EnderchestSpy(VoidSurvival2025 plugin) {
        new CommandAPICommand("enderchestspy")
                .withPermission(CommandPermission.OP)
                .withAliases("ecspy", "viewec", "ec")
                .withArguments(new PlayerArgument("target"))
                .executesPlayer((player, args) -> {

                    Player target = (Player) args.get("target");

                    if (target == null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Invalid player"));
                        return;
                    }
                    player.openInventory(target.getEnderChest());
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<yellow>Viewing " + target.getName() + "'s enderchest"));
                }).register();
    }
}
