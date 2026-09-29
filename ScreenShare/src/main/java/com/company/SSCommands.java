package com.company;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class SSCommands implements CommandExecutor {

    private ConfigManager config = ConfigManager.getIstance();
    private Plugin plugin;
    private SSManager ssManager;

    public SSCommands(Plugin plugin,SSManager ssManager){
        this.ssManager = ssManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {
        if(!(sender instanceof Player)){
            System.out.println("Non sei un player!");
            return true;
        }

        if(sender.hasPermission("screenshare.ss")){

            if(cmd.getName().equalsIgnoreCase("ss")){
                if(args.length == 1){
                    Player staff = (Player) sender;
                    Player user = Bukkit.getPlayer(args[0]);


                    if(ssManager.isValidSS(staff,args[0])){
                        SS ss = new SS(staff,user);
                        ss.start(plugin,ssManager.getWorld());
                        ssManager.addSS(ss);
                        sender.sendMessage(config.getSuccessSSMessage().replace("%player%",args[0]));
                    }

                    return true;

                }
            }
            if(cmd.getName().equalsIgnoreCase("ssclean")){
                if(args.length == 0){
                    SS ss = ssManager.getSS((Player) sender);
                    if(ss != null && ss.getStaff().equals(sender)){
                        String userName = ss.getUser().getName();
                        ssManager.getSS((Player) sender).stop(ssManager.getStatistics(),
                                Result.CLEAN,ssManager.getLobby());
                        ssManager.removeSS(ss);
                        ss = null;
                        sender.sendMessage(config.getCleanSSMessage().replace("%player%",userName));
                    }else {
                        sender.sendMessage(config.getNotInSS());
                    }
                    return true;
                }
            }
            if(cmd.getName().equalsIgnoreCase("sscommands")){
                if(args.length == 0){
                    SS ss = ssManager.getSS((Player) sender);
                    if(ss != null && ss.getStaff().equals(sender)){
                        ss.createButtons();
                    }
                    return true;
                }
            }
            if(cmd.getName().equalsIgnoreCase("ssspec")){
                if(args.length == 1){
                    Player sser = Bukkit.getPlayer(args[0]);
                    if(ssManager.isValidSpec(sser)){
                        SS ss = ssManager.getSS(sser);
                        ss.addSpectator(ssManager.getWorld(),(Player) sender);
                        sender.sendMessage(config.getStartSpecMessage()
                                .replace("%staff%",ss.getStaff().getName()));
                    }else {
                        sender.sendMessage(config.getErrorNotValidSpec());
                    }
                    return true;
                }
            }
            if(cmd.getName().equalsIgnoreCase("ssleave")){
                if(args.length == 0){
                    SS ss = ssManager.getSS((Player) sender);
                    if(ss != null && ss.getSpectators().contains((Player) sender)){
                        ss.removeSpectator(ssManager.getLobby(),(Player) sender);
                        sender.sendMessage(config.getLeaveSpecMessage()
                                .replace("%staff%",ss.getStaff().getName()));
                    }else {
                        sender.sendMessage(config.getErrorNotSpectatorMessage());
                    }
                    return true;
                }
            }
        }

        if(sender.hasPermission("screenshare.admin")){
            Statistics statistics = ssManager.getStatistics();
            if(cmd.getName().equalsIgnoreCase("sslist")){
                if(args.length == 0){
                    String message = statistics.ssList();

                    if(!message.isEmpty()){
                        sender.sendMessage(config.getSSListTitle());
                        sender.sendMessage(message);
                    }
                    if(message.isEmpty()){
                        sender.sendMessage(config.getSSListEmpty());
                    }

                    return true;
                }
            }

            if(cmd.getName().equalsIgnoreCase("sslog")){
                if(args.length == 1){

                    Player player = Bukkit.getPlayer(args[0]);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

                    if(player != null || offlinePlayer.hasPlayedBefore()){

                        if(statistics.hasEverSSed(offlinePlayer.getUniqueId())){

                            String message = statistics.ssLogs(offlinePlayer.getUniqueId());
                            sender.sendMessage(config.getSSLogTitle()
                                    .replace("%player%",args[0]));
                            sender.sendMessage(message);
                        }else {
                            sender.sendMessage(config.getSSLogEmpty()
                                    .replace("%player%",args[0]));
                        }

                    }else {
                        sender.sendMessage(config.getErrorNeverPlayedMessage().replace("%player%",args[0]));
                    }
                    return true;

                }
            }

            if(cmd.getName().equalsIgnoreCase("ssstats")){

                if(args.length == 1){

                    Player player = Bukkit.getPlayer(args[0]);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

                    if(((player != null || offlinePlayer.hasPlayedBefore())
                            && statistics.isStaff(offlinePlayer.getUniqueId()))){

                        String message = statistics.stats(args[0]);
                        sender.sendMessage(message);


                    }else {
                        sender.sendMessage(config.getErrorNotStaffMessage().replace("%player%",args[0]));
                    }

                    return true;
                }

            }



        }




        return false;
    }
}
