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

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class ElixirActionTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private ElixirAction elixirAction = new ElixirAction();

    @Test
    public void shouldConsumeElixir() {

        Round round = TestUtils.round();
        int firstElixirCount = round.getPlayer().getElixirCount();
        int firstHealthCount = round.getPlayer().getHealth();

        elixirAction.execute(round);

        int lastElixirCount = round.getPlayer().getElixirCount();
        int lastHealthCount = round.getPlayer().getHealth();

        assertThat(systemOutRule.getLog(), containsString("you drink a health elixir"));
        assertTrue(firstElixirCount > lastElixirCount);
        assertTrue(firstHealthCount < lastHealthCount);
    }

    @Test
    public void shouldPrintNoElixirMessage() {
        Round round = TestUtils.round();
        round.getPlayer().setElixirCount(0);

        elixirAction.execute(round);

        assertThat(systemOutRule.getLog(),
                containsString("you have no elixir for healing. play more for get one"));
    }

}