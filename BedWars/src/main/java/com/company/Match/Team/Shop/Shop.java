package com.company.Match.Team.Shop;

import com.company.Config.ShopConfig;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class Shop implements Listener {

    protected ArrayList<ShopElement> shopElements;
    protected Inventory inventory;
    private NPC npc;

    public Shop(int size,String title){
        shopElements = new ArrayList<>();
        inventory = Bukkit.createInventory(null,size,title);
    }

    public void initNPC(Location location){

        NPCRegistry registry = CitizensAPI.getNPCRegistry();

        npc = registry.createNPC(
                EntityType.PLAYER,
                "shop"
        );

        npc.spawn(location);

    }

    public void openInventory(Player player){
        player.openInventory(inventory);
    }

    public abstract void initShop(ArrayList<ShopElement> shopElements);

    @EventHandler
    public void onRightClick(NPCRightClickEvent e){

        if(e.getNPC() != npc){
            return;
        }

        Player player = e.getClicker();

        for(ShopElement element : shopElements){
            element.init(inventory,player.getInventory());
        }

        initShop(shopElements);
        openInventory(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() == null){
            return;
        }

        if(e.getInventory() == inventory){

            for(ShopElement shopElement : shopElements){

                if(shopElement.getSlot() == e.getSlot()){
                    shopElement.clicked(inventory);
                }
            }

        }

    }



}
