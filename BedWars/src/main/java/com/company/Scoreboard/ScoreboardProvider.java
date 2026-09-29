package com.company.Scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreboardProvider {
    void update(Player player,String title, List<String> content);
}
