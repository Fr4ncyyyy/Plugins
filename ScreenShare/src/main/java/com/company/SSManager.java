package com.company;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SSManager {

    private ConfigManager config = ConfigManager.getIstance();
    private Plugin plugin;
    private Statistics statistics;
    private World world;
    private ArrayList<SS> sessions;

    public SSManager(Plugin plugin){
        this.plugin = plugin;
        sessions = new ArrayList<>();
        statistics = new Statistics();

        try{
            world = loadWorld(plugin);
            setWorldSettings();

            if(world == null){
                throw new SSWorldNotCreated("Some problems occurred while creating the world!");
            }

        }catch(SSWorldNotCreated e){
            plugin.getLogger().severe("Error: " + e);
            Bukkit.getPluginManager().disablePlugin(plugin);
        };
    }

    private World loadWorld(Plugin plugin){
        String worldName = config.getWorldName();
        World world = plugin.getServer().getWorld(worldName);

        if(world == null){
            return new WorldCreator(worldName).createWorld();
        }

        return world;
    }

    public boolean isValidSS(Player staff,String suspectedName){
        Player suspected = Bukkit.getPlayer(suspectedName);
        if(!Bukkit.getOnlinePlayers().contains(suspected)){
            staff.sendMessage(config.getNotOnlineMessage()
                    .replace("%player%",suspectedName));
            return false;
        }
        if(staff.equals(suspected)){
            staff.sendMessage(config.getSamePersonMessage());
            return false;
        }
        if(getSS(staff) != null){
            staff.sendMessage(config.getAlreadyInSSMessage()
                    .replace("%player%",staff.getName()));
            return false;
        }
        if(getSS(suspected) != null){
            suspected.sendMessage(config.getAlreadyInSSMessage()
                    .replace("%player%",suspected.getName()));
            return false;
        }

        return true;
    }

    public SS getSS(Player player){
        for(SS ss : sessions){
            if(ss.getSS(player)){
                return ss;
            }
        }
        return null;
    }
    public World getWorld(){
        return world;
    }

    public void setWorldSettings() {
        if (world != null) {
            world.setGameRule(GameRule.ADVANCE_TIME, false);
            world.setTime(6000);

            world.setGameRule(GameRule.ADVANCE_WEATHER, false);
            world.setStorm(false);
            world.setThundering(false);

            world.setGameRule(GameRule.SPAWN_MOBS, false);
        }
    }

    public Statistics getStatistics(){
        return statistics;
    }

    public void addSS(SS ss){
        sessions.add(ss);
    }
    public void removeSS(SS ss){
        sessions.remove(ss);
    }

    public World getLobby(){
        return plugin.getServer().getWorld(config.getLobbyName());
    }

    public boolean isValidSpec(Player player){
        SS ss = getSS(player);

        if(ss == null)return false;
        return ss.getStaff().equals(player);
    }
    public void ssVisit(Player player){
        ArrayList<Player> p = new ArrayList<>();
        p.add(player);

        String title = config.getTitleScoreboardVisit();
        var rows = config.getScoreboardVisit();

        Scoreboard scoreboard = new com.company.Scoreboard(
                p,
                title,
                rows
        );
        scoreboard.setLines(player,null,"");
    }
    }
