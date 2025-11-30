package com.game;

import java.util.*;
import java.util.logging.Logger;

/**
 * Class untuk menyimpan statistik pertarungan.
 * Tracks win/loss records dan menghasilkan leaderboard.
 */
public class BattleStats {
    private static final Logger LOGGER = Logger.getLogger(BattleStats.class.getName());
    private Map<String, Integer> wins;
    private Map<String, Integer> losses;
    private int totalBattles;

    public BattleStats() {
        this.wins = new HashMap<>();
        this.losses = new HashMap<>();
        this.totalBattles = 0;
    }

    /**
     * Mencatat kemenangan untuk karakter.
     */
    public void recordWin(String characterName) {
        wins.put(characterName, wins.getOrDefault(characterName, 0) + 1);
        totalBattles++;
    }

    /**
     * Mencatat kekalahan untuk karakter.
     */
    public void recordLoss(String characterName) {
        losses.put(characterName, losses.getOrDefault(characterName, 0) + 1);
    }

    /**
     * Mendapatkan total pertarungan.
     */
    public int getTotalBattles() {
        return totalBattles;
    }

    /**
     * Mendapatkan jumlah kemenangan untuk karakter.
     */
    public int getWins(String characterName) {
        return wins.getOrDefault(characterName, 0);
    }

    /**
     * Mendapatkan jumlah kekalahan untuk karakter.
     */
    public int getLosses(String characterName) {
        return losses.getOrDefault(characterName, 0);
    }

    /**
     * Menampilkan top winners.
     */
    public void displayTopWinners() {
        if (wins.isEmpty()) {
            LOGGER.info("  Belum ada kemenangan.");
            return;
        }

        // Sort by wins
        List<Map.Entry<String, Integer>> sortedWins = new ArrayList<>(wins.entrySet());
        sortedWins.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedWins) {
            String name = entry.getKey();
            int winCount = entry.getValue();
            int lossCount = losses.getOrDefault(name, 0);
            double winRate = (double) winCount / (winCount + lossCount) * 100;

            String medal = "";
            if (rank == 1) medal = "🥇";
            else if (rank == 2) medal = "🥈";
            else if (rank == 3) medal = "🥉";
            else medal = "  ";

            String output = String.format("  %s #%d %-20s | W: %3d | L: %3d | Win Rate: %.1f%%", 
                medal, rank, name, winCount, lossCount, winRate);
            LOGGER.info(output);
            rank++;
        }
    }

    /**
     * Mendapatkan semua karakter yang pernah menang.
     */
    public Set<String> getAllCharacters() {
        Set<String> all = new HashSet<>(wins.keySet());
        all.addAll(losses.keySet());
        return all;
    }
}
