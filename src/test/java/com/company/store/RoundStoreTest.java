package com.company.store;

import com.company.TestUtils;
import com.company.domain.Player;
import com.company.domain.Round;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RoundStore.class})
public class RoundStoreTest {

    @InjectMocks
    private RoundStore roundStore;

    @Before
    public void before() {
        PowerMockito.mockStatic(RoundStore.class);
        PowerMockito.when(RoundStore.getInstance()).thenReturn(roundStore);
    }

    @Test
    public void shouldSaveRound() {
        roundStore.reset();
        Round round = TestUtils.round();
        round.setRunning(Boolean.FALSE);
        round.setSaved(Boolean.TRUE);
        roundStore.save(round);
        assertThat(roundStore.getUnCompletedRoundsCount(round.getPlayer().getName()), is(1));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenNoRoundExist() {
        roundStore.reset();
        Player player = TestUtils.round().getPlayer();
        roundStore.pop(player.getName());
    }

    @Test
    public void shouldPopRoundAndReinitializeMap() {
        roundStore.reset();
        Round round = TestUtils.round();
        round.setRunning(Boolean.FALSE);
        round.setSaved(Boolean.TRUE);
        roundStore.save(round);
        Round roundFromStore = roundStore.pop(round.getPlayer().getName());
        //check popped round
        assertEquals(round.getInitTime(), roundFromStore.getInitTime());
        ///check reinitialization
        assertTrue(roundStore.getUnCompletedRoundsCount(round.getPlayer().getName()) == 0);
    }

    @Test
    public void shouldCheckUnCompletedGame() {
        roundStore.reset();
        Round round = TestUtils.round();
        round.setRunning(Boolean.FALSE);
        round.setSaved(Boolean.TRUE);
        roundStore.save(round);
        int unCompletedRoundsCount = roundStore.getUnCompletedRoundsCount(round.getPlayer().getName());
        assertThat(unCompletedRoundsCount, is(1));
    }


}