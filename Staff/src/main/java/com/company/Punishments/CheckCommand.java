package com.company.Punishments;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class CheckCommand implements CommandExecutor {

    private PunishmentManager punishmentManager;

    public CheckCommand(PunishmentManager punishmentManager){
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

        if(!sender.hasPermission("staff.check")){
            sender.sendMessage("§cNon hai abbastanza permessi");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("check")){

            if(args.length == 1){

                if(punishmentManager.hasEverJoined(args[0])){

                    Player user = punishmentManager.getPlayer(args[0]);

                    executeCheck((Player)sender, Objects.requireNonNull
                            (user));
                }else {
                    sender.sendMessage("§cIl player non è mai entrato nel server");
                }

                return true;
            }

        }

        return false;
    }

    private void executeCheck(Player staff,Player user){

        Punishment punishment = punishmentManager.getPunishment(user.getUniqueId());
        if(punishment == null){
            staff.sendMessage("§cQuesto giocatore non ha nessuna sanzione attiva!");
            return;
        }
        staff.sendMessage(punishment.toString());

    }

}
