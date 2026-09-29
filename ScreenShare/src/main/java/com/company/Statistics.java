package com.company;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class Statistics {

    private HashSet<Player> staffList;
    private HashMap<SS,Result> ssHistory;

    public Statistics(){
        ssHistory = new HashMap<>();
        staffList = new HashSet<>();
    }

    public void addStaff(Player player){
        staffList.add(player);
    }
    public void removeStaff(Player player){
        staffList.remove(player);
    }

    public void save(SS ss,Result result){
        ssHistory.put(ss,result);
    }

    public String ssList(){

        String s = "";

        for(Map.Entry<SS,Result> entry : ssHistory.entrySet()){

            SS ss = entry.getKey();

            if(ss != null){
                s += ss.toString();
            }

        }

        return s;

    }
    public String stats(String name){

        ConfigManager config = ConfigManager.getIstance();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        UUID uuid = offlinePlayer.getUniqueId();

        int count = getSSBans(uuid);
        int count1 = getCleans(uuid);

        return config.getStats()
                .replace("%SSBans%",String.valueOf(count))
                .replace("%SSCleans%",String.valueOf(count1))
                        .replace("%player%",name);

    }

    public boolean containsStaff(Player player){
        return staffList.contains(player);
    }

    public boolean isStaff(UUID uuid){

        for(Player player : staffList){
            if(player.getUniqueId().equals(uuid)){
                return true;
            }
        }
        return false;

    }


    public boolean hasEverSSed(UUID uuid){

        for(Map.Entry<SS,Result> entry : ssHistory.entrySet()){
            UUID uuidUser = entry.getKey().getUser().getUniqueId();
            if(uuidUser.equals(uuid)){
                return true;
            }
        }
        return false;

    }

    public String ssLogs(UUID uuid){

        String s = "";
        for(Map.Entry<SS,Result> entry : ssHistory.entrySet()){

            SS ss = entry.getKey();
            UUID uuidUser = ss.getUser().getUniqueId();

            if(uuidUser.equals(uuid)){
                s += ss.toString();
            }

        }
        return s;
    }

    private int getSSBans(UUID uuid){

        int count = 0;
        for(Map.Entry<SS,Result> entry : ssHistory.entrySet()){
            if(entry.getValue() == Result.BANNED && entry.getKey().getStaff()
                    .getUniqueId().equals(uuid)){
                count++;
            }
        }

        return count;

    }

    private int getCleans(UUID uuid){

        int count = 0;
        for(Map.Entry<SS,Result> entry : ssHistory.entrySet()){
            if(entry.getValue() == Result.CLEAN && entry.getKey().getStaff()
                    .getUniqueId().equals(uuid)){
                count++;
            }
        }

        return count;

    }



}
