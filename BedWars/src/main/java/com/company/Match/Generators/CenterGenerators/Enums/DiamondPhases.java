package com.company.Match.Generators.CenterGenerators.Enums;

public enum DiamondPhases {
    FIRST,
    SECOND,
    THIRD,
    FOURTH;

    public String toString(){
        return switch (this) {
            case FIRST -> "FIRST";
            case SECOND -> "SECOND";
            case THIRD -> "THIRD";
            case FOURTH -> "FOURTH";
        };
    }

}
