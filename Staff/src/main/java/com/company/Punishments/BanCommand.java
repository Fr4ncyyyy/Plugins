package com.company.Punishments;

import com.company.Durations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class BanCommand implements CommandExecutor {

    private Durations durations;
    private PunishmentManager punishmentManager;

    public BanCommand(PunishmentManager punishmentManager){
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

        if(!sender.hasPermission("staff.ban")){
            sender.sendMessage("§cNon hai i permessi per eseguire questo comando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("ban")){

            if(args.length >= 2){

                Ban ban;

                if(punishmentManager.hasEverJoined(args[0])){

                    durations = punishmentManager.isValidDuration(args[1]);
                    if(durations != null){
                        String reason = "";
                        for(int i=2;i<args.length;++i){
                            reason += args[i] + " ";
                        }
                        reason = reason.trim();

                        Player user = punishmentManager.getPlayer(args[0]);
                        ban = createBan(
                                ((Player) sender),
                                Objects.requireNonNull(user),
                                reason,
                                args[1]
                        );

                        messageBanned(ban,args[1]);

                    }else {

                        String reason = "";
                        for(int i=1;i<args.length;++i){
                            reason += args[i] + " ";
                        }
                        reason = reason.trim();

                        ban = createBan(
                                ((Player) sender),
                                Objects.requireNonNull(punishmentManager.getPlayer(args[0])),
                                reason,
                                ""
                        );

                        messageBanned(ban,"Permanente");

                    }

                    punishmentManager.add(Objects.requireNonNull(punishmentManager.getPlayer(args[0]))
                            .getUniqueId(),ban);

                }else {
                    sender.sendMessage("§cIl giocatore non è mai entrato nel server");
                }

                return true;

            }

        }


        return false;
    }

    private Ban createBan(Player staff, Player user,String reason,String durationString){

        Long duration = null;
        if(!durationString.isEmpty()){
            duration = punishmentManager.getDurationMillis(this.durations,durationString);
        }

        return new Ban(staff,user,reason,duration);
    }

    private void messageBanned(Ban ban,String duration){
        ban.getStaff().sendMessage(ban.banMessage(duration));
    }


}
