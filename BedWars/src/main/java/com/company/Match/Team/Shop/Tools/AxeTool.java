package com.company.Match.Team.Shop.Tools;

import com.company.Match.Team.Shop.Price;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AxeTool extends Tools{
    public AxeTool(ItemStack itemStack, int slot, int amount, Map<Type, Price> map) {
        super(itemStack, slot, amount, map);
        tool = Tool.AXE;
    }

    @Override
    public void init(Inventory inventory, Inventory playerInventory) {
        Type axeType = null;

        for(ItemStack is : playerInventory){

            if(is == null)continue;

            Tool tool = Tool.getTool(is);
            Type type = Type.getType(is);

            if(tool != this.tool || type == null)continue;

            axeType = type;

        }

        initTool(inventory,playerInventory,this.tool,axeType);
    }
}
