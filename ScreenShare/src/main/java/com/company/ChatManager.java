package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    private SSManager ssManager;

    public ChatManager(SSManager ssManager){
        this.ssManager = ssManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();
        SS ss = ssManager.getSS(player);
        if(ss != null){

            e.setCancelled(true);
            ss.sendAll(player,message);

        }
    }

}
