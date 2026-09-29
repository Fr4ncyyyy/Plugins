package com.company;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private ScoreboardManager manager;
    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private Objective objective;
    private String title;
    private List<String> lines;
    public Scoreboard(ArrayList<Player> players,String title, List<String> lines){

        this.title = title;
        this.lines = lines;
        manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();

        init();

        for(Player p : players){
            setScoreboard(p);
        }


    }

    public void init(){
        objective = scoreboard.registerNewObjective(
                "ss",
                Criteria.DUMMY,
                title
        );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setLines(Player staff,Player user,String time){

        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        int score = lines.size();
        if(!time.isEmpty()){
            for (String line : lines) {
                objective.getScore(line
                        .replace("%staff%", staff.getName())
                        .replace("%user%", user.getName())
                        .replace("%time%",time)).setScore(score--);
            }
            return;
        }

        if(user != null || staff != null){

            for (String line : lines) {
                objective.getScore(line
                        .replace("%player%", staff.getName())
                ).setScore(score--);
            }

        }

    }

    public void setScoreboard(Player player){
        player.setScoreboard(scoreboard);
    }

}
