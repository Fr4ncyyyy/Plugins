package com.company.Listeners;

import com.company.*;
import com.company.BackUp.Backup;
import com.company.BackUp.BackupType;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public abstract class Event implements Listener {

    private final boolean enabled;
    private final int maxBackups;
    protected BackupType backupType;

    public Event(BackupType backupType) {

        ConfigManager config = ConfigManager.getInstance();
        this.backupType = backupType;
        enabled = config.getEnabledEvent(backupType);
        maxBackups = config.getMaxBackups(backupType);

    }

    protected void triggered(UUID uuid, ItemStack[] inventory) {

        if (!enabled || maxBackups == 0) return;

        Backup backup = new Backup(uuid, backupType, inventory, System.currentTimeMillis());

        InventoryBackupper inventoryBackupper = InventoryBackupper.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(inventoryBackupper, () -> {

                    DataBaseManager dataBase = DataBaseManager.getInstance();

                    try {
                        if (dataBase.getNBackups(uuid, backupType) >= maxBackups) {
                            dataBase.delLastBackup(uuid, backupType);
                        }

                        DataBaseManager.getInstance().saveBackup(backup,maxBackups);
                    } catch (SQLException | IOException e) {
                        inventoryBackupper.getLogger()
                                .severe("Errore durante il salvataggio del backup");
                    }

                }
        );


    }
}
