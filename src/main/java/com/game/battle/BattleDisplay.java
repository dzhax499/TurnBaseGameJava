package com.game.battle;

import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.utils.GameStrings;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles all display logic for the Battle system.
 * Separates concerns from the Battle logic class.
 */
public class BattleDisplay {

    private static final Logger LOGGER = Logger.getLogger(BattleDisplay.class.getName());
    private final Battle battle;

    public BattleDisplay(Battle battle) {
        this.battle = battle;
    }

    /**
     * Menampilkan status pertarungan saat ini.
     */
    public void displayStatus() {
        String divider = "═══════════════════════════════════════════";
        String dividerWithNewline = String.format("%n%s", divider);
        LOGGER.info(dividerWithNewline);
        String turnInfo = String.format(GameStrings.TURN_INFO, battle.getTurnCount(), battle.getCurrentPlayerName());
        LOGGER.info(turnInfo);
        LOGGER.info(divider);

        // Status Player 1
        displayCharacterStatus(battle.getPlayer1(), "⚔️ PLAYER 1");
        LOGGER.info("");

        // Status Player 2
        displayCharacterStatus(battle.getPlayer2(), "🛡️  PLAYER 2");
        String dividerEndNewline = String.format("%s%n", divider);
        LOGGER.info(dividerEndNewline);
    }

    /**
     * Menampilkan status detail karakter (HP, FP, Effects, dll).
     */
    private void displayCharacterStatus(BaseCharacter character, String label) {
        int maxHp = character.getMaxHealthPoints();
        int currentHp = character.getHealthPoints();

        // HP Bar
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(" ").append(character.getName()).append(" │ ");
        sb.append(formatHpBar(currentHp, maxHp));
        sb.append(" ").append(currentHp).append("/").append(maxHp).append(" HP");
        String hpStatus = sb.toString();
        LOGGER.info(hpStatus);

        // FP Bar
        int maxFp = character.getMaxFocusPoints();
        int currentFp = character.getFocusPoints();
        StringBuilder fpSb = new StringBuilder("           │ FP: ");
        fpSb.append(formatFpBar(currentFp, maxFp));
        fpSb.append(" ").append(currentFp).append("/").append(maxFp);
        String fpStatus = fpSb.toString();
        LOGGER.info(fpStatus);

        // Status Effects
        List<String> effects = character.getActiveEffectNames();
        if (!effects.isEmpty()) {
            StringBuilder effectSb = new StringBuilder("           │ Effects: ");
            for (String effect : effects) {
                effectSb.append("[").append(effect).append("] ");
            }
            String effectStatus = effectSb.toString();
            LOGGER.info(effectStatus);
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
        BaseCharacter currentPlayer = battle.getCurrentPlayer();
        List<Skill> skills = currentPlayer.getSkills();

        LOGGER.info("\n📋 Skill tersedia untuk " + currentPlayer.getName() + ":");
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            String infoSkill = (i + 1) + ". " + skill.getName() + " (FP Cost: " + skill.getFpCost() + ")";
            LOGGER.info(infoSkill);
        }
    }
}
