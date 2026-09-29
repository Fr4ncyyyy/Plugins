package com.company;

import com.company.Punishments.*;
import com.company.Utils.InvseeCommand;
import com.company.Utils.Teleport;
import com.company.Utils.Vanish.VanishCommand;
import com.company.Utils.Vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public class Staff extends JavaPlugin {

    private VanishManager vanishManager;
    private PunishmentManager punishmentManager;
    @Override
    public void onEnable(){
        System.out.println("Plugin ABILITATO!");

        try{
            ConfigManager.load(this);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Sono stati riscontrati problemi" +
                    " durante il caricamento del file config.yml: \n");
            e.printStackTrace();
            onDisable();
            return;
        }

        punishmentManager = new PunishmentManager();
        vanishManager = new VanishManager(this);
        registerCommands();
        registerEvents();

    }

    private void registerCommands(){
        Objects.requireNonNull(getServer()
                .getPluginCommand("vanish")).setExecutor(new VanishCommand
                (vanishManager));

        Objects.requireNonNull(getServer()
                .getPluginCommand("invsee"))
                .setExecutor(new InvseeCommand());

        Objects.requireNonNull(getServer()
                .getPluginCommand("tp"))
                .setExecutor(new Teleport());

        Objects.requireNonNull(getServer().getPluginCommand("kick"))
                .setExecutor(new KickCommand());

        Objects.requireNonNull(getServer().getPluginCommand("mute"))
                .setExecutor(new MuteCommand(punishmentManager));

        Objects.requireNonNull(getServer().getPluginCommand("ban"))
                .setExecutor(new BanCommand(punishmentManager));

        Objects.requireNonNull(getServer().getPluginCommand("check"))
                .setExecutor(new CheckCommand(punishmentManager));

    }

    private void registerEvents(){

        getServer().getPluginManager().registerEvents(vanishManager,this);
        getServer().getPluginManager().registerEvents(punishmentManager,this);

    }


    @Override
    public void onDisable(){
        System.out.println("Plugin DISABILITATO!");
    }

}
