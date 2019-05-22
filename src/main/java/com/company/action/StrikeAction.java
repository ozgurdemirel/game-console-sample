package com.company.action;

import com.company.domain.Round;
import com.company.store.RoundStore;
import com.company.util.MakeRandom;

import static com.company.util.ChoiceScanner.p;

public class StrikeAction implements Action {

    private MakeRandom random = MakeRandom.getInstance();
    private RoundStore roundStore = RoundStore.getInstance();

    @Override
    public Boolean execute(Round round) {
        int damageToEnemy = random.get(round.getPlayer().getAttackDamage());
        int damageFromEnemy = random.get(round.getEnemy().getAttackDamage());

        int playerHealth = round.getPlayer().receivedDamage(damageFromEnemy);
        int enemyHealth = round.getEnemy().receivedDamage(damageToEnemy);

        if (playerHealth < 1) {
            p("\tYou are too weak to continue\n" +
                    "\tYour health point : " + round.getPlayer().getHealth() + "\n" +
                    "\tEnemy's health point :" + round.getEnemy().getHealth() + "\n");
            round.setRunning(false);
            round.setPlayerWin(Boolean.FALSE);
            roundStore.save(round);
        }

        if (enemyHealth < 1) {
            p("\tCongrats you beat the enemy!!!!!!!!!!!\n" +
                    "\tYour health point : " + round.getPlayer().getHealth() + "\n" +
                    "\tEnemy's health point :" + round.getEnemy().getHealth() + "\n");
            round.setRunning(false);
            round.setPlayerWin(Boolean.TRUE);
            roundStore.save(round);
        }

        return round.isRunning();
    }
}
