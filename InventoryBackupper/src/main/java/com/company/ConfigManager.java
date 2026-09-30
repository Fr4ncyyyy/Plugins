package com.company;

import com.company.BackUp.BackupType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigManager {

    private static ConfigManager instance = null;
    private final InventoryBackupper inventoryBackupper;
    private YamlConfiguration config;

    private ConfigManager(InventoryBackupper inventoryBackupper){
        this.inventoryBackupper = inventoryBackupper;
        initConfig();
    }

    public void initConfig() {

        File file = new File(inventoryBackupper.getDataFolder(), "config.yml");

        if(!inventoryBackupper.getDataFolder().exists()){
            inventoryBackupper.getDataFolder().mkdirs();
        }

        if(!file.exists()){
            inventoryBackupper.saveResource("config.yml",
                    false);
        }

        config = YamlConfiguration.loadConfiguration(file);


    }

    public static void load(InventoryBackupper inventoryBackupper) throws IOException {
        if(instance == null){
            instance = new ConfigManager(inventoryBackupper);
        }
    }

    public boolean getEnabledEvent(BackupType backupType){
        return config.getBoolean("triggers." + backupType.name() + ".enabled");
    }

    public int getMaxBackups(BackupType backupType){
        return config.getInt("triggers." + backupType.name() + ".max_backup");
    }

    public static ConfigManager getInstance(){
        return instance;
    }

    public String getSuccessInfoBackUpMessage(int nInventoriesSaved){

        return Objects.requireNonNull(config.getString("inventoriesSavedMessage"))
                .replace("%number%",String.valueOf(nInventoriesSaved));

    }






}
