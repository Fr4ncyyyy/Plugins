package com.company;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkeletonHunter extends Challenge{
    public SkeletonHunter(HashMap<UUID, Integer> players) {
        super("SkeletonHunter","Uccidi più scheletri possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onKillSkeleton(EntityDeathEvent event){

        if(this.isActive){
            Entity entity = event.getEntity();
            if(entity instanceof Skeleton){
                Player killer = ((Skeleton) entity).getKiller();

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
