package com.game.test;

import com.game.battle.Battle;
import com.game.characters.*;
import com.game.skills.effects.FreezeEffect;

public class TestBattleLogic {
    public static void main(String[] args) {
        testFreezeLogic();
        testFpValidation();
    }

    private static void testFreezeLogic() {
        System.out.println("=== TEST FREEZE LOGIC ===");
        // Setup
        BaseCharacter p1 = new FireCharacter("Player");
        BaseCharacter p2 = new WaterCharacter("Enemy");
        Battle battle = new Battle(p1, p2);

        // Apply Freeze to Player (1 turn)
        p1.addEffect(new FreezeEffect(1));

        System.out.println("Player has Freeze (1 turn). Executing turn...");
        // Execute turn (should skip)
        // Note: executePlayerAction returns true if turn is consumed (even if skipped)
        boolean turnResult = battle.executePlayerAction(1); // Skill index 1 (Attack)

        if (turnResult) {
            System.out.println("Turn executed (skipped as expected).");
        } else {
            System.out.println("FAILED: Turn should be considered executed (skipped).");
        }

        // Check if Freeze is removed
        if (p1.canMove()) {
            System.out.println("SUCCESS: Player can move now (Freeze expired).");
        } else {
            System.out.println("FAILED: Player still frozen!");
        }
    }

    private static void testFpValidation() {
        System.out.println("\n=== TEST FP VALIDATION ===");
        BaseCharacter p1 = new FireCharacter("Player");
        BaseCharacter p2 = new WaterCharacter("Enemy");
        Battle battle = new Battle(p1, p2);

        // Drain FP
        // FireCharacter starts with default FP (20). Fireball costs 15.
        // Let's set FP to 5.
        // Since we don't have setFocusPoints, we use useFocusPoints to drain.
        p1.useFocusPoints(p1.getFocusPoints() - 5);

        System.out.println("Player FP: " + p1.getFocusPoints());
        System.out.println("Trying to use Skill 4 (Fireball, Cost 15)...");

        // FireCharacter skills: Attack(1), Guard(2), Heal(3), Fireball(4)
        boolean result = battle.executePlayerAction(4); // Fireball

        if (!result) {
            System.out.println("SUCCESS: Action rejected due to low FP.");
        } else {
            System.out.println("FAILED: Action executed despite low FP!");
        }
    }
}
