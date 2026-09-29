package com.company;

import java.util.ArrayList;

public class LoadEvents {

    private Plugin plugin;
    private ArrayList<Challenge> challenges;

    public LoadEvents(Plugin plugin, ArrayList<Challenge> challenges){
        this.plugin = plugin;
        this.challenges = challenges;

        registerAll();

    }

    public void registerAll(){

        for(Challenge c : challenges){
            plugin.getServer().getPluginManager().registerEvents(c,plugin);
        }

    }

}
