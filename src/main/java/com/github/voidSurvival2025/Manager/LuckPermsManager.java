package com.github.voidSurvival2025.Manager;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.entity.Player;

public class LuckPermsManager {
    private final LuckPerms API;

    public LuckPermsManager() {
        API = LuckPermsProvider.get();
    }

    public boolean hasToggledItems(Player player) {
        // obtain CachedMetaData - the easiest way is via the PlayerAdapter
        // of course, you can get it via a User too if the player is offline.
        CachedMetaData metaData = API.getPlayerAdapter(Player.class).getMetaData(player);

        // query & parse the meta value
        return metaData.getMetaValue("items", Boolean::parseBoolean).orElse(true);
    }

    public void setToggledItems(Player player, boolean toggleItems) {
        // obtain a User instance (by any means! see above for other ways)
        User user = API.getPlayerAdapter(Player.class).getUser(player);

        // create a new MetaNode holding the level value
        // of course, this can have context/expiry/etc too!
        MetaNode node = MetaNode.builder("items", Boolean.toString(toggleItems)).build();

        // clear any existing meta nodes with the same key - we want to override
        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("items")));
        // add the new node
        user.data().add(node);

        // save!
        API.getUserManager().saveUser(user);
    }

    public boolean hasBossBarToggled(Player player) {
        // Obtain CachedMetaData via the LuckPerms API
        CachedMetaData metaData = API.getPlayerAdapter(Player.class).getMetaData(player);

        // Query & parse the meta value
        return metaData.getMetaValue("bossbar", Boolean::parseBoolean).orElse(true);
    }

    public boolean hasVanishToggled(Player player) {
        CachedMetaData metaData = API.getPlayerAdapter(Player.class).getMetaData(player);
        return metaData.getMetaValue("vanish", Boolean::parseBoolean).orElse(true);
    }

    public void setVanishToggled(Player player, boolean toggleBossBar) {
        // Obtain a User instance via the LuckPerms API
        User user = API.getPlayerAdapter(Player.class).getUser(player);

        MetaNode node = MetaNode.builder("vanish", Boolean.toString(toggleBossBar)).build();

        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("vanish")));
        // Add the new node
        user.data().add(node);

        // Save the changes
        API.getUserManager().saveUser(user);
    }

    public void setBossBarToggled(Player player, boolean toggleBossBar) {
        // Obtain a User instance via the LuckPerms API
        User user = API.getPlayerAdapter(Player.class).getUser(player);

        // Create a new MetaNode holding the toggleBossBar value
        MetaNode node = MetaNode.builder("bossbar", Boolean.toString(toggleBossBar)).build();

        // Clear any existing meta nodes with the same key to override
        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("bossbar")));
        // Add the new node
        user.data().add(node);

        // Save the changes
        API.getUserManager().saveUser(user);
    }

    public String getPlayerRole(Player player) {
        User user = API.getPlayerAdapter(Player.class).getUser(player);

        CachedMetaData data = user.getCachedData().getMetaData();

        String group = data.getPrimaryGroup();
        if (group == null || group.equalsIgnoreCase("default")) {
            return "<color:#b9b9b9>";
        }
        return data.getPrefix() + "<white> ";
    }
}
