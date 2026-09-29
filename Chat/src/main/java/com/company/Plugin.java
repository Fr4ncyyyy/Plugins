package com.company;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class Plugin extends JavaPlugin {

    private static Plugin istance;

    @Override
    public void onEnable(){
        istance = this;
        System.out.println("Plugin Abilitato!");

        ChatManager chatManager = new ChatManager(new ConfigManager());
        getServer().getPluginManager().registerEvents(new Chat(chatManager),this);

        }
    @Override
    public void onDisable(){
        System.out.println("Plugin Disabilitato!");
    }

    public static Plugin getIstance(){
        return istance;
    }

}
