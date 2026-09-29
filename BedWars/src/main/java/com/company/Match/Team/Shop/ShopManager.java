package com.company.Match.Team.Shop;

import com.company.BedWars;
import com.company.Config.ShopConfig;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ShopManager {

    private ShopConfig shopConfig;
    private NormalShop normalShop;
    private UpgradeShop upgradeShop;

    public ShopManager(ShopConfig shopConfig){
        this.shopConfig = shopConfig;
        normalShop = new NormalShop(shopConfig.getNormalSize(),shopConfig.getNormalTitle());
    }

    public void initNPCs(Location locationNormalShop,Location locationUpgradeShop){
        normalShop.initNPC(locationNormalShop);
    }

    public void initShops(){
        normalShop.initShop((ArrayList<ShopElement>) shopConfig.getItemsNormalShop());
    }

    public void registerNPCEvent(BedWars bw){

        bw.getServer().getPluginManager().registerEvents(normalShop,bw);
    }

}
