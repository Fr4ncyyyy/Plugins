package com.company;

import com.company.Arenas.ArenaManager;
import com.company.Match.Match;
import com.company.Match.MatchManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class CommandTest implements CommandExecutor {

    private final ArenaManager arenaManager;
    private final MatchManager matchManager;

    public CommandTest(MatchManager matchManager,ArenaManager arenaManager){
        this.matchManager = matchManager;
        this.arenaManager = arenaManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NonNull @NotNull String[] args) {

        if(cmd.getName().equalsIgnoreCase("bw")){
            if(args[0].equalsIgnoreCase("join")){
                if(sender instanceof Player){
                    Match match = matchManager.selectFreeMatch();

                    if(match == null){
                        sender.sendMessage("Nessuna arena disponibile");
                        return true;
                    }

                    matchManager.joinBW(match,(Player) sender);
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("leave")){

                if(matchManager.inAMatch((Player) sender)){

                    Match match = matchManager.getMatch((Player) sender);
                    matchManager.leaveBW(match,(Player) sender);
                    arenaManager.getLobby().toSpawn((Player) sender);
                }

            }
        }

        return false;
    }
}
