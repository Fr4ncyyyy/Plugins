package com.company.Punishments;

import com.company.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.UUID;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {

        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Non sei un giocatore");
            return true;
        }

        if(!sender.hasPermission("staff.kick")){
            sender.sendMessage("§cNon hai i permessi di eseguire questo comando");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("kick")){

            if(args.length >= 1){

                if(existPlayer(args[0])){

                    String reason = getReason(args);
                    kickExecute((Player) sender, Objects.requireNonNull
                            (Bukkit.getPlayer(args[0])),reason);

                    return true;
                }
            }

        }

        return false;
    }

    private boolean existPlayer(String name){
        return Bukkit.getPlayerExact(name) != null;
    }

    private void kickExecute(Player staff,Player user,String reason){

        ConfigManager config = ConfigManager.getInstance();
        user.kick(Component.text(config
                .getKickScreen(staff.getName(),user.getName(),reason)));
        staff.sendMessage(config.getKickMessage(user.getName()));

    }

    private String getReason(String[] args){

        String s = "";
        for(int i=1;i<args.length;++i){
            s += args[i] + " ";
        }
        return s.trim();
    }
}
