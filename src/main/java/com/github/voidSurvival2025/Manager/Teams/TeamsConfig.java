package com.github.voidSurvival2025.Manager.Teams;

import com.github.voidSurvival2025.Manager.Teams.Enum.TeamMessage;
import com.github.voidSurvival2025.Manager.Teams.Model.TeamsData;
import com.github.voidSurvival2025.VoidSurvival2025;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class TeamsConfig {

    private final VoidSurvival2025 plugin;
    private final YamlConfiguration config;
    private final File file;
    private final Map<UUID, String> invites = new HashMap<>();
    private final Map<UUID, UUID> invitations = new HashMap<>();

    private final String TEAM_NAME_PATTERN = "[a-zA-Z0-9]{2,10}$";

    public TeamsConfig(VoidSurvival2025 plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "teams.yml");
        config = YamlConfiguration.loadConfiguration(file);

        try {
            config.save(file);
            plugin.getLogger().log(Level.INFO, "Able to load teams.yml, enabling plugin.");
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Unable to load teams.yml. Disabling plugin.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public String getTeamsId(OfflinePlayer player) {
        ConfigurationSection players = getOrCreateSection("players");
        return players.getString(player.getUniqueId().toString());
    }

    public void setTeamsId(Player player, String uuid) {
        ConfigurationSection players = getOrCreateSection("players");
        players.set(player.getUniqueId().toString(), uuid);
    }

    public void removeTeamsId(OfflinePlayer player) {
        ConfigurationSection players = getOrCreateSection("players");
        players.set(player.getUniqueId().toString(), null);
    }

    public boolean hasTeam(OfflinePlayer player) {
        String teamsId = getTeamsId(player);
        if (teamsId == null) return false;

        TeamsData team = getTeamById(teamsId);
        if (team == null) return false;

        if (!team.getMembers().contains(player.getUniqueId().toString())) {
            removeTeamsId(player);
            return false;
        }
        return true;
    }

    public String getTeamsName(Player player) {
        if (!hasTeam(player)) return "";

        TeamsData data = getTeamById(getTeamsId(player));
        String name = data.getName();

        return name.matches(TEAM_NAME_PATTERN) ? "<yellow>(" + name + "<yellow>) " : "";
    }

    public TeamsData getTeamById(String teamId) {
        ConfigurationSection teams = getOrCreateSection("teams");
        ConfigurationSection team = teams.getConfigurationSection(teamId);

        if (team == null) return null;

        return new TeamsData(
                team.getString("name"),
                team.getString("leader"),
                team.getStringList("members"),
                team.getLong("created")
        );
    }

    public void transferLeader(Player you, OfflinePlayer other) {
        String teamsId = getTeamsId(you);
        TeamsData data = getTeamById(teamsId);

        if (data == null) {
            you.sendMessage(TeamMessage.NO_TEAM_TO_TRANSFER.toComponent());
            return;
        }

        if (!data.getLeader().equalsIgnoreCase(you.getUniqueId().toString())) {
            you.sendMessage(TeamMessage.NOT_LEADER.toComponent());
            return;
        }

        if (you.getName().equalsIgnoreCase(other.getName())) {
            you.sendMessage(TeamMessage.CANNOT_TRANSFER_TO_SELF.toComponent());
            return;
        }

        if (!data.getMembers().contains(other.getUniqueId().toString())) {
            you.sendMessage(TeamMessage.PLAYER_NOT_MEMBER.toComponent());
            return;
        }

        data.setLeader(other.getUniqueId().toString());
        updateTeamsData(teamsId, data);

        for (String member : data.getMembers()) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.getUniqueId().toString().equalsIgnoreCase(member)) {
                    player.sendMessage(TeamMessage.LEADERSHIP_TRANSFERRED.toComponent(you.getName(), other.getName()));
                }
            }
        }
    }

    public void updateTeamsData(String teamId, TeamsData data) {
        ConfigurationSection teams = getOrCreateSection("teams");
        ConfigurationSection team = teams.getConfigurationSection(teamId);

        if (team == null) return;

        team.set("name", data.getName());
        team.set("leader", data.getLeader());
        team.set("members", data.getMembers());
        team.set("created", data.getCreated());

        save();
    }

    public void createTeam(Player player, String name) {
        if (!name.matches(TEAM_NAME_PATTERN)) {
            player.sendMessage(TeamMessage.INVALID_TEAM_NAME.toComponent());
            return;
        }

        if (hasTeam(player)) {
            player.sendMessage(TeamMessage.ALREADY_HAVE_TEAM.toComponent());
            return;
        }

        ConfigurationSection teams = getOrCreateSection("teams");
        for (String key : teams.getKeys(false)) {
            if (name.equalsIgnoreCase(getTeamById(key).getName())) {
                player.sendMessage(TeamMessage.NAME_ALREADY_TAKEN.toComponent());
                return;
            }
        }

        UUID teamId = UUID.randomUUID();
        teams.createSection(teamId.toString());
        setTeamsId(player, teamId.toString());

        updateTeamsData(teamId.toString(), new TeamsData(
                name,
                player.getUniqueId().toString(),
                List.of(player.getUniqueId().toString()),
                new Date().getTime()
        ));

        player.sendMessage(TeamMessage.TEAM_CREATED.toComponent());
    }

    public void disbandTeam(Player player) {
        if (!hasTeam(player)) {
            player.sendMessage(TeamMessage.NO_TEAM_TO_DISBAND.toComponent());
            return;
        }

        String teamsId = getTeamsId(player);
        TeamsData data = getTeamById(teamsId);

        if (!data.getLeader().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(TeamMessage.MUST_BE_LEADER_TO_DISBAND.toComponent());
            return;
        }

        ConfigurationSection teams = getOrCreateSection("teams");
        removeTeamsId(player);
        teams.set(teamsId, null);
        save();

        player.sendMessage(TeamMessage.TEAM_DISBANDED.toComponent());
    }

    public void invitePlayer(Player player, OfflinePlayer who) {
        if (!hasTeam(player)) {
            player.sendMessage(TeamMessage.NO_TEAM_TO_INVITE.toComponent());
            return;
        }

        String teamId = getTeamsId(player);
        TeamsData data = getTeamById(teamId);

        if (!data.getLeader().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(TeamMessage.NOT_LEADER_INVITE.toComponent());
            return;
        }

        if (data.getMembers().contains(who.getUniqueId().toString())) {
            player.sendMessage(TeamMessage.PLAYER_ALREADY_MEMBER.toComponent());
            return;
        }

        if (hasTeam(who)) {
            player.sendMessage(TeamMessage.PLAYER_ALREADY_IN_TEAM.toComponent());
            return;
        }

        invitations.put(player.getUniqueId(), who.getUniqueId());

        if (!who.isOnline() || !who.isConnected()) return;

        Player online = who.getPlayer();
        if (online == null) return;

        player.sendMessage(TeamMessage.INVITATION_SENT.toComponent());
        online.sendMessage(TeamMessage.INVITED_PLAYER.toComponent(player.getName()));
        online.sendMessage(TeamMessage.INVITE_COMMAND.toComponent());
    }

    public void joinTeam(Player player, Player who) {
        if (hasTeam(player)) {
            player.sendMessage(TeamMessage.ALREADY_IN_TEAM.toComponent());
            return;
        }

        if (!hasTeam(who)) {
            player.sendMessage(TeamMessage.LEADER_NO_LONGER_HAS_TEAM.toComponent());
            return;
        }

        UUID uuid = invitations.get(who.getUniqueId());

        if (uuid == null) {
            player.sendMessage(TeamMessage.NOT_INVITED.toComponent());
            return;
        }

        if (uuid.compareTo(player.getUniqueId()) > 0) {
            player.sendMessage(TeamMessage.INVITATION_EXPIRED.toComponent());
            return;
        }

        String teamsId = getTeamsId(who);
        TeamsData data = getTeamById(teamsId);

        if (!data.getLeader().equalsIgnoreCase(who.getUniqueId().toString())) {
            invitations.remove(who.getUniqueId());
            player.sendMessage(TeamMessage.INVITATION_EXPIRED.toComponent());
            return;
        }

        invitations.remove(who.getUniqueId());
        setTeamsId(player, teamsId);

        if (!data.getMembers().contains(player.getUniqueId().toString())) {
            data.addMember(player.getUniqueId().toString());
        }

        updateTeamsData(teamsId, data);

        for (String member : data.getMembers()) {
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (online.getUniqueId().toString().equalsIgnoreCase(member)) {
                    online.sendMessage(TeamMessage.JOIN_TEAM_SUCCESS.toComponent(player.getName()));
                }
            }
        }
    }

    public void leaveTeam(Player player) {
        if (!hasTeam(player)) {
            player.sendMessage(TeamMessage.NO_TEAM_TO_LEAVE.toComponent());
            return;
        }

        String teamsId = getTeamsId(player);
        TeamsData data = getTeamById(teamsId);

        if (data.getLeader().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(TeamMessage.LEADER_CANNOT_LEAVE.toComponent());
            return;
        }

        removeTeamsId(player);
        data.removeMember(player.getUniqueId().toString());
        player.sendMessage(TeamMessage.LEFT_TEAM.toComponent());
        updateTeamsData(teamsId, data);

        for (String member : data.getMembers()) {
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (online.getUniqueId().toString().equalsIgnoreCase(member)) {
                    online.sendMessage(TeamMessage.PLAYER_LEFT_TEAM.toComponent(player.getName()));
                }
            }
        }
    }

    public void kickMember(Player player, OfflinePlayer who) {
        if (!hasTeam(player)) {
            player.sendMessage(TeamMessage.NO_TEAM_TO_KICK.toComponent());
            return;
        }

        if (who.getName() == null) {
            player.sendMessage(TeamMessage.PLAYER_NOT_IN_TEAM.toComponent());
            return;
        }

        if (who.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage(TeamMessage.CANNOT_KICK_SELF.toComponent());
            return;
        }

        String teamsId = getTeamsId(player);
        TeamsData data = getTeamById(teamsId);

        if (!data.getMembers().contains(who.getUniqueId().toString())) {
            player.sendMessage(TeamMessage.PLAYER_NOT_IN_TEAM.toComponent());
            return;
        }

        removeTeamsId(who);
        data.removeMember(who.getUniqueId().toString());

        if (who.getPlayer() != null && who.isOnline()) {
            who.getPlayer().sendMessage(TeamMessage.KICKED_FROM_TEAM.toComponent());
        }

        updateTeamsData(teamsId, data);

        for (String member : data.getMembers()) {
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (online.getUniqueId().toString().equalsIgnoreCase(member)) {
                    online.sendMessage(TeamMessage.PLAYER_KICKED.toComponent(who.getName()));
                }
            }
        }
    }

    private ConfigurationSection getOrCreateSection(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) section = config.createSection(path);
        return section;
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<UUID, String> getInvites() {
        return invites;
    }
}
