package com.company.GUI;

import com.company.BackUp.Backup;
import com.company.BackUp.BackupType;
import com.company.ConfigManager;
import com.company.DataBaseManager;
import com.company.InventoryBackupper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class GUI {

    public static final int GUI_SIZE = 36;
    private final HashMap<Integer, Backup> backupsPlayer;
    public enum StatusGUI{HOME,INVENTORIES,INVENTORY}

    private StatusGUI statusGUI;
    private Inventory inventory;
    private final OfflinePlayer user;

    public GUI(OfflinePlayer user){

        inventory = Bukkit.createInventory(null, GUI_SIZE,
                Component.text("Menu' principale"));
        this.user = user;
        backupsPlayer = new HashMap<>();
        putItems();
        statusGUI = StatusGUI.HOME;

    }

    public void openInventory(Player staff){
        staff.openInventory(inventory);
    }

    public void putItems(){

        ConfigManager config = ConfigManager.getInstance();

        for (BackupType type : BackupType.values()) {

            if (!config.getEnabledEvent(type)) {
                continue;
            }

            ItemStack itemStack = type.getItem().getItemStack();
            int slot = type.getItem().getSlot();

            inventory.setItem(slot,itemStack);

        }

    }

    public void onClick(Player staff,BackupType backupType) {

        backupsPlayer.clear();
        setTitle(staff,"Backups " + backupType.name());

        InventoryBackupper inventoryBackupper = InventoryBackupper.getInstance();
        DataBaseManager dataBase = DataBaseManager.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(inventoryBackupper,() -> {
            try {
                List<Backup> backups = dataBase.getBackups(user.getUniqueId());

                Bukkit.getScheduler().runTask(inventoryBackupper,() -> {

                    int slot = 0;
                    for (Backup backup : backups) {

                        if (backup.getBackupType() == backupType) {

                            inventory.setItem(slot, backupType.getItem()
                                    .getItemStack());

                            putMap(slot, backup);
                            ++slot;

                        }
                    }

                });

            } catch (SQLException | IOException e) {
                inventoryBackupper.getLogger().severe("Errore durante il" +
                        " caricamento del backup: " + e.getMessage());
            }
        });

    }


    public void putMap(int slot,Backup backup){
        backupsPlayer.put(slot,backup);
    }

    public boolean onClickBackUp(Player staff,int slot){

        Backup backup = backupsPlayer.get(slot);

        if(backup != null){
            viewInventory(staff,backup);
            return true;
        }
        return false;
    }


    public void viewInventory(Player staff,Backup backup){

        setTitle(staff,"Backup specifico " + backup.getBackupType().name());

        ItemStack[] contents = backup.getContents();
        int slot = 0;
        for(ItemStack itemStack : contents){

            if (slot >= inventory.getSize()) {
                break;
            }

            inventory.setItem(slot,itemStack);
            ++slot;
        }

    }

    public void handleClick(Player staff,int slot) {

        switch(getStatusGUI()){

            case HOME -> {
                for(BackupType backupType : BackupType.values()){
                    if(backupType.getItem().getSlot() == slot){
                        onClick(staff,backupType);
                        setStatusGUI(GUI.StatusGUI.INVENTORIES);
                        break;
                    }
                }
            }
            case INVENTORIES -> {

                boolean value = onClickBackUp(staff,slot);
                if(value){
                    setStatusGUI(GUI.StatusGUI.INVENTORY);
                }
            }

        }

    }


    public Inventory getInventory(){
        return inventory;
    }

    private void setStatusGUI(StatusGUI statusGUI){
        this.statusGUI = statusGUI;
    }

    public StatusGUI getStatusGUI(){
        return statusGUI;
    }

    private void setTitle(Player staff,String title){

        inventory = Bukkit.createInventory(null,GUI_SIZE,
                Component.text(title));

        staff.openInventory(inventory);

    }



}
