package com.game.battle;

import com.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class untuk mencatat setiap aksi yang terjadi dalam pertarungan.
 * Digunakan untuk menampilkan battle yang terlihat hidup dan interaktif.
 */
public class BattleLog {
    private static final Logger LOGGER = Logger.getLogger(BattleLog.class.getName());
    private List<BattleAction> actions;
    private static final int MAX_LOG_SIZE = Constants.MAX_BATTLE_LOG_ENTRIES;

    public BattleLog() {
        this.actions = new ArrayList<>();
    }

    /**
     * Menambahkan aksi ke log.
     */
    public void addAction(BattleAction action) {
        actions.add(action);
        // Batasi ukuran log agar tidak terlalu besar
        if (actions.size() > MAX_LOG_SIZE) {
            actions.remove(0);
        }
    }

    /**
     * Mendapatkan aksi terakhir N buah.
     */
    public List<BattleAction> getLastActions(int count) {
        int startIdx = Math.max(0, actions.size() - count);
        return new ArrayList<>(actions.subList(startIdx, actions.size()));
    }

    /**
     * Menampilkan aksi terakhir yang terjadi (untuk live feedback).
     */
    public void displayLastAction() {
        if (!actions.isEmpty()) {
            BattleAction lastAction = actions.get(actions.size() - 1);
            LOGGER.info(lastAction.toString());
        }
    }

    /**
     * Menampilkan N aksi terakhir.
     */
    public void displayLastActions(int count) {
        LOGGER.info("\n========== BATTLE RECAP ==========");
        List<BattleAction> lastActions = getLastActions(count);
        for (BattleAction action : lastActions) {
            LOGGER.info(action.toString());
        }
        LOGGER.info("==================================\n");
    }

    /**
     * Menampilkan seluruh log pertarungan.
     */
    public void displayFullLog() {
        LOGGER.info("\n========== FULL BATTLE LOG ==========");
        for (BattleAction action : actions) {
            LOGGER.info(action.toString());
        }
        LOGGER.info("====================================\n");
    }

    /**
     * Menghapus semua log (untuk pertarungan baru).
     */
    public void clear() {
        actions.clear();
    }

    /**
     * Mendapatkan jumlah aksi dalam log.
     */
    public int getActionCount() {
        return actions.size();
    }
}
