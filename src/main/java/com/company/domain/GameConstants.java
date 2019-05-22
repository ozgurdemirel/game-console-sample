package com.company.domain;

public final class GameConstants {

    //player variables
    public static final int PLAYER_HEALTH = 100;
    public static final int PLAYER_MAX_ATTACK_DAMAGE = 50;
    public static final int PLAYER_HEALTH_ELIXIR_COUNT = 3;

    //enemy variables
    public static final int ENEMY_MAX_HEALTH = 75;
    public static final int ENEMY_MAX_ATTACK_DAMAGE = 25;

    //elixir variables
    public static final int ELIXIR_HEAL_AMOUNT = 30;
    public static final int ELIXIR_DROP_CHANCE_PERCENTAGE = 70;

    //game state variables
    public static final Boolean EXIT = Boolean.FALSE;
    public static final Boolean CONTINUE = Boolean.TRUE;
    public static int HEALTH_WON_GAME_EXPERIENCE_POINT = 30;
    public static int HEALTH_LOST_GAME_EXPERIENCE_POINT = 17;


    private GameConstants() {
    }
}
