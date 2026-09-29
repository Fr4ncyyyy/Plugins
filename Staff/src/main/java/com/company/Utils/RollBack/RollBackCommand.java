package com.company.Utils.RollBack;

import com.company.Durations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;

public class RollBackCommand implements CommandExecutor {

    private BlocksManager blocksManager;

    public RollBackCommand(BlocksManager blocksManager){
        this.blocksManager = blocksManager;
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

        if(!sender.hasPermission("staff.rollback")){
            sender.sendMessage("§cNon hai il permesso di eseguire questo comando!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("co")){

            if(args.length == 3){
                if(args[0].equalsIgnoreCase("rollback")){

                    Durations durations = isValidDuration(args[1]);
                    double radius = isValidRadius(args[2]);

                    if(durations != null && radius != 0.0){

                        long time = getDurationMillis(durations,args[1]);
                        executeRollBack(((Player) sender).getLocation(),time,radius);
                        return true;

                    }

                }
            }

        }



        return false;
    }

    private Durations isValidDuration(String string){

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

    private double isValidRadius(String radius){

        char[] chars = radius.toCharArray();
        for(int i=0;i<chars.length;++i){
            if(!Character.isDigit(chars[i])){
                return 0.0;
            }
        }

        return Double.parseDouble(radius);

    }

    private Long getDurationMillis(Durations durations,String s){
        StringBuilder number = new StringBuilder();
        char[] chars = s.toCharArray();
        for(int i=0;i<chars.length-1;++i){
            number.append(chars[i]);
        }
        return (durations.getValue()
                * Long.parseLong(String.valueOf(number))
                * 1000L);
    }

    private void executeRollBack(Location location, long time, double radius){

        HashMap<Location,ChangeBlock> blocksInRadius
                = blocksManager.getBlocksInRadius(location,radius);

        blocksManager.getBlocksInTime(blocksInRadius,time);



    }


}
