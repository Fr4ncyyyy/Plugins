package com.company.Listeners;

import com.company.Lobby;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class Damage implements Listener {

    private final Lobby lobby;

    public Damage(Lobby lobby){
        this.lobby = lobby;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        Entity entity = e.getEntity();
        if(entity instanceof Player){

            World world = entity.getWorld();
            if(world.equals(lobby.getWorld())){
                e.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void itemDamage(PlayerItemDamageEvent e){
        e.setCancelled(true);
    }

}
