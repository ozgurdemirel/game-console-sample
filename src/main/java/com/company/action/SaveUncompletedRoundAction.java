package com.company.action;

import com.company.domain.Round;
import com.company.store.RoundStore;

import static com.company.util.ChoiceScanner.p;

public class SaveUncompletedRoundAction implements Action {

    RoundStore roundStore = RoundStore.getInstance();

    @Override
    public Boolean execute(Round round) {
        round.setSaved(true);
        round.setRunning(false);
        roundStore.save(round);
        p("\tRound with " + round.getEnemy().getName() + " saved for later...");
        return round.isRunning();//round is still running save and exit game
    }
}
