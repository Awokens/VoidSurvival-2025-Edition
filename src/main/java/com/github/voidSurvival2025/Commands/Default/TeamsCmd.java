package com.github.voidSurvival2025.Commands.Default;

import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.OfflinePlayerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamsCmd {

    public String TEAMS_PREFIX = "☞ <color:#05a3ff><b>TEAMS</b></color> •";
    public List<String> usage() {
        
        return List.of(
                "",
                TEAMS_PREFIX + " Command Usage",
                " <color:#aaaaaa>-</color> /teams help",
                " <color:#aaaaaa>-</color> /teams leave",
                " <color:#aaaaaa>-</color> /teams disband",
                " <color:#aaaaaa>-</color> /teams join <leader>",
                " <color:#aaaaaa>-</color> /teams create <team name>",
                " <color:#aaaaaa>-</color> /teams kick <player>",
                " <color:#aaaaaa>-</color> /teams invite <player>",
                " <color:#aaaaaa>-</color> /teams transfer <player>",
                " <color:#aaaaaa>-</color> /teams info <red>(WIP)",
                " <color:#aaaaaa>-</color> /teams chest <red>(WIP)",
                " <color:#aaaaaa>-</color> /teams rename <new team name> <red>(WIP)",
                " <color:#aaaaaa>-</color> /teams teamschat <red>(WIP)",
                ""
        );
    }


    public TeamsCmd(VoidSurvival2025 plugin) {

        CommandAPICommand help = new CommandAPICommand("help")
                .executesPlayer((player, args) -> {
                    for (String usage : usage()) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize(
                                usage
                        ));
                    }
                });

        CommandAPICommand teamschat = new CommandAPICommand("togglechat");
        CommandAPICommand info = new CommandAPICommand("info");
        CommandAPICommand leave = new CommandAPICommand("leave")
                .executesPlayer((player, args) -> {
                    plugin.getTeamsConfig().leaveTeam(player);
                });
        CommandAPICommand disband =  new CommandAPICommand("disband")
                .executesPlayer((player, args) -> {
                    plugin.getTeamsConfig().disbandTeam(player);
                });
        CommandAPICommand chest = new CommandAPICommand("chest");

        CommandAPICommand rename = new CommandAPICommand("rename");

        CommandAPICommand create = new CommandAPICommand("create")
                .withUsage("/teams create <name>")
                .withArguments(new StringArgument("name"))
                .executesPlayer((player, args) -> {

                    String name = args.get("name").toString();

                    plugin.getTeamsConfig().createTeam(player, name);

                });
        CommandAPICommand transfer = new CommandAPICommand("transfer")
                .withUsage("/teams transfer <member>")
                .withArguments(new OfflinePlayerArgument("target"))
                .executesPlayer((player, args) -> {

                    Player who = (Player) args.get("target");

                    plugin.getTeamsConfig().transferLeader(player, who);

                });
        CommandAPICommand invite = new CommandAPICommand("invite")
                .withUsage("/teams invite <player>")
                .withArguments(new OfflinePlayerArgument("target"))
                .executesPlayer((player, args) -> {

                    Player target = (Player) args.get("target");

                    plugin.getTeamsConfig().invitePlayer(player, target);

                });
        CommandAPICommand kick = new CommandAPICommand("kick")
                .withUsage("/teams kick <member>")
                .withArguments(new OfflinePlayerArgument("target"))
                .executesPlayer((player, args) -> {

                    OfflinePlayer target = (OfflinePlayer) args.get("target");

                    if (target == null || !target.hasPlayedBefore()) {
                        player.sendMessage(Component.text("This player doesn't exist it seems"));
                        return;
                    }

                plugin.getTeamsConfig().kickMember(player, target);

                });
        CommandAPICommand join = new CommandAPICommand("join")
                .withUsage("/teams join <leader>")
                .withArguments(new PlayerArgument("leader"))
                .executesPlayer((player, args) -> {


                    Player leader = (Player) args.get("leader");
                    plugin.getTeamsConfig().joinTeam(player, leader);

                });
        new CommandAPICommand("teams")
                .withUsage("/teams")
                .withSubcommands(help, teamschat, info, leave, disband, chest, rename, create, transfer, invite, kick, join)
                .register();
    }
}
