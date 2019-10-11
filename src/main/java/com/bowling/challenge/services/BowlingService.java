package com.bowling.challenge.services;

import com.bowling.challenge.dao.Game;
import java.util.Map;

public interface BowlingService {
    void displayScores();
    void createRowsToDisplay(Map<String, Game> gameMap);
}
