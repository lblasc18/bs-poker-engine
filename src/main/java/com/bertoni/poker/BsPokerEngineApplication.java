package com.bertoni.poker;

import com.bertoni.poker.enums.Rank;
import com.bertoni.poker.util.Constants;
import com.bertoni.poker.util.PokerGameOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BsPokerEngineApplication implements CommandLineRunner {

    final Logger logger = LoggerFactory.getLogger(getClass());

    PokerGameOperations pokerGameOperations = new PokerGameOperations();

    public static void main(String[] args) {
        SpringApplication.run(BsPokerEngineApplication.class, args);
    }

    /**
     *run: Override method run to execute the process to start application
     * */
    @Override
    public void run(String... args) throws Exception {
        logger.info("::Run Application - Init");
        try {
            List<String> pokerGameList = readInPutFile();
            List<Map<String, Object>> pokerGameResultList = new ArrayList<>();
            pokerGameList.forEach((p) -> {
                List<String> handsListSplit = Arrays.asList(p.split(" "));
                List<String> handsListWithPrefix = handsListSplit.stream()
                        .map(s -> Constants.CARD_PREFIX.concat(s))
                        .collect(Collectors.toList());

                int midIndex = (handsListWithPrefix.size() - 1) / 2;
                List<List<String>> pokerGameHandsList = new ArrayList<>(
                        handsListWithPrefix.stream()
                                .collect(Collectors.partitioningBy(s -> handsListWithPrefix.indexOf(s) > midIndex))
                                .values()
                );
                pokerGameResultList.add(pokerGameEngine(pokerGameHandsList.get(0), pokerGameHandsList.get(1)));
            });
            createOutPutFile(pokerGameResultList);
            logger.info("::Run Application - End");
        } catch (Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            logger.info("::Run Application - End with errors");
            ex.printStackTrace();
        }
    }

    /**
     * pokerGameEngine: Method to define the result for hands in a game
     * @param handOne are the cards for a hand game player 1
     * @param handTwo are the cards for a hand game player 2
     * */
    public Map<String, Object> pokerGameEngine (List<String> handOne, List<String> handTwo) {
        logger.info("::pokerGameEngine - Init");
        String result = Constants.RESULT_DRAW;
        Rank rankOfHandOne = pokerGameOperations.getRankOfHandValue(handOne);
        Rank rankOfHandTwo = pokerGameOperations.getRankOfHandValue(handTwo);
        int rankHandOne = rankOfHandOne.getRankValue();
        int rankHandTwo = rankOfHandTwo.getRankValue();
        double rankProbabilityHandOne = rankOfHandOne.getRankProbabilityToWin();
        double rankProbabilityHandTwo = rankOfHandTwo.getRankProbabilityToWin();
        Map<String, Object> pokerGameResult = new HashMap<>();
        if (rankHandOne > rankHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_1;
        } else if (rankHandOne < rankHandTwo) {
            result = Constants.RESULT_WIN_PLAYER_2;
        } else {
            if (rankHandOne == Rank.HIGH_CARD.getRankValue()) {
                result = pokerGameOperations.evaluateHighCardAndFlush(handOne, handTwo, 5);
            } else if (rankHandOne == Rank.ONE_PAIR.getRankValue()) {
                result = pokerGameOperations.evaluateOnePair(handOne, handTwo);
            } else if (rankHandOne == Rank.TWO_PAIRS.getRankValue()) {
                result = pokerGameOperations.evaluateTwoPairs(handOne, handTwo);
            } else if (rankHandOne == Rank.THREE_OF_A_KIND.getRankValue()) {
                result = pokerGameOperations.evaluateFourOfAKindAndFullHouseAndThreeOfAKind(handOne, handTwo);
            } else if (rankHandOne == Rank.STRAIGHT.getRankValue()) {
                result = pokerGameOperations.evaluateStraightFlushAndStraight(handOne, handTwo);
            } else if (rankHandOne == Rank.FLUSH.getRankValue()) {
                result = pokerGameOperations.evaluateHighCardAndFlush(handOne, handTwo, 5);
            } else if (rankHandOne == Rank.FULL_HOUSE.getRankValue()) {
                result = pokerGameOperations.evaluateFourOfAKindAndFullHouseAndThreeOfAKind(handOne, handTwo);
            } else if (rankHandOne == Rank.FOUR_OF_A_KIND.getRankValue()) {
                result = pokerGameOperations.evaluateFourOfAKindAndFullHouseAndThreeOfAKind(handOne, handTwo);
            } else if (rankHandOne == Rank.STRAIGHT_FLUSH.getRankValue()) {
                result = pokerGameOperations.evaluateStraightFlushAndStraight(handOne, handTwo);
            } else if (rankHandOne == Rank.ROYAL_FLUSH.getRankValue()) {
                result = Constants.RESULT_DRAW;
            }
        }
        pokerGameResult.put(Constants.PROBABILITY_TO_WIN_PLAYER1, rankProbabilityHandOne);
        pokerGameResult.put(Constants.PROBABILITY_TO_WIN_PLAYER2, rankProbabilityHandTwo);
        pokerGameResult.put(Constants.GAME_RESULT, result);
        logger.info("::pokerGameEngine - Result: " + pokerGameResult);
        logger.info("::pokerGameEngine - End");
        return pokerGameResult;
    }

    /**
     * readInPutFile: Method to read input file
     * */
    public List<String> readInPutFile() {
        logger.info("::readInFile - Init");
        String inputFile = "templates/input/pokerdata.txt";
        List<String> pokerGameList = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            /**Read @inputFile like to stream*/
            Stream<String> streamFile = Files.lines(Paths.get(classLoader.getResource(inputFile).getPath()));
            /**Convert stream to list*/
            pokerGameList = streamFile.collect(Collectors.toList());
            logger.info("::readInFile - Init");
            return pokerGameList;
        } catch (IOException e) {
            logger.error("File " + inputFile + " not exists!!!");
            logger.error("::readInFile - End with errors");
            e.printStackTrace();
            return pokerGameList;
        } catch (Exception ex) {
            logger.error("::readInFile - End with errors");
            ex.printStackTrace();
            return pokerGameList;
        }
    }

    /**
     * createOutPutFile: Method to write and create output file
     * @param pokerGameResultList is the list of results for each game
     * */
    public void createOutPutFile(List<Map<String, Object>> pokerGameResultList) {
        logger.info("::createOutFile - Init");
        String outputFile = "src/main/resources/templates/output/pokerdata[results].txt";
        try {
            long countPlayerOneWinner = pokerGameResultList.stream().filter((a) -> a.get(Constants.GAME_RESULT).equals(Constants.RESULT_WIN_PLAYER_1)).count();
            long countPlayerTwoWinner = pokerGameResultList.stream().filter((a) -> a.get(Constants.GAME_RESULT).equals(Constants.RESULT_WIN_PLAYER_2)).count();
            long countDrawGames = pokerGameResultList.stream().filter((a) -> a.get(Constants.GAME_RESULT).equals(Constants.RESULT_DRAW)).count();
            FileWriter fileWriter = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("1. Player 1 win: %d", countPlayerOneWinner);
            printWriter.println("");
            printWriter.printf("2: Player 2 win: %d", countPlayerTwoWinner);
            printWriter.println("");
            printWriter.printf("3: Neither player win: %d", countDrawGames);
            printWriter.println("");
            printWriter.println("4: -------- PLAYER 1 (%) -------- | -------- PLAYER 2 (%) --------");
            pokerGameResultList.forEach((ph) -> {
                printWriter.printf("              %.5f            |           %.5f             ", ph.get(Constants.PROBABILITY_TO_WIN_PLAYER1), ph.get(Constants.PROBABILITY_TO_WIN_PLAYER2));
                printWriter.println("");
            });
            printWriter.close();
            logger.info("::createOutFile - End");
        } catch (IOException ioe) {
            logger.error("File " + outputFile + " not exists!!!");
            logger.error("::createOutFile - End with errors");
            ioe.printStackTrace();
        } catch (Exception ex) {
            logger.error("::createOutFile - End with errors");
            ex.printStackTrace();
        }
    }

}
