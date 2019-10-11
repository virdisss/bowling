package com.bowling.challenge.constants;

public final class Constants {

    public static final int NUMBER_PINS = 10;
    /**
     * Generally, the number of the chances(ball) per frame is 2 but here i take
     * into account the case where we have 2 extra balls when we achieve a strike in the first ball of the 10th frame.
     * And one extra ball if we do achieve a spare, there we pick the maximum value that a frame may possess chances.
     */
    public static final int NUMBER_CHANCES_PER_FRAME = 3;
    public static final int NUMBER_FRAMES_PER_GAME = 10;
    public static final int PENULTIMATE_FRAME = 9;
    public static final String SCORE = "Score";
    public static final String PINFALLS = "Pinfalls";
    public static final String FRAME = "Frame";
    public static final String STRIKE = "X";
    public static final String SPARE = "/";

    private Constants() { }
}
