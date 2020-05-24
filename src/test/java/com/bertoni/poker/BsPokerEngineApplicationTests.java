package com.bertoni.poker;

import com.bertoni.poker.util.Constants;
import com.bertoni.poker.util.PokerGameOperations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BsPokerEngineApplicationTests {

    final Logger logger = LoggerFactory.getLogger(getClass());
    BsPokerEngineApplication bsPokerEngineApplication = new BsPokerEngineApplication();
    PokerGameOperations pokerGameOperations = new PokerGameOperations();

    @Test
    public void testPokerGameEngine() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARDTH");
        handOne.add("CARD8H");
        handOne.add("CARD5C");
        handOne.add("CARDQS");
        handOne.add("CARDTC");
        handTwo.add("CARD9H");
        handTwo.add("CARD4D");
        handTwo.add("CARDJC");
        handTwo.add("CARDKS");
        handTwo.add("CARDJS");

        Map<String, Object> pokerGameResult = bsPokerEngineApplication.pokerGameEngine(handOne, handTwo);
        logger.info("pokerGameResult: " + pokerGameResult);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_2, pokerGameResult.get(Constants.GAME_RESULT));
    }

    @Test
    public void testReadInPutFile() {
        List<String> pokerGameList = bsPokerEngineApplication.readInPutFile();
        logger.info("pokerGameList size: " + pokerGameList.size());
        Assert.assertEquals(1000, pokerGameList.size());
    }

    @Test
    public void testEvaluateHighCardAndFlush() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARD8C");
        handOne.add("CARDTS");
        handOne.add("CARDKC");
        handOne.add("CARD9H");
        handOne.add("CARD4S");
        handTwo.add("CARD7D");
        handTwo.add("CARD2S");
        handTwo.add("CARD5D");
        handTwo.add("CARD3S");
        handTwo.add("CARDAC");

        String result = pokerGameOperations.evaluateHighCardAndFlush(handOne, handTwo, 5);
        logger.info("testEvaluateHighCardAndFlush result: " + result);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_2, result);
    }

    @Test
    public void testEvaluateOnePair() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARD8C");
        handOne.add("CARDTS");
        handOne.add("CARDKC");
        handOne.add("CARD9H");
        handOne.add("CARD9S");
        handTwo.add("CARD7D");
        handTwo.add("CARD2S");
        handTwo.add("CARD5D");
        handTwo.add("CARD7S");
        handTwo.add("CARDAC");

        String result = pokerGameOperations.evaluateOnePair(handOne, handTwo);
        logger.info("testEvaluateOnePair result: " + result);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_1, result);
    }

    @Test
    public void testEvaluateTwoPairs() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARD8C");
        handOne.add("CARDTS");
        handOne.add("CARD8D");
        handOne.add("CARD9H");
        handOne.add("CARD9S");
        handTwo.add("CARD7D");
        handTwo.add("CARD2S");
        handTwo.add("CARDKD");
        handTwo.add("CARD7S");
        handTwo.add("CARDKC");

        String result = pokerGameOperations.evaluateTwoPairs(handOne, handTwo);
        logger.info("testEvaluateTwoPairs result: " + result);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_2, result);
    }

    @Test
    public void testEvaluateFourOfAKindAndFullHouseAndThreeOfAKind() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARD8C");
        handOne.add("CARDTS");
        handOne.add("CARD9C");
        handOne.add("CARD9H");
        handOne.add("CARD9S");
        handTwo.add("CARD7D");
        handTwo.add("CARD2S");
        handTwo.add("CARDKD");
        handTwo.add("CARD7S");
        handTwo.add("CARD7C");

        String result = pokerGameOperations.evaluateFourOfAKindAndFullHouseAndThreeOfAKind(handOne, handTwo);
        logger.info("testEvaluateFourOfAKindAndFullHouseAndThreeOfAKind result: " + result);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_1, result);
    }

    @Test
    public void testEvaluateStraightFlushAndStraight() {
        List<String> handOne = new ArrayList<>();
        List<String> handTwo = new ArrayList<>();
        handOne.add("CARD6C");
        handOne.add("CARD7S");
        handOne.add("CARD8C");
        handOne.add("CARD9H");
        handOne.add("CARDTS");
        handTwo.add("CARD2D");
        handTwo.add("CARD3S");
        handTwo.add("CARD4D");
        handTwo.add("CARD5S");
        handTwo.add("CARD6D");

        String result = pokerGameOperations.evaluateStraightFlushAndStraight(handOne, handTwo);
        logger.info("testEvaluateStraightFlushAndStraight result: " + result);
        Assert.assertEquals(Constants.RESULT_WIN_PLAYER_1, result);
    }

    @Test
    public void testGetRankOfHand() {
        List<String> handOne = new ArrayList<>();
        handOne.add("CARD8C");
        handOne.add("CARDTS");
        handOne.add("CARDKC");
        handOne.add("CARDKH");
        handOne.add("CARD8S");

        String result = pokerGameOperations.getRankOfHand(handOne);
        logger.info("testGetRankOfHand result: " + result);
        Assert.assertEquals(Constants.HAND_TWO_PAIRS, result);
    }

}
