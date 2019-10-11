package com.bowling.challenge.dao.impl;

import static com.bowling.challenge.constants.Constants.NUMBER_CHANCES_PER_FRAME;
import static com.bowling.challenge.constants.Constants.NUMBER_PINS;
import com.bowling.challenge.model.Chance;
import com.bowling.challenge.dao.Frame;

public class FrameImpl implements Frame {

    private final Chance[] chances;
    private int currentIndex = 0;

    public FrameImpl() { 
        chances = new Chance[NUMBER_CHANCES_PER_FRAME];
        for (int i = 0; i < chances.length; i++) {
            chances[i] = new Chance(0, "0");
        }
    }

    @Override
    public boolean strike() {
        return chances[0].getCardinal() == NUMBER_PINS;
    }

    @Override
    public boolean spare() {
        return chances[0].getCardinal() != NUMBER_PINS 
                && (chances[0].getCardinal() + chances[1].getCardinal()) == NUMBER_PINS;
    }

    @Override
    public boolean complete() {
        return currentIndex == 2;
    }

    @Override
    public int getScore() {
        return chances[0].getCardinal() + chances[1].getCardinal();
    }

    @Override
    public int getChanceCardinal(int chanceIndex) {
        return chances[chanceIndex].getCardinal();
    }

    @Override
    public void setRollScore(int cardinal, String value) {
        chances[currentIndex].setCardinal(cardinal);
        chances[currentIndex].setValue(value);
        currentIndex++;
    }

    @Override
    public int getExtraScore() {
        return chances[1].getCardinal() + chances[2].getCardinal();
    }

    @Override
    public String getChanceValue(int chanceIndex) {
        return chances[chanceIndex].getValue();
    }
}
