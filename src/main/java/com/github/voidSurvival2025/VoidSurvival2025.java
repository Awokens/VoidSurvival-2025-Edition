package com.github.voidSurvival2025;

import com.github.voidSurvival2025.Commands.*;
import com.github.voidSurvival2025.Commands.Admin.ForceResetWorld;
import com.github.voidSurvival2025.Commands.Admin.InventorySpy;
import com.github.voidSurvival2025.Commands.Admin.MaxHealthCmd;
import com.github.voidSurvival2025.Commands.Admin.SetOverworldClearTimer;
import com.github.voidSurvival2025.Features.Entities.*;
import com.github.voidSurvival2025.Features.Interact.*;
import com.github.voidSurvival2025.Features.Player.*;
import com.github.voidSurvival2025.Features.Powerskulls.PiglinSkull;
import com.github.voidSurvival2025.Features.Powerskulls.SkeletonSkull;
import com.github.voidSurvival2025.Features.Powerskulls.ZombieSkull;
import com.github.voidSurvival2025.Manager.ConfigManager;
import com.github.voidSurvival2025.Manager.LuckPermsManager;
import com.github.voidSurvival2025.Manager.Schedulers.RandomItems;
import com.github.voidSurvival2025.Manager.Schedulers.TipAnnouncements;
import com.github.voidSurvival2025.Manager.WorldResetManager;
import com.samjakob.spigui.SpiGUI;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.logging.Level;

public final class VoidSurvival2025 extends JavaPlugin {
    private LuckPermsManager luckPermsManager;
    private ConfigManager configManager;
    private WorldResetManager mapResetScheduler;
    private SpiGUI spiGUI;
    private BukkitTask tipAnnouncements;

//    private PowerSkullManager powerSkullManager;

    public LuckPermsManager luckPermsUtils() { return luckPermsManager; }
    public WorldResetManager worldResetManager() {
        return mapResetScheduler;
    }
    public ConfigManager configManager() {
        return configManager;
    }
    public SpiGUI spiGUI() { return this.spiGUI; }
    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).shouldHookPaperReload(true));


//        new PowerSkullCmd(this);
        new WhatIsThisServerCmd();
        new SetOverworldClearTimer(this);
        new ServerRulesCmd(this);
        new PingCmd(this);
        new InventorySpy(this);
        new PlayerStatsCmd(this);
        new MaxHealthCmd(this);
        new NonchestCmd();
        new HatCmd();
        new WikiCmd(this);
        new ForceResetWorld(this);
        new ToggleCmd(this);
        new CommandsCmd();
        new RespawnCmd();
        new DiscordCmd();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (!CommandAPI.isLoaded()) CommandAPI.onEnable();

        for (Player player : this.getServer().getOnlinePlayers()) {
            player.playerListName(MiniMessage.miniMessage().deserialize(
                    "<white>" + player.getName() + " <color:#57caff>\uD83C\uDFA3</color> " + player.getStatistic(Statistic.FISH_CAUGHT)
            ));
        }

        spiGUI = new SpiGUI(this);
        luckPermsManager = new LuckPermsManager();
        configManager = new ConfigManager(this, getDataFolder());
        mapResetScheduler = new WorldResetManager(this);
//        this.powerSkullManager = new PowerSkullManager(this);

        registerListeners();

        this.getServer().getScheduler().runTaskTimer(
                this,
                new RandomItems(this),
                20L, 20L * 10
        );


        tipAnnouncements = this.getServer().getScheduler().runTaskTimer(
                this,
                new TipAnnouncements(this),
                20L * 60L, 20L * 60L * 4L);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();

        tipAnnouncements.cancel();

        worldResetManager()
                .getMapResetBar()
                .setVisible(false);
        worldResetManager()
                .getTask()
                .cancel();
    }

    private void registerListeners() {
        List<Listener> listeners = List.of(

                // Entities
                new WanderTrader(),
                new VillagerTrades(),
                new CreeperExplosionRadius(),
                new PiglinConvert(this),
                new SkeletonSummon(),
                new WitchDrops(),
                new Guardian(),

                // Interact
                new EnderPortalDebuff(),
                new LightningRod(),
                new FireballProjectile(this),
                new CorianRoot(),
                new EnderEyePlace(this),
                new CraftingTable(),
                new DirtClaySand(),
                new DirtMoss(),
                new EntityExplode(this),
                new NautilusShell(),
                new SusSandDrops(),
                new LavaCauldronMechanism(this),

                // Player
                new NetherPortalEnter(),
                new PlayerSaddleRidePlayer(this),
                new PlayerFish(),
                new PlayerBreakBlock(),

                new PlayerChat(this),
                new PlayerDeath(),

                new PlayerChat(this),
                new PlayerJoin(this),
                new PlayerQuit(this),
                new WorldGuard(this),

                new PiglinSkull(),
                new SkeletonSkull(),
                new ZombieSkull()

        );

        this.getServer().getConsoleSender().sendMessage("Registered listeners: ");
        for (Listener listener : listeners) {
            try {
                this.getServer().getPluginManager().registerEvents(listener, this);
                this.getServer().getConsoleSender().sendMessage("- " + listener.getClass().getSimpleName());

            } catch (NullPointerException e) {
                getLogger().log(Level.WARNING, "Failed to load listeners");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }
}

































