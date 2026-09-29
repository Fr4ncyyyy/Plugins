package com.company;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public class ChatManager {

    private ConfigManager configManager;

    public ChatManager(ConfigManager configManager){
        this.configManager = configManager;
    }

    public void sendMessage(Player player,String message){
        String group = getGroup(player);
        if(isValidGroup(group,configManager.getGroups())){
            String mex = configManager.getMessage(group).
                    replace("%player%",player.getName()).replace("%message%",message);
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(mex);
            }
        }
    }

    private String getGroup(Player player){
        LuckPerms lp = LuckPermsProvider.get();
        return lp.getPlayerAdapter(Player.class).getUser(player).getPrimaryGroup();
    }
    private boolean isValidGroup(String group, Set<String> groups){
        return groups.contains(group);
    }

}
