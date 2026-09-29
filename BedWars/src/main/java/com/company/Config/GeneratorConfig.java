package com.company.Config;

import com.company.BedWars;
import com.company.Match.Generators.CenterGenerators.Enums.DiamondPhases;
import com.company.Match.Generators.CenterGenerators.Enums.EmeraldPhases;
import com.company.Match.Generators.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;
import java.util.Set;

public class GeneratorConfig extends Config{
    @Override
    public void load(BedWars bw) {
        bw.saveResource("generatorConfig.yml",false);
        file = new File(bw.getDataFolder(),"generatorConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public Set<String> getGeneratorBaseByID(String arenaID){
        ConfigurationSection section = config.getConfigurationSection("locations."
                + arenaID + ".generators");
        return section.getKeys(false);
    }

    public Set<String> getGeneratorCenterByID(String arenaID){
        ConfigurationSection section = config.getConfigurationSection("locations." + arenaID + ".center");
        return section.getKeys(false);
    }

    public Location getBaseLocation(String arenaID, int id){

        String path = "locations." + arenaID + ".generators." + id + ".location";

        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("locations."
                + arenaID + ".world")));
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        return new Location(world,x,y,z);

    }

    public Location getCenterLocation(String arenaID, int id){
        String path = "locations." + arenaID + ".center." + id + ".location";

        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("locations."
                + arenaID + ".world")));
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        return new Location(world,x,y,z);
    }

    public Type getBaseType(String arenaID, int id){return Type.valueOf(config.getString("locations."
            + arenaID + ".generators." + id + ".type"));}
    public Material getMaterial(Type type){
        return Material.valueOf(Objects.requireNonNull(config.getString("generators."
                + type + ".material")).toUpperCase());
    }
    public int getAmount(Type type){
        return config.getInt("generators." + type + ".amount");
    }
    public int getBaseTime(Type type){
        return config.getInt("generators." + type + ".time");
    }
    public int getMax(Type type){
        return config.getInt("generators." + type + ".max");
    }

    public Type getCenterType(String arenaID,int id){return Type.valueOf(config.getString("locations."
            + arenaID + ".center." + id + ".type"));}
    public int getDiamondTime(Type type, DiamondPhases diamondPhase){return config.getInt("generators."
            + type + ".time." + diamondPhase.toString().toLowerCase());}
    public int getEmeraldTime(Type type, EmeraldPhases emeraldPhase){return config.getInt("generators."
            + type + ".time." + emeraldPhase.toString().toLowerCase());}


    public Location getHologramLocation(String id,String arenaID){

        World world = Bukkit.getWorld(Objects.requireNonNull(config.getString("locations." + arenaID + ".world")));

        double x = config.getDouble("locations." + arenaID + ".center." + id + ".hologram.location.x");
        double y = config.getDouble("locations." + arenaID + ".center." + id + ".hologram.location.y");
        double z = config.getDouble("locations." + arenaID + ".center." + id + ".hologram.location.z");

        return new Location(world,x,y,z);

    }
    public String getTextHologram(Type type){return config.getString("holograms."
            + type + ".message");}


}
