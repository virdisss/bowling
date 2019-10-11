package com.bowling.challenge.dao;

import com.bowling.challenge.exception.BowlingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Bowling {
    List<String> loadFrames(String path) throws IOException, BowlingException;
    Map<String, Game> populateGameMap(List<String> lines);
    void handleScore();
    void display();
    void init();
}
