package com.company.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class Scoreboard implements ScoreboardProvider{

    @Override
    public void update(Player player, String title, List<String> content) {

        org.bukkit.scoreboard.Scoreboard scoreboard =
                Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(
                "bedwars",
                Criteria.DUMMY,
                title
        );

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(int i=0;i<content.size();++i){
            objective.getScore(content.get(i))
                    .setScore(content.size() - i);
        }

        player.setScoreboard(scoreboard);
    }
}
