package com.company.Listeners;

import com.company.BackUp.BackupType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class OnJoin extends Event{
    public OnJoin() {
        super(BackupType.JOIN);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ItemStack[] inventory = player.getInventory().getContents().clone();
        super.triggered(player.getUniqueId(),inventory);
    }

}
