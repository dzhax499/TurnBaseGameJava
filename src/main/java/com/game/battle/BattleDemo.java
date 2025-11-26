package com.game.battle;

import com.game.characters.*;

/**
 * Demo class untuk menjalankan battle secara otomatis (untuk testing).
 * Ini menunjukkan bagaimana sistem battle bekerja tanpa interaksi user.
 */
public class BattleDemo {
    
    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║         TURN-BASED BATTLE SYSTEM DEMO                  ║");
        System.out.println("║     Fire vs Water - Menunjukkan Elemental Advantage   ║");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");

        // Buat karakter: Fire vs Water (Water memiliki keuntungan)
        BaseCharacter fireCharacter = new FireCharacter("Pyro");
        BaseCharacter waterCharacter = new WaterCharacter("Aqua");

        // Buat battle
        Battle battle = new Battle(fireCharacter, waterCharacter);
        battle.start();

        // Demo beberapa turn
        demoTurns(battle);

        // Tampilkan hasil
        battle.displayBattleResult();
        
        System.out.println("\n📊 ANALISIS PERTARUNGAN:");
        analyzeCharacterMatchup(fireCharacter, waterCharacter);
    }

    /**
     * Demo beberapa turn pertarungan.
     */
    private static void demoTurns(Battle battle) {
        int maxTurns = 10;
        int turn = 0;

        while (!battle.isBattleFinished() && turn < maxTurns) {
            battle.displayStatus();

            // Cek apakah pemain bisa bergerak
            if (!battle.getCurrentPlayer().canMove()) {
                System.out.println("❄️  " + battle.getCurrentPlayerName() + " terkena Freeze! Skip turn.\n");
                battle.endTurn();
                turn++;
                continue;
            }

            // Pilih skill secara otomatis (demo purposes)
            int skillChoice = selectAutomaticSkill(battle);
            
            if (battle.executePlayerAction(skillChoice)) {
                if (battle.isBattleFinished()) {
                    break;
                }
                battle.endTurn();
                turn++;
            }
        }
    }

    /**
     * Memilih skill secara otomatis untuk demo.
     * Strategy: 
     * - Jika HP rendah, gunakan Heal
     * - Jika HP sedang, gunakan Guard
     * - Kalau tidak, gunakan skill special jika FP cukup
     * - Kalau tidak, gunakan basic attack
     */
    private static int selectAutomaticSkill(Battle battle) {
        BaseCharacter current = battle.getCurrentPlayer();
        BaseCharacter opponent = battle.getOpponentPlayer();

        // Cek HP current player
        int hpPercent = (current.getHealthPoints() * 100) / current.getMaxHealthPoints();
        
        // Jika HP < 40%, gunakan Heal (skill 3)
        if (hpPercent < 40) {
            return 3; // Heal skill
        }
        
        // Jika HP 40-70%, gunakan Guard (skill 2) untuk defense
        if (hpPercent < 70) {
            return 2; // Guard skill
        }

        // Jika FP cukup, gunakan skill special (skill 4)
        java.util.List<com.game.skills.Skill> skills = current.getSkills();
        if (skills.size() > 3) {
            com.game.skills.Skill specialSkill = skills.get(3);
            if (current.getFocusPoints() >= specialSkill.getFpCost()) {
                return 4; // Special skill (elemental)
            }
        }

        // Kalau tidak, gunakan basic attack (skill 1)
        return 1;
    }

    /**
     * Analisis matchup karakter.
     */
    private static void analyzeCharacterMatchup(BaseCharacter char1, BaseCharacter char2) {
        System.out.println("\n" + char1.getName() + " (" + char1.getElementType() + "):");
        System.out.println("  - HP: " + char1.getMaxHealthPoints());
        System.out.println("  - Attack: " + char1.getAttackPower());
        System.out.println("  - Defense: " + char1.getDefense());
        System.out.println("  - Speed: " + char1.getSpeed());

        System.out.println("\n" + char2.getName() + " (" + char2.getElementType() + "):");
        System.out.println("  - HP: " + char2.getMaxHealthPoints());
        System.out.println("  - Attack: " + char2.getAttackPower());
        System.out.println("  - Defense: " + char2.getDefense());
        System.out.println("  - Speed: " + char2.getSpeed());

        // Elemental advantage
        double multiplier = com.game.characters.ElementalAdvantage.getMultiplier(
            char2.getElementType(), 
            char1.getElementType()
        );
        
        System.out.println("\n⚡ Elemental Advantage:");
        System.out.println("  - " + char2.getName() + " vs " + char1.getName() + ": " + multiplier + "x damage");
        
        String message = com.game.characters.ElementalAdvantage.getEffectivenessMessage(
            char2.getElementType(),
            char1.getElementType()
        );
        if (!message.isEmpty()) {
            System.out.println("  - " + message);
        }
    }
}
