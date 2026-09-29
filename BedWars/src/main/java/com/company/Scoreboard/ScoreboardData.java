package com.company.Scoreboard;

import com.company.Match.Team.Team;

import java.util.ArrayList;

public class ScoreboardData {

    private int currentPlayers;
    private final String arenaName;
    private final int maxPlayers;
    private int time;
    private final ArrayList<Team> teams;

    public ScoreboardData(String arenaName,int maxPlayers,ArrayList<Team> teams){
        this.arenaName = arenaName;
        this.maxPlayers = maxPlayers;
        this.teams = teams;
    }

    public int getCurrentPlayers(){
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers){
        this.currentPlayers = currentPlayers;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public String getArenaName(){
        return arenaName;
    }
    public int getMaxPlayers(){
        return maxPlayers;
    }

    public ArrayList<Team> getTeams(){
        return teams;
    }

}
