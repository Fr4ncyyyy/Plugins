package com.company.Listeners;

import com.company.Match.Match;
import com.company.Match.MatchManager;
import com.company.Match.Team.Team;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;

public class Blocks implements Listener {

    private final MatchManager matchManager;
    private HashSet<Block> placedBlocks;

    public Blocks(MatchManager matchManager){
        this.matchManager = matchManager;
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){

        Player p = e.getPlayer();

        Match match = matchManager.getPlayer(p);

        if(match != null && (match.getMatchStatus() == Match.MatchStatus.WAITING
                || match.getMatchStatus() == Match.MatchStatus.STARTING)){
            e.setCancelled(true);
        }

        if(match != null && (match.getMatchStatus() == Match.MatchStatus.RUNNING)){
            placedBlocks = match.getPlacedBlocks();
            placedBlocks.add(e.getBlock());
        }


    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){

        Player p = e.getPlayer();

        Match match = matchManager.getPlayer(p);

        if(match == null
                || match.getMatchStatus() == Match.MatchStatus.WAITING
                || match.getMatchStatus() == Match.MatchStatus.STARTING){
            e.setCancelled(true);
        }else {
            if(match.getMatchStatus() == Match.MatchStatus.RUNNING){
                if (e.getBlock().getType() == Material.RED_BED) {

                    Team playerTeam = match.getTeam(p);
                    Team bedTeam = match.getTeam(e.getBlock().getLocation());

                    if (playerTeam == null) {
                        e.setCancelled(true);
                        return;
                    }

                    if (bedTeam == null) {
                        e.setCancelled(true);
                        return;
                    }

                    if (playerTeam == bedTeam) {
                        e.setCancelled(true);
                        return;
                    }

                    match.destroyBed(e.getBlock().getLocation());
                    e.setDropItems(false);
                }else {
                    if(!match.getPlacedBlocks().contains(e.getBlock())){
                        e.setCancelled(true);
                    }
                }

            }
        }

    }

}
