package com.company;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Miner extends Challenge{
    public Miner(HashMap<UUID, Integer> players) {
        super("Miner","Estrai più pietra possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onBreakStone(BlockBreakEvent event){

        if(this.isActive()){
            Block block = event.getBlock();
            Player breaker = event.getPlayer();

            if(block.getType() == Material.STONE){

                for (Map.Entry<UUID, Integer> entry : players.entrySet()) {
                    if (breaker.getUniqueId().equals(entry.getKey())) {
                        entry.setValue(entry.getValue() + 1);
                    }
                }

            }

        }

    }
}
