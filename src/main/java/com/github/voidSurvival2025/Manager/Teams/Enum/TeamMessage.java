package com.github.voidSurvival2025.Manager.Teams.Enum;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public enum TeamMessage {
    NOT_INVITED("%prefix%You are not invited."),
    LEADER_NOT_ONLINE("%prefix%Invalid leader."),
    NO_TEAM_TO_TRANSFER("%prefix%You have no team to transfer leadership."),
    NOT_LEADER("%prefix%You are not the leader of your team."),
    CANNOT_TRANSFER_TO_SELF("%prefix%You cannot transfer to yourself."),
    PLAYER_NOT_MEMBER("%prefix%The new leader must be a member of the team."),
    LEADERSHIP_TRANSFERRED("%prefix%Leadership transferred from <white>%s %prefix%to <white>%s%prefix%."),
    INVALID_TEAM_NAME("%prefix%This name is invalid.<newline>- Must be from <red>2 to 10</red> characters<newline>- Must contain <red>no special</red> characters"),
    ALREADY_HAVE_TEAM("%prefix%You already have a team."),
    NAME_ALREADY_TAKEN("%prefix%This name is already taken."),
    TEAM_CREATED("%prefix%Created a new team!"),
    NO_TEAM_TO_DISBAND("%prefix%You have no team to disband."),
    MUST_BE_LEADER_TO_DISBAND("%prefix%You must be the leader to disband your team."),
    TEAM_DISBANDED("%prefix%You have disbanded your team."),
    NO_TEAM_TO_INVITE("%prefix%You don't have a team to invite players to."),
    NOT_LEADER_INVITE("%prefix%You are not the leader."),
    PLAYER_ALREADY_MEMBER("%prefix%This player is already a member."),
    PLAYER_ALREADY_IN_TEAM("%prefix%This player is already in a team."),
    INVITATION_SENT("%prefix%Invitation sent!"),
    INVITED_PLAYER("%prefix%Leader <white>%s has sent you an invitation."),
    INVITE_COMMAND("%prefix%Use <white>/teams join <leader> to join their team."),
    ALREADY_IN_TEAM("%prefix%You are already in a team."),
    LEADER_NO_LONGER_HAS_TEAM("%prefix%The leader no longer has a team."),
    INVITATION_EXPIRED("%prefix%The leader's invitation has expired."),
    JOIN_TEAM_SUCCESS("%prefix%%s has joined the team!"),
    NO_TEAM_TO_LEAVE("%prefix%You don't have a team to leave."),
    LEADER_CANNOT_LEAVE("%prefix%You cannot leave your own team. You must transfer leadership or disband."),
    LEFT_TEAM("%prefix%You have left the team."),
    PLAYER_LEFT_TEAM("%prefix%%s has left the team!"),
    NO_TEAM_TO_KICK("%prefix%You do not have a team to kick players from."),
    CANNOT_KICK_SELF("%prefix%You cannot kick yourself. Use leave, disband, or transfer leadership instead."),
    PLAYER_NOT_IN_TEAM("%prefix%That player is not in your team."),
    KICKED_FROM_TEAM("%prefix%You have been kicked from the team."),
    PLAYER_KICKED("%prefix%%s has been kicked from the team!");

    private String message;


    private final String PREFIX = "☞ <color:#05a3ff><b>TEAMS</b></color> • ";


    TeamMessage(String message) {
        this.message = message;
    }

    public Component toComponent(Object... args) {

        this.message = message.replaceAll("%prefix%", PREFIX);

        return MiniMessage.miniMessage().deserialize(String.format(message, args));
    }
}
