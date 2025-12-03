package com.game.battle;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.utils.GameStrings;
import com.utils.Constants;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Class utama yang menangani logika pertarungan 1 vs 1.
 * 
 * Fitur:
 * - Turn-based system dengan penentuan giliran berdasarkan speed
 * - Sistem skill dengan FP (Focus Points)
 * - Status effects (Burn, Freeze, dll)
 * - Elemental advantage/disadvantage
 * - Battle logging untuk live feedback
 * - Victory conditions (opponent HP = 0 atau surrender)
 */
public class Battle {

    private static final Logger LOGGER = Logger.getLogger(Battle.class.getName());

    // Karakter yang bertarung
    private BaseCharacter player1;
    private BaseCharacter player2;

    // State management
    private BattleState currentState;
    private BattleState firstTurn; // Siapa yang jalan duluan (PLAYER1_TURN atau PLAYER2_TURN)
    private int turnCount;

    // Winner tracking (untuk surrender case)
    private BaseCharacter winner;

    // Logging
    private BattleLog battleLog;

    // Display Delegate
    private BattleDisplay display;

    // Random untuk spin/coin flip
    private static final Random random = new Random();

    /**
     * Constructor untuk membuat battle baru.
     * 
     * @param player1 Karakter pemain 1
     * @param player2 Karakter pemain 2
     */
    public Battle(BaseCharacter player1, BaseCharacter player2) {
        // Validation
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Player tidak boleh null");
        }

