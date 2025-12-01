package com.game.characters;

public class CharacterFactory {

    public static BaseCharacter createCharacter(int choice, String name) {
        switch (choice) {
            case 1:
                return new FireCharacter(name);
            case 2:
                return new WaterCharacter(name);
            case 3:
                return new EarthCharacter(name);
            case 4:
                return new WindCharacter(name);
            default:
                return new FireCharacter(name);
        }
    }

    public static BaseCharacter createCharacter(String type, String name) {
        if (type == null) {
            return new FireCharacter(name);
        }

        switch (type.toUpperCase()) {
            case "FIRE":
                return new FireCharacter(name);
            case "WATER":
                return new WaterCharacter(name);
            case "EARTH":
                return new EarthCharacter(name);
            case "WIND":
                return new WindCharacter(name);
            default:
                return new FireCharacter(name);
        }
    }
}
