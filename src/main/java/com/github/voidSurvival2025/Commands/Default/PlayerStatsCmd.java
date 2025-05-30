package com.github.voidSurvival2025.Commands.Default;

import com.github.voidSurvival2025.VoidSurvival2025;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerStatsCmd {

    private final VoidSurvival2025 plugin;
    public PlayerStatsCmd(VoidSurvival2025 plugin) {
        this.plugin = plugin;

        new CommandAPICommand("playerstat")
                .withAliases("pstat", "playerstats", "stats")
                .withFullDescription("View specific stats of others or yourself")
                .withOptionalArguments(new StringArgument("target").replaceSuggestions(ArgumentSuggestions.strings(
                        Bukkit.getOnlinePlayers().stream().map(Player::getName).toArray(String[]::new))))
                .executesPlayer((executor, args) -> {

                    String input = (String) args.getOptional("target").orElse(executor.getName());

                    if (!input.matches("^[a-zA-Z0-9_]{3,16}$")) {
                        executor.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>Invalid Minecraft username</red>"
                        ));
                        return;
                    }

                    OfflinePlayer target = plugin.getServer().getOfflinePlayer(input);

                    if (!target.hasPlayedBefore()) {
                        executor.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<red>This player doesn't exist</red>"
                        ));
                        return;
                    }

                    SGMenu gui = plugin.spiGUI().create(target.getName() + "'s stats", 5);

                    for (int i = 0; i < 45 ; i++) {
                        gui.setButton(i, new SGButton(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
                    }

                    int[] values = {10, 19, 28};

                    for (int value : values) {
                        for (int i = value; i <= value + 6; i++) {
                            gui.setButton(i, new SGButton(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
                        }
                    }

                    ItemStack fishStat = new ItemStack(Material.FISHING_ROD);
                    ItemMeta fishMeta = fishStat.getItemMeta();
                    fishMeta.customName(MiniMessage.miniMessage().deserialize(
                            "<color:#57caff>\uD83C\uDFA3</color> " + target.getStatistic(Statistic.FISH_CAUGHT)
                    ).decoration(TextDecoration.ITALIC, false));
                    fishStat.setItemMeta(fishMeta);
                    gui.setButton(20, new SGButton(fishStat));


                    ItemStack timeStat = new ItemStack(Material.CLOCK);
                    ItemMeta timeMeta = timeStat.getItemMeta();
                    timeMeta.customName(MiniMessage.miniMessage().deserialize(
                            "<color:#45ff73>⌛</color> " + (target.getStatistic(Statistic.TOTAL_WORLD_TIME) / 20 / 60) + " minutes"
                    ).decoration(TextDecoration.ITALIC, false));
                    timeStat.setItemMeta(timeMeta);
                    gui.setButton(21, new SGButton(timeStat));

                    ItemStack killStat = new ItemStack(Material.NETHERITE_AXE);
                    ItemMeta killMeta = killStat.getItemMeta();
                    killMeta.customName(MiniMessage.miniMessage().deserialize(
                            "<color:#ffcc00>\uD83E\uDE93</color> " + target.getStatistic(Statistic.PLAYER_KILLS) + " player kills"
                    ).decoration(TextDecoration.ITALIC, false));
                    killStat.setItemMeta(killMeta);
                    gui.setButton(23, new SGButton(killStat));

                    ItemStack deathStat = new ItemStack(Material.SKELETON_SKULL);
                    ItemMeta deathMeta = deathStat.getItemMeta();
                    deathMeta.customName(MiniMessage.miniMessage().deserialize(
                            "<color:#ff383f>☠</color> <white>" + target.getStatistic(Statistic.DEATHS) + " deaths</white>"
                    ).decoration(TextDecoration.ITALIC, false));
                    deathStat.setItemMeta(deathMeta);
                    gui.setButton(24, new SGButton(deathStat));

                    gui.setTag("playerstat");

                    executor.openInventory(gui.getInventory());


                }).register();

    }

}
