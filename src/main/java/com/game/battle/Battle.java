package com.game.battle;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import java.util.List;
import java.util.Random;

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
    
    // Karakter yang bertarung
    private BaseCharacter player1;
    private BaseCharacter player2;
    
    // State management
    private BattleState currentState;
    private BattleState firstTurn; // Siapa yang jalan duluan (PLAYER1_TURN atau PLAYER2_TURN)
    private int turnCount;
    
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
        this.player1 = player1;
        this.player2 = player2;
        this.currentState = BattleState.WAITING;
        this.turnCount = 0;
        this.battleLog = new BattleLog();
    }

    /**
     * Memulai pertarungan.
     * Menentukan siapa yang jalan duluan berdasarkan speed stat.
     * Bisa juga menggunakan coin flip jika speed sama.
     */
    public void start() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   PERTARUNGAN DIMULAI!             ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        System.out.println("⚔️  " + player1.getName() + " (HP: " + player1.getHealthPoints() + "/" + player1.getMaxHealthPoints() + ")");
        System.out.println("⚔️  vs");
        System.out.println("⚔️  " + player2.getName() + " (HP: " + player2.getHealthPoints() + "/" + player2.getMaxHealthPoints() + ")\n");

        // Tentukan siapa yang jalan duluan
        determineTurnOrder();
        
        this.currentState = firstTurn;
        this.turnCount = 1;

        System.out.println("🎲 Penentuan Giliran:");
        if (player1.getSpeed() > player2.getSpeed()) {
            System.out.println("   " + player1.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else if (player2.getSpeed() > player1.getSpeed()) {
            System.out.println("   " + player2.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else {
            System.out.println("   Kecepatan sama! Hasil Coin Flip menentukan...\n");
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
        System.out.println("\n" + divider);
        System.out.println("TURN " + turnCount + " - " + getCurrentPlayerName() + " GILIRAN");
        System.out.println(divider);
        
        // Status Player 1
        displayCharacterStatus(player1, "⚔️ PLAYER 1");
        System.out.println();
        
        // Status Player 2
        displayCharacterStatus(player2, "🛡️  PLAYER 2");
        System.out.println(divider + "\n");
    }

    /**
     * Menampilkan status detail karakter (HP, FP, Effects, dll).
     */
    private void displayCharacterStatus(BaseCharacter character, String label) {
        int maxHp = character.getMaxHealthPoints();
        int currentHp = character.getHealthPoints();
        int hpPercent = (currentHp * 100) / maxHp;
        
        // HP Bar
        System.out.print(label + " " + character.getName());
        System.out.print(" │ ");
        displayHpBar(currentHp, maxHp);
        System.out.println(" " + currentHp + "/" + maxHp + " HP");
        
        // FP Bar
        int maxFp = character.getMaxFocusPoints();
        int currentFp = character.getFocusPoints();
        System.out.print("           │ FP: ");
        displayFpBar(currentFp, maxFp);
        System.out.println(" " + currentFp + "/" + maxFp);
        
        // Status Effects
        List<String> effects = character.getActiveEffectNames();
        if (!effects.isEmpty()) {
            System.out.print("           │ Effects: ");
            for (String effect : effects) {
                System.out.print("["+ effect +"] ");
            }
            System.out.println();
        }
    }

    /**
     * Menampilkan visual HP bar.
     */
    private void displayHpBar(int current, int max) {
        int barLength = 20;
        int filledLength = (current * barLength) / max;
        System.out.print("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                System.out.print("█");
            } else {
                System.out.print("░");
            }
        }
        System.out.print("]");
    }

    /**
     * Menampilkan visual FP bar.
     */
    private void displayFpBar(int current, int max) {
        int barLength = 10;
        int filledLength = (current * barLength) / max;
        System.out.print("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                System.out.print("⚡");
            } else {
                System.out.print("○");
            }
        }
        System.out.print("]");
    }

    /**
     * Menampilkan skill yang tersedia untuk pemain saat ini.
     */
    public void displayAvailableSkills() {
        BaseCharacter currentPlayer = getCurrentPlayer();
        List<Skill> skills = currentPlayer.getSkills();
        
        System.out.println("\n📋 Skill tersedia untuk " + currentPlayer.getName() + ":");
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            System.out.println((i + 1) + ". " + skill.getName() + " (FP Cost: " + skill.getFpCost() + ")");
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
        
        List<Skill> skills = attacker.getSkills();
        
        // Validasi index
        if (skillIndex < 1 || skillIndex > skills.size()) {
            System.out.println("❌ Pilihan skill tidak valid!");
            return false;
        }

        Skill selectedSkill = skills.get(skillIndex - 1);
        
        // Cek FP cukup
        if (attacker.getFocusPoints() < selectedSkill.getFpCost()) {
            System.out.println("❌ " + attacker.getName() + " tidak punya cukup FP! Butuh " + selectedSkill.getFpCost() + " FP.");
            return false;
        }

        // Log aksi
        BattleAction action = new BattleAction(
            attacker.getName(),
            "SKILL",
            selectedSkill.getName(),
            defender.getName()
        );

        // Simpan HP dan FP sebelum aksi
        int defenderHpBefore = defender.getHealthPoints();
        int attackerFpBefore = attacker.getFocusPoints();

        // Eksekusi skill
        System.out.println();
        selectedSkill.use(attacker, defender);
        System.out.println();

        // Hitung damage/healing
        int damageDealt = defenderHpBefore - defender.getHealthPoints();
        if (damageDealt < 0) {
            // Negative berarti healing
            action.setHealingDone(-damageDealt);
        } else {
            action.setDamageDealt(damageDealt);
        }

        // Set deskripsi
        if (selectedSkill.getFpCost() > 0) {
            action.setDescription("FP digunakan: " + selectedSkill.getFpCost());
        }

        battleLog.addAction(action);

        // Proses status effects setiap turn
        processStatusEffects();

        return true;
    }

    /**
     * Pemain surrender dari pertarungan.
     */
    public void surrender() {
        BaseCharacter surrenderer = getCurrentPlayer();
        BaseCharacter winner = getOpponentPlayer();
        
        System.out.println("\n💔 " + surrenderer.getName() + " menyerah!");
        System.out.println("🎉 " + winner.getName() + " MENANG!\n");
        
        BattleAction action = new BattleAction(
            surrenderer.getName(),
            "SURRENDER",
            "Menyerah",
            ""
        );
        battleLog.addAction(action);
        
        currentState = BattleState.FINISHED;
    }

    /**
     * Memproses status effects pada setiap karakter.
     */
    private void processStatusEffects() {
        System.out.println("\n⚡ Memproses Status Effects...");
        
        // Process player1 effects
        player1.updateStatusEffects();
        
        // Process player2 effects
        player2.updateStatusEffects();
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
        }

        // Regenerasi FP setiap turn
        regenerateFocusPoints();
    }

    /**
     * Regenerasi FP untuk kedua pemain.
     * Regen per turn dikurangi agar pemain tidak bisa spam special skill.
     */
    private void regenerateFocusPoints() {
        int fpRegenAmount = 5; // FP regenerasi per turn (reduced dari 10)
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
        if (player1.getHealthPoints() <= 0) {
            return player2;
        } else if (player2.getHealthPoints() <= 0) {
            return player1;
        } else if (currentState == BattleState.FINISHED) {
            // Surrender case sudah handled
            return getCurrentPlayer().getHealthPoints() > 0 ? getCurrentPlayer() : getOpponentPlayer();
        }
        return null;
    }

    /**
     * Menampilkan hasil akhir pertarungan.
     */
    public void displayBattleResult() {
        BaseCharacter winner = getWinner();
        
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║       PERTARUNGAN SELESAI!         ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        if (winner != null) {
            System.out.println("🎉 PEMENANG: " + winner.getName() + " 🎉");
            System.out.println("   HP Tersisa: " + winner.getHealthPoints() + "/" + winner.getMaxHealthPoints());
        }
        
        System.out.println("\nTotal Turn: " + turnCount);
        System.out.println("Total Aksi: " + battleLog.getActionCount());
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

    public BaseCharacter getPlayer1() { return player1; }
    public BaseCharacter getPlayer2() { return player2; }
    public BattleState getCurrentState() { return currentState; }
    public int getTurnCount() { return turnCount; }
    public BattleLog getBattleLog() { return battleLog; }

    /**
     * Mendapatkan pemain yang sedang bermain saat ini.
     */
    public BaseCharacter getCurrentPlayer() {
        return currentState == BattleState.PLAYER1_TURN ? player1 : player2;
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
