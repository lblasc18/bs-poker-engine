package com.bertoni.poker.enums;

public enum Card {
    CARD2S(2, 1),
    CARD3S(3, 1),
    CARD4S(4, 1),
    CARD5S(5, 1),
    CARD6S(6, 1),
    CARD7S(7, 1),
    CARD8S(8, 1),
    CARD9S(9, 1),
    CARDTS(10, 1),
    CARDJS(11, 1),
    CARDQS(12, 1),
    CARDKS(13, 1),
    CARDAS(14, 1),
    CARD2H(2, 2),
    CARD3H(3, 2),
    CARD4H(4, 2),
    CARD5H(5, 2),
    CARD6H(6, 2),
    CARD7H(7, 2),
    CARD8H(8, 2),
    CARD9H(9, 2),
    CARDTH(10, 2),
    CARDJH(11, 2),
    CARDQH(12, 2),
    CARDKH(13, 2),
    CARDAH(14, 2),
    CARD2D(2, 3),
    CARD3D(3, 3),
    CARD4D(4, 3),
    CARD5D(5, 3),
    CARD6D(6, 3),
    CARD7D(7, 3),
    CARD8D(8, 3),
    CARD9D(9, 3),
    CARDTD(10, 3),
    CARDJD(11, 3),
    CARDQD(12, 3),
    CARDKD(13, 3),
    CARDAD(14, 3),
    CARD2C(2, 4),
    CARD3C(3, 4),
    CARD4C(4, 4),
    CARD5C(5, 4),
    CARD6C(6, 4),
    CARD7C(7, 4),
    CARD8C(8, 4),
    CARD9C(9, 4),
    CARDTC(10, 4),
    CARDJC(11, 4),
    CARDQC(12, 4),
    CARDKC(13, 4),
    CARDAC(14, 4);

    private final int cardValue;
    private final int cardSuit;

    Card(int cardValue, int cardSuit) {
        this.cardValue = cardValue;
        this.cardSuit = cardSuit;
    }

    public int getCardValue() {
        return cardValue;
    }

    public int getCardSuit() {
        return cardSuit;
    }
}
