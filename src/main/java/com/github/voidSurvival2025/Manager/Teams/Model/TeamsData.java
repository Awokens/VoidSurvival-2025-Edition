package com.github.voidSurvival2025.Manager.Teams.Model;

import java.util.List;

public class TeamsData {

    private String name;
    private String leader;
    private List<String> members;
    private long created;

    // Constructor
    public TeamsData(String name, String leader, List<String> members, long created) {
        this.name = name;
        this.leader = leader;
        this.members = members;
        this.created = created;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLeader() {
        return leader;
    }

    public List<String> getMembers() {
        return members;
    }

    public long getCreated() {
        return created;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    // Add a member
    public void addMember(String member) {
        members.add(member);
    }

    // Remove a member
    public void removeMember(String member) {
        members.remove(member);
    }

    // toString
    @Override
    public String toString() {
        return "Team{name='" + name + "', members=" + members + ", dateCreated=" + created + "}";
    }
}
