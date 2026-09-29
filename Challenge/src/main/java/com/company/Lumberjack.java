package com.company;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.Wood;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Lumberjack extends Challenge {
    public Lumberjack(HashMap<UUID, Integer> players) {
        super("Lumberjack", "Abbatti più tronchi possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onBreakTrunk(BlockBreakEvent event) {

        if (this.isActive) {
            Block block = event.getBlock();
            if (Tag.LOGS.isTagged(block.getType())) {

                Player breaker = event.getPlayer();
                for (Map.Entry<UUID, Integer> entry : players.entrySet()) {
                    if (breaker.getUniqueId().equals(entry.getKey())) {
                        entry.setValue(entry.getValue() + 1);
                    }
                }

            }
        }
    }

}
