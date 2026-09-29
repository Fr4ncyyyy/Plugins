package com.company.Listeners;

import com.company.Match.Match;
import com.company.Match.MatchManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Disconnect implements Listener {

    private final MatchManager matchManager;

    public Disconnect(MatchManager matchManager){
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();

        Match match = matchManager.getMatch(p);

        if(match != null){
            match.leaveBW(p);
        }


    }

}
