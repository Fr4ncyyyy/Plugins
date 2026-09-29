package com.company;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class OtherListeners implements Listener {

    private ConfigManager config = ConfigManager.getIstance();
    private SSManager ssManager;

    public OtherListeners(SSManager ssManager){
        this.ssManager = ssManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        Player p = e.getPlayer();

        if(ssManager.getSS(p) != null){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player p)) {
            return;
        }

        if (ssManager.getSS(p) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent e){

        if(e.getEntity() instanceof Player p){
            if(ssManager.getSS(p) != null){
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        World world = p.getWorld();

        Statistics statistics = ssManager.getStatistics();
        if(p.hasPermission("screenshare.ss")){

            if(!statistics.containsStaff(p)){
                statistics.addStaff(p);
            }

        }else {
            if(statistics.containsStaff(p)){
                statistics.removeStaff(p);
            }
        }


        if(world.getName().equalsIgnoreCase(config.getWorldName())){
            if(ssManager.getSS(p) == null){
                ssManager.ssVisit(p);
            }
        }
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e){

        Player p = e.getPlayer();
        World world = p.getWorld();

        if(world.getName().equalsIgnoreCase(config.getWorldName())){
            if(ssManager.getSS(p) == null){
                ssManager.ssVisit(p);
            }
        }else {
            if(ssManager.getSS(p) == null){
                p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager())
                        .getMainScoreboard());
            }
        }

    }

}
