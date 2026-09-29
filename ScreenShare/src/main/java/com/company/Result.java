package com.company;

public enum Result {

    CLEAN,
    BANNED;

    public String toString(){
        switch(this){
            case CLEAN -> {
                return "CLEAN";
            }
            case BANNED -> {
                return "BANNED";
            }
        }
        return "NONE";
    }

}
