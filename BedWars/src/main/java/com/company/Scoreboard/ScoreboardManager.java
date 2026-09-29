package com.company.Scoreboard;

import com.company.Config.ScoreboardConfig;
import com.company.Match.Match;
import com.company.Match.Team.Team;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private final ScoreboardConfig scoreboardConfig;
    private final Scoreboard scoreboard;

    public ScoreboardManager(
            ScoreboardConfig scoreboardConfig,
            Scoreboard scoreboard
    ){
        this.scoreboardConfig = scoreboardConfig;
        this.scoreboard = scoreboard;
    }

    public void update(Match match, Player player){

        if(match == null){
            scoreboard.update(
                    player,
                    scoreboardConfig.getTitleLobby(),
                    scoreboardConfig.getContentLobby());
            return;
        }

        ScoreboardData scoreboardData = match.getScoreboardData();


        switch(match.getMatchStatus()){
            case WAITING -> {
                List<String> content = fixMessage(scoreboardData,scoreboardConfig.getContentWaiting());
                scoreboard.update(
                        player,
                        scoreboardConfig.getTitleWaiting(),
                        content
                );
            }
            case STARTING -> {
                List<String> content = fixMessage(scoreboardData,scoreboardConfig.getContentStarting());
                scoreboard.update(
                        player,
                        scoreboardConfig.getTitleStarting(),
                        content
                );
            }
            case RUNNING -> {
                List<String> content = gameScoreboard(match.getTeam(player)
                        ,scoreboardData,scoreboardConfig.getContentRunning());
                scoreboard.update(
                    player,
                    scoreboardConfig.getTitleRunning(),
                    content
            );
            }
        }
    }

    private List fixMessage(ScoreboardData scoreboardData,List<String> array){

        ArrayList<String> array1 = new ArrayList<>();
        for(int i=0;i<array.size();++i){
            array1.add(array.get(i).replace("%players%",String.valueOf(scoreboardData.getCurrentPlayers()))
                    .replace("%arena%",scoreboardData.getArenaName())
                    .replace("%time%",String.valueOf(scoreboardData.getTime()))
                    .replace("%maxPlayers%",String.valueOf(scoreboardData.getMaxPlayers())));
        }

        return array1;
    }

    private List gameScoreboard(Team playerTeam,ScoreboardData scoreboardData,List<String> array){
        ArrayList<Team> teams = scoreboardData.getTeams();
        ArrayList<String> array1 = new ArrayList<>();

        for(String line : array){

            for(Team team : teams){

                String value;
                if(team.hasBed()){
                    value = "§a✓";
                }else {
                    if(team.isDied()){
                        value = "§c✗";
                    }else {
                        value = "§a" + team.getSize();
                    }
                }

                if(team == playerTeam){
                    value += " " + scoreboardConfig.getYouTeam();
                }

                switch(team.getColor()){
                    case RED -> line = line.replace("%red%",value);
                    case BLUE -> line = line.replace("%blue%",value);
                    case GREEN -> line = line.replace("%green%",value);
                    case YELLOW -> line = line.replace("%yellow%",value);
                    case ACQUA -> line = line.replace("%acqua%",value);
                    case WHITE -> line = line.replace("%white%",value);
                    case PINK -> line = line.replace("%pink%",value);
                    case GRAY -> line = line.replace("%gray%",value);
                }

            }

            array1.add(line);

        }

        return array1;

    }
}
