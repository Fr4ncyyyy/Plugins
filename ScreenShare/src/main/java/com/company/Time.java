package com.company;

public class Time {

    private int time;

    public void setTime(int time){
        this.time = time;
    }

    public String toString(){

        if(time < 60){
            return time + "s";
        }

        if(time < 3600){
            int min = time / 60;
            return min + "m " + (time - (min * 60)) + "s";
        }

        if(time < 3600 * 24){
            int min = time / 60;
            int hours = min / 60;
            int sec = time - (min * 60);

            return hours + "h " + (min % 60) + "m " + sec + "s";
        }

        int days = time / 86400;
        int hours = (time % 86400) / 3600;
        int min = (time % 3600) / 60;
        int sec = time % 60;

        return days + "d " + hours + "h " + min + "m " + sec + "s";
    }

}
