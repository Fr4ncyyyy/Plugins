package com.company.Match.Team.Shop.Tools;

import com.company.Match.Team.Shop.ItemShop;
import com.company.Match.Team.Shop.Price;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public abstract class Tools extends ItemShop {

    protected Tool tool;
    private Map<Type,Price> map;

    public Tools(ItemStack itemStack, int slot, int amount, Map<Type,Price> map) {
        super(itemStack, null, slot, amount);
        this.map = map;
    }

    public abstract void init(Inventory inventory,Inventory playerInventory);

    protected void initTool(Inventory inventory,Inventory playerInventory,Tool tool ,Type type){
        if(tool == null)return;
        if(type == null){
            itemStack.setType(Type.values()[0].getMaterial(tool));
        }else {
            itemStack.setType(type.progress(tool));
        }
        super.init(inventory,playerInventory);
    }



}
