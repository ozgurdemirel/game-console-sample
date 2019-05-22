package com.company.manager;

import com.company.action.*;
import com.company.domain.Enemy;
import com.company.domain.Player;
import com.company.domain.Round;
import com.company.util.ChoiceScanner;
import com.company.util.ExperiencePointUtil;

import java.util.HashMap;
import java.util.Map;

import static com.company.util.ChoiceScanner.p;

public final class FightManager { //todo : round manager maybe ?

    private Map<String, Action> gameRoundActions = new HashMap<>();
    private static FightManager fightManager;

    //player round options for readability purpose
    private static final String STRIKE_ACTION_OPTION = "1";
    private static final String ELIXIR_ACTION_OPTION = "2";
    private static final String EXIT_ROUND_OPTION = "3";
    private static final String SAVE_ROUND_OPTION = "4";

    private FightManager() {
    }

    public static synchronized FightManager getInstance() {
        if (fightManager != null) {
            return fightManager;
        }
        return fightManager = new FightManager();
    }

    /**
     * Round initialized here and
     * player is choosing round actions for playing game like strike, drinking elixir, save game
     * for round action look initGameRoundActions method
     *
     * @param player
     * @param enemy
     * @return round state with updated round status like isRunning? or Saved?
     */
    public Round startRound(Player player, Enemy enemy) {
        //init round
        initGameRoundActions();
        Round round = Round.newRound().player(player).enemy(enemy).running(true).build();
        String message = status(round);
        ChoiceScanner gameActionChoiceScanner = new ChoiceScanner(message, "1|2|3|4");
        while (round.isRunning()) {
            gameActionChoiceScanner.setPrompt(status(round));
            String gameAction = gameActionChoiceScanner.askUserForNumberInput();
            gameRoundActions.get(gameAction).execute(round);  //check ?
        }
        //  p(status(round));
        p("\tlet's continue to play...");
        if (!round.isRoundAborted()) {
            ExperiencePointUtil.getInstance().addExperiencePointToPlayer(round);
            ExperiencePointUtil.getInstance().addElixirToPlayer(round);
        }
        return round;//its  useful information for test
    }

    private void initGameRoundActions() {
        gameRoundActions.put(STRIKE_ACTION_OPTION, new StrikeAction());//todo : enum ?
        gameRoundActions.put(ELIXIR_ACTION_OPTION, new ElixirAction());
        gameRoundActions.put(EXIT_ROUND_OPTION, new ExitRoundAction());
        gameRoundActions.put(SAVE_ROUND_OPTION, new SaveUncompletedRoundAction());
    }

    private String status(Round round) {
        String message = "" +
                "---------------------------------------------\n" +
                "\tYour health point : " + round.getPlayer().getHealth() + "\n" +
                "\t" + round.getEnemy().getName() + "'s health point :" + round.getEnemy().getHealth() + "\n" +
                "\tWhat would you like to do?\n" +
                "\t1.strike\n" +
                "\t2.drink elixir for health point\n" +
                "\t3.exit round\n" +
                "\t4.save round and exit\n" +
                "\tPlease enter an action\n" +
                "---------------------------------------------\n";
        return message;
    }

}
