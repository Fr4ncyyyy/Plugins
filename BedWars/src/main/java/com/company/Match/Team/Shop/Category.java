package com.company.Match.Team.Shop;
import net.citizensnpcs.trait.ShopTrait;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category extends ShopElement{

    private HashMap<ItemShop,Integer> itemsShop;

    public Category(int slot,ItemStack itemStack,HashMap<ItemShop,Integer> itemsShop){
        super(itemStack,slot);
        this.slot = slot;
        this.itemsShop = itemsShop;
    }

    public void init(Inventory inventory,Inventory playerInventory){
        inventory.setItem(slot,itemStack);

        /*for(ItemShop itemShop : itemsShop){
            itemShop.init(inventory);
        }*/

    }

    public void clicked(Inventory inventory){

        inventory.clear();

        System.out.println("categoria cliccata");

        for(Map.Entry<ItemShop,Integer> entry : itemsShop.entrySet()){

            inventory.setItem(entry.getValue(),entry.getKey().getItemStack());

        }


    }

}
