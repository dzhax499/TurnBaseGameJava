package com.game.battle;

/**
 * Class untuk merepresentasikan setiap aksi dalam pertarungan.
 * 
 * BattleAction menyimpan semua informasi tentang satu aksi yang terjadi
 * dalam battle, termasuk siapa yang melakukan, skill apa yang digunakan,
 * damage/healing yang terjadi, dan detail mekanik seperti crit dan dodge.
 * 
 * Informasi yang disimpan:
 * - Actor: Siapa yang melakukan aksi
 * - Action Type: Tipe aksi (SKILL, SURRENDER, WAIT, FROZEN)
 * - Action Name: Nama skill atau aksi
 * - Target: Siapa yang terkena aksi
 * - Damage/Healing: Jumlah damage atau healing
 * - Combat Mechanics: Critical, Dodge, Effectiveness
 * - Status Effect: Efek status yang diterapkan
 * - Timestamp: Waktu aksi terjadi
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BattleLog
 */
public class BattleAction {

    /** Nama karakter yang melakukan aksi */
    private String actor;

    /** Tipe aksi: "SKILL", "SURRENDER", "WAIT", "FROZEN" */
    private String actionType;

    /** Nama skill atau aksi yang dilakukan */
    private String actionName;

    /** Nama target yang terkena aksi */
    private String target;

    /** Deskripsi detail apa yang terjadi */
    private String description;

    /** Damage yang diberikan (jika ada) */
    private int damageDealt;

    /** Healing yang dilakukan (jika ada) */
    private int healingDone;

    /** Apakah serangan critical */
    private boolean isCritical;

    /** Apakah serangan di-dodge */
    private boolean isDodged;

    /** Multiplier effectiveness (0.75, 1.0, 1.5) */
    private double effectiveness;

    /** Status effect yang diberikan (jika ada) */
    private String statusEffect;

    /** Waktu aksi terjadi (untuk urutan) */
    private long timestamp;

    /**
     * Konstruktor untuk membuat battle action baru.
     * 
     * @param actor      Nama karakter yang melakukan aksi
     * @param actionType Tipe aksi (SKILL, SURRENDER, dll)
     * @param actionName Nama skill atau aksi
     * @param target     Nama target
     */
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

    /**
     * Mengatur deskripsi detail aksi.
     * 
     * @param description Deskripsi aksi
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Mengatur jumlah damage yang diberikan.
     * 
     * @param damage Jumlah damage
     */
    public void setDamageDealt(int damage) {
        this.damageDealt = damage;
    }

    /**
     * Mengatur jumlah healing yang dilakukan.
     * 
     * @param healing Jumlah healing
     */
    public void setHealingDone(int healing) {
        this.healingDone = healing;
    }

    /**
     * Mengatur apakah serangan critical.
     * 
     * @param critical true jika critical
     */
    public void setCritical(boolean critical) {
        this.isCritical = critical;
    }

    /**
     * Mengatur apakah serangan di-dodge.
     * 
     * @param dodged true jika di-dodge
     */
    public void setDodged(boolean dodged) {
        this.isDodged = dodged;
    }

    /**
     * Mengatur multiplier effectiveness elemental.
     * 
     * @param effectiveness Multiplier (0.75, 1.0, 1.5)
     */
    public void setEffectiveness(double effectiveness) {
        this.effectiveness = effectiveness;
    }

    /**
     * Mengatur status effect yang diterapkan.
     * 
     * @param statusEffect Nama status effect
     */
    public void setStatusEffect(String statusEffect) {
        this.statusEffect = statusEffect;
    }

    /** @return Nama actor */
    public String getActor() {
        return actor;
    }

    /** @return Tipe aksi */
    public String getActionType() {
        return actionType;
    }

    /** @return Nama aksi */
    public String getActionName() {
        return actionName;
    }

    /** @return Nama target */
    public String getTarget() {
        return target;
    }

    /** @return Deskripsi */
    public String getDescription() {
        return description;
    }

    /** @return Damage yang diberikan */
    public int getDamageDealt() {
        return damageDealt;
    }

    /** @return Healing yang dilakukan */
    public int getHealingDone() {
        return healingDone;
    }

    /** @return true jika critical */
    public boolean isCritical() {
        return isCritical;
    }

    /** @return true jika di-dodge */
    public boolean isDodged() {
        return isDodged;
    }

    /** @return Multiplier effectiveness */
    public double getEffectiveness() {
        return effectiveness;
    }

    /** @return Nama status effect */
    public String getStatusEffect() {
        return statusEffect;
    }

    /** @return Timestamp aksi */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Format display untuk log pertarungan.
     * 
     * @return String representasi aksi untuk ditampilkan
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
