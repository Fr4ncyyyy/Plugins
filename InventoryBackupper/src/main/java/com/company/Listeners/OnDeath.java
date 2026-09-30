package com.company.Listeners;

import com.company.BackUp.BackupType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class OnDeath extends Event {

    public OnDeath() {
        super(BackupType.DEATH);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        ItemStack[] inventory = player.getInventory().getContents().clone();
        super.triggered(player.getUniqueId(),inventory);
    }

}
