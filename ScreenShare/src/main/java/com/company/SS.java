package com.company;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitTask;

import java.io.ObjectInputFilter;
import java.util.*;

public class SS{

    private enum Status{
        CREATING,
        ACTIVE
    }
    private ConfigManager config = ConfigManager.getIstance();

    private Time timer;
    private int time;
    private Status status;
    private Result result;
    private Player staff;
    private Player suspected;
    private ArrayList<Player> spectators;

    private Scoreboard scoreboardStaff;
    private Scoreboard scoreboardUser;
    private Scoreboard scoreboardSpectator;

    private BukkitTask task;

    public SS(Player staff,Player suspected){
        this.staff = staff;
        this.suspected = suspected;
        spectators = new ArrayList<>();
        time = 0;
        timer = new Time();

    }

    public void start(Plugin plugin,World world){
        try{
            createSS(world);
            task = Bukkit.getScheduler().runTaskTimer(
                    plugin,
                    () -> {
                        time++;
                        timer.setTime(time);

                        scoreboardStaff.setLines(staff, suspected, timer.toString());
                        scoreboardUser.setLines(staff, suspected, timer.toString());
                        scoreboardSpectator.setLines(staff, suspected, timer.toString());
                    },
                    0L,
                    20L
            );

            playerMessages();


        }catch(Exception e){
            System.out.println("Error during the ScreenShare creation: " + e);
        }
    }

    private void playerMessages(){
        suspected.sendTitle(
                config.getTitleSSMessage(),
                config.getDescSSMessage(),
                10,
                60,
                10
        );

        suspected.sendMessage(config.getPlayerSSedMessage());

    }

    public void stop(Statistics statistics,Result result,World world){

        this.result = result;

        statistics.save(this,result);

        task.cancel();
        teleportPlayersBack(world);

        staff.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        suspected.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        for(Player p : spectators){
            p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }

    }

    public void addSpectator(World world,Player player){

        spectators.add(player);
        player.teleport(config.getSpawnSpectator(world));
        createScoreboard();

    }

    public void createSS(World world){
        status =  Status.CREATING;

        teleportPlayers(world);
        createScoreboard();
        createButtons();
        status = Status.ACTIVE;
    }

    public Player getStaff(){
        return staff;
    }
    public Player getUser() {return suspected;}
    public ArrayList<Player> getSpectators(){
        return spectators;
    }
    public void removeSpectator(World world,Player p){
        p.teleport(config.getSpawnLobby(world));
        p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard());
        spectators.remove(p);
    }

    private void teleportPlayers(World world){
        staff.teleport(config.getSpawnStaff(world));
        suspected.teleport(config.getSpawnSuspected(world));
    }
    private void teleportPlayersBack(World world){
        staff.teleport(config.getSpawnLobby(world));
        suspected.teleport(config.getSpawnLobby(world));

        for(Player p : spectators){
            p.teleport(config.getSpawnLobby(world));
        }

    }
    private void createScoreboard(){
        ArrayList<Player> staff = new ArrayList<>();
        staff.add(this.staff);
        scoreboardStaff = new com.company.Scoreboard(staff,
                config.getTitleScoreboardStaff(),
                config.getScoreboardStaff());

        ArrayList<Player> suspected = new ArrayList<>();
        suspected.add(this.suspected);
        scoreboardUser = new com.company.Scoreboard(new ArrayList<>(suspected),
                config.getTitleScoreboardUser(),
                config.getScoreboardUser());

        scoreboardSpectator = new com.company.Scoreboard(spectators,
                config.getTitleSpectator(),
                config.getScoreboardSpectator());
    }

    public boolean getSS(Player player){
        if(spectators.contains(player)
                || staff.equals(player)
                || suspected.equals(player)){
            return true;
        }
        return false;
    }
    public void sendAll(Player player,String message){

        String format;
        if(player.equals(staff)){
            format = config.getStaffMessage();
        } else if (player.equals(suspected)) {
            format = config.getUserMessage();
        } else if (spectators.contains(player)) {
            format = config.getSpectatorMessage();
        } else {
            return;
        }
        format = format.replace("%player%",player.getName()).replace("%message%",message);

        staff.sendMessage(format);
        suspected.sendMessage(format);

        for(Player p : spectators){
            p.sendMessage(format);
        }

    }
    public void createButtons(){

        for(String s : config.getButtons()){
            Button btn = new Button(config.getButtonsPath() + "."
                    + s,suspected.getName(),config.getPrepareCommands());
            staff.spigot().sendMessage(btn.getButton());
        }

    }

    public String toString(){
        return config.getSSToString()
                .replace("%staff%",staff.getName())
                .replace("%user%",suspected.getName())
                .replace("%result%",result.toString())
                .replace("%time%", timer.toString());
    }

}
