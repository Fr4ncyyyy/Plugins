package com.company.BackUp;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Backup {

    private final UUID uuid;
    private final BackupType backupType;
    private final Long time;
    private final ItemStack[] contents;

    public Backup(UUID uuid,
                  BackupType backupType,
                  ItemStack[] contents,
                  Long time
                ){

        this.uuid = uuid;
        this.backupType = backupType;
        this.contents = contents;
        this.time = time;

    }

    public UUID getUuid(){
        return uuid;
    }
    public BackupType getBackupType(){
        return backupType;
    }
    public ItemStack[] getContents(){
        return contents;
    }
    public Long getTime(){
        return time;
    }

}
