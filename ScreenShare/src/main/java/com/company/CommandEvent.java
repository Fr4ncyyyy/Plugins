package com.company;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    private SSManager ssManager;
    private ConfigManager config;

    public CommandEvent(SSManager ssManager){
        config = ConfigManager.getIstance();
        this.ssManager = ssManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){

        String command = e.getMessage().split(" ")[0];

        SS ss = ssManager.getSS(e.getPlayer());

        if (ss == null) {
            return;
        }

        if(config.getAllowedCommands().contains(command)){
            return;
        }

        if(ss.getStaff().equals(e.getPlayer())){
            return;
        }

        e.setCancelled(true);
        e.getPlayer().sendMessage(config.getLockCommand());
    }

}
