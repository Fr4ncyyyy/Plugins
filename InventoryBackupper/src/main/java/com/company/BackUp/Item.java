package com.company.BackUp;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;

public class Item {

    private final ItemStack itemStack;
    private final int slot;

    public Item(ItemStack itemStack,int slot,String name){
        this.itemStack = itemStack;
        this.slot = slot;

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(name));

    }

    public int getSlot(){
        return slot;
    }
    public ItemStack getItemStack(){
        return itemStack;
    }

}
