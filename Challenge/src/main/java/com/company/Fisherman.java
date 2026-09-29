package com.company;

import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fisherman extends Challenge{
    public Fisherman(HashMap<UUID, Integer> players) {
        super("Fisherman", "Pesca più pesci possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){

        if(isActive()){
            if(event.getCaught() instanceof Fish){
                Player player = event.getPlayer();

                for(Map.Entry<UUID,Integer> entry : players.entrySet()){
                    if(player.getUniqueId().equals(entry.getKey())){
                        entry.setValue(entry.getValue() + 1);
                    }
                }

            }
        }

    }
}
