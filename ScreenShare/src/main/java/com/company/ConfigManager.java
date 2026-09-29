package com.company;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ConfigManager {

    private static ConfigManager istance;
    private File file;
    private YamlConfiguration config;

    public ConfigManager(Plugin plugin){
        istance = this;
        load(plugin);
    }
    public void load(Plugin plugin){
        plugin.saveDefaultConfig();
        file = new File(plugin.getDataFolder(),"config.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }
    public String getWorldName(){
        return config.getString("world.name");
    }
    public String getLobbyName(){
        return config.getString("lobby.name");
    }
    public Location getSpawnStaff(World world){
        double x = config.getDouble("world.spawnStaff.x");
        double y = config.getDouble("world.spawnStaff.y");
        double z = config.getDouble("world.spawnStaff.z");
        float yaw = (float) config.getDouble("world.spawnStaff.yaw");
        float pitch = (float) config.getDouble("world.spawnStaff.pitch");
        return new Location(world,x,y,z,yaw,pitch);
    }
    public Location getSpawnSuspected(World world){
        double x = config.getDouble("world.spawnUser.x");
        double y = config.getDouble("world.spawnUser.y");
        double z = config.getDouble("world.spawnUser.z");
        float yaw = (float) config.getDouble("world.spawnUser.yaw");
        float pitch = (float) config.getDouble("world.spawnUser.pitch");
        return new Location(world,x,y,z,yaw,pitch);
    }
    public Location getSpawnLobby(World world){
        double x = config.getDouble("lobby.spawn.x");
        double y = config.getDouble("lobby.spawn.y");
        double z = config.getDouble("lobby.spawn.z");
        float yaw = (float) config.getDouble("lobby.spawn.yaw");
        float pitch = (float) config.getDouble("lobby.spawn.pitch");
        return new Location(world,x,y,z,yaw,pitch);
    }
    public Location getSpawnSpectator(World world){
        double x = config.getDouble("world.spawnSpectator.x");
        double y = config.getDouble("world.spawnSpectator.y");
        double z = config.getDouble("world.spawnSpectator.z");
        float yaw = (float) config.getDouble("world.spawnSpectator.yaw");
        float pitch = (float) config.getDouble("world.spawnSpectator.pitch");
        return new Location(world,x,y,z,yaw,pitch);
    }


    public String getNotOnlineMessage(){
        return config.getString("messages.notOnlineMessage");
    }
    public String getSamePersonMessage(){
        return config.getString("messages.samePersonMessage");
    }
    public String getAlreadyInSSMessage(){
        return config.getString("messages.alreadyInSSMessage");
    }

    public String getStaffMessage(){
        return config.getString("messages.staffMessage");
    }
    public String getUserMessage(){
        return config.getString("messages.userMessage");
    }
    public String getSpectatorMessage(){
        return config.getString("messages.spectatorMessage");
    }

    public String getTitleScoreboardStaff(){
        return config.getString("scoreboardStaff.title");
    }
    public List<String> getScoreboardStaff() {
        return config.getStringList("scoreboardStaff.rows");
    }
    public String getTitleScoreboardUser(){
        return config.getString("scoreboardUser.title");
    }
    public List<String> getScoreboardUser(){
        return config.getStringList("scoreboardUser.rows");
    }
    public String getTitleSpectator(){
        return config.getString("scoreboardSpectator.title");
    }
    public List<String> getScoreboardSpectator(){return config.getStringList("scoreboardSpectator.rows");}
    public String getTitleScoreboardVisit(){return config.getString("scoreboardVisit.title");}
    public List<String> getScoreboardVisit(){return config.getStringList("scoreboardVisit.rows");}

    public String getNotInSS(){
        return config.getString("messages.notInSS");
    }

    public boolean getPrepareCommands(){
        return config.getBoolean("prepareCommands");
    }

    public Set<String> getButtons(){
        ConfigurationSection buttons = config.getConfigurationSection("buttons");
        return buttons.getKeys(false);
    }
    public String getButtonsPath(){
        return "buttons";
    }
    public String getNameButton(String path){
        return config.getString(path + ".text");
    }
    public String getColorButton(String path) {
        return config.getString(path + ".color");
    }
    public String getHoverButton(String path){
        return config.getString(path + ".hover");
    }
    public String getCommandButton(String path){
        return config.getString(path + ".command");
    }

    public String getLockCommand(){
        return config.getString("messages.lockedCommand");
    }
    public List<String> getAllowedCommands(){
        return config.getStringList("allowedCommands");
    }

    public String getSuccessSSMessage(){return config.getString("messages.successSSMessage");}
    public String getCleanSSMessage(){return config.getString("messages.cleanSSMessage");}
    public String getStartSpecMessage(){return config.getString("messages.startSpecMessage");}
    public String getLeaveSpecMessage(){return config.getString("messages.leaveSpecMessage");}
    public String getTitleSSMessage(){return config.getString("messages.titleSSMessage");}
    public String getDescSSMessage(){return config.getString("messages.descSSMessage");}
    public String getPlayerSSedMessage(){return config.getString("messages.playerSSedMessage");}
    public String getSSListTitle(){return config.getString("messages.ssListTitle");}
    public String getSSToString(){return config.getString("messages.ssToString");}
    public String getSSListEmpty(){return config.getString("messages.ssListEmpty");}
    public String getSSLogTitle(){return config.getString("messages.ssLogTitle");}
    public String getSSLogEmpty(){return config.getString("messages.ssLogEmpty");}
    public String getStats(){return config.getString("messages.stats");}

    public String getErrorNeverPlayedMessage(){return config.getString("messages.errorNeverPlayedMessage");}
    public String getErrorNotSpectatorMessage(){return config.getString("messages.errorNotSpectatorMessage");}
    public String getErrorNotValidSpec(){return config.getString("messages.errorNotValidSpec");}
    public String getErrorNotStaffMessage(){return config.getString("messages.errorNotStaffMessage");}


    public static ConfigManager getIstance(){
        return istance;
    }

}
