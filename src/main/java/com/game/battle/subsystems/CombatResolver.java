package com.game.battle.subsystems;

import com.game.battle.BattleAction;
import com.game.battle.BattleLog;
import com.game.characters.BaseCharacter;
import com.game.skills.Skill;
import com.game.utils.GameStrings;
import java.util.List;
import java.util.logging.Logger;

/**
 * Subsystem untuk mengeksekusi combat actions dan skill execution.
 * 
 * <p>
 * CombatResolver bertanggung jawab untuk:
 * <ul>
 * <li>Memproses status effects di awal dan akhir turn</li>
 * <li>Validasi skill selection dan FP requirements</li>
 * <li>Eksekusi skill dengan tracking damage/healing</li>
 * <li>Recording battle actions ke battle log</li>
 * </ul>
 * 
 * <p>
 * Alur eksekusi action:
 * <ol>
 * <li>Process start turn effects (DoT, buffs, etc.)</li>
 * <li>Check if character can move (Freeze check)</li>
 * <li>Validate skill selection and FP cost</li>
 * <li>Execute skill</li>
 * <li>Track damage/healing and combat mechanics</li>
 * <li>Log action to battle log</li>
 * <li>Process end turn effects (duration decrement)</li>
 * </ol>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Battle
 * @see BattleLog
 * @see StatusEffectProcessor
 */
public class CombatResolver {

    /**
     * Mengeksekusi action combat untuk satu turn.
     * 
     * <p>
     * Method ini menangani seluruh flow combat dari start turn effects
     * hingga end turn effects, termasuk validasi, eksekusi skill, dan logging.
     * 
     * @param attacker   Karakter yang melakukan action
     * @param defender   Karakter yang menjadi target
     * @param skillIndex Index skill yang dipilih (1-based)
     * @param battleLog  Battle log untuk recording actions
     * @param logger     Logger untuk output
     * @return true jika action berhasil dieksekusi, false jika perlu re-selection
     */
    public boolean executeAction(BaseCharacter attacker, BaseCharacter defender,
            int skillIndex, BattleLog battleLog, Logger logger) {
        // 1. Process Start Turn Effects (DoT, etc.)
        logger.info("\n⚡ Memproses Status Effects (Start Turn)...");
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
            logger.info(GameStrings.MSG_INVALID_SKILL);
            return false;
        }

        Skill selectedSkill = skills.get(skillIndex - 1);

        // 3. Check FP BEFORE execution
        if (attacker.getFocusPoints() < selectedSkill.getFpCost()) {
            logger.info(GameStrings.MSG_INSUFFICIENT_FP);
            return false; // Return false to allow re-selection
        }

        // Log aksi
        String targetName = selectedSkill.isSelfTargeting() ? attacker.getName() : defender.getName();
        BattleAction action = new BattleAction(
                attacker.getName(),
                "SKILL",
                selectedSkill.getName(),
                targetName);

        // Simpan HP attacker DAN defender untuk tracking healing & damage
        int defenderHpBefore = defender.getHealthPoints();
        int attackerHpBefore = attacker.getHealthPoints();

        // Eksekusi skill
        logger.fine("");
        selectedSkill.use(attacker, defender);
        logger.fine("");

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

        // Retrieve detailed mechanics from defender
        BaseCharacter.DamageDetails details = defender.getLastDamageDetails();
        if (details != null) {
            action.setCritical(details.getisCritical());
            action.setDodged(details.getisDodged());
            action.setEffectiveness(details.getEffectiveness());
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
}
