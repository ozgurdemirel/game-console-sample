package com.company.manager;

import com.company.TestUtils;
import com.company.domain.Enemy;
import com.company.domain.Player;
import com.company.domain.Round;
import com.company.store.RoundStore;
import com.company.util.EnemyProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

@RunWith(PowerMockRunner.class)
public class FightManagerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @InjectMocks
    private FightManager fightManager;

    private EnemyProvider enemyProvider;
    private RoundStore roundStore;

    @Before
    public void Before() {
        enemyProvider = EnemyProvider.getInstance();
        roundStore = RoundStore.getInstance();
    }

    @Test
    public void shouldExitWhenEnterAnOptionExit() {
        systemInMock.provideLines("3");//exit round ...
        Enemy enemyDavros = enemyProvider.get("3");
        Player player = player();

        fightManager.startRound(player, enemyDavros);

        assertThat(systemOutRule.getLog(), containsString("let's continue to play."));
    }

    @Test
    public void shouldStrikeMustDecreaseTheEnemysHealthPoint() {
        //im running strike action, player can win at first attack or second, nevertheless
        //enemy's health should be lower than first state
        Player player = player();
        player.setAttackDamage(1000);//to guarantee of strike violence

        Enemy enemyDavros = enemyProvider.get("3");
        int firstHealthPointOfEnemy = enemyDavros.getHealth();
        systemInMock.provideLines("1", "3");//strike action and exit

        Round round = fightManager.startRound(player, enemyDavros);

        int lastStateOfEnemyHeathPoint = round.getEnemy().getHealth();
        assertTrue(firstHealthPointOfEnemy > lastStateOfEnemyHeathPoint);
    }

    @Test
    public void shouldElixirCountDecreasedWhenActionExecuted() {
        Enemy enemyDavros = enemyProvider.get("3");
        //elixir action
        Player player = player();
        int firstElixirCount = player.getElixirCount();
        int firstHealthPoint = player.getHealth();
        systemInMock.provideLines("2", "3");//drink elixir and exit
        fightManager.startRound(player, enemyDavros);

        int lastElixirCount = player.getElixirCount();
        int lastHealthPoint = player.getHealth();

        assertTrue(lastElixirCount < firstElixirCount);
        assertTrue(lastHealthPoint > firstHealthPoint);
    }

    @Test
    public void shouldSaveGameHasInitializedAfterSaveOptionSelected() {
        roundStore.reset();
        Enemy enemyDavros = enemyProvider.get("3");
        Player player = player();

        systemInMock.provideLines("4"); //save_quit
        Round round = fightManager.startRound(player, enemyDavros);

        assertTrue(round.isSaved());
        int unCompletedRoundsCount = roundStore.getUnCompletedRoundsCount(player.getName());

        assertThat(unCompletedRoundsCount, is(1));
    }

    private Player player() {
        return TestUtils.player();
    }
}