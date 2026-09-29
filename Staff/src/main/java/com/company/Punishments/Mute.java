package com.company.Punishments;
import com.company.ConfigManager;
import org.bukkit.entity.Player;

public class Mute extends Punishment{

    private Long duration;

    public Mute(Player staff, Player user, String reason, Long duration) {
        super(staff, user, reason);
        this.duration = duration;
    }

    public boolean isMuted(){
        if(duration == null){
            return true;
        }

        return !(System.currentTimeMillis() - currentTime >= duration);
    }

    public String muteMessage(String duration){

        ConfigManager config = ConfigManager.getInstance();
        return config.getMessageMuted(user.getName(),duration);
    }

    public String mutedMessage() {

        ConfigManager config = ConfigManager.getInstance();

        if (duration == null) {
            return config.getPlayerMutedMessage(staff.getName(), "Permanente", reason);
        } else {
            return config.getPlayerMutedMessage(staff.getName(), getDuration(duration), reason);
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

        return config.getMuteToString(staff.getName(),user.getName(),duration,reason);

    }

}
