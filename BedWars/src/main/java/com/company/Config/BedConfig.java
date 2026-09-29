package com.company.Config;

import com.company.BedWars;
import com.company.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BedConfig extends Config{
    @Override
    public void load(BedWars bw) {
        bw.saveResource("arenaConfig.yml",false);
        file = new File(bw.getDataFolder(),"arenaConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public Location getBedLocation(String arenaID,Color color){

        String path = "arenas." + arenaID + ".teams." + color + ".bed.location";

        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");

        String worldName = config.getString("arenas." + arenaID + ".world");

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            throw new IllegalStateException(
                    "Il mondo '" + worldName + "' non è caricato!"
            );
        }

        return new Location(world, x, y, z);
    }

}
