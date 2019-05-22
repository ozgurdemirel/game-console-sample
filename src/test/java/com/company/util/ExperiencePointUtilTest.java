package com.company.util;

import com.company.TestUtils;
import com.company.domain.Round;
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

import static com.company.domain.GameConstants.HEALTH_LOST_GAME_EXPERIENCE_POINT;
import static com.company.domain.GameConstants.HEALTH_WON_GAME_EXPERIENCE_POINT;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MakeRandom.class})
public class ExperiencePointUtilTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
    @InjectMocks
    private ExperiencePointUtil experiencePointUtil;
    @Mock
    private MakeRandom makeRandom;

    @Before
    public void before() {
        PowerMockito.mockStatic(MakeRandom.class);
        PowerMockito.when(MakeRandom.getInstance()).thenReturn(makeRandom);
    }

    @Test
    public void shouldAddExperiencePointWhenRoundWonByPlayer() {
        Round round = TestUtils.round();
        round.setRunning(false);
        round.setPlayerWin(Boolean.TRUE);
        int firstHealthPoint = round.getPlayer().getHealth();

        experiencePointUtil.addExperiencePointToPlayer(round);

        int latestHealth = round.getPlayer().getHealth();
        assertTrue((latestHealth - HEALTH_WON_GAME_EXPERIENCE_POINT) == firstHealthPoint);
        String expectedOut = "congrats you win " + HEALTH_WON_GAME_EXPERIENCE_POINT + " experience points";
        assertThat(systemOutRule.getLog(), containsString(expectedOut));
    }

    @Test
    public void shouldAddExperiencePointWhenRoundLostByPlayer() { //lest health point than winnings...
        Round round = TestUtils.round();
        round.setRunning(false);
        round.setPlayerWin(Boolean.FALSE);
        int firstHealthPoint = round.getPlayer().getHealth();

        experiencePointUtil.addExperiencePointToPlayer(round);

        int latestHealth = round.getPlayer().getHealth();
        assertTrue((latestHealth - HEALTH_LOST_GAME_EXPERIENCE_POINT) == firstHealthPoint);
        String expectedOut = "congrats you win " + HEALTH_LOST_GAME_EXPERIENCE_POINT + " experience points";
        assertThat(systemOutRule.getLog(), containsString(expectedOut));
    }

    @Test
    public void shouldGotElixirByDropChancePercentage() {

        Round round = TestUtils.round();
        int firstElixirCount = round.getPlayer().getElixirCount();

        PowerMockito.when(makeRandom.get(100)).thenReturn(1);//this value will cause dropping elixir

        experiencePointUtil.addElixirToPlayer(round);

        int lastElixirCount = round.getPlayer().getElixirCount();

        assertTrue(lastElixirCount > firstElixirCount);
        assertThat(systemOutRule.getLog(),
                containsString("the enemy " + round.getEnemy().getName() + " dropped an elixir, congrats you"));
    }


}