package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Collector extends Challenge{
    public Collector(HashMap<UUID, Integer> players) {
        super("Collector","Raccogli più oggetti possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onCollection(EntityPickupItemEvent event){

        if(isActive()){

            if(event.getEntity() instanceof Player){
                Player player = (Player)event.getEntity();

                for(Map.Entry<UUID,Integer> entry : players.entrySet()){
                    if(player.getUniqueId().equals(entry.getKey())){
                        entry.setValue(entry.getValue() + 1);
                    }
                }

            }

        }

    }
}
