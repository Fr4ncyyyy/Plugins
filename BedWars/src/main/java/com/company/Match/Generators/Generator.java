package com.company.Match.Generators;

import com.company.BedWars;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class Generator {

    public static final int RADIUS = 3;

    private final NamespacedKey GENERATOR_KEY;

    private final Location location;
    private final Material material;
    protected int time; //1 spawn ogni quanto tempo [in secondi]
    private final int id;
    private final int max;
    private final int amount;

    protected BukkitRunnable bukkitRunnable;

    public Generator(int id,NamespacedKey GENERATOR_KEY,Location location,
                     Material material,int time,int amount,int max){
        this.GENERATOR_KEY = GENERATOR_KEY;
        this.id = id;
        this.location = location;
        this.material = material;
        this.time = time;
        this.amount = amount;
        this.max = max;

        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Generator.this.run();
            }
        };

    }

    public void run(){

        if(nItems() >= Generator.this.max){
            return;
        }

        ItemStack item = new ItemStack(material,amount);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                GENERATOR_KEY,
                PersistentDataType.INTEGER,
                getId()
        );
        item.setItemMeta(meta);

        World world = location.getWorld();
        Location dropLocation = location.clone().add(0.5,1.1,0.5);
        world.dropItem(dropLocation,item);


    }

    public void start(BedWars bw){

        bukkitRunnable.runTaskTimer(
                bw,
                time * 20L,
                time * 20L
        );

    }

    public int getId(){
        return id;
    }

    private int nItems(){

        Collection<Entity> entities = location.getWorld().getNearbyEntities(
                location,
                RADIUS,RADIUS, RADIUS
        );

        int total = 0;
        for(Entity e : entities){

            if(!(e instanceof Item item)){
                continue;
            }

            ItemMeta meta = item.getItemStack().getItemMeta();

            Integer id = meta.getPersistentDataContainer().get(
                    GENERATOR_KEY,
                    PersistentDataType.INTEGER
            );

            if (id != null && id == this.id) {
                total += item.getItemStack().getAmount();
            }

        }

        return total;


    }


}
