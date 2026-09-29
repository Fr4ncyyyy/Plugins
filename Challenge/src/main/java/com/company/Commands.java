package com.company;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;

public class Commands implements CommandExecutor {

    private Plugin plugin;
    private ArrayList<Challenge> challenges;
    private Challenge challenge;

    public Commands(Plugin plugin,ArrayList<Challenge> challenges){
        this.plugin = plugin;
        this.challenges = challenges;
        challenge = null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NonNull @NotNull String[] args) {
        if(sender instanceof Player){
            if(challenge == null)return false;
            if(cmd.getName().equalsIgnoreCase("challenge")){
                if(args.length > 2){
                    return false;
                }
                if(args.length == 0){
                    sender.sendMessage(challenge.toString());
                    return true;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("progress")){
                        sender.sendMessage("Il tuo progresso è di " + challenge.getProgress((Player)sender));
                        return true;
                    } else if (args[0].equalsIgnoreCase("top")) {
                        sender.sendMessage("Sta vincendo: \n" + challenge.getTop().getName() +
                                " con " + challenge.getVal(challenge.getTop()));
                        return true;
                    } else if (sender.hasPermission("challenge.admin")) {
                        if(args[0].equalsIgnoreCase("start")){
                            challenge.stop();
                            Challenge.setCanStart(true);
                            return true;
                        } else if (args[0].equalsIgnoreCase("stop")) {
                            Challenge.setCanStart(false);
                            challenge.stop();
                            return true;
                        }else {
                            return false;
                        }
                    }
                    return false;
                } else {
                    if(sender.hasPermission("challenge.admin")){

                        if(args[0].equalsIgnoreCase("set")){
                            for(Challenge c : challenges){
                                if(c.getName().equalsIgnoreCase(args[1])){
                                    Challenge.setCanStart(false);
                                    challenge.stop();
                                    c.start(plugin);
                                    setChallenge(c);
                                    Challenge.setCanStart(true);
                                    return true;
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("reset")) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player.getName().equalsIgnoreCase(args[1])) {
                                    challenge.reset(player.getUniqueId());
                                    return true;
                                }
                            }
                        }else {
                            return false;
                        }

                    }
                }
            }
        }
        return false;
    }

    public void setChallenge(Challenge challenge){
        this.challenge = challenge;
    }
}
