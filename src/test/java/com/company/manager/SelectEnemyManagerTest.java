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
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.company.manager.SelectEnemyManager.EXIT_ROUND_OPTION;
import static com.company.manager.SelectEnemyManager.START_ROUND_OPTION;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FightManager.class})
public class SelectEnemyManagerTest {

    private EnemyProvider enemyProvider;
    private RoundStore roundStore;

    @InjectMocks
    private SelectEnemyManager selectEnemyManager;

    private Player player;
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Mock
    private FightManager fightManager;

    private Boolean notResume = Boolean.FALSE;
    private Boolean resume = Boolean.TRUE;

    @Before
    public void before() {
        player = player();
        roundStore = RoundStore.getInstance();
        enemyProvider = EnemyProvider.getInstance();

        PowerMockito.mockStatic(FightManager.class);
        PowerMockito.when(FightManager.getInstance()).thenReturn(fightManager);

    }

    @Test
    public void shouldPrintSelectEnemyMessageAndExit() {
        systemInMock.provideLines("3", EXIT_ROUND_OPTION);//select enemy = davros then exit

        selectEnemyManager.settleEnemyAndFight(player, notResume);

        assertThat(systemOutRule.getLog(), containsString("Selected enemy => " + enemyProvider.get("3").getName()));
        assertThat(systemOutRule.getLog(), containsString("exit -(game will be aborted)"));
    }

    @Test
    public void shouldSelectEnemyAndPreRoundInitialization() {
        systemInMock.provideLines("3", START_ROUND_OPTION); //enter enemy id then start round
        Enemy enemy = enemyProvider.get("3");
        //when
        Round dummyRound = Round.newRound().player(player).enemy(enemy).running(true).build();
        when(fightManager.startRound(player, enemy)).thenReturn(dummyRound);

        //then
        selectEnemyManager.settleEnemyAndFight(player, notResume);

        verify(fightManager).startRound(player, enemy);
        assertThat(systemOutRule.getLog(), containsString("Selected enemy => " + enemyProvider.get("3").getName()));
        assertThat(systemOutRule.getLog(), containsString("Round started"));
        verify(fightManager).startRound(any(Player.class), any(Enemy.class));
    }

    @Test
    public void shouldForcePlayerToFinishRoundWithEnemy() {
        systemInMock.provideLines("3", START_ROUND_OPTION); //enter enemy id then start round
        Enemy enemy = enemyProvider.get("3");

        Round round1 = Round.newRound().player(player).enemy(enemy).running(false).saved(true).build();
        roundStore.reset();
        roundStore.save(round1);

        //when
        Round dummyRound = Round.newRound().player(player).enemy(enemy).running(true).build();
        when(fightManager.startRound(player, enemy)).thenReturn(dummyRound);
        //then
        selectEnemyManager.settleEnemyAndFight(player, notResume);

        assertThat(systemOutRule.getLog(), containsString("You have an uncompleted game with this enemy, "));
    }

    @Test
    public void shouldPrintWarningIfYouDontHaveASavedGAme() {
        roundStore.reset();
        selectEnemyManager.settleEnemyAndFight(player, resume);
        assertThat(systemOutRule.getLog(), containsString("You don't have saved game!"));
    }

    @Test
    public void shouldResumeLastSavedGameAndStartRound() {
        systemInMock.provideLines(START_ROUND_OPTION);
        Enemy enemy = enemyProvider.get("3");
        //created a saved round
        roundStore.reset();
        Round round1 = Round.newRound().player(player).enemy(enemy).running(false).saved(true).build();
        roundStore.save(round1);

        Round dummyRound = Round.newRound().player(player).enemy(enemy).running(true).build();
        when(fightManager.startRound(player, enemy)).thenReturn(dummyRound);

        selectEnemyManager.settleEnemyAndFight(player, resume);

        assertThat(systemOutRule.getLog(), containsString("You have 1 saved game"));
        assertThat(systemOutRule.getLog(), containsString("The round resumed with enemy : " + enemy.getName()));

        //!!!!saved game should be removed on list
        boolean hasUnCompletedGame = roundStore.hasUnCompletedGame(player.getName(), enemy.getName());
        assertFalse(hasUnCompletedGame);
        verify(fightManager).startRound(any(Player.class), any(Enemy.class));
    }

    @Test
    public void shouldPlayLastSavedGamesAndPlayOneOfThem() {
        systemInMock.provideLines(START_ROUND_OPTION); //save or
        Enemy enemy = enemyProvider.get("3");
        Enemy enemy2 = enemyProvider.get("4");
        //created 2 saved rounds
        Round round1 = Round.newRound().player(player).enemy(enemy).running(false).saved(true).build();
        Round round2 = Round.newRound().player(player).enemy(enemy2).running(false).saved(true).build();
        roundStore.reset();

        roundStore.save(round1);
        roundStore.save(round2);

        //when
        Round dummyRound = Round.newRound().player(player).enemy(enemy).running(true).build();
        when(fightManager.startRound(player, enemy)).thenReturn(dummyRound);

        selectEnemyManager.settleEnemyAndFight(player, resume);

        assertThat(systemOutRule.getLog(), containsString("You have 2 saved game"));
        assertThat(systemOutRule.getLog(), containsString("The round resumed with enemy : " + enemy2.getName()));

        //!!!!saved game should be removed on list
        int unCompletedRoundsCount = roundStore.getUnCompletedRoundsCount(player.getName());
        assertThat(unCompletedRoundsCount, is(1)); //you popped one of them
        verify(fightManager).startRound(any(Player.class), any(Enemy.class));
    }

    private Player player() {
        return TestUtils.player();
    }

}