package com.github.voidSurvival2025.Manager;

import com.fastasyncworldedit.core.FaweAPI;
import com.github.voidSurvival2025.VoidSurvival2025;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionBuilder;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.BlockMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WorldResetManager {
    public final long RESET_TIMER = 60 * 60 * 24 * 5;
    private final int MAX_BORDER_THRESHOLD = 800;
    private final int MIN_BORDER_THRESHOLD = 300;
    private final World world = Bukkit.getWorld("world");
    private final BukkitTask task;
    private final BossBar mapResetBar;
    private final VoidSurvival2025 plugin;
    public WorldResetManager(VoidSurvival2025 plugin) {

        this.plugin = plugin;

        mapResetBar = Bukkit.createBossBar(
                "Loading...",
                BarColor.GREEN,
                BarStyle.SEGMENTED_20,
                BarFlag.PLAY_BOSS_MUSIC);

        mapResetBar.setVisible(true);

        mapResetBar.setProgress(1.0);
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (plugin.luckPermsUtils().hasBossBarToggled(player)) {
                mapResetBar.addPlayer(player);
            }
        }
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                long timeLeft = updateTime();
                mapResetBar.setProgress(percentage(timeLeft));
                mapResetBar.setTitle("Overworld will clear in: " + formatUnix(timeLeft));
            }
        }.runTaskTimer(plugin, 20L, 20L);

    }
    public BossBar getMapResetBar() {
        return this.mapResetBar;
    }
    public BukkitTask getTask() {
        return this.task;
    }
    public long updateTime() {
        long timestamp = plugin.configManager()
                .getWorldResetTimer();

        timestamp -= 1;

        if (timestamp < 1) {
            timestamp = RESET_TIMER; // Set default timer if not set
            plugin.configManager()
                    .getVoidConfig()
                    .set("world_reset_timer", timestamp);
            reset(false);
        } else {
            plugin.configManager()
                    .getVoidConfig()
                    .set("world_reset_timer", timestamp);
        }

        plugin.configManager().saveConfig();

        return timestamp;
    }
    public double percentage(long currentValue) {

        double value = ((double) currentValue / (60L * 60L * 24L * 2L));

        if (value > 1.0D) value = 1.0D;

        if (value < 0.0D) value = 0.0D;

        return value;
    }
    public String formatUnix(long timestamp) {
        // Convert Unix timestamp to milliseconds
        long milliseconds = timestamp * 1000;

        // Convert milliseconds to days, hours, minutes, and seconds
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

        // Construct the formatted string
        StringBuilder formattedTime = new StringBuilder();
        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0 || (days == 0 && hours == 0 && minutes == 0)) {
            formattedTime.append(seconds).append("s");
        }

        return formattedTime.toString().trim();
    }
    public int randomBorderSize() {
        return ThreadLocalRandom.current().nextInt(MIN_BORDER_THRESHOLD, MAX_BORDER_THRESHOLD);
    }
    public void reset(boolean resetTimer) {

        if (world == null) {
            return;
        }

        if (resetTimer) {
            plugin.configManager()
                    .getVoidConfig()
                    .set("world_reset_timer", RESET_TIMER);
        }

        for (Player player : world.getPlayers()) {
            player.teleportAsync(SpawnPointManager.getWorldSpawn()).thenRun(() -> {
                player.setFoodLevel(10);
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.RESISTANCE,
                        20 * 15,
                        3,
                        true,
                        true,
                        true
                ));
                player.stopAllSounds();
                player.playSound(player, Sound.MUSIC_UNDER_WATER, 0.8F, 1.0F);
                player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.7F, 0.5f);
                player.showElderGuardian(true);

            });
        }

        WorldBorder border = world.getWorldBorder();
        Location center = border.getCenter().clone().toCenterLocation();

        double originalSize = border.getSize();
        double originalRadius = (originalSize / 2) + 5;
        double size = randomBorderSize();
        border.setSize((int) (size / 2) + 1);

        if (size > originalSize) {
            originalRadius = size / 2 + 5;
        }

        Location cornerA = center.clone().subtract(originalRadius, 0, originalRadius);
        cornerA.setY(world.getMinHeight());
        Location cornerB = center.clone().add(originalRadius, 0, originalRadius);
        cornerB.setY(world.getMaxHeight());


        EditSessionBuilder builder = WorldEdit.getInstance()
                .newEditSessionBuilder().fastMode(true).world(FaweAPI.getWorld(world.getName()));

        world.setWeatherDuration(20 * 60 * 15);

        try ( EditSession editSession = builder.build()) {
            Region region = new CuboidRegion(BlockVector3.at(
                    cornerA.getX(), cornerA.getY(), cornerA.getZ()),
                    BlockVector3.at(cornerB.getX(), cornerB.getY(), cornerB.getZ())
            );

            assert BlockTypes.BEDROCK != null;
            Mask mask = new BlockMask(editSession, BlockTypes.BEDROCK.getDefaultState().toBaseBlock());

            Pattern pattern = new BaseBlock(BukkitAdapter.adapt(Material.AIR.createBlockData()));

            FaweAPI.getTaskManager().async(() -> {
                editSession.replaceBlocks(
                        region, mask.inverse(), pattern);
                editSession.flushQueue();
                Operation operation = editSession.commit();
                Operations.complete(operation);
            });
        } catch (Exception ignored) { }

    }
}
