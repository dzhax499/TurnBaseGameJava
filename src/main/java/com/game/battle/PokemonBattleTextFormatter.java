package com.game.battle;

public class PokemonBattleTextFormatter {
    static final String CRITICAL_HIT = "CRITICAL HIT!";

    private PokemonBattleTextFormatter() {
        // Private constructor to prevent instantiation
    }

    public static String formatAttackMessage(String attackerName, String skillName) {
        return attackerName.toUpperCase() + " menggunakan\n" + skillName.toUpperCase() + "!";
    }

    public static String formatDamageMessage(int damage) {
        if (damage > 0) {
            return "Memberikan " + damage + " damage!";
        }
        return "";
    }

    /**
     * Format healing message.
     */
    public static String formatHealingMessage(String characterName, int healing) {
        if (healing > 0) {
            return characterName.toUpperCase() + " memulihkan\n" + healing + " HP!";
        }
        return "";
    }

    /**
     * Format status effect applied message.
     */
    public static String formatStatusEffectApplied(String targetName, String effectName) {
        return targetName.toUpperCase() + " mengalami\n" + effectName.toUpperCase() + "!";
    }

    /**
     * Format status effect damage message (DoT).
     */
    public static String formatStatusEffectDamage(String characterName, String effectName, int damage) {
        return characterName.toUpperCase() + " terkena damage\ndari " + effectName.toUpperCase() + "!\n(" + damage
                + " damage)";
    }

    /**
     * Format effectiveness message.
     */
    public static String formatEffectiveness(double multiplier) {
        if (multiplier > 1.5) {
            return "Sangat efektif!";
        } else if (multiplier > 1.0) {
            return "Cukup efektif!";
        } else if (multiplier < 0.5) {
            return "Hampir tidak berpengaruh...";
        } else if (multiplier < 1.0) {
            return "Tidak terlalu efektif...";
        }
        return "";
    }

    /**
     * Format dodge message.
     */
    public static String formatDodge(String defenderName) {
        return defenderName.toUpperCase() + " menghindari\nserangan!";
    }

    /**
     * Format faint message.
     */
    public static String formatFaint(String characterName) {
        return characterName.toUpperCase() + " pingsan!";
    }

    /**
     * Format frozen/can't move message.
     */
    public static String formatCannotMove(String characterName, String reason) {
        return characterName.toUpperCase() + " tidak bisa\nbergerak!\n(" + reason + ")";
    }

    /**
     * Format FP insufficient message.
     */
    public static String formatInsufficientFP(String characterName) {
        return characterName.toUpperCase() + " tidak punya\ncukup FP!";
    }

    /**
     * Format guard/defense message.
     */
    public static String formatGuard(String characterName) {
        return characterName.toUpperCase() + " bertahan!";
    }

    /**
     * Format FP regeneration message.
     */
    public static String formatFPRegen(int amount) {
        return "FP pulih sebanyak " + amount + "!";
    }

    /**
     * Combine multiple messages dengan spacing yang tepat.
     */
    public static String combineMessages(String... messages) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String msg : messages) {
            if (msg != null && !msg.trim().isEmpty()) {
                if (!first) {
                    sb.append("\n\n");
                }
                sb.append(msg);
                first = false;
            }
        }

        return sb.toString();
    }

    /**
     * Format complete battle action dengan semua detail.
     * Menggunakan BattleActionInfo untuk menghindari terlalu banyak parameter.
     */
    public static String formatBattleAction(BattleActionInfo<?> info) {


        // Attack message
        StringBuilder sb = new StringBuilder(formatAttackMessage(info.getAttackerName(), info.getSkillName()) + "\n\n");

        // Dodge check
        if (info.getisDodged()) {
            sb.append(formatDodge(info.getDefenderName()));
            return sb.toString();
        }

        // Critical hit
        if (info.getisCritical()) {
            sb.append(CRITICAL_HIT);
            sb.append("\n\n");
        }

        // Damage
        if (info.getDamage() > 0) {
            sb.append(formatDamageMessage(info.getDamage()));
            sb.append("\n\n");
        }

        // Effectiveness
        String effMsg = formatEffectiveness(info.getEffectiveness());
        if (!effMsg.isEmpty()) {
            sb.append(effMsg);
            sb.append("\n\n");
        }

        // Healing
        if (info.getHealing() > 0) {
            sb.append(formatHealingMessage(info.getAttackerName(), info.getHealing()));
            sb.append("\n\n");
        }

        // Status effect
        if (info.getStatusEffect() != null && !info.getStatusEffect().isEmpty()) {
            sb.append(formatStatusEffectApplied(info.getDefenderName(), info.getStatusEffect()));
        }
        return sb.toString().trim();
    }

}
