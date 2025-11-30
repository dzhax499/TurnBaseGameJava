package com.game.battle;

import com.game.characters.*;
import java.util.logging.Logger;

/**
 * Demo class untuk menjalankan battle secara otomatis (untuk testing).
 * Ini menunjukkan bagaimana sistem battle bekerja tanpa interaksi user.
 */
public class BattleDemo {
    private static final Logger LOGGER = Logger.getLogger(BattleDemo.class.getName());
    
    public static void main(String[] args) {
        LOGGER.info("\n╔════════════════════════════════════════════════════════╗");
        LOGGER.info("║         TURN-BASED BATTLE SYSTEM DEMO                  ║");
        LOGGER.info("║     Fire vs Water - Menunjukkan Elemental Advantage   ║");
        LOGGER.info("╚════════════════════════════════════════════════════════╝\n");

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
        
        LOGGER.info("\n📊 ANALISIS PERTARUNGAN:");
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

            boolean isFrozen = !battle.getCurrentPlayer().canMove();
            if (isFrozen) {
                LOGGER.info("❄️  " + battle.getCurrentPlayerName() + " terkena Freeze! Skip turn.");
                battle.endTurn();
            } else {
                // Pilih skill secara otomatis (demo purposes)
                int skillChoice = selectAutomaticSkill(battle);
                if (battle.executePlayerAction(skillChoice)) {
                    battle.endTurn();
                }
            }
            turn++;
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
        LOGGER.info("\n" + char1.getName() + " (" + char1.getElementType() + "):");
        LOGGER.info("  - HP: " + char1.getMaxHealthPoints());
        LOGGER.info("  - Attack: " + char1.getAttackPower());
        LOGGER.info("  - Defense: " + char1.getDefense());
        LOGGER.info("  - Speed: " + char1.getSpeed());

        LOGGER.info("\n" + char2.getName() + " (" + char2.getElementType() + "):");
        LOGGER.info("  - HP: " + char2.getMaxHealthPoints());
        LOGGER.info("  - Attack: " + char2.getAttackPower());
        LOGGER.info("  - Defense: " + char2.getDefense());
        LOGGER.info("  - Speed: " + char2.getSpeed());

        // Elemental advantage
        double multiplier = com.game.characters.ElementalAdvantage.getMultiplier(
            char2.getElementType(), 
            char1.getElementType()
        );
        
        String advantageInfo = String.format("  - %s vs %s: %.1fx damage", char2.getName(), char1.getName(), multiplier);
        LOGGER.info("%n⚡ Elemental Advantage:");
        LOGGER.info(advantageInfo);
        
        String message = com.game.characters.ElementalAdvantage.getEffectivenessMessage(
            char2.getElementType(),
            char1.getElementType()
        );
        if (!message.isEmpty()) {
            String messageInfo = String.format("  - %s", message);
            LOGGER.info(messageInfo);
        }
    }
}
