package com.github.voidSurvival2025.Commands.Default;

import dev.jorel.commandapi.CommandAPICommand;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class WhatIsThisServerCmd {

    public WhatIsThisServerCmd() {
        new CommandAPICommand("whatisthisserver?")
                .executesPlayer((player, args) -> {


                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            """
                                    
                                    <yellow>What is this server?</yellow>
                                    This is essentially <color:#ffa600>skyblock</color> mashed with\s
                                    a <color:#ffa600>border</color> and a platform in the middle.\s
                                    And you are given <color:#ffa600>wood</color>, <color:#ffa600>cobble</color> and some\s
                                    <color:#ffa600>iron nuggets</color> <color:#ffee00>randomly every 10 seconds</color>.\s
                                    
                                    → Find out more with <green>/wiki</green>
                                    We also have a community discord.\s
                                    → <color:#308aff><click:open_url:'https://discord.gg/FJKxwNGdVC'>https://discord.gg/FJKxwNGdVC</click></color>
                                    
                                    """
                    ));
                }).register();
    }
}
