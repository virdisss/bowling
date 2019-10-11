package com.bowling.challenge.dao;

public interface Frame {
    boolean strike();
    boolean spare();
    boolean complete();
    int getScore();
    int getChanceCardinal(int chanceIndex);
    String getChanceValue(int chanceIndex);
    int getExtraScore();
    void setRollScore(int cardinal, String value);
}
