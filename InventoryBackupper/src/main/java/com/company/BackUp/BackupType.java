package com.company.BackUp;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum BackupType {

    JOIN(new Item(new ItemStack(Material.GREEN_WOOL),13,"Join")),
    DEATH(new Item(new ItemStack(Material.RED_WOOL),14,"Death"));

    private final Item item;

    BackupType(Item item) {
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

}
