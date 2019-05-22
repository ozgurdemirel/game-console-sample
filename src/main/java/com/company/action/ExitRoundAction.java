package com.company.action;

import com.company.domain.Round;

public class ExitRoundAction implements Action {
    @Override
    public Boolean execute(Round round) {
        round.setRunning(Boolean.FALSE);
        return round.isRunning();
    }
}
