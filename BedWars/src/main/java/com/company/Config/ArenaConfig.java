package com.company.Config;

import com.company.BedWars;
import com.company.Color;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class ArenaConfig extends Config{


    public void load(BedWars bw){
        bw.saveResource("arenaConfig.yml",false);
        file = new File(bw.getDataFolder(),"arenaConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }


    public Set<String> getArenas(){
        ConfigurationSection section = config.getConfigurationSection("arenas");
        return section.getKeys(false);
    }
    public Set<String> getTeams(String arenaId){

        ConfigurationSection section =
                config.getConfigurationSection("arenas." + arenaId + ".teams");

        return section.getKeys(false);
    }

    public World getWorldLobby(){
        String lobbyName = config.getString("lobby.name");
        World lobby = Bukkit.getWorld(lobbyName);
        if(lobby == null){
            lobby = Bukkit.createWorld(new WorldCreator(lobbyName));
        }
        return lobby;
    }

    public Location getSpawnLobby(){
        double x = config.getDouble("lobby.spawn.x");
        double y = config.getDouble("lobby.spawn.y");
        double z = config.getDouble("lobby.spawn.z");
        float yaw = (float) config.getDouble("lobby.spawn.yaw");
        float pitch = (float) config.getDouble("lobby.spawn.pitch");

        return new Location(getWorldLobby(),x,y,z,yaw,pitch);

    }

    public int getIdTeam(String idArena,String color){
        return config.getInt("arenas." + idArena + ".teams." + color + ".id");
    }
    public World getWorld(String name){
        String nameWorld = config.getString("arenas." + name + ".world");
        World world = Bukkit.getWorld(nameWorld);

        if(world == null){
            world = Bukkit.createWorld(new WorldCreator(nameWorld));
        }

        return world;
    }
    public Location getWaitingSpawn(String name){

        World world = Bukkit.getWorld(
                Objects.requireNonNull(config.getString("arenas." + name + ".world"))
        );
        double x = config.getDouble("arenas." + name + ".waitingSpawn.x");
        double y = config.getDouble("arenas." + name + ".waitingSpawn.y");
        double z = config.getDouble("arenas." + name + ".waitingSpawn.z");
        float yaw = (float) config.getDouble("arenas." + name + ".waitingSpawn.yaw");
        float pitch = (float) config.getDouble("arenas." + name + ".waitingSpawn.pitch");
        return new Location(world,x,y,z,yaw,pitch);

    }
    public Location getSpectatorSpawn(String name){
        World world = Bukkit.getWorld(name);
        double x = config.getDouble("arenas." + name + ".spectatorSpawn.x");
        double y = config.getDouble("arenas." + name + ".spectatorSpawn.y");
        double z = config.getDouble("arenas." + name + ".spectatorSpawn.z");
        float yaw = (float) config.getDouble("arenas." + name + ".spectatorSpawn.yaw");
        float pitch = (float) config.getDouble("arenas." + name + ".spectatorSpawn.pitch");
        return new Location(world,x,y,z,yaw,pitch);
    }
    public Location getSpawnTeam(String id, Color color){

        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("arenas." + id + ".world")));
        double x = config.getDouble("arenas." + id + ".teams." + color + ".spawn.x");
        double y = config.getDouble("arenas." + id + ".teams." + color + ".spawn.y");
        double z = config.getDouble("arenas." + id + ".teams." + color + ".spawn.z");
        float yaw = (float) config.getDouble("arenas." + id + ".teams." + color + ".spawn.yaw");
        float pitch = (float) config.getDouble("arenas." + id + ".teams." + color + ".spawn.pitch");
        return new Location(world,x,y,z,yaw,pitch);

    }

    public Location getNormalNPCLocation(String arenaID,Color color){

        World world = Bukkit.getWorld(Objects.requireNonNull(config
                .getString("arenas." + arenaID + ".world")));
        double x = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.normal.location.x");
        double y = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.normal.location.y");
        double z = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.normal.location.z");

        return new Location(world,x,y,z);
    }

    public Location getUpgradeNPCLocation(String arenaID,Color color){

        World world = Bukkit.getWorld(Objects.requireNonNull(config
                .getString("arenas." + arenaID + ".world")));
        double x = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.upgrade.location.x");
        double y = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.upgrade.location.y");
        double z = config.getDouble("arenas." + arenaID
                + ".teams." + color + ".npcs.upgrade.location.z");

        return new Location(world,x,y,z);

    }



}
