package com.company.Match;

import com.company.Arenas.Arena;
import com.company.BedWars;
import com.company.Config.MatchConfig;
import com.company.Match.Generators.CenterGenerators.Enums.DiamondPhases;
import com.company.Match.Generators.CenterGenerators.Enums.EmeraldPhases;
import com.company.Match.Generators.GeneratorManager;
import com.company.Match.Team.Team;
import com.company.Scoreboard.ScoreboardData;
import com.company.Scoreboard.ScoreboardManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;

public class Match{

    public enum MatchStatus{WAITING,STARTING,RUNNING,END}

    private final DiamondPhases diamondPhase = DiamondPhases.FIRST;
    private final EmeraldPhases emeraldPhase = EmeraldPhases.FIRST;

    private final MatchConfig matchConfig;

    private final Arena arena;
    private MatchStatus matchStatus;

    private final ScoreboardManager scoreboardManager;
    private final ScoreboardData scoreboardData;

    private final ArrayList<Player> players;
    private final int minPlayersToStart;
    private final int maxPlayersToStart;

    private StartCountDown startCountDown;
    private final GeneratorManager generatorManager;

    private final HashSet<Block> placedBlocks;

    public Match(MatchConfig matchConfig,
                 Arena arena,
                 int minPlayersToStart,
                 int maxPlayersToStart,
                 GeneratorManager generatorManager,
                 ScoreboardManager scoreboardManager
    ){
        this.matchConfig = matchConfig;
        this.arena = arena;
        this.minPlayersToStart = minPlayersToStart;
        this.maxPlayersToStart = maxPlayersToStart;
        this.generatorManager = generatorManager;
        this.scoreboardManager = scoreboardManager;
        players = new ArrayList<>();
        scoreboardData = getDatesScoreboard();
        setMatchStatus(MatchStatus.WAITING);
        placedBlocks = new HashSet<>();
    }

    public void start(BedWars bw){

        setMatchStatus(MatchStatus.RUNNING);
        for(Team team : arena.getTeams()) {
            team.sendPlayersToBase();
            team.equipPlayers();
        }
        generatorManager.startAll(bw);

    }

    public void joinBW(Player player){

        if(matchStatus == MatchStatus.WAITING && players.size() < maxPlayersToStart){
            Location location = arena.getWaitingSpawm();
            player.teleport(location);
            players.add(player);
            scoreboardData.setCurrentPlayers(players.size());
            updateAllScoreboards();
        }
    }

    public void leaveBW(Player player){

        players.remove(player);
        for(Team team : arena.getTeams()){
            team.removePlayer(player);
            scoreboardData.setCurrentPlayers(scoreboardData.getCurrentPlayers() - 1);
        }
        updateAllScoreboards();
        scoreboardManager.update(null,player);
    }

    public void createTeams(){

        ArrayList<Team> teams = arena.getTeams();

        int index = 0;
        for(Team team : teams){
            if(!team.isFull()){
                if(index == players.size())break;
                team.addPlayer(players.get(index));
                index++;
            }
        }
    }

    public void loadGenerators(){
        generatorManager.loadAll(arena.getID(),emeraldPhase,diamondPhase);
    }


    public void updateCountDown(int time){

        if(time > 0){

            if(time % 10 == 0){
                for(Player player : players){
                    updateTitle(player,time);
                    updateChat(player,matchConfig.getStartingMessage(),time);
                }
            }

            if(time <= 5) {

                if(time == 5){
                    createTeams();
                }
                for (Player player : players) {
                    updateTitle(player, time);
                    updateChat(player, matchConfig.getStartingMessageFast(), time);
                }
            }

            scoreboardData.setTime(time);
            updateAllScoreboards();

        }
    }

    public ScoreboardData getScoreboardData(){
        return scoreboardData;
    }

    public StartCountDown getStartCountDown(){
        return startCountDown;
    }
    public void setStartCountDown(StartCountDown startCountDown){
        this.startCountDown = startCountDown;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public boolean isPresent(Player player){
        return players.contains(player);
    }



    public String getWorldName(){
        return arena.getWorldName();
    }

    public int getSizePlayers(){
        return players.size();
    }

    public MatchStatus getMatchStatus(){
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus){
        this.matchStatus = matchStatus;
        if(!players.isEmpty())updateAllScoreboards();
    }


    private void updateTitle(Player player,int time){

        player.sendTitle(
                String.valueOf(time),
                "",
                0,20,0
        );

    }

    private void updateChat(Player player,String message,int time){
        player.sendMessage(message.replace("%time%",String.valueOf(time)));
    }

    public boolean checkTime(){
        return players.size() >= minPlayersToStart;
    }

    public void updateAllScoreboards(){
        for(Player p : players){
            scoreboardManager.update(this,p);
        }
    }

    public ScoreboardData getDatesScoreboard() {
        ScoreboardData data = new ScoreboardData(
                arena.getWorldName(),
                maxPlayersToStart,
                arena.getTeams()
        );

        if (startCountDown != null) {
            data.setTime(startCountDown.getTime());
        } else {
            data.setTime(0);
        }

        return data;
    }

    public Team getTeam(Player player){

        for(Team team : arena.getTeams()){

            if(team.contains(player)){
                return team;
            }
        }
        return null;

    }

    public Team getTeam(Location location){

        for (Team team : arena.getTeams()) {

            if (team.getBed() != null &&
                    team.getBed().isBedBlock(location)) {

                return team;
            }
        }

        return null;

    }

    public void destroyBed(Location location){

        Team team = getTeam(location);

        if(team != null){
            team.destroyBed();
            updateAllScoreboards();
        }
    }

    public HashSet<Block> getPlacedBlocks(){
        return placedBlocks;
    }

}
