package com.company;
import com.company.Commands.InventoryBackupperCommand;
import com.company.GUI.GUIListener;
import com.company.GUI.GUIManager;
import com.company.Listeners.OnDeath;
import com.company.Listeners.OnJoin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class InventoryBackupper extends JavaPlugin {

    private static InventoryBackupper instance = null;

    private DataBaseManager dataBaseManager;

    private GUIManager guiManager;
    private GUIListener guiListener;

    @Override
    public void onEnable(){

        dataBaseManager = DataBaseManager.init(this);
        instance = this;

        try{
            dataBaseManager.connect();
            dataBaseManager.createTables();
        } catch (SQLException e) {
            getLogger().severe("Errore collegamento con il DataBase");
            e.printStackTrace();
            onDisable();
            return;
        }

        try{
            ConfigManager.load(this);
        } catch (IOException e) {
            getLogger().severe("Errore caricamento file di config");
            e.printStackTrace();
            onDisable();
            return;
        }

        getLogger().info("Plugin ABILITATO!");

        guiManager = new GUIManager();
        guiListener = new GUIListener(guiManager);

        registerEvents();
        registerCommands();


    }


    private void registerEvents(){

        getServer().getPluginManager().registerEvents(new OnDeath(),this);
        getServer().getPluginManager().registerEvents(new OnJoin(),this);
        getServer().getPluginManager().registerEvents(guiListener,this);

    }

    private void registerCommands(){

        Objects.requireNonNull(getServer().getPluginCommand("inventorybackupper"))
                .setExecutor(new InventoryBackupperCommand(guiManager));

    }


    @Override
    public void onDisable(){

        try{
            dataBaseManager.close();
        }catch (SQLException e){
            getLogger().severe("Errore chiusura DataBase");
            e.printStackTrace();
            return;
        }

        getLogger().info("Plugin DISABILITATO!");
    }

    public static InventoryBackupper getInstance(){
        return instance;
    }

}
