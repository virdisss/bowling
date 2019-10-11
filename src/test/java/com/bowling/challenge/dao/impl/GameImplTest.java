package com.bowling.challenge.dao.impl;

import static com.bowling.challenge.constants.Constants.NUMBER_FRAMES_PER_GAME;
import com.bowling.challenge.dao.Frame;
import com.bowling.challenge.dao.Game;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GameImplTest {
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new GameImpl();
    }

    /**
     * {@link GameImpl#getFrames()}
     */
    @Test
    public void whenCreatingGameTenFrameMustBeAvailable() {
        int framesAvailable = game.getFrames().length;
        assertEquals(framesAvailable, NUMBER_FRAMES_PER_GAME);
    }

    /**
     * {@link GameImpl#setScoreForRoll(int, String)}
     */
    @Test
    public void givenTwoChancesFrameShouldBeCreated() {
        Frame frame = game.getFrames()[0];
        game.setScoreForRoll(5, "5");
        game.setScoreForRoll(2, "2");
        assertEquals(frame.getChanceCardinal(0), 5);
        assertEquals(frame.getChanceCardinal(1), 2);
        assertEquals(frame.getScore(), 7);
    }
}
