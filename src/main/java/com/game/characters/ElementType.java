package com.game.characters;

/**
 * Enum untuk tipe elemen karakter dalam game.
 * 
 * Setiap karakter memiliki satu elemen yang menentukan keunggulan
 * dan kelemahan mereka dalam pertarungan. Sistem elemental mengikuti
 * siklus rock-paper-scissors.
 * 
 * Elemental Cycle:
 * Fire → Wind → Earth → Water → Fire
 * 
 * Damage multiplier:
 * - Super Effective (advantage): 1.5x damage
 * - Not Very Effective (disadvantage): 0.75x damage
 * - Neutral: 1.0x damage
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see ElementalAdvantage
 */
public enum ElementType {
    /** Elemen Fire - Kuat melawan Wind, Lemah melawan Water */
    FIRE,

    /** Elemen Water - Kuat melawan Fire, Lemah melawan Earth */
    WATER,

    /** Elemen Earth - Kuat melawan Water, Lemah melawan Wind */
    EARTH,

    /** Elemen Wind - Kuat melawan Earth, Lemah melawan Fire */
    WIND,

    /** Elemen Neutral - Tidak memiliki keuntungan atau kerugian */
    NEUTRAL
}
