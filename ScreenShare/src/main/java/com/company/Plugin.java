package com.company;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Plugin extends JavaPlugin {

    public void onEnable(){

        new ConfigManager(this);
        SSManager ssManager = new SSManager(this);
        SSCommands sscommands = new SSCommands(this,ssManager);
        ChatManager chatManager = new ChatManager(ssManager);
        Disconnection disconnection = new Disconnection(ssManager);
        CommandEvent commandEvent = new CommandEvent(ssManager);
        OtherListeners otherListeners = new OtherListeners(ssManager);
        getServer().getPluginManager().registerEvents(chatManager,this);
        getServer().getPluginManager().registerEvents(disconnection,this);
        getServer().getPluginManager().registerEvents(commandEvent,this);
        getServer().getPluginManager().registerEvents(otherListeners,this);
        Objects.requireNonNull(getServer().getPluginCommand("ss")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("ssclean")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("sscommands")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("ssspec")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("ssleave")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("sslist")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("sslog")).setExecutor(sscommands);
        Objects.requireNonNull(getServer().getPluginCommand("ssstats")).setExecutor(sscommands);
    }

}
