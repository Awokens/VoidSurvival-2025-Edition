package com.github.voidSurvival2025.Commands.Admin;
import com.github.voidSurvival2025.Manager.Others.SpawnPointManager;
import com.github.voidSurvival2025.VoidSurvival2025;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.WorldArgument;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldCmd {

    private final VoidSurvival2025 plugin;

    public WorldCmd(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        new CommandAPICommand("world")
                .withPermission(CommandPermission.OP)
                .withArguments(new WorldArgument("target"))
                .executesPlayer((player, args) -> {

                    World world = (World) args.get("target");

                    if (world == null) {
                        return;
                    }

                    Location spawn = switch (world.getName()) {
                        case "world" -> SpawnPointManager.getWorldSpawn();
                        case "world_nether" -> SpawnPointManager.getNetherSpawn();
                        case "world_the_end" -> SpawnPointManager.getEndSpawn();
                        default -> throw new IllegalStateException("Unexpected value: " + world.getName());
                    };

                    player.teleportAsync(spawn);
                }).register();

    }
}
