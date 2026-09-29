package com.company;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.List;

public class Lobby {

    private final World world;
    private final Location spawn;

    public Lobby(World world, Location spawn) {
        this.world = world;
        this.spawn = spawn;
        setSettings();
    }

    public void update(Player player, String title, List<String> content) {
        org.bukkit.scoreboard.Scoreboard scoreboard =
                Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective(
                "bedwars",
                Criteria.DUMMY,
                title
        );

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 0; i < content.size(); ++i) {
            objective.getScore(content.get(i).replace("%player%",player.getName())
                            .replace("%lobby%",world.getName()))
                    .setScore(content.size() - i);
        }

        player.setScoreboard(scoreboard);
    }

    public void toSpawn(Player player) {
        player.teleport(spawn);
    }
    public String getName(){
        return world.getName();
    }

    public World getWorld(){return world;}

    private void setSettings(){

        world.setTime(1000);
        world.setThundering(false);
        world.setStorm(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRule(GameRules.ADVANCE_TIME, false);
        world.setGameRule(GameRules.SPAWN_MOBS,false);
        world.setGameRule(GameRules.ADVANCE_WEATHER,false);

    }

}


