package com.company.Match;

import org.bukkit.scheduler.BukkitRunnable;

public class StartCountDown extends BukkitRunnable {

    private int time;
    private final MatchManager matchManager;
    private final Match match;

    public StartCountDown(MatchManager matchManager,Match match,int time){
        this.matchManager = matchManager;
        this.match = match;
        this.time = time;
        match.setMatchStatus(Match.MatchStatus.STARTING);
    }

    @Override
    public void run() {
        match.updateCountDown(time--);
        if(time <= 0){
            cancel();
            matchManager.startBW(match);
        }
    }

    public int getTime(){
        return time;
    }
}
