package com.game.battle;

/**
 * Class untuk merepresentasikan setiap aksi dalam pertarungan.
 * Digunakan untuk logging dan menampilkan apa yang terjadi dalam battle.
 */
public class BattleAction {
    private String actor; // Nama karakter yang melakukan aksi
    private String actionType; // "SKILL", "SURRENDER", "WAIT"
    private String actionName; // Nama skill atau aksi
    private String target; // Nama target
    private String description; // Deskripsi detail apa yang terjadi
    private int damageDealt; // Damage yang diberikan (jika ada)
    private int healingDone; // Healing yang dilakukan (jika ada)
    private boolean isCritical; // Apakah serangan critical
    private boolean isDodged; // Apakah serangan di-dodge
    private double effectiveness; // Multiplier effectiveness (0.5, 1.0, 2.0, etc)
    private String statusEffect; // Status effect yang diberikan (jika ada)
    private long timestamp; // Waktu aksi (untuk urutan)

    public BattleAction(String actor, String actionType, String actionName, String target) {
        this.actor = actor;
        this.actionType = actionType;
        this.actionName = actionName;
        this.target = target;
        this.description = "";
        this.damageDealt = 0;
        this.healingDone = 0;
        this.isCritical = false;
        this.isDodged = false;
        this.effectiveness = 1.0;
        this.statusEffect = "";
        this.timestamp = System.currentTimeMillis();
    }

    // Setters untuk detail tambahan
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDamageDealt(int damage) {
        this.damageDealt = damage;
    }

    public void setHealingDone(int healing) {
        this.healingDone = healing;
    }

    public void setCritical(boolean critical) {
        this.isCritical = critical;
    }

    public void setDodged(boolean dodged) {
        this.isDodged = dodged;
    }

    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
    }

    public void setStatusEffect(String statusEffect) {
        this.statusEffect = statusEffect;
    }

    // Getters
    public String getActor() {
        return actor;
    }

    public String getActionType() {
        return actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getHealingDone() {
        return healingDone;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public boolean isDodged() {
        return isDodged;
    }

    public double getEffectiveness() {
        return effectiveness;
    }

    public String getStatusEffect() {
        return statusEffect;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Format display untuk log pertarungan.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(">>> ").append(actor).append(" menggunakan ").append(actionName);
        if (!target.isEmpty()) {
            sb.append(" pada ").append(target);
        }
        sb.append("!\n");
        if (!description.isEmpty()) {
            sb.append("    ").append(description).append("\n");
        }
        return sb.toString();
    }
}
