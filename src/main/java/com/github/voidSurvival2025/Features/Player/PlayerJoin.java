package com.github.voidSurvival2025.Features.Player;

import com.github.voidSurvival2025.Manager.Others.UpdatePlayerListName;
import com.github.voidSurvival2025.VoidSurvival2025;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class PlayerJoin implements Listener {

    private final VoidSurvival2025 plugin;
    public PlayerJoin(VoidSurvival2025 plugin) { this.plugin = plugin; }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getName().equalsIgnoreCase("awokens")) {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                onlinePlayer.playSound(onlinePlayer, Sound.ITEM_GOAT_HORN_SOUND_2, 0.5F, 1F);
            }
            event.joinMessage(MiniMessage.miniMessage().deserialize(
                    "<color:#00bfff>The <white>Awokens</white> has joined</color>"
            ));
        } else if (player.hasPlayedBefore()) {
            event.joinMessage(MiniMessage.miniMessage().deserialize(
                    "<yellow>Welcome back <white>" + player.getName()
            ));
        } else {
            event.joinMessage(MiniMessage.miniMessage().deserialize(
                    "<white>" + player.getName() + " <yellow>has joined for the first time"
            ));
        }

        if (plugin.luckPermsUtils().hasBossBarToggled(player)) {
            plugin.worldResetManager().getMapResetBar().addViewer(player);
        }

        new UpdatePlayerListName(player);

        String welcome_message;
        if (player.hasPlayedBefore()) {
            welcome_message = "<b><color:#35b7de>SUP NERD</color></b>";
        } else {
            welcome_message = "<color:#44e971><b>WELCOME NEWBIE";
        }

        long letterDelay = 20L;

        String[] letters = player.getName().split("");

        StringBuilder subtitle = new StringBuilder();

        for (String letter : letters) {
            subtitle.append(letter);
            Title formatter = Title.title(
                    MiniMessage.miniMessage().deserialize(welcome_message),
                    Component.text(subtitle.toString()),
                    Title.Times.times(
                            Duration.ZERO,
                            Duration.ofSeconds(2),
                            Duration.ZERO
                    )
            );

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.showTitle(formatter);
                    player.playSound(player, Sound.BLOCK_DEEPSLATE_TILES_STEP, 1.0F, 1.0F);
                }
            }.runTaskLater(plugin, letterDelay);
            letterDelay += 2L;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.playSound(player, Sound.BLOCK_STONE_PLACE, 1F, 1F);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);
            }
        }.runTaskLater(plugin, letterDelay);

        letterDelay += 17L;

        new BukkitRunnable() {
            @Override
            public void run() {
                Title officialTitle = Title.title(
                        MiniMessage.miniMessage().deserialize("<bold>VOID SURVIVAL"),
                        Component.text("leap.minehut.gg"),
                        Title.Times.times(
                                Duration.ZERO,
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(1)
                        )
                );
                player.showTitle(officialTitle);
                player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1F, 1F);
            }
        }.runTaskLater(plugin, letterDelay);

        letterDelay += 1L;

        new BukkitRunnable() {
            @Override
            public void run() {
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.2F, 1F);
                player.playSound(player, Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 0.2F , 1F);
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_IMITATE_ENDER_DRAGON, 0.5F, 1F);

                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        """
                                
                                <b>LEAP: <color:#ffc400>VOID SURVIVAL</color></b>
                                
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


            }
        }.runTaskLater(plugin, letterDelay);
    }
}
