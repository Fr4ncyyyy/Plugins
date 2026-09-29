package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Disconnection implements Listener {

    private SSManager ssManager;

    public Disconnection(SSManager ssManager){
        this.ssManager = ssManager;
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        Player p = e.getPlayer();
        SS ss = ssManager.getSS(p);
        if(ss != null){
            if(ss.getSpectators().contains(p)){
                ss.removeSpectator(ssManager.getLobby(),p);
                return;
            }
            ssManager.removeSS(ss);

            Result result;

            if(ss.getStaff().equals(p)){
                result = Result.CLEAN;
            }else {
                result = Result.BANNED;
            }

            ss.stop(ssManager.getStatistics(),result,ssManager.getLobby());
        }

    }

}
