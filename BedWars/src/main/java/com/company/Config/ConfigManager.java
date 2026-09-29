package com.company.Config;

import com.company.BedWars;
import com.company.Match.Team.Bed;

import java.util.ArrayList;

public class ConfigManager {

    private final BedWars bw;
    private final ArrayList<Config> configs;

    public ConfigManager(BedWars bw){
        this.bw = bw;
        configs = new ArrayList<>();
        initConfigs();
    }

    public void initConfigs(){
        configs.add(new ArenaConfig());
        configs.add(new MatchConfig());
        configs.add(new GeneratorConfig());
        configs.add(new ScoreboardConfig());
        configs.add(Bed.bedConfig);
        configs.add(new ShopConfig());
    }

    public void loadAll(){

        for(Config config : configs){
            try{
                config.load(bw);
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    public ArenaConfig getArenaConfig(){
        for(Config config : configs){
            if(config instanceof ArenaConfig){
                return (ArenaConfig) config;
            }
        }
        return null;
    }
    public MatchConfig getMatchConfig(){
        for(Config config : configs){
            if(config instanceof MatchConfig){
                return (MatchConfig) config;
            }
        }
        return null;
    }
    public GeneratorConfig getGeneratorConfig(){
        for(Config config : configs){
            if(config instanceof GeneratorConfig){
                return (GeneratorConfig) config;
            }
        }
        return null;
    }

    public ScoreboardConfig getScoreboardConfig(){
        for(Config config : configs){
            if(config instanceof ScoreboardConfig){
                return (ScoreboardConfig) config;
            }
        }
        return null;
    }

    public ShopConfig getShopConfig(){
        for(Config config : configs){
            if(config instanceof ShopConfig){
                return (ShopConfig) config;
            }
        }
        return null;
    }


}
