package com.company.Punishments;

import com.company.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class Ban extends Punishment{

    private Long duration;

    public Ban(Player staff, Player user, String reason, Long duration) {
        super(staff, user, reason);
        this.duration = duration;
        user.kick(Component.text(ConfigManager.getInstance()
                .getPlayerBannedMessage(staff.getName(),user.getName(),reason)));
    }

    public boolean isBanned(){
            if(duration == null){
                return true;
            }

            return !(System.currentTimeMillis() - currentTime >= duration);
        }

    public String banMessage(String duration){
        ConfigManager config = ConfigManager.getInstance();
        return config.getMessageBanned(user.getName(),duration);
    }

    public String bannedMessage() {

        ConfigManager config = ConfigManager.getInstance();

        if (duration == null) {
            return config.getPlayerBannedMessage(staff.getName(), "Permanente", reason);
        } else {
            return config.getPlayerBannedMessage(staff.getName(), getDuration(duration), reason);
        }
    }

    public String toString(){

        ConfigManager config = ConfigManager.getInstance();

        String duration;

        if(this.duration == null){
            duration = "Permanente";
        }else {
            duration = getDuration(this.duration);
        }

        return config.getBanToString(staff.getName(),user.getName(),duration,reason);
    }


}
