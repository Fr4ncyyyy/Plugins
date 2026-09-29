package com.company;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Challenge implements Listener{

    public static final int TIME = 300;

    private String name;
    private String desc;
    protected boolean isActive;
    protected HashMap<UUID, Integer> players;
    private static boolean canStart;

    public Challenge(String name,String desc,HashMap<UUID,Integer> players){
        this.name = name;
        this.desc = desc;
        this.players = players;
        setActive(false);
        canStart = true;
    }

    public void start(Plugin plugin){
        this.setActive(true);

        sendMessageAll("E' iniziata la challenge " + name);

        new BukkitRunnable(){
            @Override
            public void run(){
                stop();
            }
        }.runTaskLater(plugin,TIME * 20L);
    }

    public void stop(){
        this.setActive(false);
        sendMessageAll("E' terminata la challenge " + name + "\n" +
                "Vincitore: " + getTop().getName() + ": " + getVal(getTop()));
        resetAll();
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    public String toString(){
        return "=== " + name.toUpperCase() + " === \n" +
                desc;
    }

    public Player getTop(){
        int max = 0;
        UUID uuid = null;
        for(Map.Entry<UUID, Integer> entry : players.entrySet()){
            int m = max;
            max = Math.max(max,entry.getValue());
            if(m != max || max == 0){
                uuid = entry.getKey();
            }
        }

        assert uuid != null;
        return Bukkit.getPlayer(uuid);

    }

    public void resetAll(){
        for(Map.Entry<UUID,Integer> entry : players.entrySet()){
            reset(entry.getKey());
        }
    }

    public void reset(UUID uuid){
        for(Map.Entry<UUID,Integer> entry : players.entrySet()){
            if(entry.getKey().equals(uuid)){
                entry.setValue(0);
            }
        }
    }

    public boolean isActive(){
        return isActive;
    }

    private void sendMessageAll(String message){
        for(Map.Entry<UUID,Integer> entry : players.entrySet()){
            UUID uuid = entry.getKey();
            Player player = Bukkit.getPlayer(uuid);

            assert player != null;
            player.sendMessage(message);

        }
    }
    public int getVal(Player player) {
        return players.get(player.getUniqueId());
    }

    public int getProgress(Player player){
        for(Map.Entry<UUID,Integer> entry : players.entrySet()){
            if(entry.getKey().equals(player.getUniqueId())){
                return entry.getValue();
            }
        }
        return 0;
    }

    public static boolean getCanStart(){
        return canStart;
    }
    public static void setCanStart(boolean canStart){
        Challenge.canStart = canStart;
    }
    public String getName(){
        return name;
    }

}
