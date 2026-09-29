package com.company.Punishments;

import com.company.Durations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.*;

public class PunishmentManager implements Listener {

    private HashMap<UUID,Punishment> punishments;
    private Set<Player> everJoinedPlayer;

    public PunishmentManager(){
        punishments = new HashMap<>();
        everJoinedPlayer = new HashSet<>();
    }

    public void add(UUID userUUID,Punishment punishment) {
        punishments.put(userUUID, punishment);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {

        UUID uuid = event.getPlayer().getUniqueId();

        if (punishments.containsKey(uuid)) {

            if (punishments.get(uuid) instanceof Ban ban) {

                if (ban.isBanned()) {
                    event.disallow(
                            PlayerLoginEvent.Result.KICK_BANNED,
                            LegacyComponentSerializer.legacySection()
                                    .deserialize(ban.bannedMessage())
                    );
                } else {
                    punishments.remove(uuid);
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){

        UUID uuid = event.getPlayer().getUniqueId();

        if(punishments.containsKey(uuid)){

            if(punishments.get(uuid) instanceof Mute mute){
                if(mute.isMuted()){
                    event.getPlayer().sendMessage(mute.mutedMessage());
                    event.setCancelled(true);
                }else {
                    punishments.remove(uuid);
                }

            }

        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        everJoinedPlayer.add(event.getPlayer());
    }

    public Durations isValidDuration(String string){

        if(string.length() < 2){
            return null;
        }

            char[] chars = string.toCharArray();
            for(int i=0;i<chars.length-1;++i){
                if(!Character.isDigit(chars[i])){
                    return null;
                }
            }
            if(!Character.isDigit(chars[chars.length-1])){
                String s = String.valueOf(chars[chars.length-1]);

                for(Durations duration : Durations.values()){
                    if(duration.validChar(s)){
                        return duration;
                    }
                }
            }
            return null;
        }

    public Long getDurationMillis(Durations durations,String s){
        StringBuilder number = new StringBuilder();
        char[] chars = s.toCharArray();
        for(int i=0;i<chars.length-1;++i){
            number.append(chars[i]);
        }
        return (durations.getValue()
                * Long.parseLong(String.valueOf(number))
                * 1000L);
    }

    public Punishment getPunishment(UUID uuid){
        return punishments.get(uuid);
    }

    public boolean hasEverJoined(String name){
        for(Player player : everJoinedPlayer){
            if(player.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(String name){
        if(hasEverJoined(name)){

            for(Player player : everJoinedPlayer){
                if(player.getName().equalsIgnoreCase(name)){
                    return player;
                }
            }
        }

        return null;
    }
}
