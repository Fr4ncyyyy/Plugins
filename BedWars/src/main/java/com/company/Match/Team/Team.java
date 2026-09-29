package com.company.Match.Team;


import com.company.Color;
import com.company.Match.Generators.Generator;
import com.company.Match.Team.Shop.ShopManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Team {

    private int id;
    private Color color;
    private Location spawn;
    private final Bed bed;

    private final ArrayList<Generator> generators;

    private final ArrayList<Player> players;
    private final boolean isFull;
    private final int maxPlayers;
    private ShopManager shopManager;

    public Team(int id,Color color,Location spawn,Bed bed,ShopManager shopManager){
        load(id,color,spawn,shopManager);
        isFull = false;
        maxPlayers = 1;
        players = new ArrayList<>();
        generators = new ArrayList<>();
        this.bed = bed;

        shopManager.initShops();
    }

    private void load(int id, Color color, Location spawn,ShopManager shopManager){

        this.id = id;
        this.color = color;
        this.spawn = spawn;
        this.shopManager = shopManager;

    }

    public void sendPlayersToBase(){

        for(Player player : players){
            player.teleport(spawn);
        }

    }

    public void equipPlayers(){
        for(Player player : players){
            player.give(new ItemStack(Material.WOODEN_SWORD));
            player.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
            player.getEquipment().setChestplate(new ItemStack(Material.LEATHER_HELMET));
            player.getEquipment().setLeggings(new ItemStack(Material.LEATHER_HELMET));
            player.getEquipment().setBoots(new ItemStack(Material.LEATHER_HELMET));
        }
    }

    public ShopManager getShopManager(){
        return shopManager;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public boolean isFull(){
        return players.size() >= maxPlayers;
    }

    public boolean hasBed(){
        return !bed.isDestroyed();
    }

    public boolean contains(Player player){
        return players.contains(player);
    }

    public Color getColor(){
        return color;
    }
    public Bed getBed(){
        return bed;
    }

    public void destroyBed(){
        bed.setDestroyed(true);
    }
    public boolean isDied(){
        return players.isEmpty();
    }
    public int getSize(){
        return players.size();
    }


}
