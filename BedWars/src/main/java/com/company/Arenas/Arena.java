package com.company.Arenas;
import com.company.Match.Team.Team;
import org.bukkit.Difficulty;
import org.bukkit.GameRules;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;


public class Arena {

    private String id;
    private World world;
    private Location waitingSpawm;
    private Location spectatorSpawn;
    private ArrayList<Team> teams;

    public Arena(String id,World world, Location waitingSpawm, Location spectatorSpawn,ArrayList<Team> teams){
       load(id,world,waitingSpawm,spectatorSpawn,teams);
    }

    private void load(String id,World world, Location waitingSpawm, Location spectatorSpawn,ArrayList<Team> teams){
        this.id = id;
        this.world = world;
        this.waitingSpawm = waitingSpawm;
        this.spectatorSpawn = spectatorSpawn;
        this.teams = teams;
        setSettings();
    }

    private void setSettings(){

        world.setTime(1000);
        world.setThundering(false);
        world.setStorm(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRule(GameRules.ADVANCE_TIME, false);
        world.setGameRule(GameRules.SPAWN_MOBS,false);
        world.setGameRule(GameRules.ADVANCE_WEATHER,false);

    }

    public Location getWaitingSpawm(){
        return waitingSpawm;
    }
    public String getWorldName(){
        return world.getName();
    }
    public ArrayList<Team> getTeams(){
        return teams;
    }
    public String getID(){return id;}
}
