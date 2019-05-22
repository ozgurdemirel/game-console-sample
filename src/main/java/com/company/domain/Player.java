package com.company.domain;

public final class Player {
    //todo : further implementation base class with enemy?

    private String name;
    private int health;
    private int elixirCount;
    private int attackDamage;

    private Player(Builder builder) {
        this.name = builder.name;
        this.health = builder.health;
        this.elixirCount = builder.elixirCount;
        this.attackDamage = builder.attackDamage;
    }

    public static Builder newPlayer() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private int health;
        private int elixirCount;
        private int attackDamage;

        private Builder() {
        }

        public Player build() {
            return new Player(this);
        }

        public Builder name(String playerName) {
            this.name = playerName;
            return this;
        }

        public Builder health(int health) {
            this.health = health;
            return this;
        }

        public Builder elixirCount(int elixirCount) {
            this.elixirCount = elixirCount;
            return this;
        }

        public Builder attackDamage(int maxAttackDamage) {
            this.attackDamage = maxAttackDamage;
            return this;
        }
    }

    public int receivedDamage(int health) {
        return this.health -= health;
    }

    public int addHealthPoint(int health) {
        return this.health += health;
    }

    public void addElixir(int elixirCount) {
        this.elixirCount += elixirCount;
    }

    public void drinkHealthPotion() {
        this.elixirCount--;
        this.health += GameConstants.ELIXIR_HEAL_AMOUNT;//todo: readable?
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

    public void setElixirCount(int elixirCount) {
        this.elixirCount = elixirCount;
    }

    public int getElixirCount() {
        return elixirCount;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", elixirCount=" + elixirCount +
                ", attackDamage=" + attackDamage +
                '}';
    }
}
