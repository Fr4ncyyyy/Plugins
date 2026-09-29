package com.company;


public enum Durations {


    SECONDS("s",1),
    MINUTES("m",60),
    HOURS("h",60 * 60),
    DAYS("d",60 * 60 * 24);

    private final String symbol;
    private final int value;

    Durations(String symbol,int value){
        this.symbol = symbol;
        this.value = value;
    }

    public boolean validChar(String s){
        return symbol.equalsIgnoreCase(s);
    }
    public int getValue(){
        return value;
    }
}
