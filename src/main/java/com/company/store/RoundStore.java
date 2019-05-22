package com.company.store;

import com.company.domain.Round;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Disk store and some utilities for round storing
 * saving all completed rounds for further implementations like score board...
 * saving paused rounds as well
 */
public final class RoundStore {

    private Map<String, List<Round>> roundMap = new HashMap<>();
    private static RoundStore instance;

    private RoundStore() {
    }

    public static synchronized RoundStore getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new RoundStore();
    }

    /**
     * saves all round completed  for furher score board etc.. implementation
     * and saves marked as a saved status round for resuming game round
     *
     * @param round
     */
    public void save(Round round) {
        List<Round> rounds = roundMap.get(round.getPlayer().getName());
        if (rounds == null) {
            rounds = new ArrayList<>();
        }
        rounds.add(round);
        roundMap.put(round.getPlayer().getName(), rounds);
    }

    /**
     * implemented for using on  unit tests
     * reset the hashMap store
     */
    public void reset() {
        this.roundMap = new HashMap<>();
    }

    /**
     * this method pop the latest saved game and  remove it on the store
     * working like a stack data structure
     *
     * @param playerName player username for key of storing rounds
     * @return last saved game
     */
    public Round pop(String playerName) {
        //poping latest saved  enemy from list and reinit store
        int unCompletedRoundsCount = getUnCompletedRoundsCount(playerName);
        if (unCompletedRoundsCount == 0) {
            //custom Exception class implementation can be implemented
            //but this case will never happen
            //and better than arrayIndexOutOfBoundsException
            throw new RuntimeException("NotFoundUnCompletedRound");
        }
        Round round = getUnCompletedRounds(playerName).get(unCompletedRoundsCount - 1);
        reInitRoundMap(playerName, round);
        return round;
    }

    /**
     * check has an uncompleted game round with player name and enemy name
     *
     * @param playerNickName
     * @param enemyName
     * @return uncompleted game exist or not exist
     */
    public boolean hasUnCompletedGame(String playerNickName, String enemyName) {
        return getUnCompletedRounds(playerNickName).stream()
                .filter(o -> o.getEnemy().getName() == enemyName)
                .collect(Collectors.toList()).size() > 0;
    }

    /**
     * get players uncompleted round count
     *
     * @param playerName
     * @return
     */
    public int getUnCompletedRoundsCount(String playerName) {
        return getUnCompletedRounds(playerName).size();
    }

    private void reInitRoundMap(String playerName, Round round) {
        List<Round> rounds = roundMap.get(playerName);
        rounds.removeIf(o -> o.getEnemy().getId() == round.getEnemy().getId());
        roundMap.put(playerName, rounds);
    }

    private List<Round> getUnCompletedRounds(String playerName) {
        List<Round> rounds = roundMap.get(playerName);//whole rounds

        Stream<Round> roundStream = Optional.ofNullable(rounds)
                .map(Collection::stream)
                .orElseGet(Stream::empty);

        List<Round> unCompletedRounds = roundStream
                .filter((round) -> round.isSaved()).collect(Collectors.toList());

        return unCompletedRounds;
    }


    //todo : disk persistence might be implemented when necessary
}
