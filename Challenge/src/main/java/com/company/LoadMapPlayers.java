package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class LoadMapPlayers implements Listener {

    private HashMap<UUID,Integer> players;

    public LoadMapPlayers(){
        players = new HashMap<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(!players.containsKey(p.getUniqueId())){
            players.put(p.getUniqueId(),0);
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player p = event.getPlayer();
        players.remove(p.getUniqueId());

    }
    public HashMap<UUID,Integer> getPlayers(){
        return players;
    }

}
