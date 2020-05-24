package com.bertoni.poker.enums;

public enum Rank {
    ROYAL_FLUSH(10, 99.99985),
    STRAIGHT_FLUSH(9, 99.9985),
    FOUR_OF_A_KIND(8, 99.9744),
    FULL_HOUSE(7, 99.83),
    FLUSH(6, 99.633),
    STRAIGHT(5, 99.24),
    THREE_OF_A_KIND(4, 97.13),
    TWO_PAIRS(3, 92.38),
    ONE_PAIR(2, 50.1),
    HIGH_CARD(1, 38.46);

    private final int rankValue;
    private final double rankProbabilityToWin;

    Rank(int rankValue, double rankProbabilityToWin) {
        this.rankValue = rankValue;
        this.rankProbabilityToWin = rankProbabilityToWin;
    }

    public int getRankValue() {
        return rankValue;
    }

    public double getRankProbabilityToWin() {
        return rankProbabilityToWin;
    }
}
