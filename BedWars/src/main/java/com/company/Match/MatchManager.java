package com.company.Match;

import com.company.Arenas.Arena;
import com.company.Arenas.ArenaManager;
import com.company.BedWars;
import com.company.Config.GeneratorConfig;
import com.company.Config.MatchConfig;
import com.company.Config.ScoreboardConfig;
import com.company.Match.Generators.GeneratorManager;
import com.company.Match.Team.Team;
import com.company.Scoreboard.Scoreboard;
import com.company.Scoreboard.ScoreboardManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class MatchManager {

    private final BedWars bw;
    private final MatchConfig matchConfig;
    private final ArenaManager arenaManager;
    private final ArrayList<Match> matches;

    public MatchManager(BedWars bw,MatchConfig matchConfig, ArenaManager arenaManager){
        this.bw = bw;
        this.matchConfig = matchConfig;
        this.arenaManager = arenaManager;
        this.matches = new ArrayList<>();
    }

    public void loadMatches(ScoreboardConfig scoreboardConfig, GeneratorConfig generatorConfig, NamespacedKey GENERATOR_KEY){

        orderMatches();

        ArrayList<Arena> arenas = arenaManager.getArenas();
        int minPlayersToStart = matchConfig.getMinPlayersToStart();
        int maxPlayersToStart = matchConfig.getMaxPlayersToStart();

        for(Arena arena : arenas){
            GeneratorManager generatorManager = new GeneratorManager(generatorConfig,GENERATOR_KEY);
            ScoreboardManager scoreboardManager = new ScoreboardManager(
                    scoreboardConfig,
                    new Scoreboard());

            matches.add(new Match(
                    matchConfig,
                    arena,
                    minPlayersToStart,
                    maxPlayersToStart,
                    generatorManager,
                    scoreboardManager
                    )
            );
        }

        for(Match match : matches){
            match.loadGenerators();
        }


    }

    public void startBW(Match match){
        match.start(bw);
    }

    public void joinBW(Match match, Player player){

        match.joinBW(player);
        boolean shouldStart = match.checkTime();
        startCountDown(match, shouldStart);

    }

    public void leaveBW(Match match, Player player){
        if(match.getMatchStatus() == Match.MatchStatus.WAITING
                || match.getMatchStatus() == Match.MatchStatus.STARTING){
            boolean shouldStop = match.checkTime();
            startCountDown(match,shouldStop);

        }
        match.leaveBW(player);
    }

    public void startCountDown(Match match,boolean value){

        if(value){
            if(match.getStartCountDown() == null){
                StartCountDown startCountDown
                        = new StartCountDown(this,match,matchConfig.getTimeCountDown());
                startCountDown.runTaskTimer(bw,0,20L);
                match.setStartCountDown(startCountDown);
                match.setMatchStatus(Match.MatchStatus.STARTING);
            }
        }else {
            if(match.getStartCountDown() != null){
                match.getStartCountDown().cancel();
                match.setStartCountDown(null);
                match.setMatchStatus(Match.MatchStatus.WAITING);
            }
        }

    }

    public Match selectFreeMatch(){
        orderMatches();
        Match match = null;
        int index = 0;
        do{
            if(index >= matches.size()){
                break;
            }
            match = matches.get(index++);
        }while(!(match.getMatchStatus() == Match.MatchStatus.WAITING));

        return match;
    }

    public Match getMatch(Player player){
        for(Match match : matches){
            for(Player p : match.getPlayers()){
                if(player.equals(p)){
                    return match;
                }
            }
        }
        return null;
    }

    public boolean inAMatch(Player player){

        for(Match match : matches){
            if(match.isPresent(player)){
                return true;
            }
        }
        return false;

    }

    public Match getPlayer(Player player){
        for(Match match : matches){
            for(Player p : match.getPlayers()){
                if(player.equals(p)){
                    return match;
                }
            }
        }
        return null;
    }

    public void registerNPCEvent(BedWars bw){

        for(Arena arena : arenaManager.getArenas()){
            for(Team team : arena.getTeams()){

                team.getShopManager().registerNPCEvent(bw);

            }
        }

    }

    private void orderMatches(){
        matches.sort(Comparator.comparing(Match::getSizePlayers).reversed().thenComparing(Match::getWorldName));
    }

}
