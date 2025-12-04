package com.game.battle;

import com.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class untuk mencatat setiap aksi yang terjadi dalam pertarungan.
 * 
 * BattleLog menyimpan semua BattleAction yang terjadi selama battle
 * dan menyediakan berbagai cara untuk menampilkan log tersebut.
 * Digunakan untuk membuat battle terlihat hidup dan interaktif.
 * 
 * Fitur:
 * - Menyimpan semua aksi battle
 * - Batasan ukuran log (MAX_BATTLE_LOG_ENTRIES)
 * - Display aksi terakhir (live feedback)
 * - Display N aksi terakhir (recap)
 * - Display full log
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BattleAction
 */
public class BattleLog {

    private static final Logger LOGGER = Logger.getLogger(BattleLog.class.getName());

    /** List semua aksi yang terjadi */
    private List<BattleAction> actions;

    /** Maksimal ukuran log untuk mencegah memory overflow */
    private static final int MAX_LOG_SIZE = Constants.MAX_BATTLE_LOG_ENTRIES;

    /**
     * Konstruktor untuk membuat battle log baru.
     * Inisialisasi dengan list kosong.
     */
    public BattleLog() {
        this.actions = new ArrayList<>();
    }

    /**
     * Menambahkan aksi ke log.
     * Jika ukuran log melebihi MAX_LOG_SIZE, aksi terlama akan dihapus.
     * 
     * @param action Aksi yang akan ditambahkan
     */
    public void addAction(BattleAction action) {
        actions.add(action);
        // Batasi ukuran log agar tidak terlalu besar
        if (actions.size() > MAX_LOG_SIZE) {
            actions.remove(0);
        }
    }

    /**
     * Mendapatkan N aksi terakhir dari log.
     * 
     * @param count Jumlah aksi yang ingin diambil
     * @return List berisi N aksi terakhir
     */
    public List<BattleAction> getLastActions(int count) {
        int startIdx = Math.max(0, actions.size() - count);
        return new ArrayList<>(actions.subList(startIdx, actions.size()));
    }

    /**
     * Menampilkan aksi terakhir yang terjadi.
     * Digunakan untuk live feedback setelah setiap action.
     */
    public void displayLastAction() {
        if (!actions.isEmpty()) {
            BattleAction lastAction = actions.get(actions.size() - 1);
            String actionStr = lastAction.toString();
            LOGGER.info(actionStr);
        }
    }

    /**
     * Menampilkan N aksi terakhir dengan format recap.
     * 
     * @param count Jumlah aksi yang ingin ditampilkan
     */
    public void displayLastActions(int count) {
        LOGGER.info("\n========== BATTLE RECAP ==========");
        List<BattleAction> lastActions = getLastActions(count);
        for (BattleAction action : lastActions) {
            String actionStr = action.toString();
            LOGGER.info(actionStr);
        }
        LOGGER.info("==================================\n");
    }

    /**
     * Menampilkan seluruh log pertarungan dari awal hingga akhir.
     */
    public void displayFullLog() {
        LOGGER.info("\n========== FULL BATTLE LOG ==========");
        for (BattleAction action : actions) {
            String actionStr = action.toString();
            LOGGER.info(actionStr);
        }
        LOGGER.info("====================================\n");
    }

    /**
     * Menghapus semua log.
     * Digunakan untuk pertarungan baru.
     */
    public void clear() {
        actions.clear();
    }

    /**
     * Mendapatkan jumlah aksi dalam log.
     * 
     * @return Jumlah aksi yang tercatat
     */
    public int getActionCount() {
        return actions.size();
    }
}
