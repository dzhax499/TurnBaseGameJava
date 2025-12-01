package com.game.test;

import com.game.characters.*;

/**
 * Simple test program untuk verify elemental advantage system.
 */
public class ElementalAdvantageTest {

    public static void main(String[] args) {
        System.out.println("=== ELEMENTAL ADVANTAGE SYSTEM TEST ===\n");

        // Create characters
        FireCharacter fire = new FireCharacter("Blaze");
        WaterCharacter water = new WaterCharacter("Aqua");
        EarthCharacter earth = new EarthCharacter("Terra");
        WindCharacter wind = new WindCharacter("Gale");

        System.out.println("Characters created:");
        printCharacterInfo(fire);
        printCharacterInfo(water);
        printCharacterInfo(earth);
        printCharacterInfo(wind);

        System.out.println("\n=== TEST 1: Fire vs Wind (Advantage) ===");
        testAttack(fire, wind);

        System.out.println("\n=== TEST 2: Fire vs Water (Disadvantage) ===");
        testAttack(fire, water);

        System.out.println("\n=== TEST 3: Fire vs Fire (Neutral) ===");
        FireCharacter fire2 = new FireCharacter("Inferno");
        testAttack(fire, fire2);

        System.out.println("\n=== TEST 4: All Matchups ===");
        testAllMatchups();

        System.out.println("\n=== TEST COMPLETE ===");
    }

    private static void printCharacterInfo(BaseCharacter character) {
        System.out.println(String.format("%s (%s) - HP: %d, ATK: %d, DEF: %d, SPD: %d",
                character.getName(),
                character.getElementType(),
                character.getHealthPoints(),
                character.getAttackPower(),
                character.getDefense(),
                character.getSpeed()));
    }

    private static void testAttack(BaseCharacter attacker, BaseCharacter defender) {
        int initialHP = defender.getHealthPoints();

        System.out.println(String.format("\n%s (%s) attacks %s (%s)",
                attacker.getName(),
                attacker.getElementType(),
                defender.getName(),
                defender.getElementType()));

        // Calculate expected multiplier
        double multiplier = ElementalAdvantage.getMultiplier(
                attacker.getElementType(),
                defender.getElementType());
        System.out.println("Expected multiplier: " + multiplier + "x");

        // Perform attack (using basic attack damage)
        int baseDamage = attacker.getAttackPower();
        defender.takeDamageWithMechanics(baseDamage, attacker);

        int damageDealt = initialHP - defender.getHealthPoints();
        System.out.println("Damage dealt: " + damageDealt);
        System.out.println("Defender HP: " + initialHP + " -> " + defender.getHealthPoints());

        // Restore HP for next test
        defender.heal(damageDealt);
    }

    private static void testAllMatchups() {
        BaseCharacter[] characters = {
                CharacterFactory.createCharacter("FIRE", "Fire"),
                CharacterFactory.createCharacter("WATER", "Water"),
                CharacterFactory.createCharacter("EARTH", "Earth"),
                CharacterFactory.createCharacter("WIND", "Wind")
        };

        System.out.println("\nMatchup Chart:");
        System.out.println("Attacker -> Defender | Multiplier");
        System.out.println("----------------------------------------");

        for (BaseCharacter attacker : characters) {
            for (BaseCharacter defender : characters) {
                double multiplier = ElementalAdvantage.getMultiplier(
                        attacker.getElementType(),
                        defender.getElementType());

                String emoji = "";
                if (multiplier > 1.0)
                    emoji = "✓ (Advantage)";
                else if (multiplier < 1.0)
                    emoji = "✗ (Disadvantage)";
                else
                    emoji = "= (Neutral)";

                System.out.println(String.format("%s -> %s | %.2fx %s",
                        attacker.getElementType(),
                        defender.getElementType(),
                        multiplier,
                        emoji));
            }
        }
    }
}
