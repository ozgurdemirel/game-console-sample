package com.company.manager;

import com.company.domain.Enemy;
import com.company.domain.Player;
import com.company.domain.Round;
import com.company.store.RoundStore;
import com.company.util.EnemyProvider;
import com.company.util.ChoiceScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.company.domain.GameConstants.CONTINUE;
import static com.company.domain.GameConstants.EXIT;
import static com.company.util.ChoiceScanner.p;

public class SelectEnemyManager {

    private EnemyProvider enemyProvider = EnemyProvider.getInstance();
    private RoundStore roundStore = RoundStore.getInstance();
    private Map<String, Supplier<Boolean>> roundActions = new HashMap<>();
    private Player player;
    private static SelectEnemyManager instance;
    private FightManager fightManager = FightManager.getInstance();

    //menu option constants for readability
    protected static final String EXIT_ROUND_OPTION = "0";
    protected static final String START_ROUND_OPTION = "1";

    public static synchronized SelectEnemyManager getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new SelectEnemyManager();
    }

    private SelectEnemyManager() {
    }

    /***
     * Round pre - initialization is starting here with choosing enemy
     * IF RESUME=FALSE
     * you can get an enemy and start a round unless you dont have a saved game with this enemy
     * Game is forcing you to finish uncompleted game first (business decision)
     * END
     *
     * IF RESUME=TRUE
     * it is checking the store with your username,
     * finding a round and removing it on the saved-game-list
     * END
     * @param player
     * @param resume
     * @return
     */
    public boolean settleEnemyAndFight(Player player, boolean resume) {
        init(player);
        //todo : we can check all enemies status and exit game for further request :) keep it agile
        Enemy enemy = settleEnemy(player, resume);
        initRoundActions(enemy);

        if (enemy == null) return EXIT;

        //force player to finish saved game with enemy
        if (!resume && roundStore.hasUnCompletedGame(player.getName(), enemy.getName())) {
            p("\t!!!!!!You have an uncompleted game with this enemy, please choose resume first!\n");
            return EXIT;
        }

        String message = "" +
                "\t0.exit -(game will be aborted)\n" +
                "\t1.start fighting\n";

        ChoiceScanner startFightOrExit = new ChoiceScanner(message, "0|1");
        String option = startFightOrExit.askUserForNumberInput();
        return roundActions.get(option).get();
    }

    private void initRoundActions(Enemy enemy) {
        roundActions.put(EXIT_ROUND_OPTION, () -> EXIT);
        roundActions.put(START_ROUND_OPTION, () -> {
            //start fight
            p("\tRound started\n");
            fightManager.startRound(player, enemy);
            return CONTINUE;
        });
    }

    private Enemy settleEnemy(Player player, boolean resume) {
        Enemy enemy;
        if (!resume) {
            enemy = printEnemiesAndGotSelection();
            p("\tSelected enemy => " + enemy.getName() + "\n");
        } else {
            //resume functionality
            //latest unfinished round
            int unCompletedRoundsCount = roundStore.getUnCompletedRoundsCount(player.getName());
            if (unCompletedRoundsCount < 1) {
                p("\t!!!!!!You don't have saved game!\n");
                return null;
            }
            p("\tYou have " + unCompletedRoundsCount + " saved game\n");
            Round round = roundStore.pop(player.getName());
            round.setRunning(true);
            enemy = enemyProvider.get(round.getEnemy().getId());
            p("\tThe round resumed with enemy : " + enemy.getName() + "\n");
        }

        return enemy;
    }

    private void init(Player player) {
        this.player = player;
    }

    private Enemy printEnemiesAndGotSelection() {
        Enemy enemy = askEnemy();

        boolean running = true;
        while (running) {
            //will try to refactor ? any better solution ? validator maybe?
            // i left it for lack of time ...
            if (enemy.getHealth() < 1) {
                p("\t!!!!!!You already beat this enemy. Please chose new one!\n");
                enemy = askEnemy();
                running = true;
            } else {
                running = false;
            }
        }
        return enemy;
    }

    private Enemy askEnemy() {
        Enemy enemy;
        p("\tList of enemies....\n");
        enemyProvider.getEnemies().forEach((k, v) -> p("\t" + k + ", " + v.toString()));
        String message = "\tPlease select an Enemy\n";
        ChoiceScanner choiceScanner = new ChoiceScanner(message, "1|2|3|4|5|6|7|8|9");

        String option = choiceScanner.askUserForNumberInput();
        enemy = enemyProvider.get(option);
        return enemy;
    }


}
