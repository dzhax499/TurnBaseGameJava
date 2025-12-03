package com.game.test;

import com.game.battle.Battle;
import com.game.battle.BattleAction;
import com.game.characters.CharacterFactory;
import com.game.characters.BaseCharacter;
import com.game.skills.common.HealSkill;
import com.game.skills.common.AttackSkill;

public class BattleLogReproduction {

    public static void main(String[] args) {
        System.out.println("=== Starting Reproduction Test ===");

        BaseCharacter p1 = CharacterFactory.createCharacter("FIRE", "Player1");
        BaseCharacter p2 = CharacterFactory.createCharacter("WATER", "Player2");

        Battle battle = new Battle(p1, p2);
        battle.start();

        // Turn 1: P1 Attacks P2 (Should record damage details)
        System.out.println("\n--- Turn 1: Attack ---");
        battle.executePlayerAction(1); // Attack
        BattleAction action1 = battle.getBattleLog().getLastActions(1).get(0);
        System.out.println("Log 1: " + action1.getDescription() +
                " | Crit: " + action1.isCritical() +
                " | Eff: " + action1.getEffectiveness());

        // Turn 2: P2 Heals Self (Should NOT record damage details from Turn 1)
        System.out.println("\n--- Turn 2: Heal ---");
        // Force P2 to be current player if needed, but battle flow handles it.
        // Assuming P2 is next.
        // We need to find the Heal skill index.
        int healIndex = -1;
        for (int i = 0; i < p2.getSkills().size(); i++) {
            if (p2.getSkills().get(i) instanceof HealSkill) {
                healIndex = i + 1;
                break;
            }
        }

        if (healIndex != -1) {
            battle.executePlayerAction(healIndex);
            BattleAction action2 = battle.getBattleLog().getLastActions(1).get(0);
            System.out.println("Log 2: " + action2.getDescription() +
                    " | Crit: " + action2.isCritical() +
                    " | Eff: " + action2.getEffectiveness());

            if (action2.isCritical() || action2.getEffectiveness() != 1.0) {
                System.out.println("FAILURE: Stale damage details detected in Heal action!");
            } else {
                System.out.println("SUCCESS: No stale details.");
            }
        } else {
            System.out.println("Could not find Heal skill for P2");
        }
    }
}
