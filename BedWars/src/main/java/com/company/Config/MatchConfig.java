package com.company.Config;

import com.company.BedWars;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MatchConfig extends Config{
    @Override
    public void load(BedWars bw) {
        bw.saveResource("matchConfig.yml",false);
        file = new File(bw.getDataFolder(),"matchConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public int getMinPlayersToStart(){
        return config.getInt("matches.minPlayersToStart");
    }
    public int getMaxPlayersToStart(){return config.getInt("matches.maxPlayersToStart");}
    public String getStartingMessage(){
        return config.getString("matches.startingMessage");
    }
    public String getStartingMessageFast(){
        return config.getString("matches.startingMessageFast");
    }
    public int getTimeCountDown(){
        return config.getInt("matches.timeCountDown");
    }

}
