package com.github.voidSurvival2025.Commands;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.RegisteredCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;

public class ServerRulesCmd {

    private VoidSurvival2025 plugin;

    public ServerRulesCmd(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        new CommandAPICommand("serverrules")
                .withAliases("rules", "guidelines")
                .executesPlayer((player, args) -> {
                    final Component title = MiniMessage.miniMessage().deserialize(
                            "<newline><red>Rules:<newline>");

                    player.sendMessage(title);

                    HashMap<String, String> rules = new HashMap<>();

                    rules.put("Client Usage", "Do not use modified clients with INTENDED cheats");
                    rules.put("Chatting", "Do not spam or flood the flow of chat");
                    rules.put("Language", "Do not comment INTENSE language or RACIAL profanity");
                    rules.put("Social", "Please do not impersonate staff or others unless obviously joking lol.");
                    rules.put("Heart Voiding", "You CANNOT log to avoid losing all HEARTS!");
                    rules.put("Other", "Do not ask me to TP you lmao.");

                    rules.forEach((rule, desc) -> player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "→ <hover:show_text:'" + desc + "'>" + rule +"</hover>"
                    )));

                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<newline><gray>Hover over text for rule description<newline>"
                    ).decoration(TextDecoration.ITALIC, true));

                }).register();
    }
}
