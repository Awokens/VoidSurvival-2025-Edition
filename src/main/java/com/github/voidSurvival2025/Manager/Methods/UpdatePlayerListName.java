package com.github.voidSurvival2025.Manager.Methods;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class UpdatePlayerListName {
    public UpdatePlayerListName(Player player) {
        player.playerListName(MiniMessage.miniMessage().deserialize(
                "<white>" + player.getName() + " <color:#57caff>\uD83C\uDFA3</color> " + player.getStatistic(Statistic.FISH_CAUGHT)
        ));
    }
}
