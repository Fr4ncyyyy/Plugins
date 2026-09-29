package com.company.Match.Team.Shop;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemShop extends ShopElement{

    private Price price;
    private int amount;

    public ItemShop(ItemStack itemStack,Price price,int slot,int amount){
        super(itemStack,slot);
        this.price = price;
        this.amount = amount;

        itemStack.setAmount(amount);
    }

    public void init(Inventory inventory,Inventory playerInventory){
        inventory.setItem(slot,itemStack);
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

    public void clicked(Inventory inventory){



    }

}
