package com.bowling.challenge.dao.impl;

import static com.bowling.challenge.constants.Constants.NUMBER_FRAMES_PER_GAME;
import static com.bowling.challenge.constants.Constants.PENULTIMATE_FRAME;
import com.bowling.challenge.dao.Frame;
import com.bowling.challenge.dao.Game;

public class GameImpl implements Game {

    private final Frame[] frames;
    private int currentFrameIndex = 0;

    public GameImpl() {
        frames = new Frame[NUMBER_FRAMES_PER_GAME];
        for (int i = 0; i < NUMBER_FRAMES_PER_GAME; i++) {
            frames[i] = new FrameImpl();
        }
    }

    @Override
    public void setScoreForRoll(int score, String value) {
        if (currentFrameIndex < PENULTIMATE_FRAME
                && (frames[currentFrameIndex].strike()
                || frames[currentFrameIndex].complete())) {
            currentFrameIndex++;
        }
        frames[currentFrameIndex].setRollScore(score, value);
    }

    @Override
    public Frame[] getFrames() {
        return frames;
    }
}
