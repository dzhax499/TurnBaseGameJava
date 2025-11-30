package com.game.battle;

import com.game.characters.*;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class untuk menjalankan pertarungan dalam mode interaktif.
 * Pemain 1 dan Pemain 2 dapat memilih karakter dan mengambil keputusan strategis.
 */
public class BattleSimulator {
    private static final Logger LOGGER = Logger.getLogger(BattleSimulator.class.getName());
    private static Scanner scanner = new Scanner(System.in);
    private Battle battle;

    /**
     * Main method untuk menjalankan simulator pertarungan.
     */
    public static void main(String[] args) {
        BattleSimulator simulator = new BattleSimulator();
        simulator.run();
    }

    /**
     * Menjalankan flow pertarungan interaktif.
     */
    public void run() {
        LOGGER.info("\n╔════════════════════════════════════╗");
        LOGGER.info("║   TURN-BASED BATTLE SIMULATOR      ║");
        LOGGER.info("╚════════════════════════════════════╝\n");

        // Pemain memilih karakter
        BaseCharacter player1Character = selectCharacter("Pemain 1");
        BaseCharacter player2Character = selectCharacter("Pemain 2");

        // Buat battle baru
        battle = new Battle(player1Character, player2Character);

        // Mulai pertarungan
        battle.start();

        // Main battle loop
        battleLoop();

        // Tampilkan hasil
        battle.displayBattleResult();

        // Tanyakan apakah ingin bermain lagi
        askPlayAgain();
    }

    /**
     * Loop utama pertarungan.
     */
    private void battleLoop() {
        while (!battle.isBattleFinished()) {
            battle.displayStatus();
            processPlayerTurn();
        }
    }

    /**
     * Proses turn pemain saat ini.
     */
    private void processPlayerTurn() {
        BaseCharacter currentPlayer = battle.getCurrentPlayer();
        if (!currentPlayer.canMove()) {
            String freezeMsg = String.format("%n❄️  %s terkena Freeze! Tidak bisa bergerak!%n", battle.getCurrentPlayerName());
            LOGGER.info(freezeMsg);
            battle.endTurn();
        } else {
            battle.displayAvailableSkills();
            int choice = getPlayerChoice();
            
            if (choice == -1) {
                battle.surrender();
            } else if (battle.executePlayerAction(choice) && !battle.isBattleFinished()) {
                battle.endTurn();
            }
        }
    }

    /**
     * Mendapatkan input pilihan dari pemain.
     * 
     * @return Index skill (1-based), atau -1 untuk surrender
     */
    private int getPlayerChoice() {
        String prompt = String.format("%n👉 %s, pilih skill (1-4) atau 0 untuk menyerah: ", battle.getCurrentPlayerName());
        LOGGER.info(prompt);
        try {
            int choice = scanner.nextInt();
            return choice == 0 ? -1 : choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -999; // Invalid
        }
    }

    /**
     * Memilih karakter untuk pemain.
     * 
     * @param playerName Nama pemain ("Pemain 1" atau "Pemain 2")
     * @return Karakter pilihan pemain
     */
    private BaseCharacter selectCharacter(String playerName) {
        String header = String.format("%n%s, pilih karaktermu:", playerName);
        LOGGER.info(header);
        LOGGER.info("1. Fire Character  - High Attack, Low Defense");
        LOGGER.info("2. Water Character - Balanced, Good Defense");
        LOGGER.info("3. Earth Character - High Defense, Low Speed");
        LOGGER.info("4. Wind Character  - High Speed, Low Defense");
        
        LOGGER.info("\nPilihan (1-4): ");
        
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline
        } catch (Exception e) {
            scanner.nextLine();
            LOGGER.warning("Input tidak valid, memilih Fire Character...");
            return new FireCharacter(playerName);
        }

        // Input nama karakter
        LOGGER.info("Masukkan nama karaktermu: ");
        String characterName = scanner.nextLine();
        if (characterName.trim().isEmpty()) {
            characterName = playerName + " Character";
        }

        // Buat karakter berdasarkan pilihan
        return createCharacterByChoice(choice, characterName);
    }

    /**
     * Buat karakter berdasarkan pilihan nomor.
     */
    private BaseCharacter createCharacterByChoice(int choice, String characterName) {
        switch (choice) {
            case 1:
                return new FireCharacter(characterName);
            case 2:
                return new WaterCharacter(characterName);
            case 3:
                return new EarthCharacter(characterName);
            case 4:
                return new WindCharacter(characterName);
            default:
                LOGGER.warning("Pilihan tidak valid, memilih Fire Character...");
                return new FireCharacter(characterName);
        }
    }

    /**
     * Tanyakan apakah pemain ingin bermain lagi.
     */
    private void askPlayAgain() {
        LOGGER.info("\nBermain lagi? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        
        if (input.equals("y") || input.equals("yes")) {
            run();
        } else {
            String farewell = String.format("%n👋 Terima kasih telah bermain! Sampai jumpa!%n");
            LOGGER.info(farewell);
            scanner.close();
        }
    }
}
