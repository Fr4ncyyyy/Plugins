package com.company;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Plugin extends JavaPlugin {

    public void onEnable(){
        System.out.println("Plugin Abilitato!");

        LoadMapPlayers loadMap = new LoadMapPlayers();
        getServer().getPluginManager().registerEvents(loadMap,this);
        ChallengeManager cm = new ChallengeManager(this,loadMap.getPlayers());

        Commands commands = new Commands(this,cm.getChallenges());
        cm.setCommands(commands);
        Objects.requireNonNull(getCommand("challenge")).setExecutor(commands);
    }
    public void onDisable(){
        System.out.println("Plugin Disabilitato!");
    }

}
