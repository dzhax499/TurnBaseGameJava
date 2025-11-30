package com.game.battle;

/**
 * Data class untuk menyimpan informasi battle action.
 * Menggunakan builder pattern untuk menghindari method dengan terlalu banyak parameter.
 */
public class BattleActionInfo {
    public String attackerName;
    public String skillName;
    public String defenderName;
    public int damage;
    public int healing;
    public boolean isCritical;
    public boolean isDodged;
    public double effectiveness;
    public String statusEffect;

    public BattleActionInfo(String attackerName, String skillName, String defenderName) {
        this.attackerName = attackerName;
        this.skillName = skillName;
        this.defenderName = defenderName;
        this.damage = 0;
        this.healing = 0;
        this.isCritical = false;
        this.isDodged = false;
        this.effectiveness = 1.0;
        this.statusEffect = "";
    }

    public BattleActionInfo withDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public BattleActionInfo withHealing(int healing) {
        this.healing = healing;
        return this;
    }

    public BattleActionInfo withCritical(boolean isCritical) {
        this.isCritical = isCritical;
        return this;
    }

    public BattleActionInfo withDodged(boolean isDodged) {
        this.isDodged = isDodged;
        return this;
    }

    public BattleActionInfo withEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public BattleActionInfo withStatusEffect(String statusEffect) {
        this.statusEffect = statusEffect;
        return this;
    }
}
