package com.company.manager;

import com.company.domain.Player;
import com.company.util.ChoiceScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.company.domain.GameConstants.CONTINUE;
import static com.company.domain.GameConstants.EXIT;

public final class GameEngineManager {

    private static GameEngineManager gameEngineManager;
    private Map<String, Supplier<Boolean>> menuActions = new HashMap<>();
    private SelectEnemyManager selectEnemyManager;
    private Player player;

    //menu option constants for readability
    protected final static String EXIT_GAME_OPTION = "1";
    protected final static String SELECT_ENEMY_START_FIGHT_OPTION = "2";
    protected final static String RESUME_GAME_OPTION = "3";

    public static synchronized GameEngineManager getInstance() {
        if (gameEngineManager != null) {
            return gameEngineManager;
        }
        return gameEngineManager = new GameEngineManager();
    }

    //singleton
    private GameEngineManager() {
    }

    /***
     * With this logic you can chose
     * 1 .EXIT GAME
     * 2. SELECT_ENEMY_START_FIGHT_OPTION
     *    With this the enemy selection process start
     * 3. RESUME
     *    If you have 3 saved games. It will resume your latest saved game then removed it to 'Saved Store'.
     *    Then it will do the same thing for rest of saved games
     *
     * @param player
     * @return
     */
    public boolean play(Player player) {
        init(player);
        initMenuActions();
        String selectGameOption = selectGameMenuOption();
        //execute the menu action ....  look => initMenuActions method
        Boolean continueToRun = menuActions.get(selectGameOption).get();
        return continueToRun;
    }

    private void init(Player player) {
        this.player = player;
        selectEnemyManager = SelectEnemyManager.getInstance();
    }

    private void initMenuActions() {
        menuActions.put(EXIT_GAME_OPTION, () -> EXIT);
        menuActions.put(SELECT_ENEMY_START_FIGHT_OPTION, () -> {
            //WITH this method RESUME=FALSE
            selectEnemyManager.settleEnemyAndFight(this.player, Boolean.FALSE);
            return CONTINUE;
        });
        menuActions.put(RESUME_GAME_OPTION, () -> {
            //RESUME GAME! RESUME=TRUE
            selectEnemyManager.settleEnemyAndFight(this.player, Boolean.TRUE);
            return CONTINUE;
        });
    }

    private String selectGameMenuOption() {
        String message = "" +
                "\t+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n" +
                "\tHello " + this.player.getName() + "\n" +
                "\thealth : " + this.player.getHealth() + "\n" +
                "\tattack strength : " + this.player.getAttackDamage() + "\n" +
                "\thealth elixir : " + this.player.getElixirCount() + "\n" +
                "\tWhat would you like to do?\n" +
                "\t1. Exit\n" +
                "\t2. Select an enemy and start fighting\n" +
                "\t3. Resume game\n" +
                "";
        ChoiceScanner askUserForEnemySelection = new ChoiceScanner(message, "1|2|3");
        String option = askUserForEnemySelection.askUserForNumberInput();
        return option;
    }

}
