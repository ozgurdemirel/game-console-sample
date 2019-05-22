package com.company.domain;

public class Round {
    private Player player;
    private Enemy enemy;
    private Boolean playerWin;//using null values for aborted games
    private boolean running;
    private boolean saved;
    private long initTime = System.currentTimeMillis();

    private Round(Builder builder) {
        this.player = builder.player;
        this.enemy = builder.enemy;
        this.playerWin = builder.playerWin;
        this.running = builder.running;
        this.saved = builder.saved;
    }

    public static Builder newRound() {
        return new Builder();
    }


    public static final class Builder {
        private Player player;
        private Enemy enemy;
        private Boolean playerWin;//using null values for aborted games
        private boolean running;
        private boolean saved;

        private Builder() {
        }

        public Round build() {
            return new Round(this);
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder enemy(Enemy enemy) {
            this.enemy = enemy;
            return this;
        }

        public Builder playerWin(Boolean playerWin) {
            this.playerWin = playerWin;
            return this;
        }

        public Builder running(boolean running) {
            this.running = running;
            return this;
        }

        public Builder saved(boolean saved) {
            this.saved = saved;
            return this;
        }
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPlayerWin(Boolean playerWin) {
        this.playerWin = playerWin;
    }

    public boolean isRoundAborted() {
        return this.playerWin == null;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean getPlayerWin() {
        return playerWin;
    }

    public boolean isRunning() {
        return running;
    }

    public long getInitTime() {
        return initTime;
    }

    @Override
    public String toString() {
        return "Round{" +
                "player=" + player +
                ", enemy=" + enemy +
                ", playerWin=" + playerWin +
                ", running=" + running +
                '}';
    }
}
