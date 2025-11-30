package com.game.battle;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
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
    }

    /**
     * Memulai pertarungan.
     * Menentukan siapa yang jalan duluan berdasarkan speed stat.
     * Bisa juga menggunakan coin flip jika speed sama.
     */
    public void start() {
        LOGGER.info("\n╔════════════════════════════════════╗");
        LOGGER.info("║   PERTARUNGAN DIMULAI!             ║");
        LOGGER.info("╚════════════════════════════════════╝\n");

        LOGGER.info("⚔️  " + player1.getName() + " (HP: " + player1.getHealthPoints() + "/"
                + player1.getMaxHealthPoints() + ")");
        LOGGER.info("⚔️  vs");
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
     */
    public void displayStatus() {
        String divider = "═══════════════════════════════════════════";
        LOGGER.info("\n" + divider);
        LOGGER.info("TURN " + turnCount + " - " + getCurrentPlayerName() + " GILIRAN");
        LOGGER.info(divider);

        // Status Player 1
        displayCharacterStatus(player1, "⚔️ PLAYER 1");
        LOGGER.info("");

        // Status Player 2
        displayCharacterStatus(player2, "🛡️  PLAYER 2");
        LOGGER.info(divider + "\n");
    }

    /**
     * Menampilkan status detail karakter (HP, FP, Effects, dll).
     */
    private void displayCharacterStatus(BaseCharacter character, String label) {
        int maxHp = character.getMaxHealthPoints();
        int currentHp = character.getHealthPoints();
        // Removed unused hpPercent variable

        // HP Bar
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(" ").append(character.getName()).append(" │ ");
        sb.append(formatHpBar(currentHp, maxHp));
        sb.append(" ").append(currentHp).append("/").append(maxHp).append(" HP");
        LOGGER.info(sb.toString());

        // FP Bar
        int maxFp = character.getMaxFocusPoints();
        int currentFp = character.getFocusPoints();
        StringBuilder fpSb = new StringBuilder("           │ FP: ");
        fpSb.append(formatFpBar(currentFp, maxFp));
        fpSb.append(" ").append(currentFp).append("/").append(maxFp);
        LOGGER.info(fpSb.toString());

        // Status Effects
        List<String> effects = character.getActiveEffectNames();
        if (!effects.isEmpty()) {
            StringBuilder effectSb = new StringBuilder("           │ Effects: ");
            for (String effect : effects) {
                effectSb.append("[").append(effect).append("] ");
            }
            LOGGER.info(effectSb.toString());
        }
    }

    /**
     * Menampilkan visual HP bar.
     */
    private String formatHpBar(int current, int max) {
        int barLength = 20;
        int filledLength = (current * barLength) / max;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Menampilkan visual FP bar.
     */
    private String formatFpBar(int current, int max) {
        int barLength = 10;
        int filledLength = (current * barLength) / max;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append("⚡");
            } else {
                bar.append("○");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Menampilkan skill yang tersedia untuk pemain saat ini.
     */
    public void displayAvailableSkills() {
        BaseCharacter currentPlayer = getCurrentPlayer();
        List<Skill> skills = currentPlayer.getSkills();

        LOGGER.info("\n📋 Skill tersedia untuk " + currentPlayer.getName() + ":");
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            LOGGER.info((i + 1) + ". " + skill.getName() + " (FP Cost: " + skill.getFpCost() + ")");
        }
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
            LOGGER.info("❄️ " + attacker.getName() + " terkena Freeze! Skip turn!");

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
            LOGGER.info("❌ Pilihan skill tidak valid!");
            return false;
        }

        Skill selectedSkill = skills.get(skillIndex - 1);

        // 3. Check FP BEFORE execution
        if (attacker.getFocusPoints() < selectedSkill.getFpCost()) {
            LOGGER.info("❌ FP tidak cukup! Pilih skill lain.");
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

        LOGGER.info("\n💔 " + surrenderer.getName() + " menyerah!");
        LOGGER.info("🎉 " + this.winner.getName() + " MENANG!\n");

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
        if (currentState == BattleState.FINISHED) {
            return true;
        }

        if (player1.getHealthPoints() <= 0 || player2.getHealthPoints() <= 0) {
            return true;
        }

        return false;
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
     * Menampilkan hasil akhir pertarungan.
     */
    public void displayBattleResult() {
        BaseCharacter winner = getWinner();

        LOGGER.info("\n╔════════════════════════════════════╗");
        LOGGER.info("║       PERTARUNGAN SELESAI!         ║");
        LOGGER.info("╚════════════════════════════════════╝\n");

        if (winner != null) {
            LOGGER.info("🎉 PEMENANG: " + winner.getName() + " 🎉");
            LOGGER.info("   HP Tersisa: " + winner.getHealthPoints() + "/" + winner.getMaxHealthPoints());
        }

        LOGGER.info("\nTotal Turn: " + turnCount);
        LOGGER.info("Total Aksi: " + battleLog.getActionCount());
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
