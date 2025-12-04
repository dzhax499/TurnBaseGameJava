package com.game.battle;

import com.game.battle.subsystems.*;
import com.game.characters.BaseCharacter;
import java.util.logging.Logger;

/**
 * Facade untuk sistem pertarungan yang mengelola seluruh alur battle.
 * 
 * Class ini mengimplementasikan Facade Pattern dengan mendelegasikan
 * semua business logic ke subsystems (TurnManager, CombatResolver,
 * VictoryConditionChecker, BattleLog). Menyediakan interface sederhana
 * untuk mengontrol pertarungan.
 * 
 * Alur battle:
 * 1. start() - Inisialisasi battle dan tentukan turn order
 * 2. executePlayerAction() - Eksekusi skill pemain
 * 3. endTurn() - Pindah ke giliran berikutnya
 * 4. isBattleFinished() - Cek apakah battle selesai
 * 5. getWinner() - Dapatkan pemenang
 * 
 * Subsystems yang digunakan:
 * - TurnManager: Mengelola turn order dan switching
 * - CombatResolver: Mengeksekusi combat actions
 * - VictoryConditionChecker: Memeriksa kondisi kemenangan
 * - BattleLog: Mencatat semua aksi battle
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see TurnManager
 * @see CombatResolver
 * @see VictoryConditionChecker
 * @see BattleLog
 */
public class Battle {

    private static final Logger LOGGER = Logger.getLogger(Battle.class.getName());

    /** Pemain pertama */
    private BaseCharacter player1;

    /** Pemain kedua */
    private BaseCharacter player2;

    /** Subsystem untuk mengelola turn order */
    private TurnManager turnManager;

    /** Subsystem untuk mengeksekusi combat */
    private CombatResolver combatResolver;

    /** Subsystem untuk memeriksa kondisi kemenangan */
    private VictoryConditionChecker victoryChecker;

    /** Log untuk mencatat semua aksi battle */
    private BattleLog battleLog;

    /**
     * Konstruktor untuk membuat battle baru antara dua pemain.
     * Inisialisasi semua subsystems yang diperlukan.
     * 
     * @param player1 Pemain pertama
     * @param player2 Pemain kedua
     * @throws IllegalArgumentException jika salah satu player null
     */
    public Battle(BaseCharacter player1, BaseCharacter player2) {
        // Validation
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Player tidak boleh null");
        }

        this.player1 = player1;
        this.player2 = player2;

        // Initialize subsystems
        this.turnManager = new TurnManager();
        this.combatResolver = new CombatResolver();
        this.victoryChecker = new VictoryConditionChecker();
        this.battleLog = new BattleLog();
    }

    /**
     * Memulai battle dengan menampilkan intro dan menentukan turn order.
     * Turn order ditentukan berdasarkan speed karakter.
     */
    public void start() {
        // Delegate to TurnManager untuk intro dan turn order
        turnManager.displayBattleIntro(player1, player2, LOGGER);

        // Determine turn order
        turnManager.determineTurnOrder(player1, player2);

        // Start battle
        turnManager.startBattle();

        // Display turn info
        turnManager.displayTurnOrderInfo(player1, player2, LOGGER);
    }

    /**
     * Mengeksekusi action pemain dengan skill yang dipilih.
     * 
     * @param skillIndex Index skill yang dipilih (1-based)
     * @return true jika action berhasil, false jika perlu re-selection
     */
    public boolean executePlayerAction(int skillIndex) {
        BaseCharacter attacker = getCurrentPlayer();
        BaseCharacter defender = getOpponentPlayer();

        // Delegate to CombatResolver
        return combatResolver.executeAction(attacker, defender, skillIndex, battleLog, LOGGER);
    }

    /**
     * Pemain saat ini menyerah.
     * Lawan otomatis menang dan battle berakhir.
     */
    public void surrender() {
        BaseCharacter surrenderer = getCurrentPlayer();
        BaseCharacter opponent = getOpponentPlayer();

        // Delegate to VictoryConditionChecker
        victoryChecker.handleSurrender(surrenderer, opponent, battleLog, LOGGER);

        // Finish battle in TurnManager
        turnManager.finishBattle();
    }

    /**
     * Mengakhiri giliran saat ini dan pindah ke giliran berikutnya.
     * Jika kembali ke Player1, regenerasi FP untuk kedua pemain.
     */
    public void endTurn() {
        // Delegate to TurnManager
        turnManager.switchTurn();

        // Regenerate FP after full cycle
        if (turnManager.getCurrentState() == BattleState.PLAYER1_TURN) {
            turnManager.regenerateFP(player1, player2);
        }
    }

    /**
     * Mengecek apakah battle sudah selesai.
     * Battle selesai jika ada yang HP-nya 0 atau ada yang surrender.
     * 
     * @return true jika battle sudah selesai
     */
    public boolean isBattleFinished() {
        // Delegate to VictoryConditionChecker
        return victoryChecker.isBattleFinished(
                player1,
                player2,
                turnManager.getCurrentState());
    }

    /**
     * Mendapatkan pemenang battle.
     * 
     * @return Karakter yang menang, atau null jika battle belum selesai
     */
    public BaseCharacter getWinner() {
        // Delegate to VictoryConditionChecker
        return victoryChecker.determineWinner(player1, player2);
    }

    /**
     * Menampilkan seluruh battle log ke console.
     */
    public void displayBattleLog() {
        battleLog.displayFullLog();
    }

    /**
     * @return Pemain pertama
     */
    public BaseCharacter getPlayer1() {
        return player1;
    }

    /**
     * @return Pemain kedua
     */
    public BaseCharacter getPlayer2() {
        return player2;
    }

    /**
     * @return State battle saat ini
     */
    public BattleState getCurrentState() {
        return turnManager.getCurrentState();
    }

    /**
     * @return Jumlah turn yang telah berlalu
     */
    public int getTurnCount() {
        return turnManager.getTurnCount();
    }

    /**
     * @return Battle log
     */
    public BattleLog getBattleLog() {
        return battleLog;
    }

    /**
     * Mendapatkan pemain yang sedang aktif saat ini.
     * 
     * @return Karakter yang sedang giliran
     * @throws IllegalStateException jika battle state invalid
     */
    public BaseCharacter getCurrentPlayer() {
        BattleState state = turnManager.getCurrentState();
        if (state == BattleState.PLAYER1_TURN) {
            return player1;
        } else if (state == BattleState.PLAYER2_TURN) {
            return player2;
        }
        throw new IllegalStateException("Invalid battle state: " + state);
    }

    /**
     * Mendapatkan lawan dari pemain yang sedang aktif.
     * 
     * @return Karakter lawan
     */
    public BaseCharacter getOpponentPlayer() {
        return turnManager.getCurrentState() == BattleState.PLAYER1_TURN ? player2 : player1;
    }

    /**
     * @return Nama pemain yang sedang aktif
     */
    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    /**
     * Mengecek apakah battle sudah dimulai.
     * 
     * @return true jika battle sudah dimulai
     */
    public boolean hasStarted() {
        return turnManager.getCurrentState() != BattleState.WAITING;
    }
}