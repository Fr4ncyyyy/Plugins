package com.company.Utils.RollBack;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ChangeBlock {

    private HashMap<Block,Long> historyBlocks;

    public ChangeBlock(Block block){
        put(block,System.currentTimeMillis());
    }

    public void put(Block block,Long time){
        historyBlocks.put(block,time);
    }

}
