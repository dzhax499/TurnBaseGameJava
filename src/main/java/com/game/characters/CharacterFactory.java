package com.game.characters;

/**
 * Factory class untuk membuat instance karakter berdasarkan tipe.
 * 
 * CharacterFactory mengimplementasikan Factory Pattern untuk
 * menyederhanakan pembuatan objek karakter. Menyediakan dua cara
 * untuk membuat karakter: berdasarkan angka pilihan atau string tipe.
 * 
 * Tipe karakter yang tersedia:
 * 1 / "FIRE" - FireCharacter
 * 2 / "WATER" - WaterCharacter
 * 3 / "EARTH" - EarthCharacter
 * 4 / "WIND" - WindCharacter
 * 
 * Default: Jika pilihan tidak valid, akan membuat FireCharacter
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see BaseCharacter
 * @see FireCharacter
 * @see WaterCharacter
 * @see EarthCharacter
 * @see WindCharacter
 */
public class CharacterFactory {

    /**
     * Private constructor untuk mencegah instantiation.
     * Class ini hanya menyediakan static methods.
     */
    private CharacterFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Membuat karakter berdasarkan angka pilihan.
     * 
     * @param choice Angka pilihan (1=Fire, 2=Water, 3=Earth, 4=Wind)
     * @param name   Nama karakter
     * @return Instance karakter sesuai pilihan, default FireCharacter
     */
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

    /**
     * Membuat karakter berdasarkan string tipe.
     * Case-insensitive, akan dikonversi ke uppercase.
     * 
     * @param type String tipe ("FIRE", "WATER", "EARTH", "WIND")
     * @param name Nama karakter
     * @return Instance karakter sesuai tipe, default FireCharacter
     */
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
