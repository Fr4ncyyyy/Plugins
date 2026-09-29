package com.company.Config;

import com.company.BedWars;
import com.company.Match.Team.Shop.Bows.EnchantedBow;
import com.company.Match.Team.Shop.Category;
import com.company.Match.Team.Shop.ItemShop;
import com.company.Match.Team.Shop.Price;
import com.company.Match.Team.Shop.ShopElement;
import com.company.Match.Team.Shop.Tools.AxeTool;
import com.company.Match.Team.Shop.Tools.PickaxeTool;
import com.company.Match.Team.Shop.Tools.Tools;
import com.company.Match.Team.Shop.Tools.Type;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class ShopConfig extends Config{
    @Override
    public void load(BedWars bw) {
        bw.saveResource("shopConfig.yml",false);
        file = new File(bw.getDataFolder(),"shopConfig.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public int getNormalSize(){
        return config.getInt("shop.normals.normal.size");
    }
    public String getNormalTitle(){
        return config.getString("shop.normals.normal.title");
    }

    public List<ShopElement> getItemsNormalShop() {

        String itemsPath = "shop.normals.normal.itemsShop";
        String categoriesPath = "shop.normals.normal.categories";

        ConfigurationSection itemsSection =
                config.getConfigurationSection(itemsPath);

        List<ShopElement> shopElements = new ArrayList<>();

        if (itemsSection == null) {
            return shopElements;
        }

        Map<String, ItemShop> itemShops = new HashMap<>();

        for (String id : itemsSection.getKeys(false)) {

            String path = itemsPath + "." + id;

            ItemShop itemShop = null;

            if (id.equalsIgnoreCase("pickaxe")){

                Map<Type, Price> prices = getToolPrices(path);

                itemShop = new PickaxeTool(
                        getToolItemStack(id),
                        config.getInt(path + ".slot"),
                        getAmount(id),
                        prices
                );

            } else if(id.equalsIgnoreCase("axe")){

                Map<Type, Price> prices = getToolPrices(path);

                itemShop = new AxeTool(
                        getToolItemStack(id),
                        config.getInt(path + ".slot"),
                        getAmount(id),
                        prices
                );

            }else if(id.equalsIgnoreCase("enchanted_bows")){

                ConfigurationSection bowSection = config.getConfigurationSection(path);
                for(String bowID : bowSection.getKeys(false)){

                    itemShop = new EnchantedBow(
                            new ItemStack(Material.BOW),
                            getBowPrice(bowID),
                            config.getInt(path + "." + bowID + ".slot"),
                            1,
                            getBowEnchants(bowID)
                    );

                    itemShops.put(id + "." + bowID,itemShop);
                }

            }else {

                itemShop = new ItemShop(
                        getItemStack(itemsPath + ".", id),
                        getPrice(id),
                        config.contains(path + ".slot")
                                ? config.getInt(path + ".slot")
                                : -1,
                        getAmount(id)
                );
            }

            itemShops.put(id, itemShop);

            if (config.contains(path + ".slot")) {
                shopElements.add(itemShop);
            }
        }

        ConfigurationSection categoriesSection =
                config.getConfigurationSection(categoriesPath);

        if (categoriesSection != null) {

            for (String categoryId : categoriesSection.getKeys(false)) {

                String path = categoriesPath + "." + categoryId;

                HashMap<ItemShop, Integer> categoryItems = new HashMap<>();

                ConfigurationSection items =
                        config.getConfigurationSection(path + ".items");

                if (items != null) {

                    for (String itemId : items.getKeys(false)) {

                        ItemShop itemShop = itemShops.get(itemId);

                        if (itemShop != null) {
                            categoryItems.put(
                                    itemShop,
                                    getSlotItemCategory(categoryId, itemId)
                            );
                        }
                    }
                }

                Category category = new Category(
                        config.getInt(path + ".slot"),
                        getItemStack(categoriesPath + ".", categoryId),
                        categoryItems
                );

                shopElements.add(category);
            }
        }

        return shopElements;
    }

    private Map<Type, Price> getToolPrices(String path) {

        Map<Type, Price> prices = new HashMap<>();

        ConfigurationSection typeSection =
                config.getConfigurationSection(path + ".type");

        if (typeSection == null) {
            return prices;
        }

        for (String typeName : typeSection.getKeys(false)) {

            String pricePath =
                    path + ".type." + typeName + ".price";

            Material material = Material.valueOf(
                    config.getString(pricePath + ".material")
            );

            int amount = config.getInt(
                    pricePath + ".amount"
            );

            Type type = Type.valueOf(typeName.toUpperCase());

            prices.put(
                    type,
                    new Price(material, amount)
            );
        }

        return prices;
    }

    private ItemStack getToolItemStack(String id) {

        if (id.equalsIgnoreCase("pickaxe")) {
            return new ItemStack(Material.WOODEN_PICKAXE);
        }

        if (id.equalsIgnoreCase("axe")) {
            return new ItemStack(Material.WOODEN_AXE);
        }

        throw new IllegalArgumentException(
                "Tool non riconosciuto: " + id
        );
    }

    private Price getBowPrice(String bowID){

        String path = "shop.normals.normal.itemsShop.enchanted_bows." + bowID + ".price";

        return new Price(Material.valueOf(config.getString(path
                + ".material")),config.getInt(path + ".amount"));

    }

    private HashMap<Enchantment,Integer> getBowEnchants(String bowID){

        String path = "shop.normals.normal.itemsShop.enchanted_bows." + bowID + ".effects";
        ConfigurationSection bowSection = config.getConfigurationSection(path);

        HashMap<Enchantment,Integer> enchantments = new HashMap<>();

        for(String string : bowSection.getKeys(false)){
            enchantments.put(Enchantment.getByName(string),
                    config.getInt(path + "." + string + ".level"));
        }
        return enchantments;
    }

    private boolean containsSlot(String path){return config.contains(path);}

    private ItemStack getItemStack(String path,String string){
         String nameMaterial = config.getString(path + string + ".material");
         Material material = Material.valueOf(nameMaterial);
         return new ItemStack(material);
    }
    private Price getPrice(String string){

        Material material = Material.valueOf(config
                .getString("shop.normals.normal.itemsShop." + string + ".price.material"));
        int amount = config.getInt("shop.normals.normal.itemsShop." + string + ".price.amount");

        return new Price(material,amount);

    }
    private int getSlotItemCategory(String nameCategory,String nameItem){
        return config.getInt("shop.normals.normal.categories."
                + nameCategory + ".items." + nameItem + ".slot");
    }

    private int getAmount(String string){
        return config.getInt("shop.normals.normal.itemsShop." + string + ".amount");
    }
}
