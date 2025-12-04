package com.game.battle.subsystems;

import com.game.battle.BattleAction;
import com.game.battle.BattleLog;
import com.game.battle.BattleState;
import com.game.characters.BaseCharacter;
import com.game.utils.GameStrings;
import java.util.logging.Logger;

/**
 * Subsystem untuk memeriksa kondisi kemenangan dalam battle.
 * 
 * <p>
 * VictoryConditionChecker bertanggung jawab untuk:
 * <ul>
 * <li>Mendeteksi kondisi kemenangan (HP <= 0 atau surrender)</li>
 * <li>Menentukan pemenang battle</li>
 * <li>Handling surrender action</li>
 * </ul>
 * 
 * <p>
 * Kondisi kemenangan:
 * <ul>
 * <li>HP musuh <= 0 (knockout)</li>
 * <li>Musuh surrender</li>
 * <li>Battle state = FINISHED</li>
 * </ul>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Battle
 * @see BattleState
 */
public class VictoryConditionChecker {

    /** Pemenang jika ada surrender (null jika tidak ada) */
    private BaseCharacter surrenderWinner;

    /**
     * Konstruktor VictoryConditionChecker.
     * Inisialisasi dengan surrenderWinner null.
     */
    public VictoryConditionChecker() {
        this.surrenderWinner = null;
    }

    /**
     * Menangani surrender action dari pemain.
     * 
     * <p>
     * Ketika pemain surrender:
     * <ul>
     * <li>Lawan ditetapkan sebagai pemenang</li>
     * <li>Pesan surrender ditampilkan</li>
     * <li>Action dicatat ke battle log</li>
     * </ul>
     * 
     * @param surrenderer Pemain yang menyerah
     * @param opponent    Lawan yang menang
     * @param battleLog   Battle log untuk recording
     * @param logger      Logger untuk output
     */
    public void handleSurrender(BaseCharacter surrenderer, BaseCharacter opponent,
            BattleLog battleLog, Logger logger) {
        this.surrenderWinner = opponent;

        logger.info(String.format("\n" + GameStrings.MSG_SURRENDERED, surrenderer.getName()));
        logger.info(String.format(GameStrings.MSG_WINNER, opponent.getName()) + "\n");

        BattleAction action = new BattleAction(
                surrenderer.getName(),
                "SURRENDER",
                "Menyerah",
                "");
        battleLog.addAction(action);
    }

    /**
     * Mengecek apakah battle sudah selesai.
     * 
     * <p>
     * Battle dianggap selesai jika:
     * <ul>
     * <li>State = FINISHED (dari surrender)</li>
     * <li>HP salah satu pemain <= 0</li>
     * </ul>
     * 
     * @param player1      Pemain pertama
     * @param player2      Pemain kedua
     * @param currentState State battle saat ini
     * @return true jika battle sudah selesai
     */
    public boolean isBattleFinished(BaseCharacter player1, BaseCharacter player2, BattleState currentState) {
        return (currentState == BattleState.FINISHED)
                || (player1.getHealthPoints() <= 0 || player2.getHealthPoints() <= 0);
    }

    /**
     * Menentukan pemenang battle.
     * 
     * <p>
     * Prioritas penentuan pemenang:
     * <ol>
     * <li>Jika ada surrender, surrenderWinner adalah pemenang</li>
     * <li>Jika HP player1 <= 0, player2 menang</li>
     * <li>Jika HP player2 <= 0, player1 menang</li>
     * <li>Jika tidak ada kondisi di atas, return null (battle belum selesai)</li>
     * </ol>
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     * @return Pemenang battle, atau null jika belum ada pemenang
     */
    public BaseCharacter determineWinner(BaseCharacter player1, BaseCharacter player2) {
        // Cek jika winner sudah ditentukan (surrender case)
        if (surrenderWinner != null) {
            return surrenderWinner;
        }

        // Cek berdasarkan HP
        if (player1.getHealthPoints() <= 0) {
            return player2;
        } else if (player2.getHealthPoints() <= 0) {
            return player1;
        }

        return null; // Belum ada pemenang
    }
}
