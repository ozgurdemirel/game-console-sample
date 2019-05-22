package com.company.util;

import com.company.domain.GameConstants;
import com.company.domain.Round;

import static com.company.domain.GameConstants.HEALTH_LOST_GAME_EXPERIENCE_POINT;
import static com.company.domain.GameConstants.HEALTH_WON_GAME_EXPERIENCE_POINT;
import static com.company.util.ChoiceScanner.p;

public final class ExperiencePointUtil {

    private static ExperiencePointUtil experiencePointUtil;

    private MakeRandom makeRandom = MakeRandom.getInstance();

    public static synchronized ExperiencePointUtil getInstance() {
        if (experiencePointUtil != null) {
            return experiencePointUtil;
        }
        return experiencePointUtil = new ExperiencePointUtil();
    }

    public void addExperiencePointToPlayer(Round round) {
        if (round.getPlayerWin()) {
            p("\tcongrats you win " + HEALTH_WON_GAME_EXPERIENCE_POINT + " experience points," +
                    " added as a health points\n");
            round.getPlayer().addHealthPoint(HEALTH_WON_GAME_EXPERIENCE_POINT);
        } else {
            p("\tcongrats you win " + HEALTH_LOST_GAME_EXPERIENCE_POINT + " experience points," +
                    " added as a health points\n");
            round.getPlayer().addHealthPoint(HEALTH_LOST_GAME_EXPERIENCE_POINT);
        }
    }

    public void addElixirToPlayer(Round round) {
        if (makeRandom.get(100) < GameConstants.ELIXIR_DROP_CHANCE_PERCENTAGE) {
            p("\tthe enemy " + round.getEnemy().getName() + " dropped an elixir, congrats you!");
            round.getPlayer().addElixir(1);
        }
    }
}
