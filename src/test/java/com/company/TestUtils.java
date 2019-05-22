package com.company;

import com.company.domain.Enemy;
import com.company.domain.GameConstants;
import com.company.domain.Player;
import com.company.domain.Round;
import com.company.util.EnemyProvider;
import com.company.util.MakeRandom;

/**
 * dummy entity implementations
 */
public class TestUtils {

    public static Player player() {
        return Player.newPlayer()
                .name("player")
                .health(GameConstants.PLAYER_HEALTH)
                .attackDamage(MakeRandom.getInstance().get(GameConstants.PLAYER_MAX_ATTACK_DAMAGE) + 1)
                .elixirCount(GameConstants.PLAYER_HEALTH_ELIXIR_COUNT)
                .build();
    }

    public static Round round() {
        return Round.newRound().player(player()).enemy(enemy()).running(true).build();
    }

    public static Enemy enemy() {
        return EnemyProvider.getInstance().get("3");//davros
    }

}
