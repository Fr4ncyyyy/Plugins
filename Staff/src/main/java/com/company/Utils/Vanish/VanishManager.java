package com.company.Utils.Vanish;

import com.company.Staff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class VanishManager implements Listener {

    private Staff staff;
    private Set<UUID> vanishPlayers;

    public VanishManager(Staff staff){
        this.staff = staff;
        vanishPlayers = new HashSet<>();
    }
    public void add(UUID uuid){
        vanishPlayers.add(uuid);
        updateHiddenPlayers();
    }
    public void remove(UUID uuid){

        Player vanished = Bukkit.getPlayer(uuid);
        vanishPlayers.remove(uuid);

        if (vanished != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(staff, vanished);
            }
        }

        updateHiddenPlayers();
    }
    public boolean isVanished(UUID uuid){
        return vanishPlayers.contains(uuid);
    }

    private void updateHiddenPlayers(){

        for (Player player : Bukkit.getOnlinePlayers()) {

            for (UUID vanishUUID : vanishPlayers) {

                Player vanished = Bukkit.getPlayer(vanishUUID);

                if (vanished == null) {
                    continue;
                }

                if (vanishPlayers.contains(player.getUniqueId())) {
                    continue;
                }

                if (!player.getUniqueId().equals(vanishUUID)) {
                    player.hidePlayer(staff, vanished);
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        updateHiddenPlayers();
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();

        if(vanishPlayers.contains(uuid)){
            remove(uuid);
        }
    }

}
