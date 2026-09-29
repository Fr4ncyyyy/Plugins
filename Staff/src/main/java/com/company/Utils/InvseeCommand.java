package com.company.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {

        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Non sei un giocatore!");
            return true;
        }

        if(!(sender.hasPermission("staff.invsee"))){
            sender.sendMessage("§cNon hai il permesso di eseguire questo comando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("invsee")){

            if(args.length == 1){

                if(canSeeInventory(args[0])){
                    invseeCommand((Player) sender,
                            Objects.requireNonNull(Bukkit
                                    .getPlayer(args[0])));
                }else {
                    sender.sendMessage("§cIl giocatore non è online nel server");
                }

                return true;

            }

        }



        return false;
    }


    private void invseeCommand(Player staff,Player user){
        staff.openInventory(user.getInventory());
    }

    private boolean canSeeInventory(String name){
        return Bukkit.getPlayerExact(name) != null;
    }

}
