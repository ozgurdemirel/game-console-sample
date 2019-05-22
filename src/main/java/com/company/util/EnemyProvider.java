package com.company.util;

import com.company.domain.Enemy;

import java.util.HashMap;
import java.util.Map;

public final class EnemyProvider {

    private Map<String, Enemy> enemies = new HashMap<>();
    private static EnemyProvider instance;

    public static synchronized EnemyProvider getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new EnemyProvider();
    }

    private EnemyProvider() {
        //doctor who enemies
        enemies.put("1", Enemy.newEnemy().id("1").name("Dalek").build());
        enemies.put("2", Enemy.newEnemy().id("2").name("Cybermen").build());
        enemies.put("3", Enemy.newEnemy().id("3").name("Davros").build());
        enemies.put("4", Enemy.newEnemy().id("4").name("Omega").build());
        enemies.put("5", Enemy.newEnemy().id("5").name("The Postmen").build());
        enemies.put("6", Enemy.newEnemy().id("6").name("Master").build());
        enemies.put("7", Enemy.newEnemy().id("7").name("The Silence").build());
        enemies.put("8", Enemy.newEnemy().id("8").name("Weeping Angels").build());
        enemies.put("9", Enemy.newEnemy().id("9").name("War Machines").build());
    }

    public Map<String, Enemy> getEnemies() {
        return enemies;
    }

    public Enemy get(String id) {
        return enemies.get(id);
    }


}
