//package com.github.voidSurvival2025.Commands.Admin;
//
//import com.github.voidSurvival2025.VoidSurvival2025;
//import dev.jorel.commandapi.CommandAPICommand;
//import dev.jorel.commandapi.CommandPermission;
//import org.bukkit.entity.Player;
//
//public class VanishMode {
//
//    private VoidSurvival2025 plugin;
//
//    public VanishMode(VoidSurvival2025 plugin) {
//        new CommandAPICommand("vanishmode")
//                .withPermission(CommandPermission.OP)
//                .withAliases("vanish", "hide")
//                .executesPlayer((player, args) -> {
//
//                    if (plugin.luckPermsUtils().hasVanishToggled(player)) {
//                        plugin.luckPermsUtils().setVanishToggled(player, false);
//                        player.
//                        return;
//                    }
//
//                    plugin.luckPermsUtils().setVanishToggled(player, true);
//                    for (Player other : plugin.getServer().getOnlinePlayers()) {
//                        player.hidePlayer(plugin, other);
//                    }
//
//                }).register();
//    }
//}
