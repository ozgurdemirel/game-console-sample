package com.company.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class EnemyProviderTest {

    @Test
    public void shouldEnemiesExistOnMap() {
        assertTrue(EnemyProvider.getInstance().getEnemies().size() > 0);
    }

    @Test
    public void shouldGetEnemiesById() {
        Random random = new Random();
        int sizeEnemy = EnemyProvider.getInstance().getEnemies().size() + 1;
        String id = random.nextInt(sizeEnemy) + "";
        assertNotNull(EnemyProvider.getInstance().get(id));
    }

}