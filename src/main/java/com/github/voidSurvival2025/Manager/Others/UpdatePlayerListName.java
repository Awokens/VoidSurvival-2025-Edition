package com.github.voidSurvival2025.Manager.Others;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class UpdatePlayerListName {
    public UpdatePlayerListName(Player player) {

        final Component header = MiniMessage.miniMessage().deserialize(
                "<newline><white><b>VOID SURVIVAL</b></white><newline>"
        );
        final Component footer = MiniMessage.miniMessage().deserialize(
                "<newline><yellow>ʟᴇᴀᴘ.ᴍɪɴᴇʜᴜᴛ.ɢɢ</yellow><newline>"
        );

        player.sendPlayerListHeader(header);
        player.sendPlayerListFooter(footer);

        player.playerListName(MiniMessage.miniMessage().deserialize(
                "<white>" + player.getName()
        ));
    }
}
