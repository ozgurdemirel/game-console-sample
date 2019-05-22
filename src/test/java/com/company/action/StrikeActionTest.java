package com.company.action;

import com.company.TestUtils;
import com.company.domain.Round;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class StrikeActionTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private StrikeAction strikeAction = new StrikeAction();

    @Test
    public void shouldPlayerWinGame() {
        Round round = TestUtils.round();
        round.getPlayer().setAttackDamage(1000); //to win quick
        round.getEnemy().setHealth(1);
        strikeAction.execute(round);
        assertThat(systemOutRule.getLog(), containsString("Congrats you beat the enemy"));
    }

    @Test
    public void shouldPlayerLostGame() {
        Round round = TestUtils.round();
        round.getPlayer().setHealth(1); //to lost quick
        round.getEnemy().setAttackDamage(1111);
        strikeAction.execute(round);
        assertThat(systemOutRule.getLog(), containsString("You are too weak to continue"));

    }
}