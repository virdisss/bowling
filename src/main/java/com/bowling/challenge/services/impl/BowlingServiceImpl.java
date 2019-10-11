package com.bowling.challenge.services.impl;

import static com.bowling.challenge.constants.Constants.FRAME;
import static com.bowling.challenge.constants.Constants.NUMBER_PINS;
import static com.bowling.challenge.constants.Constants.PINFALLS;
import static com.bowling.challenge.constants.Constants.SCORE;
import static com.bowling.challenge.constants.Constants.SPARE;
import static com.bowling.challenge.constants.Constants.STRIKE;
import com.bowling.challenge.dao.Frame;
import com.bowling.challenge.dao.Game;
import com.bowling.challenge.services.BowlingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BowlingServiceImpl implements BowlingService {

    private final List<StringBuilder> rowList;
    private static final String SEPARATOR = " ";
    private static final int SEPARATOR_MAX_LENGTH = 8;
    
    public BowlingServiceImpl() {
        rowList = new ArrayList<>();
    }
    
    @Override
    public void displayScores() {
        rowList.forEach(System.out::println);
    }

    @Override
    public void createRowsToDisplay(Map<String, Game> gameMap) {
        //Frame Header Row
        StringBuilder frameBuilder = createFrameRow();
        rowList.add(frameBuilder);

        gameMap.forEach((key, game) -> {
            //Username Row
            StringBuilder usernameBuilder = new StringBuilder(key);
            rowList.add(usernameBuilder);

            //PinFalls
            StringBuilder pinFallBuilder = createPinFallRow(game);
            rowList.add(pinFallBuilder);

            //Score Row
            StringBuilder scoreBuilder = computeRowScore(game);
            rowList.add(scoreBuilder);
        });
    }
    
    //Useful functions

    private StringBuilder createFrameRow() {
        StringBuilder frameBuilder =
                new StringBuilder(FRAME)
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH - FRAME.length()))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));

        for (int i = 1; i <=10 ; i++) {
            frameBuilder.append(i).append(computeSeparatorLength(SEPARATOR_MAX_LENGTH));
        }
        return frameBuilder;
    }

    /**
     * computeRowScore computes the actual score for each frame during the game.
     *
     * @param game
     * @return StringBuilder
     *
     */
    private StringBuilder computeRowScore(Game game) {

        int totalScore = 0;
        StringBuilder gameScoreBuilder = 
                new StringBuilder(SCORE)
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH - SCORE.length()))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        
        Frame[] frames = game.getFrames();

        for (int i = 0; i < frames.length-2; i++) {
            totalScore = updateScore(totalScore, frames[i], frames[i+1], frames[i+2]);
            gameScoreBuilder
                    .append(totalScore)
                    .append(computeSeparatorLength(computeSpaceByScoreLength(totalScore)));
        }
        totalScore = updateScore(totalScore, frames[8], frames[9], null);
        gameScoreBuilder
                .append(totalScore)
                .append(computeSeparatorLength(computeSpaceByScoreLength(totalScore)));
        totalScore = updateScore(totalScore, frames[9], null, null);
        gameScoreBuilder
                .append(totalScore)
                .append(computeSeparatorLength(computeSpaceByScoreLength(totalScore)));
        return gameScoreBuilder;
    }
    
    private String computeSeparatorLength(int n) {
        String space = "";
        for (int i = 0; i < n; i++) {
            space += SEPARATOR;
        }
        return space;
    }
    
    private int computeSpaceByScoreLength(int val) {
        return SEPARATOR_MAX_LENGTH - ("" + val).length() + 1;
    }
    
    /**
     * updateScore function is the function that actually implements the logics to compute a score for each frame
     * It is called in computeRowScore for each frame.
     *
     * @param score
     * @param currentFrame
     * @param nextFrame
     * @param otherFrame
     * @return int
     *
     */
    private int updateScore(int score, Frame currentFrame, Frame nextFrame, Frame otherFrame) {
        
        if (nextFrame != null && otherFrame != null) {
            if (currentFrame.strike() && nextFrame.strike() && otherFrame.strike()) {
                score += 3 * NUMBER_PINS;
            } else if (currentFrame.strike() && nextFrame.strike()) {
                score += (2 * NUMBER_PINS + otherFrame.getChanceCardinal(0));
            } else if (currentFrame.strike()) {
                score += NUMBER_PINS + nextFrame.getScore();
            } else if (currentFrame.spare()) {
                score += NUMBER_PINS + nextFrame.getChanceCardinal(0);
            } else {
                score += currentFrame.getScore();
            }
            
        } else if (nextFrame != null) {

            if (currentFrame.strike() && nextFrame.strike()) {
                score += (2 * NUMBER_PINS + nextFrame.getChanceCardinal(1));
            } else if (currentFrame.strike()) {
                score += NUMBER_PINS + nextFrame.getScore();
            } else if (currentFrame.spare()) {
                score += NUMBER_PINS + nextFrame.getChanceCardinal(0);
            } else {
                score += currentFrame.getScore();
            }
        } else {
            if (currentFrame.strike()) {
                score += NUMBER_PINS + currentFrame.getExtraScore();
            } else if (currentFrame.spare()) {
                score += NUMBER_PINS + currentFrame.getChanceCardinal(2);
            } else {
                score += currentFrame.getScore();
            }
        }
        return score;
    }
    
    /**
     * createPinFallRow create the row for pin falls.
     *
     * @param game
     * @return StringBuilder
     *
     */
    private StringBuilder createPinFallRow(Game game) {
        StringBuilder pinFallBuilder = 
                new StringBuilder(PINFALLS)
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH - PINFALLS.length()))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        
        Frame[] frames = game.getFrames();

        for (int i = 0; i < frames.length-1; i++) {

            if (frames[i].strike()) {
                pinFallBuilder
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2))
                        .append(STRIKE)
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
            } else if (frames[i].spare()) {
                pinFallBuilder
                        .append(frames[i].getChanceValue(0))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2)).append(SPARE)
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
            } else {
                pinFallBuilder
                        .append(frames[i].getChanceValue(0))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2)).append(frames[i].getChanceValue(1))
                        .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
            }
        }

        if (frames[9].strike() && frames[9].getChanceValue(1).equals("" + NUMBER_PINS)) {
            pinFallBuilder
                    .append(STRIKE)
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2))
                    .append(STRIKE)
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2))
                    .append(frames[9].getChanceValue(2).equals("" + NUMBER_PINS) ? STRIKE : frames[9].getChanceValue(2))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        } else if (frames[9].strike()) {
            pinFallBuilder
                    .append(frames[9].getChanceValue(1))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2))
                    .append(frames[9].getChanceValue(2).equals("" + NUMBER_PINS) ? STRIKE : frames[9].getChanceValue(2))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        } 
        
        else if (frames[9].spare()) {
            pinFallBuilder
                    .append(frames[9].getChanceValue(2).equals("" + NUMBER_PINS) ? STRIKE : frames[9].getChanceValue(2))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        } else {
            pinFallBuilder
                    .append(frames[9].getChanceValue(0))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2))
                    .append(frames[9].getChanceValue(1))
                    .append(computeSeparatorLength(SEPARATOR_MAX_LENGTH/2));
        }
        return pinFallBuilder;
    }

    @Override
    public void restart() {
        rowList.clear();
    }
}
