//package com.github.voidSurvival2025.Commands.Admin;
//
//import com.github.voidSurvival2025.Manager.Others.PowerSkullManager;
//import com.github.voidSurvival2025.VoidSurvival2025;
//import dev.jorel.commandapi.CommandAPICommand;
//import dev.jorel.commandapi.arguments.ArgumentSuggestions;
//import dev.jorel.commandapi.arguments.StringArgument;
//import org.bukkit.inventory.ItemStack;
//
//import java.util.List;
//
//public class PowerSkullCmd {
//
//    private VoidSurvival2025 plugin;
//
//    public PowerSkullCmd(VoidSurvival2025 plugin) {
//        this.plugin = plugin;
//
//        List<String> skulls = List.of(
//                "irongolem",
//                "enderman",
//                "warden",
//                "zombie",
//                "skeleton",
//                "piglin"
//        );
//
//        new CommandAPICommand("powerskull")
//                .withArguments(new StringArgument("skull").replaceSuggestions(ArgumentSuggestions.strings(
//                        List.of(
//                                "irongolem",
//                                "enderman",
//                                "warden",
//                                "zombie",
//                                "skeleton",
//                                "piglin"
//                        )
//                )).executesPlayer((player, args) -> {
//
//                    String skull = args.get("skull").toString();
//
//                    switch (skull) {
//                        "test"
//                    }
//
//                })).register();
//    }
//}
