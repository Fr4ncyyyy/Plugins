package com.company;

import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ChallengeManager {

    private Plugin plugin;
    private HashMap<UUID,Integer> players;
    private ArrayList<Challenge> challenges;
    private Commands commands;

    public ChallengeManager(Plugin plugin, HashMap<UUID,Integer> players){
        this.plugin = plugin;
        this.players = players;
        challenges = new ArrayList<>();

        initChallenges();

        new LoadEvents(plugin,challenges);

        new BukkitRunnable(){
            public void run(){
                if(Challenge.getCanStart()){
                    if(!isOneActive() && !isEmpty()){
                        Challenge c = generateChallenge();
                        c.start(plugin);
                        commands.setChallenge(c);
                    }
                }
            }
        }.runTaskTimer(plugin,5 * 20L,10L);
    }

    public void initChallenges(){
        challenges.add(new ZombieHunter(players));
        challenges.add(new SkeletonHunter(players));
        challenges.add(new PvPMaster(players));
        challenges.add(new Miner(players));
        challenges.add(new Lumberjack(players));
        challenges.add(new Fisherman(players));
        challenges.add(new Collector(players));
        challenges.add(new Builder(players));
    }

    private Challenge generateChallenge(){
        Random r = new Random();
        return challenges.get(r.nextInt(challenges.size()));
    }

    public boolean isOneActive(){
        for(Challenge c : challenges){
            if(c.isActive())return true;
        }
        return false;
    }

    private boolean isEmpty(){
        return players.isEmpty();
    }
    public ArrayList<Challenge> getChallenges(){
        return challenges;
    }

    public void setCommands(Commands commands){
        this.commands = commands;
    }

}
