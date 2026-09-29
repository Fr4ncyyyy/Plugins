package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Builder extends Challenge{
    public Builder(HashMap<UUID, Integer> players) {
        super("Builder","Piazza più blocchi possibile entro il tempo limite.", players);
    }
    @EventHandler
    public void onPlaceBloco(BlockPlaceEvent event){

        if(isActive()){
            Player player = event.getPlayer();

            for(Map.Entry<UUID,Integer> entry : players.entrySet()){
                if(player.getUniqueId().equals(entry.getKey())){
                    entry.setValue(entry.getValue() + 1);
                }
            }
        }

    }
}
