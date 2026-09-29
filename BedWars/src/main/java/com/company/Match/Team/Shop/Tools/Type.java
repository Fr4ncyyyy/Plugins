package com.company.Match.Team.Shop.Tools;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public enum Type {
    WOODEN,
    STONE,
    IRON,
    GOLDEN,
    DIAMOND;

    private static final Map<Material,Type> TYPES = Map.of(
            Material.WOODEN_AXE,Type.WOODEN,
            Material.STONE_AXE,Type.STONE,
            Material.IRON_AXE,Type.IRON,
            Material.GOLDEN_AXE,Type.GOLDEN,
            Material.DIAMOND_AXE,Type.DIAMOND,

            Material.WOODEN_PICKAXE,Type.WOODEN,
            Material.STONE_PICKAXE,Type.STONE,
            Material.IRON_PICKAXE,Type.IRON,
            Material.GOLDEN_PICKAXE,Type.GOLDEN,
            Material.DIAMOND_PICKAXE,Type.DIAMOND
    );

    public Material getMaterial(Tool tool){

        switch(tool){

            case AXE -> {

                switch(this){

                    case WOODEN -> {
                        return Material.WOODEN_AXE;
                    }
                    case STONE -> {
                        return Material.STONE_AXE;
                    }
                    case IRON -> {
                        return Material.IRON_AXE;
                    }
                    case GOLDEN -> {
                        return Material.GOLDEN_AXE;
                    }
                    case DIAMOND -> {
                        return Material.DIAMOND_AXE;
                    }

                }

            }
            case PICKAXE -> {

                switch(this){

                    case WOODEN -> {
                        return Material.WOODEN_PICKAXE;
                    }
                    case STONE -> {
                        return Material.STONE_PICKAXE;
                    }
                    case IRON -> {
                        return Material.IRON_PICKAXE;
                    }
                    case GOLDEN -> {
                        return Material.GOLDEN_PICKAXE;
                    }
                    case DIAMOND -> {
                        return Material.DIAMOND_PICKAXE;
                    }

                }

            }

        }

        return null;

    }

    public static Type getType(ItemStack itemStack){

            for(Type type : Type.values()){

                if(itemStack.getType().name().startsWith(String.valueOf(type))){
                    return type;
                }

            }
        return null;
    }

    public Material progress(Tool tool){

        if(this == DIAMOND)return getMaterial(tool);
        int index = this.ordinal();
        return values()[index + 1].getMaterial(tool);

    }


}
