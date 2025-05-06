package com.github.voidSurvival2025.Manager.Others;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {


    private File configFile;
    private FileConfiguration config;

    private File dataFolder;

    private final Plugin plugin;

    public ConfigManager(Plugin plugin, File dataFolder) {
        this.dataFolder = dataFolder;
        this.plugin = plugin;
        loadConfigFile();
    }

    private void loadConfigFile() {
        this.configFile = new File(this.dataFolder, "void.yml");
        if (!this.configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("void.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to get config");
        }
    }

    public FileConfiguration getVoidConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            getVoidConfig().save(getConfigFile());
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save config");
        }
    }

    public File getConfigFile() {
        return this.configFile;
    }

    public long getWorldResetTimer() {
        if (!getVoidConfig().contains("world_reset_timer")) {
            getVoidConfig().set("world_reset_timer", 60L * 60L * 24L * 2L); // Ensuring int literals
        }


        Object value = getVoidConfig().get("world_reset_timer");
        try {
            return value instanceof Number ? ((Number) value).longValue() : Long.parseLong(value.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 60L * 60L * 24L * 2L;
        }
    }


}
