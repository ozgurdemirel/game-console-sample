package com.company.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class ChoiceScannerTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Test
    public void shouldPrintInvalidCommandWhenRegexFailed() {
        systemInMock.provideLines("9");
        ChoiceScanner choiceScanner = new ChoiceScanner("Whats up? select 0,1,2,3", "0|1|2|3");
        choiceScanner.askUserForNumberInput();

    }

}