        this.player1 = player1;
        this.player2 = player2;
        this.currentState = BattleState.WAITING;
        this.turnCount = 0;
        this.winner = null;
        this.battleLog = new BattleLog();
        this.display = new BattleDisplay(this);
    }

    /**
     * Memulai pertarungan.
     * Menentukan siapa yang jalan duluan berdasarkan speed stat.
     * Bisa juga menggunakan coin flip jika speed sama.
     */
    public void start() {
        LOGGER.info("\n" + GameStrings.BATTLE_START_TITLE);

        LOGGER.info("⚔️  " + player1.getName() + " (HP: " + player1.getHealthPoints() + "/"
                + player1.getMaxHealthPoints() + ")");
        LOGGER.info(GameStrings.BATTLE_VS);
        LOGGER.info("⚔️  " + player2.getName() + " (HP: " + player2.getHealthPoints() + "/"
                + player2.getMaxHealthPoints() + ")\n");

        // Tentukan siapa yang jalan duluan
        determineTurnOrder();

        this.currentState = firstTurn;
        this.turnCount = 1;

        LOGGER.info("🎲 Penentuan Giliran:");
        if (player1.getSpeed() > player2.getSpeed()) {
            LOGGER.info("   " + player1.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else if (player2.getSpeed() > player1.getSpeed()) {
            LOGGER.info("   " + player2.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else {
            LOGGER.info("   Kecepatan sama! Hasil Coin Flip menentukan...\n");
        }
    }

    /**
     * Menentukan urutan giliran berdasarkan speed.
     * Jika speed sama, gunakan coin flip (random).
     */
    private void determineTurnOrder() {
        if (player1.getSpeed() > player2.getSpeed()) {
            firstTurn = BattleState.PLAYER1_TURN;
        } else if (player2.getSpeed() > player1.getSpeed()) {
            firstTurn = BattleState.PLAYER2_TURN;
        } else {
            // Coin flip
            firstTurn = random.nextBoolean() ? BattleState.PLAYER1_TURN : BattleState.PLAYER2_TURN;
        }
    }

    /**
     * Menampilkan status pertarungan saat ini.
     * Delegates to BattleDisplay.
     */
    public void displayStatus() {
        display.displayStatus();
    }

    /**
     * Menampilkan skill yang tersedia.
     * Delegates to BattleDisplay.
     */
    public void displayAvailableSkills() {
        display.displayAvailableSkills();
    }

    /**
     * Pemain memilih skill untuk digunakan.
     * 
     * @param skillIndex Index skill (1-based)
     * @return true jika aksi berhasil, false jika gagal
     */
    public boolean executePlayerAction(int skillIndex) {
        BaseCharacter attacker = getCurrentPlayer();
        BaseCharacter defender = getOpponentPlayer();

        // 1. Process Start Turn Effects (DoT, etc.)
        LOGGER.info("\n⚡ Memproses Status Effects (Start Turn)...");
        attacker.applyStartTurnEffects();

        // 2. Check if can move (Freeze check)
        if (!attacker.canMove()) {
            // Log freeze action
            BattleAction freezeAction = new BattleAction(
                    attacker.getName(),
                    "FROZEN",
                    "Terkena Freeze",
                    "");
            freezeAction.setDescription("Tidak bisa bergerak karena terkena efek Freeze!");
            battleLog.addAction(freezeAction);
            battleLog.displayLastAction();

            // End turn effects (duration decrement)
            attacker.applyEndTurnEffects();
            return true; // Turn berhasil tapi tidak bisa action
        }

        List<Skill> skills = attacker.getSkills();

        // Validasi index
        if (skillIndex < 1 || skillIndex > skills.size()) {
            LOGGER.info(GameStrings.MSG_INVALID_SKILL);
            return false;
        }

        Skill selectedSkill = skills.get(skillIndex - 1);

        // 3. Check FP BEFORE execution
        if (attacker.getFocusPoints() < selectedSkill.getFpCost()) {
            LOGGER.info(GameStrings.MSG_INSUFFICIENT_FP);
            return false; // Return false to allow re-selection
        }

        // Log aksi
        BattleAction action = new BattleAction(
                attacker.getName(),
                "SKILL",
                selectedSkill.getName(),
                defender.getName());

        // Simpan HP attacker DAN defender untuk tracking healing & damage
        int defenderHpBefore = defender.getHealthPoints();
        int attackerHpBefore = attacker.getHealthPoints();

        // Eksekusi skill
        LOGGER.fine("");
        selectedSkill.use(attacker, defender);
        LOGGER.fine("");

        // Hitung damage DAN healing
        int damageDealt = defenderHpBefore - defender.getHealthPoints();
        int healingDone = attacker.getHealthPoints() - attackerHpBefore;

        // Record BOTH if applicable (e.g. Drain skill)
        if (damageDealt > 0) {
            action.setDamageDealt(damageDealt);
        }
        if (healingDone > 0) {
            action.setHealingDone(healingDone);
        }

        // --- FIX: Retrieve detailed mechanics from defender ---
        BaseCharacter.DamageDetails details = defender.getLastDamageDetails();
        if (details != null) {
            action.setCritical(details.isCritical);
            action.setDodged(details.isDodged);
            action.setEffectiveness(details.effectiveness);
        }
        // ------------------------------------------------------

        // Set deskripsi
        if (selectedSkill.getFpCost() > 0) {
            action.setDescription("FP digunakan: " + selectedSkill.getFpCost());
        }

        battleLog.addAction(action);
        battleLog.displayLastAction();

        // 4. Process End Turn Effects (Duration decrement)
        attacker.applyEndTurnEffects();

        return true;
    }

    /**
     * Pemain surrender dari pertarungan.
     */
    public void surrender() {
        BaseCharacter surrenderer = getCurrentPlayer();
        this.winner = getOpponentPlayer(); // Simpan pemenang sebelum state berubah

        LOGGER.info(String.format("\n" + GameStrings.MSG_SURRENDERED, surrenderer.getName()));
        LOGGER.info(String.format(GameStrings.MSG_WINNER, this.winner.getName()) + "\n");

        BattleAction action = new BattleAction(
                surrenderer.getName(),
                "SURRENDER",
                "Menyerah",
                "");
        battleLog.addAction(action);

        currentState = BattleState.FINISHED;
    }

    /**
     * Pindah ke giliran berikutnya.
     */
    public void endTurn() {
        if (currentState == BattleState.PLAYER1_TURN) {
            currentState = BattleState.PLAYER2_TURN;
        } else if (currentState == BattleState.PLAYER2_TURN) {
            currentState = BattleState.PLAYER1_TURN;
            turnCount++;
            // Regenerasi FP hanya 1x per turn cycle (setelah kedua pemain bermain)
            regenerateFocusPoints();
        }
    }

    /**
     * Regenerasi FP untuk kedua pemain.
     * BALANCED: Increased regen untuk lebih banyak strategic options.
     */
    private void regenerateFocusPoints() {
        int fpRegenAmount = Constants.FP_REGEN_PER_TURN;
        player1.regenFocusPoints(fpRegenAmount);
        player2.regenFocusPoints(fpRegenAmount);
    }

    /**
     * Cek apakah pertarungan sudah selesai.
     * Pertarungan selesai jika:
     * 1. Salah satu HP mencapai 0
     * 2. Salah satu menyerah
     */
    public boolean isBattleFinished() {
        return (currentState == BattleState.FINISHED)
                || (player1.getHealthPoints() <= 0 || player2.getHealthPoints() <= 0);
    }

    /**
     * Menentukan pemenang pertarungan.
     */
    public BaseCharacter getWinner() {
        // Cek jika winner sudah ditentukan (surrender case)
        if (winner != null) {
            return winner;
        }

        // Cek berdasarkan HP
        if (player1.getHealthPoints() <= 0) {
            return player2;
        } else if (player2.getHealthPoints() <= 0) {
            return player1;
        }

        return null;
    }

    /**
     * Menampilkan battle log.
     */
    public void displayBattleLog() {
        battleLog.displayFullLog();
    }

    // ====================================================================
    // GETTERS
    // ====================================================================

    public BaseCharacter getPlayer1() {
        return player1;
    }

    public BaseCharacter getPlayer2() {
        return player2;
    }

    public BattleState getCurrentState() {
        return currentState;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public BattleLog getBattleLog() {
        return battleLog;
    }

    /**
     * Mendapatkan pemain yang sedang bermain saat ini.
     */
    public BaseCharacter getCurrentPlayer() {
        if (currentState == BattleState.PLAYER1_TURN) {
            return player1;
        } else if (currentState == BattleState.PLAYER2_TURN) {
            return player2;
        }
        throw new IllegalStateException("Invalid battle state: " + currentState);
    }

    /**
     * Mendapatkan pemain lawan.
     */
    public BaseCharacter getOpponentPlayer() {
        return currentState == BattleState.PLAYER1_TURN ? player2 : player1;
    }

    /**
     * Mendapatkan nama pemain yang sedang bermain.
     */
    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    /**
     * Cek apakah pertarungan dimulai.
     */
    public boolean hasStarted() {
        return currentState != BattleState.WAITING;
    }
}
