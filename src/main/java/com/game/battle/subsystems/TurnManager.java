package com.game.battle.subsystems;

import com.game.battle.BattleState;
import com.game.characters.BaseCharacter;
import com.game.utils.GameStrings;
import com.utils.Constants;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Subsystem untuk mengelola turn order, switching, dan turn counting.
 * 
 * <p>
 * TurnManager bertanggung jawab untuk:
 * <ul>
 * <li>Menentukan urutan giliran berdasarkan speed</li>
 * <li>Mengelola state transisi antar turn</li>
 * <li>Tracking turn count</li>
 * <li>FP regeneration setiap cycle</li>
 * </ul>
 * 
 * <p>
 * Turn order ditentukan oleh speed:
 * <ul>
 * <li>Karakter dengan speed lebih tinggi jalan duluan</li>
 * <li>Jika speed sama, coin flip menentukan</li>
 * </ul>
 * 
 * <p>
 * Turn cycle: PLAYER1_TURN → PLAYER2_TURN → (FP regen) → repeat
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Battle
 * @see BattleState
 */
public class TurnManager {

    /** State pertarungan saat ini */
    private BattleState currentState;

    /** State untuk giliran pertama (ditentukan oleh speed) */
    private BattleState firstTurn;

    /** Counter jumlah turn yang telah berlalu */
    private int turnCount;

    /** Random generator untuk coin flip */
    private static final Random random = new Random();

    /**
     * Konstruktor TurnManager.
     * Inisialisasi dengan state WAITING dan turn count 0.
     */
    public TurnManager() {
        this.currentState = BattleState.WAITING;
        this.turnCount = 0;
    }

    /**
     * Menampilkan intro battle dengan informasi kedua pemain.
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     * @param logger  Logger untuk output
     */
    public void displayBattleIntro(BaseCharacter player1, BaseCharacter player2, Logger logger) {
        logger.info("\n" + GameStrings.BATTLE_START_TITLE);
        logger.info(player1.getName() + " (HP: " + player1.getHealthPoints() + "/"
                + player1.getMaxHealthPoints() + ")");
        logger.info(GameStrings.BATTLE_VS);
        logger.info(player2.getName() + " (HP: " + player2.getHealthPoints() + "/"
                + player2.getMaxHealthPoints() + ")\n");
    }

    /**
     * Menentukan urutan giliran berdasarkan speed.
     * 
     * <p>
     * Logika:
     * <ul>
     * <li>Speed lebih tinggi → jalan duluan</li>
     * <li>Speed sama → coin flip (50/50)</li>
     * </ul>
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     */
    public void determineTurnOrder(BaseCharacter player1, BaseCharacter player2) {
        if (player1.getSpeed() > player2.getSpeed()) {
            firstTurn = BattleState.PLAYER1_TURN;
        } else if (player2.getSpeed() > player1.getSpeed()) {
            firstTurn = BattleState.PLAYER2_TURN;
        } else {
            // Coin flip jika speed sama
            firstTurn = random.nextBoolean() ? BattleState.PLAYER1_TURN : BattleState.PLAYER2_TURN;
        }
    }

    /**
     * Memulai battle dengan mengset state ke giliran pertama.
     * Turn count dimulai dari 1.
     */
    public void startBattle() {
        this.currentState = firstTurn;
        this.turnCount = 1;
    }

    /**
     * Menampilkan informasi urutan giliran kepada pemain.
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     * @param logger  Logger untuk output
     */
    public void displayTurnOrderInfo(BaseCharacter player1, BaseCharacter player2, Logger logger) {
        logger.info("Penentuan Giliran:");
        if (player1.getSpeed() > player2.getSpeed()) {
            logger.info("   " + player1.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else if (player2.getSpeed() > player1.getSpeed()) {
            logger.info("   " + player2.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else {
            logger.info("   Kecepatan sama! Hasil Coin Flip menentukan...\n");
        }
    }

    /**
     * Switch giliran dari satu pemain ke pemain lainnya.
     * 
     * <p>
     * Ketika kembali ke PLAYER1_TURN, turn count di-increment.
     * Ini menandakan satu cycle penuh telah selesai.
     */
    public void switchTurn() {
        if (currentState == BattleState.PLAYER1_TURN) {
            currentState = BattleState.PLAYER2_TURN;
        } else if (currentState == BattleState.PLAYER2_TURN) {
            currentState = BattleState.PLAYER1_TURN;
            turnCount++; // Increment setelah full cycle
        }
    }

    /**
     * Meregenerasi Focus Points untuk kedua pemain.
     * 
     * <p>
     * Dipanggil setiap kali kembali ke PLAYER1_TURN (setelah full cycle).
     * Jumlah regenerasi ditentukan oleh {@link Constants#FP_REGEN_PER_TURN}.
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     */
    public void regenerateFP(BaseCharacter player1, BaseCharacter player2) {
        int fpRegenAmount = Constants.FP_REGEN_PER_TURN;
        player1.regenFocusPoints(fpRegenAmount);
        player2.regenFocusPoints(fpRegenAmount);
    }

    /**
     * Mengakhiri battle dengan mengset state ke FINISHED.
     */
    public void finishBattle() {
        this.currentState = BattleState.FINISHED;
    }

    /**
     * @return State pertarungan saat ini
     */
    public BattleState getCurrentState() {
        return currentState;
    }

    /**
     * @return Jumlah turn yang telah berlalu
     */
    public int getTurnCount() {
        return turnCount;
    }
}
