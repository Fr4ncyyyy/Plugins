package com.company.Utils.RollBack;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

public class BlocksManager implements Listener {

    private HashMap<Location,ChangeBlock> changeBlocks;

    public BlocksManager(HashMap<Location,ChangeBlock> changeBlocks){
        this.changeBlocks = changeBlocks;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){

        Block block = event.getBlock();
        Location location = event.getBlock().getLocation();
        if(!changeBlocks.containsKey(location)){
            changeBlocks.put(location,new ChangeBlock(block));
        }else {
            ChangeBlock changeBlock = changeBlocks.get(location);
            changeBlock.put(block,System.currentTimeMillis());
        }

    }

    public HashMap<Location, ChangeBlock> getBlocksInRadius(Location location, double radius){

        HashMap<Location,ChangeBlock> blocksInRadius = new HashMap<>();

        for(Map.Entry<Location,ChangeBlock> entry : changeBlocks.entrySet()){

            if(!location.getWorld().equals(entry.getKey().getWorld()))continue;

            double distance = Math.sqrt(
                    Math.pow(
                            location.getX() - entry.getKey().getX(),2
                    ) + Math.pow(
                            location.getY() - entry.getKey().getY(),2
                    ) + Math.pow(
                            location.getZ() - entry.getKey().getZ(),2
                    )
            );

            if(distance <= radius){

                blocksInRadius.put(
                        entry.getKey(),
                        entry.getValue()
                );

            }

        }

        return blocksInRadius;

    }

    public void getBlocksInTime(HashMap<Location,ChangeBlock> blocksInRadius,long time){

        for(Map.Entry<Location,ChangeBlock> entry : blocksInRadius.entrySet()){

            

        }

    }



}
