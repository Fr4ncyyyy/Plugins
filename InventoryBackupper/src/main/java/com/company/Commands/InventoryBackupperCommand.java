package com.company.Commands;
import com.company.ConfigManager;
import com.company.DataBaseManager;
import com.company.GUI.GUI;
import com.company.GUI.GUIManager;
import com.company.InventoryBackupper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.sql.SQLException;
import java.util.UUID;

public class InventoryBackupperCommand implements CommandExecutor {

    private final GUIManager guiManager;
    public InventoryBackupperCommand(GUIManager guiManager){
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Non sei un giocatore!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("inventorybackupper")){

            if(args.length == 1){

                if(args[0].equalsIgnoreCase("info")){

                    taskInfoCommand((Player) sender);
                    return true;

                }

            }

            if(args.length == 2){

                if(args[0].equalsIgnoreCase("loadinv")){

                    if(!sender.hasPermission("inventorybackupper.loadinv")) {
                        sender.sendMessage("§cNon hai abbastanza permessi per eseguire questo comando!");
                        return true;
                    }

                    String playerName = args[1];

                    try {

                        org.bukkit.OfflinePlayer offlinePlayer = isValidName(playerName);

                        if(offlinePlayer != null){
                            taskHasABackup((Player) sender,offlinePlayer);
                        }else {

                            sender.sendMessage("§cIl giocatore " +
                                    playerName + " non è mai entrato nel server");

                        }
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            }


        }
        return false;
    }

    private int executeCommandInfo(UUID uuid) throws SQLException {

        DataBaseManager dataBase = DataBaseManager.getInstance();
        return dataBase.getNBackups(uuid);

    }

    private OfflinePlayer isValidName(String name) throws SQLException{

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        boolean hasPlayed = offlinePlayer.hasPlayedBefore();

        if(!hasPlayed)return null;

        return offlinePlayer;
    }

    private void taskHasABackup(Player player,OfflinePlayer offlinePlayer){

        InventoryBackupper inventoryBackupper = InventoryBackupper.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(inventoryBackupper,() -> {

            try {
                boolean hasBackup = hasABackup(offlinePlayer.getUniqueId());

                Bukkit.getScheduler().runTask(inventoryBackupper, () -> {

                    if(hasBackup){

                        GUI gui = new GUI(offlinePlayer);
                        guiManager.put(player.getUniqueId(),gui);
                        gui.openInventory(player);

                    }else {

                        player.sendMessage("§cNessun backup trovato per il giocatore " +
                                offlinePlayer.getName());

                    }

                });

            } catch (SQLException e) {
                inventoryBackupper.getLogger().severe("Errore durante la lettura dei backup");
            }

        });


    }

    private boolean hasABackup(UUID uuid) throws SQLException {

        return DataBaseManager
                .getInstance()
                .hasABackup(uuid);

    }

    private void taskInfoCommand(Player player){

        InventoryBackupper inventoryBackupper = InventoryBackupper.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(inventoryBackupper,() -> {

            try {
                int savedBackups = executeCommandInfo(player.getUniqueId());

                Bukkit.getScheduler().runTask(inventoryBackupper, () -> {

                    player.sendMessage(ConfigManager.getInstance()
                            .getSuccessInfoBackUpMessage(savedBackups));

                });

            } catch (SQLException e) {
                inventoryBackupper.getLogger().severe("Errore durante la lettura del backup");
            }
        });

    }



}
