package com.company;

public enum Color {

    RED,
    BLUE,
    GREEN,
    YELLOW,
    ACQUA,
    WHITE,
    PINK,
    GRAY;

    public String toString(){

        switch(this){
            case RED -> {
                return "red";
            }
            case BLUE -> {
                return "blue";
            }
            case GREEN -> {
                return "green";
            }
            case YELLOW -> {
                return "yellow";
            }
            case ACQUA -> {
                return "acqua";
            }
            case WHITE -> {
                return "white";
            }
            case PINK -> {
                return "pink";
            }
            case GRAY -> {
                return "gray";
            }
        }
        return "";
    }
}
