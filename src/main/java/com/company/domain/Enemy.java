package com.company.domain;

import com.company.util.MakeRandom;

import java.util.Random;

public class Enemy {

    private String id;
    private String name;
    private int health;
    private int attackDamage;

    private Enemy(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.health = MakeRandom.getInstance().get(GameConstants.ENEMY_MAX_HEALTH) + 1;
        this.attackDamage = MakeRandom.getInstance().get(GameConstants.ENEMY_MAX_ATTACK_DAMAGE) + 1;
    }

    public static Builder newEnemy() {
        return new Builder();
    }


    public static final class Builder {
        private String name;
        private String id;

        private Builder() {
        }

        public Enemy build() {
            return new Enemy(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }
    }

    public int receivedDamage(int health) {
        return this.health -= health;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Enemy " + ((this.health < 1) ? "dead" : "alive") + " : " +
                "name='"
                + name + '\''
                + ", health=" + health
                + ", attackDamage=" + attackDamage;
    }
}
