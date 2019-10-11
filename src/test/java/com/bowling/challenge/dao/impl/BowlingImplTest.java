package com.bowling.challenge.dao.impl;

import com.bowling.challenge.dao.Bowling;
import com.bowling.challenge.dao.Game;
import com.bowling.challenge.exception.BowlingException;
import com.bowling.challenge.services.impl.BowlingServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class BowlingImplTest {

    private Bowling bowling;

    @Before
    public  void setUp() {
        bowling = new BowlingImpl(new BowlingServiceImpl());
    }

    /**
   * {@link BowlingImpl#loadFrames(java.lang.String)}
     * @throws java.io.IOException
     * @throws com.bowling.challenge.exception.BowlingException
   */
    @Test
    public void givenValidPathShouldLoadFrames() throws IOException, BowlingException {

        List<String> listOfChances = bowling.loadFrames("valid_two_players_game.txt");
        //A Bowling game of two players must have in total at least 20 chances(balls) divided into 20 frames
        assertTrue(listOfChances.size() >= 20);
    }

    /**
     * {@link BowlingImpl#loadFrames(java.lang.String)}
     * @throws java.io.IOException
     * @throws com.bowling.challenge.exception.BowlingException
     */
    @Test(expected = BowlingException.class)
    public void givenValidPathWithInvalidScoreValueShouldNotLoadFrames() throws IOException, BowlingException {
        bowling.loadFrames("invalid_format.txt");
    }

    /**
     * {@link BowlingImpl#loadFrames(java.lang.String)}
     * @throws java.io.IOException
     * @throws com.bowling.challenge.exception.BowlingException
     */
    @Test(expected = IOException.class)
    public void givenInvalidPathShouldNotLoadFrames() throws IOException, BowlingException {
        bowling.loadFrames("/valid_two_players_game.txt");
    }

    /**
     * {@link BowlingImpl#populateGameMap(List)}
     * @throws java.io.IOException
     * @throws com.bowling.challenge.exception.BowlingException
     */
    @Test
    public void givenNotEmptyListOfFramesShouldPopulateGameMap() throws IOException, BowlingException {
        List<String> list = bowling.loadFrames("valid_two_players_game.txt");
        Map<String, Game> gameMap = bowling.populateGameMap(list);
        //Must have 2 players
        assertTrue(gameMap.keySet().size() == 2);
        // Each player's game must have 10 frames.
        gameMap.forEach((s, game) -> assertTrue(game.getFrames().length == 10));
    }
}
