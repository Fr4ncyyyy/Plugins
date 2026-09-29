package com.company.Utils.Vanish;

import com.company.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    private VanishManager vanishManager;

    public VanishCommand(VanishManager vanishManager){
        this.vanishManager = vanishManager;
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

        if(!sender.hasPermission("staff.vanish")){
            sender.sendMessage("§cNon hai il permesso di eseguire questo comaando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("vanish")){

            if(args.length < 2){

                UUID uuid = ((Player) sender).getUniqueId();

                if(args.length == 0){
                    turnVanish(uuid, !vanishManager.isVanished(uuid));
                    return true;
                }

                if (args[0].equalsIgnoreCase("true")
                        || args[0].equalsIgnoreCase("false")) {

                    turnVanish(uuid,Boolean.parseBoolean(args[0].toLowerCase()));

                    return true;
                }

            }

        }



        return false;
    }

    private void turnVanish(UUID uuidStaff, boolean value){

        ConfigManager config = ConfigManager.getInstance();
        Player player = Bukkit.getPlayer(uuidStaff);

        if(value){
            vanishManager.add(uuidStaff);
            player.sendMessage(config.getVanishOnMessage());
        }else {
            vanishManager.remove(uuidStaff);
            player.sendMessage(config.getVanishOffMessage());
        }

    }
}
