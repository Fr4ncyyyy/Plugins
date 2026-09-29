package com.company.Punishments;

import com.company.ConfigManager;
import com.company.Durations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {

    private Durations durations;
    private PunishmentManager punishmentManager;

    public MuteCommand(PunishmentManager punishmentManager){
        this.punishmentManager = punishmentManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {

        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Non sei un giocatore");
            return true;
        }

        if(!sender.hasPermission("staff.mute")){
            sender.sendMessage("§cNon hai i permessi di usare questo comando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("mute")){

            if(args.length >= 2){

                Mute mute;
                if(punishmentManager.hasEverJoined(args[0])){

                    durations = punishmentManager.isValidDuration(args[1]);
                    if(durations != null){
                        String reason = "";
                        for(int i=2;i<args.length;++i){
                            reason += args[i] + " ";
                        }
                        reason = reason.trim();

                        mute = createMute(
                                ((Player) sender),
                                Objects.requireNonNull(punishmentManager.getPlayer(args[0])),
                                reason,
                                args[1]
                        );

                        messageMuted(mute,args[1]);

                    }else {

                        String reason = "";
                        for(int i=1;i<args.length;++i){
                            reason += args[i] + " ";
                        }
                        reason = reason.trim();

                        Player user = punishmentManager.getPlayer(args[0]);
                        mute = createMute(
                                ((Player) sender),
                                Objects.requireNonNull(user),
                                reason,
                                ""
                        );

                        messageMuted(mute,"Permanente");

                    }

                    punishmentManager.add(Objects.requireNonNull(punishmentManager
                            .getPlayer(args[0])).getUniqueId(),mute);

                }else {
                    sender.sendMessage("§cIl giocatore non è mai entrato nel server");
                }

                return true;

            }

        }



        return false;
    }

    private Mute createMute(Player staff, Player user,String reason,String durationString){

        Long duration = null;
        if(!durationString.isEmpty()){
            duration = punishmentManager.getDurationMillis(this.durations,durationString);
        }

        return new Mute(staff,user,reason,duration);
    }

    private void messageMuted(Mute mute,String duration){
        mute.getStaff().sendMessage(mute.muteMessage(duration));
    }

}
