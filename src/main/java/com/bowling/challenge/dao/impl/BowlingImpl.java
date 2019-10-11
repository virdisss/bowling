package com.bowling.challenge.dao.impl;

import static com.bowling.challenge.constants.Constants.NUMBER_PINS;
import com.bowling.challenge.dao.Bowling;
import com.bowling.challenge.dao.Game;
import com.bowling.challenge.exception.BowlingException;
import com.bowling.challenge.services.BowlingService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

public class BowlingImpl implements Bowling {

    private final Map<String, Game> gameMap;
    private final BowlingService bowlingService;

    public BowlingImpl(BowlingService bowlingService) {
        gameMap = new HashMap<>();
        this.bowlingService = bowlingService;
    }

    @Override
    public List<String> loadFrames(String pathToFile) throws IOException, BowlingException {

        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {

                String scoreString = currentLine.split("\\s+")[1];
                if (!scoreString.equalsIgnoreCase("F") && !scoreString.matches("^[0-9]+$")) {
                    throw new BowlingException(String.format("Incorrect format: input (%s) must be numeric", scoreString));
                }
                
                int cardinal = scoreString.equalsIgnoreCase("F") ? 0 : Integer.parseInt(scoreString);
                
                if (cardinal > NUMBER_PINS || cardinal < 0) {
                    throw new BowlingException(String.format("Invalid score value: input (%s) must be between [0, 10]", scoreString));
                }
                lines.add(currentLine);
            }
        }
        return lines;
    }
    
    @Override
    public Map<String, Game> populateGameMap(List<String> lines) {

        lines.forEach(currentLine -> {
            String[] linePieces = currentLine.split("\\s+");
            String username = linePieces[0];
            String scoreString = linePieces[1];
            Game game = Optional.ofNullable(gameMap.get(username)).orElse(new GameImpl());
            int cardinal = scoreString.equalsIgnoreCase("F") ? 0 : Integer.parseInt(scoreString);
            game.setScoreForRoll(cardinal, scoreString);
            gameMap.putIfAbsent(username, game);
        });
        return gameMap;
    }

    @Override
    public void handleScore() {
        bowlingService.createRowsToDisplay(gameMap);
    }

    @Override
    public void display() {
        bowlingService.displayScores();
    }

    @Override
    public void init() {
        gameMap.clear();
        bowlingService.restart();
    }
}
