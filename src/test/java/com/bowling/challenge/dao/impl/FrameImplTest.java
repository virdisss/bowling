package com.bowling.challenge.dao.impl;

import com.bowling.challenge.dao.Frame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FrameImplTest {

    private Frame frame;

    @Before
    public void setUp() {
        frame = new FrameImpl();
    }

    /**
     * {@link FrameImpl#strike()}
     */
    @Test
    public void strikeTest() {
        frame.setRollScore(10, "10");
        assertTrue(frame.strike());
    }

    /**
     * {@link FrameImpl#spare()}
     */
    @Test
    public void spareTest() {
        frame.setRollScore(3, "3");
        frame.setRollScore(7, "7");
        assertTrue(frame.spare());
    }

    /**
     * {@link FrameImpl#complete()}
     */
    @Test
    public void completeTest() {
        frame.setRollScore(3, "3");
        frame.setRollScore(7, "7");
        assertTrue(frame.complete());
    }

    /**
     * {@link FrameImpl#getScore()}
     */
    @Test
    public void getScoreTest() {
        frame.setRollScore(6, "6");
        frame.setRollScore(2, "2");
        assertEquals(frame.getScore(), 8);
    }
}
