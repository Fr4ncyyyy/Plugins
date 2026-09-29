package com.company;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZombieHunter extends Challenge{
    public ZombieHunter(HashMap<UUID, Integer> players) {
        super("ZombieHunter", "Uccidi più zombie possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onKillZombie(EntityDeathEvent event){

        if(this.isActive){
            Entity entity = event.getEntity();
            if(entity instanceof Zombie){
                Player killer = ((Zombie) entity).getKiller();

                for(Map.Entry<UUID,Integer> entry : players.entrySet()){
                    if(killer != null
                            && killer.getUniqueId().equals(entry.getKey())){
                        entry.setValue(entry.getValue() + 1);
                    }
                }
            }
        }

    }

}
