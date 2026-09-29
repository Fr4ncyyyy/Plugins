package com.company;

import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerChangeBeaconEffectEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    private ChatManager chatManager;
    public Chat(ChatManager chatManager){
        this.chatManager = chatManager;
    }
    @EventHandler(priority = EventPriority.NORMAL,ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e){
        String message = e.getMessage();
        Player p = e.getPlayer();
        e.setCancelled(true);

        chatManager.sendMessage(p,message);

    }
}
