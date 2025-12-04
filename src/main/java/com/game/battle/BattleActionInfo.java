package com.game.battle;

/**
 * Data class untuk menyimpan informasi battle action.
 * Menggunakan builder pattern untuk menghindari method dengan terlalu banyak
 * parameter.
 * 
 * @param <T> Tipe data payload tambahan (Generic Programming)
 */
public class BattleActionInfo<T> {
    private String attackerName;
    private String skillName;
    private String defenderName;
    private int damage;
    private int healing;
    private boolean isCritical;
    private boolean isDodged;
    private double effectiveness;
    private String statusEffect;

    // Generic Data Payload
    private T data;

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

    public String getAttackerName() {
        return this.attackerName;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public String getDefenderName() {
        return this.defenderName;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getHealing() {
        return this.healing;
    }

    public boolean getisCritical() {
        return this.isCritical;
    }

    public boolean getisDodged() {
        return this.isDodged;
    }

    public double getEffectiveness() {
        return this.effectiveness;
    }

    public String getStatusEffect() {
        return this.statusEffect;
    }

    public T getData() {
        return this.data;
    }

    public BattleActionInfo<T> withDamage(int damage) {
        this.damage = damage;
        return this;
    }

    public BattleActionInfo<T> withHealing(int healing) {
        this.healing = healing;
        return this;
    }

    public BattleActionInfo<T> withCritical(boolean isCritical) {
        this.isCritical = isCritical;
        return this;
    }

    public BattleActionInfo<T> withDodged(boolean isDodged) {
        this.isDodged = isDodged;
        return this;
    }

    public BattleActionInfo<T> withEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public BattleActionInfo<T> withStatusEffect(String statusEffect) {
        this.statusEffect = statusEffect;
        return this;
    }

    public BattleActionInfo<T> withData(T data) {
        this.data = data;
        return this;
    }
}
