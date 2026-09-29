package com.company.Match.Team.Shop.Tools;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public enum Tool {
    PICKAXE,
    AXE;

    private static final Map<Material, Tool> TOOLS = Map.of(
            Material.WOODEN_PICKAXE, PICKAXE,
            Material.STONE_PICKAXE, PICKAXE,
            Material.IRON_PICKAXE, PICKAXE,
            Material.GOLDEN_PICKAXE, PICKAXE,
            Material.DIAMOND_PICKAXE, PICKAXE,

            Material.WOODEN_AXE, AXE,
            Material.STONE_AXE, AXE,
            Material.IRON_AXE, AXE,
            Material.GOLDEN_AXE, AXE,
            Material.DIAMOND_AXE, AXE
    );

    public static Tool getTool(ItemStack itemStack){

        if(itemStack == null)return null;
        return TOOLS.get(itemStack.getType());
    }

}