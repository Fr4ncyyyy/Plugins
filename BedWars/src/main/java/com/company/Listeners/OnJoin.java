package com.company.Listeners;

import com.company.Config.ArenaConfig;
import com.company.Config.ScoreboardConfig;
import com.company.Lobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    private final Lobby lobby;
    private final ArenaConfig arenaConfig;
    private final ScoreboardConfig scoreboardConfig;

    public OnJoin(Lobby lobby,ArenaConfig arenaConfig,ScoreboardConfig scoreboardConfig){
        this.lobby = lobby;
        this.arenaConfig = arenaConfig;
        this.scoreboardConfig = scoreboardConfig;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);
        lobby.toSpawn(p);
        lobby.update(p,scoreboardConfig.getTitleLobby(),scoreboardConfig.getContentLobby());

    }

}
