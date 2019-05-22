package com.company;

import com.company.domain.Player;
import com.company.manager.GameEngineManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GameEngineManager.class})
public class GameEngineTest {

    @InjectMocks
    private GameEngine gameEngine;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Mock
    private GameEngineManager gameEngineManager;

    @Before
    public void before() {
        PowerMockito.mockStatic(GameEngineManager.class);
        PowerMockito.when(GameEngineManager.getInstance()).thenReturn(gameEngineManager);
    }


    @Test
    public void shouldPrintByWhenApplicationRunningStopped() {
        systemInMock.provideLines("nickName");
        PowerMockito.when(gameEngineManager.play(any(Player.class))).thenReturn(Boolean.FALSE);

        gameEngine.start();

        assertThat(systemOutRule.getLog(), containsString("bye"));

    }

    @Test
    public void shouldPrintWarningMessageWhenNotValidNickName() {
        systemInMock.provideLines("'^^^^^'%23");
        gameEngine.start();

        assertThat(systemOutRule.getLog(), containsString("Try again"));

    }

    @Test
    public void shouldExitWhileLoopOnSecondCall() {
        systemInMock.provideLines("nickName");
        PowerMockito.when(gameEngineManager.play(any(Player.class)))
                .thenReturn(Boolean.TRUE)
                .thenReturn(Boolean.FALSE);

        gameEngine.start();
        verify(gameEngineManager, times(2)).play(any(Player.class));
    }

    @Test
    public void shouldPlayerNickCreatedWithGivenNickName() {
        String playerName = "nickName";
        systemInMock.provideLines(playerName);

        PowerMockito.when(gameEngineManager.play(any(Player.class)))
                .thenReturn(Boolean.FALSE);


        gameEngine.start();

        ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(gameEngineManager).play(captor.capture());
        Player value = captor.getValue();

        assertThat(playerName, is(value.getName()));
    }
}