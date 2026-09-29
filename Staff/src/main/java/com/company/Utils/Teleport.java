package com.company.Utils;

import com.company.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class Teleport implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {

        if(!(sender instanceof Player)){
            Bukkit.getLogger().info("Non sei un giocatore");
            return true;
        }

        if(!sender.hasPermission("staff.teleport")){
            sender.sendMessage("§cNon hai il permesso di eseguire questo comando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("tp")){

            if(args.length == 1){

                if(canTeleport(args[0])){

                    teleportCommand((Player) sender
                            , Objects.requireNonNull
                                    (Bukkit.getPlayer(args[0])));

                    return true;
                }

                sender.sendMessage("§cGiocatore non online!");
                return true;
            }

        }

        return false;
    }


    private boolean canTeleport(String name){
        return Bukkit.getPlayerExact(name) != null;
    }

    private void teleportCommand(Player staff,Player user){

        ConfigManager config = ConfigManager.getInstance();
        staff.teleport(user.getLocation());

        staff.sendMessage(config
                .getSuccessTeleportMessage(user.getName()));

    }

}
