package com.bowling.challenge.dao;

public interface Game {
    void setScoreForRoll(int score, String value);
    Frame[] getFrames();
}
