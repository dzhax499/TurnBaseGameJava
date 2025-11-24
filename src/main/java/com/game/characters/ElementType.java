package com.game.characters;

/**
 * Enum untuk tipe elemen karakter.
 * Menentukan keunggulan dan kelemahan dalam battle.
 * 
 * Elemental Cycle:
 * Fire > Wind > Earth > Water > Fire
 */
public enum ElementType {
    FIRE, // Strong vs Wind, Weak vs Water
    WATER, // Strong vs Fire, Weak vs Earth
    EARTH, // Strong vs Water, Weak vs Wind
    WIND, // Strong vs Earth, Weak vs Fire
    NEUTRAL // No advantage/disadvantage
}
