package com.company;

import com.company.domain.GameConstants;
import com.company.domain.Player;
import com.company.manager.GameEngineManager;
import com.company.util.ChoiceScanner;
import com.company.util.MakeRandom;

import java.util.Scanner;

import static com.company.util.ChoiceScanner.p;

public class GameEngine {

    /**
     * start point of application
     *
     * @param args
     */
    public static void main(String[] args) {
        //change default values can be implemented further ...
        GameEngine gameEngine = new GameEngine();
        gameEngine.start();
    }

    /**
     * the DOCTOR WHO Enemies app starting with this method
     * Player character crated with some default values like health, attackDamage, elixirCount(extra health point)
     * with creation of player, The main logic 'play' is starting
     */
    public void start() {
        String prompt = "" +
                "\tWelcome to fight with !Doctor Who! enemies " +
                "\n\t=> Enter your character nick name (alphanumeric) to start fight :";
        String alphaNumericRegex = "^[a-zA-Z0-9]*$";
        ChoiceScanner askUserForCharacterName =
                new ChoiceScanner(prompt,
                        alphaNumericRegex, "\tTry again, please enter an alphanumeric (example = testaccount)\n");

        String playerName = askUserForCharacterName.askUserForNumberInput();
        //as a player i want to create a character
        Player player = Player.newPlayer()
                .name(playerName)
                .health(GameConstants.PLAYER_HEALTH)
                .attackDamage(MakeRandom.getInstance().get(GameConstants.PLAYER_MAX_ATTACK_DAMAGE) + 1)
                .elixirCount(GameConstants.PLAYER_HEALTH_ELIXIR_COUNT)
                .build();


        //as a player i want to explore
        GameEngineManager gameEngineManager = GameEngineManager.getInstance();
        boolean applicationRunningCondition = true;
        while (applicationRunningCondition) {
            applicationRunningCondition = gameEngineManager.play(player);
            //p("Are you sure you want to exit"); extendable
        }

        p("bye.....");

        //todo : character can created again and new game can be started
    }

}
