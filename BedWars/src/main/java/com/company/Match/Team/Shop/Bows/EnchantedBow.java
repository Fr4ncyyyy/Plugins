package com.company.Match.Team.Shop.Bows;

import com.company.Match.Team.Shop.ItemShop;
import com.company.Match.Team.Shop.Price;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnchantedBow extends ItemShop implements Effect{

    private ItemStack bow;

    public EnchantedBow(ItemStack bow, Price price,
                        int slot, int amount, HashMap<Enchantment,Integer> enchantments){
        super(bow,price,slot,amount);
        this.bow = bow;
        applyEffect(enchantments);
    }

    @Override
    public void applyEffect(HashMap<Enchantment,Integer> enchantments) {
        for(Map.Entry<Enchantment,Integer> entry : enchantments.entrySet()){
            bow.addEnchantment(entry.getKey(),entry.getValue());
        }
    }
}
