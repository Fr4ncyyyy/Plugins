package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PvPMaster extends Challenge{
    public PvPMaster(HashMap<UUID, Integer> players) {
        super("PvPMaster","Sconfiggi più giocatori possibile entro il tempo limite.", players);
    }

    @EventHandler
    public void onKillPlayer(EntityDeathEvent event){

        if(isActive()){

            if(event.getEntity() instanceof Player){
                Player died = ((Player)event.getEntity());
                Player killer = died.getKiller();

                for(Map.Entry<UUID,Integer> entry : players.entrySet()){
                    if(killer != null
                            && killer.getUniqueId().equals(entry.getKey())
                            && !killer.equals(died)){
                        entry.setValue(entry.getValue() + 1);
                    }
                }

            }

        }

    }

}
