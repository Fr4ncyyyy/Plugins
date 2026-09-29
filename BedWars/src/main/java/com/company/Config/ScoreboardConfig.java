package com.company.Config;

import com.company.BedWars;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.List;

public class ScoreboardConfig extends Config{
    @Override
    public void load(BedWars bw) {
        bw.saveResource("scoreboardConfig.yml",false);
        file = new File(bw.getDataFolder(),"scoreboardConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public String getTitleLobby(){return config.getString("scoreboards.lobby.title");}
    public List<String> getContentLobby(){return config.getStringList("scoreboards.lobby.content");}

    public String getTitleWaiting(){
        return config.getString("scoreboards.waiting.title");
    }
    public String getTitleStarting(){return config.getString("scoreboards.starting.title");}
    public List<String> getContentWaiting(){
        return config.getStringList("scoreboards.waiting.content");
    }
    public List<String> getContentStarting(){return config.getStringList("scoreboards.starting.content");}

    public String getTitleRunning(){return config.getString("scoreboards.running.title");}
    public List<String> getContentRunning(){return config.getStringList("scoreboards.running.content");}

    public String getYouTeam(){
        return config.getString("scoreboards.running.you");
    }

    public String getTitleGame(){return config.getString("scoreboards.game.title");}
    public List<String> getContentGame(){
        return config.getStringList("scoreboards.game.content");
    }

}
