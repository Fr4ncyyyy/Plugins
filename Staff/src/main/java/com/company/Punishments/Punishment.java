package com.company.Punishments;

import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Punishment {

    protected Player staff;
    protected Player user;
    protected String reason;
    protected Long currentTime;

    public Punishment(Player staff, Player user, String reason){

        this.staff = staff;
        this.user = user;
        this.reason = reason;
        currentTime = System.currentTimeMillis();

    }

    protected String getDuration(Long duration) {
        long remaining = duration - (System.currentTimeMillis() - currentTime);

        if (remaining < 0)
            remaining = 0;

        long seconds = remaining / 1000;

        long years = seconds / (365 * 24 * 60 * 60);
        seconds %= 365 * 24 * 60 * 60;

        long days = seconds / (24 * 60 * 60);
        seconds %= 24 * 60 * 60;

        long hours = seconds / (60 * 60);
        seconds %= 60 * 60;

        long minutes = seconds / 60;
        seconds %= 60;

        String s = "";

        if (years > 0)
            s += years + (years == 1 ? " anno " : " anni ");

        if (days > 0)
            s += days + (days == 1 ? " giorno " : " giorni ");

        if (hours > 0)
            s += hours + (hours == 1 ? " ora " : " ore ");

        if (minutes > 0)
            s += minutes + (minutes == 1 ? " minuto " : " minuti ");

        if (seconds > 0)
            s += seconds + (seconds == 1 ? " secondo" : " secondi");
        if(seconds == 0 && minutes == 0 && hours == 0 && days == 0 && years == 0){
            s += " 1 secondo";
        }

        return s.trim();
    }

    public Player getStaff(){
        return staff;
    }


}
