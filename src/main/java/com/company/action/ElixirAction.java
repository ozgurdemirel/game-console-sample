package com.company.action;

import com.company.domain.Round;

import static com.company.util.ChoiceScanner.p;

public class ElixirAction implements Action {

    @Override
    public Boolean execute(Round round) {
        if (round.getPlayer().getElixirCount() > 0) {
            round.getPlayer().drinkHealthPotion();
            p("\tyou drink a health elixir\n");
            p("\tyour new health : " + round.getPlayer().getHealth() + "\n");
            p("\tyour elixir count : " + round.getPlayer().getElixirCount() + "\n");
        } else {
            p("you have no elixir for healing. play more for get one!\n");
        }
        return round.isRunning();
    }
}
