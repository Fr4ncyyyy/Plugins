package com.company.Listeners;

import com.company.Config.ScoreboardConfig;
import com.company.Lobby;
import com.company.Match.Match;
import com.company.Match.MatchManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ChangeWorld implements Listener {

    private final MatchManager matchManager;
    private final Lobby lobby;
    private final ScoreboardConfig scoreboardConfig;

    public ChangeWorld(MatchManager matchManager, Lobby lobby,ScoreboardConfig scoreboardConfig){
        this.matchManager = matchManager;
        this.lobby = lobby;
        this.scoreboardConfig = scoreboardConfig;
    }
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        World world = p.getWorld();
        Match match = matchManager.getMatch(p);

        if(world.getName().equalsIgnoreCase(lobby.getName())){
            lobby.toSpawn(p);
            lobby.update(p,scoreboardConfig.getTitleLobby(),scoreboardConfig.getContentLobby());
        }

        if(match != null){
            match.updateAllScoreboards();
        }
    }

}
