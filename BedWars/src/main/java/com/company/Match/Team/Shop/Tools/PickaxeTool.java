package com.company.Match.Team.Shop.Tools;

import com.company.Match.Team.Shop.Price;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PickaxeTool extends Tools{

    public PickaxeTool(ItemStack itemStack, int slot, int amount, Map<Type, Price> map) {
        super(itemStack, slot, amount, map);
        tool = Tool.PICKAXE;
    }

    public void init(Inventory inventory,Inventory playerInventory){
        Type pickaxeType = null;

        for(ItemStack is : playerInventory){

            if(is == null)continue;

            Tool tool = Tool.getTool(is);
            Type type = Type.getType(is);

            if(tool != this.tool || type == null)continue;

            pickaxeType = type;

        }

        initTool(inventory,playerInventory,this.tool,pickaxeType);
    }
}
