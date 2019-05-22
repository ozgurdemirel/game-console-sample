package com.company.manager;

import com.company.TestUtils;
import com.company.domain.GameConstants;
import com.company.domain.Player;
import com.company.util.MakeRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.company.manager.GameEngineManager.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SelectEnemyManager.class})
public class GameEngineManagerTest {

    @Mock
    private SelectEnemyManager selectEnemyManager;
    private Player player;
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @InjectMocks
    private GameEngineManager gameEngineManager;

    @Before
    public void before() {
        player = player();
        PowerMockito.mockStatic(SelectEnemyManager.class);
        PowerMockito.when(SelectEnemyManager.getInstance()).thenReturn(selectEnemyManager);
    }

    @Test
    public void shouldPrintExpectedWelcomeMessageAndExit() {
        systemInMock.provideLines(EXIT_GAME_OPTION);//exit option

        gameEngineManager.play(player);

        assertThat(systemOutRule.getLog(),
                containsString("Select an enemy and start fighting"));
    }

    @Test
    public void shouldSettleEnemyOnSelectEnemyOption() {
        //option
        systemInMock.provideLines(SELECT_ENEMY_START_FIGHT_OPTION);
        //Select an enemy and start fighting

        boolean continueToPlay = gameEngineManager.play(player);

        verify(selectEnemyManager).settleEnemyAndFight(player, Boolean.FALSE);
        assertTrue(continueToPlay);
    }

    @Test
    public void shouldSettleEnemyOnResumeOption() {
        //option resume
        systemInMock.provideLines(RESUME_GAME_OPTION);
        boolean continueToPlay = gameEngineManager.play(player);

        verify(selectEnemyManager).settleEnemyAndFight(player, Boolean.TRUE);
        assertTrue(continueToPlay);
    }

    private Player player() {
        return TestUtils.player();
    }
}