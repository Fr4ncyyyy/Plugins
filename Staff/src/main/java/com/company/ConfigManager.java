package com.company;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.Objects;

public class ConfigManager {

    private static ConfigManager instance;
    private File file;
    private YamlConfiguration config;

    private ConfigManager(Staff staff) {

        file = new File(staff.getDataFolder(),"config.yml");

        if(!file.exists()){
            file.getParentFile().mkdirs();
            staff.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);

    }

    public static ConfigManager getInstance(){
        if(instance == null){
            throw new IllegalStateException("Config manager non configurato!");
        }
        return instance;
    }

    public static ConfigManager load(Staff staff) throws IOException {
        if(instance == null){
            instance = new ConfigManager(staff);
        }
        return instance;
    }

    public String getSuccessTeleportMessage(String name){
        return Objects.requireNonNull(config.getString("teleport.successMessage"))
                .replace("%player%",name);
    }

    public String getVanishOnMessage(){
        System.out.println("VANISH ON: " + config.getString("vanish.enabled"));
        return config.getString("vanish.enabled");
    }
    public String getVanishOffMessage(){
        System.out.println("VANISH OFF: " + config.getString("vanish.disabled"));
        return config.getString("vanish.disabled");
    }
    public String getKickScreen(String staffName,String playerName,String reason){
        String string = Objects.requireNonNull(config
                        .getString("kick.screen"))
                .replace("%player%",playerName)
                .replace("%staffName%",staffName);

        if(reason.isEmpty()){
            string = string.replace("%reason%","Nessuna motivazione specificata");
        }else {
            string = string.replace("%reason%",reason);
        }

        return string;

    }
    public String getKickMessage(String playerName){
        return Objects.requireNonNull(config
                .getString("kick.message"))
                .replace("%player%",playerName);
    }

    public String getMessageMuted(String userName,String duration){

        return Objects.requireNonNull(config.getString("mute.messageMuted"))
                .replace("%user%",userName)
                .replace("%duration%",duration);

    }

    public String getMessageBanned(String userName,String duration){
        return Objects.requireNonNull(config.getString("ban.messageBanned"))
                .replace("%user%",userName)
                .replace("%duration%",duration);
    }

    public String getPlayerMutedMessage(String staffName,String duration,String reason){

        return Objects.requireNonNull(config.getString("mute.playerMutedMessage"))
                .replace("%staff%",staffName)
                .replace("%duration%",duration)
                .replace("%reason%",reason);

    }

    public String getPlayerBannedMessage(String staffName,String duration,String reason){

        return Objects.requireNonNull(config.getString("ban.playerBannedMessage"))
                .replace("%staff%",staffName)
                .replace("%duration%",duration)
                .replace("%reason%",reason);

    }

    public String getBanToString(String staffName,String userName,String duration,String reason){
        return Objects.requireNonNull(config.getString("ban.toString"))
                .replace("%staff%",staffName)
                .replace("%user%",userName)
                .replace("%duration%",duration)
                .replace("%reason%",reason);
    }
    public String getMuteToString(String staffName,String userName,String duration,String reason){
        return Objects.requireNonNull(config.getString("mute.toString"))
                .replace("%staff%",staffName)
                .replace("%user%",userName)
                .replace("%duration%",duration)
                .replace("%reason%",reason);
    }




}
