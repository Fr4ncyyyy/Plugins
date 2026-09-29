package com.company.Match.Team.Shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class ShopElement {

    protected ItemStack itemStack;
    protected int slot;

    public ShopElement(ItemStack itemStack,int slot){
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public abstract void init(Inventory inventory,Inventory playerInventory);

    public int getSlot(){
        return slot;
    }
    public abstract void clicked(Inventory inventory);

}
