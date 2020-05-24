package com.bertoni.poker.util;

import com.bertoni.poker.enums.Card;
import com.bertoni.poker.enums.Rank;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PokerGameOperations {

    /**
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * @param limit size of foreach list
     * */
    public String evaluateHighCardAndFlush (List<String> handOne, List<String> handTwo, int limit) {
        String result = Constants.RESULT_DRAW;
        for (int i = 0; i < limit; i++) {
            int highCardOne = getHighCard(handOne, i);
            int highCardTwo = getHighCard(handTwo, i);
            if (highCardOne > highCardTwo) {
                result = Constants.RESULT_WIN_PLAYER_1;
                break;
            } else if (highCardOne < highCardTwo) {
                result = Constants.RESULT_WIN_PLAYER_2;
                break;
            }
        }
        return result;
    }

    /**
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * */
    public String evaluateOnePair (List<String> handOne, List<String> handTwo) {
        List<Integer> integerHandOne = sortList(handCardValue(handOne));
        List<Integer> integerHandTwo = sortList(handCardValue(handTwo));
        Map<Integer, Long> mapHandOne = groupingCardData(sortList(handCardValue(handOne)));
        Map<Integer, Long> mapHandTwo = groupingCardData(sortList(handCardValue(handTwo)));
        String result;
        result = compareMaxValueMap(mapHandOne, mapHandTwo);
        if (result.equals(Constants.RESULT_DRAW)) {
            Integer maxCardValueOfHand1 = getMaxIndexByMaxValueOnMap(mapHandOne);
            Integer maxCardValueOfHand2 = getMaxIndexByMaxValueOnMap(mapHandTwo);
            integerHandOne.removeIf(e -> e == maxCardValueOfHand1);
            integerHandTwo.removeIf(e -> e == maxCardValueOfHand2);
            result = evaluateMaxValueForList(integerHandOne, integerHandTwo, 3);
        }
        return result;
    }

    /**
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * */
    public String evaluateTwoPairs (List<String> handOne, List<String> handTwo) {
        Map<Integer, Long> mapHandOne = groupingCardData(sortList(handCardValue(handOne)));
        Map<Integer, Long> mapHandTwo = groupingCardData(sortList(handCardValue(handTwo)));
        String result;
        result = compareMaxValueMap(mapHandOne, mapHandTwo);
        if (result.equals(Constants.RESULT_DRAW)) {
            Integer maxCardValueOfHandOne = getMaxIndexByMaxValueOnMap(mapHandOne);
            Integer maxCardValueOfHandTwo = getMaxIndexByMaxValueOnMap(mapHandTwo);
            mapHandOne.remove(maxCardValueOfHandOne);
            mapHandTwo.remove(maxCardValueOfHandTwo);
            result = compareMaxValueMap(mapHandOne, mapHandTwo);
            if (result.equals(Constants.RESULT_DRAW)) {
                Integer maxCardValueOfHandOneSec = getMaxIndexByMaxValueOnMap(mapHandOne);
                Integer maxCardValueOfHandTwoSec = getMaxIndexByMaxValueOnMap(mapHandTwo);
                mapHandOne.remove(maxCardValueOfHandOneSec);
                mapHandTwo.remove(maxCardValueOfHandTwoSec);
                result = compareMaxValueMap(mapHandOne, mapHandTwo);
            }
        }
        return result;
    }

    /**
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * */
    public String evaluateFourOfAKindAndFullHouseAndThreeOfAKind (List<String> handOne, List<String> handTwo) {
        String result = Constants.RESULT_ERROR;
        Map<Integer, Long> mapHandOne = groupingCardData(sortList(handCardValue(handOne)));
        Integer maxCardValueOfHandOne = mapHandOne.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        Map<Integer, Long> mapHandTwo = groupingCardData(sortList(handCardValue(handTwo)));
        Integer maxCardValueOfHandTwo = mapHandTwo.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        if (maxCardValueOfHandOne > maxCardValueOfHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_1;
        } else if (maxCardValueOfHandOne < maxCardValueOfHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_2;
        }
        return result;
    }

    /**
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * */
    public String evaluateStraightFlushAndStraight (List<String> handOne, List<String> handTwo) {
        String result;
        int highCardOne = getHighCard(handOne, 0);
        int highCardTwo = getHighCard(handTwo, 0);
        if (highCardOne > highCardTwo) {
            result = Constants.RESULT_WIN_PLAYER_1;
        } else if (highCardOne < highCardTwo) {
            result = Constants.RESULT_WIN_PLAYER_2;
        } else {
            result = Constants.RESULT_DRAW;
        }
        return result;
    }

    /**
     @param hand is the list of card for hand
      * */
    public Rank getRankOfHandValue (List<String> hand) {
        Optional<Rank> getRankValue = Arrays.stream(Rank.values())
                .filter(e -> e.name().equals(getRankOfHand(hand)))
                .findFirst();

        return getRankValue.get();
    }

    /**
     @param hand is the list of card for hand
      * */
    public String getRankOfHand (List<String> hand) {
        String result;
        if (isRoyalFlush(hand)) {
            result = Constants.HAND_ROYAL_FLUSH;
        } else if (isStraightFlush(hand)) {
            result = Constants.HAND_STRAIGHT_FLUSH;
        } else if (isFourOfAKind(hand)) {
            result = Constants.HAND_FOUR_OF_A_KIND;
        } else if (isFullHouse(hand)) {
            result = Constants.HAND_FULL_HOUSE;
        } else if (isFlush(hand)) {
            result = Constants.HAND_FLUSH;
        } else if (isStraight(hand)) {
            result = Constants.HAND_STRAIGHT;
        } else if (isThreeOfAKind(hand)) {
            result = Constants.HAND_THREE_OF_A_KIND;
        } else if (isTwoPairs(hand)) {
            result = Constants.HAND_TWO_PAIRS;
        } else if (isOnePair(hand)) {
            result = Constants.HAND_ONE_PAIR;
        } else {
            result = Constants.HAND_HIGH_CARD;
        }
        return result;
    }

    /**
     * @param mapHandOne is map of hand player 1 data
     * @param mapHandTwo is map of hand player 2 data
     * */
    public String compareMaxValueMap (Map<Integer, Long> mapHandOne, Map<Integer, Long> mapHandTwo) {
        String result;
        Integer maxCardValueOfHandOne = getMaxIndexByMaxValueOnMap(mapHandOne);
        Integer maxCardValueOfHandTwo = getMaxIndexByMaxValueOnMap(mapHandTwo);
        if (maxCardValueOfHandOne > maxCardValueOfHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_1;
        } else if (maxCardValueOfHandOne < maxCardValueOfHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_2;
        } else {
            result = Constants.RESULT_DRAW;
        }
        return result;
    }

    /**
     * @param mapHand is map of hand data, values and counts
     * */
    public Integer getMaxIndexByMaxValueOnMap (Map<Integer, Long> mapHand) {
        return mapHand
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
    }

    /**
     * @param integerHandOne are the cards with integer value for a hand game player 1
     * @param integerHandTwo are the cards with integer value for a hand game player 2
     * @param limit size of foreach list
     * */
    public String evaluateMaxValueForList (List<Integer> integerHandOne, List<Integer> integerHandTwo, int limit) {
        String result = Constants.RESULT_DRAW;
        for (int i = 0; i < limit; i++) {
            int highCardOne = getMaxValueForListWithSkip(integerHandOne, i);
            int highCardTwo = getMaxValueForListWithSkip(integerHandTwo, i);
            if (highCardOne > highCardTwo) {
                result = Constants.RESULT_WIN_PLAYER_1;
                break;
            } else if (highCardOne < highCardTwo) {
                result = Constants.RESULT_WIN_PLAYER_2;
                break;
            }
        }
        return result;
    }

    /**
     * @param hand is the list of card for hand
     * @param skipValue is the skip value to get max value of list [0 is first max value, 1 is second max value, 2 is third max value, 3 is fourth max value, 4 is fifth max value]
     * */
    public Integer getMaxValueForListWithSkip (List<Integer> hand, Integer skipValue) {
        Optional<Integer> foundMaxValue = hand
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(hand.size())
                .skip(skipValue)
                .findFirst();

        return foundMaxValue.get();
    }

    /**
     * @param hand is the list of card for hand
     * @param skipValue is the skip value to get max value of list [0 is first max value, 1 is second max value, 2 is third max value, 3 is fourth max value, 4 is fifth max value]
     * */
    public int getHighCard (List<String> hand, Integer skipValue) {
        List<Integer> listWithoutDuplicates = getDistinctList(sortList(handCardValue(hand)));

        Optional<Integer> foundMaxValue = listWithoutDuplicates
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(listWithoutDuplicates.size())
                .skip(skipValue)
                .findFirst();

        return foundMaxValue.get();
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isOnePair (List<String> hand) {
        boolean isOnePair = false;
        long countPairs = sumSameGroupingCardByParam(groupingCardData(sortList(handCardValue(hand))), 2);
        if (countPairs == 1) {
            isOnePair = true;
        }
        return isOnePair;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isTwoPairs (List<String> hand) {
        boolean isTwoPairs = false;
        long countPairs = sumSameGroupingCardByParam(groupingCardData(sortList(handCardValue(hand))), 2);
        if (countPairs == 2) {
            isTwoPairs = true;
        }
        return isTwoPairs;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isThreeOfAKind (List<String> hand) {
        boolean isThreeOfAKind;
        isThreeOfAKind = isGroupingCardByParam(groupingCardData(sortList(handCardValue(hand))), 3);
        return isThreeOfAKind;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isStraight (List<String> hand) {
        boolean isStraight = false;
        if (isCardValueConsecutive(sortList(handCardValue(hand)))) {
            isStraight = true;
        }
        return isStraight;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isFlush (List<String> hand) {
        boolean isFlush;
        isFlush = isGroupingCardByParam(groupingCardData(sortList(handCardSuit(hand))), 5);
        return isFlush;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isFullHouse (List<String> hand) {
        boolean isFullHouse = false;
        Map<Integer, Long> mapGrouping = groupingCardData(sortList(handCardValue(hand)));
        if (isGroupingCardByParam(mapGrouping, 3) && isGroupingCardByParam(mapGrouping, 2)) {
            isFullHouse = true;
        }
        return isFullHouse;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isFourOfAKind (List<String> hand) {
        boolean isFourOfAKind;
        isFourOfAKind = isGroupingCardByParam(groupingCardData(sortList(handCardValue(hand))), 4);
        return isFourOfAKind;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isStraightFlush (List<String> hand) {
        boolean isStraightFlush = false;
        if (isCardValueConsecutive(sortList(handCardValue(hand))) && isSameCardSuit(sortList(handCardSuit(hand)))) {
            isStraightFlush = true;
        }
        return isStraightFlush;
    }

    /**
     * @param hand is the list of card for hand
     * */
    public boolean isRoyalFlush (List<String> hand) {
        boolean isRoyalFlush = false;
        List<Integer> royalFlushListValid = new ArrayList<>();
        royalFlushListValid.add(10);
        royalFlushListValid.add(11);
        royalFlushListValid.add(12);
        royalFlushListValid.add(13);
        royalFlushListValid.add(14);
        if (royalFlushListValid.containsAll(sortList(handCardValue(hand))) && isSameCardSuit(sortList(handCardSuit(hand)))) {
            isRoyalFlush = true;
        }
        return isRoyalFlush;
    }

    /**
     * @param list is a list value to evaluate if are consecutive
     * */
    public boolean isCardValueConsecutive (List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) != list.get(i + 1) - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param list is a list of card suit value to evaluate if are same
     * */
    public boolean isSameCardSuit (List<Integer> list) {
        return list.stream()
                .distinct()
                .count() <= 1;
    }

    /**
     * @param map is a map of card grouping data for value
     * @param grouping is value of card grouping to evaluate
     * */
    public boolean isGroupingCardByParam (Map<Integer, Long> map, int grouping) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == grouping)
                .findFirst()
                .isPresent();
    }

    /**
     * @param map is a map of card grouping data for value
     * @param grouping is value of card grouping to evaluate
     * */
    public long sumSameGroupingCardByParam (Map<Integer, Long> map, int grouping) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == grouping)
                .count();
    }

    /**
     * @param hand is the list of card for hand to grouping on map data
     * */
    public Map<Integer, Long> groupingCardData (List<Integer> hand) {
        return hand
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * @param hand is the list of card for hand to sort data
     * */
    public List<Integer> sortList (List<Integer> hand) {
        return hand
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * @param hand is the list of card for hand to get unique values
     * */
    public List<Integer> getDistinctList (List<Integer> hand) {
        return hand
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * @param hand is the list of card value for hand with String format to convert Integer format
     * */
    public List<Integer> handCardValue (List<String> hand) {
        List<Integer> handCardValues = new ArrayList<>();
        hand.forEach((card) -> {
            Optional<Card> handFound = Arrays.stream(Card.values())
                    .filter(e -> e.name().equals(card))
                    .findFirst();

            int numeric = handFound.get().getCardValue();
            handCardValues.add(numeric);
        });
        return handCardValues;
    }

    /**
     * @param hand is the list of card suit for hand with String format to convert Integer format
     * */
    public List<Integer> handCardSuit (List<String> hand) {
        List<Integer> handCardSuits = new ArrayList<>();
        hand.forEach((card) -> {
            Optional<Card> handFound = Arrays.stream(Card.values())
                    .filter(e -> e.name().equals(card))
                    .findFirst();

            int numeric = handFound.get().getCardSuit();
            handCardSuits.add(numeric);
        });
        return handCardSuits;
    }

}
