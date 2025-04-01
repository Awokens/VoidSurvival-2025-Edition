package com.github.voidSurvival2025.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class NonchestCmd {

    private final Inventory nonchest;

    public NonchestCmd() {


        this.nonchest = Bukkit.createInventory(
                null,
                9 * 5,
                MiniMessage.miniMessage().deserialize("<dark_gray>Nonchest")
        );

        new CommandAPICommand("nonchest")
                .withFullDescription("Practically a public enderchest")
                .withAliases("putitinthenonchest", "didyouknowin1945thenonchestwasinvented")
                .executesPlayer((player, args) -> {
                    player.openInventory(this.nonchest);
                }).register();
    }
}
