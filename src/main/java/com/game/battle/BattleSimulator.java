package com.game.battle;

import com.game.characters.*;
import java.util.Scanner;

/**
 * Class untuk menjalankan pertarungan dalam mode interaktif.
 * Pemain 1 dan Pemain 2 dapat memilih karakter dan mengambil keputusan strategis.
 */
public class BattleSimulator {

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
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   TURN-BASED BATTLE SIMULATOR      ║");
        System.out.println("╚════════════════════════════════════╝\n");

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

            // Cek apakah pemain saat ini bisa bergerak (tidak freeze)
            if (!battle.getCurrentPlayer().canMove()) {
                System.out.println("❄️  " + battle.getCurrentPlayerName() + " terkena Freeze! Tidak bisa bergerak!\n");
                battle.endTurn();
                continue;
            }

            battle.displayAvailableSkills();

            // Input dari pemain
            int choice = getPlayerChoice();

            // Proses aksi
            if (choice == -1) {
                // Surrender
                battle.surrender();
                break;
            } else {
                // Eksekusi skill
                if (battle.executePlayerAction(choice)) {
                    // Aksi berhasil, cek apakah pertarungan selesai
                    if (battle.isBattleFinished()) {
                        break;
                    }
                    battle.endTurn();
                } else {
                    // Aksi gagal, pemain harus memilih lagi
                    System.out.println();
                }
            }
        }
    }

    /**
     * Mendapatkan input pilihan dari pemain.
     * 
     * @return Index skill (1-based), atau -1 untuk surrender
     */
    private int getPlayerChoice() {
        System.out.print("\n👉 " + battle.getCurrentPlayerName() + ", pilih skill (1-4) atau 0 untuk menyerah: ");
        try {
            int choice = scanner.nextInt();
            if (choice == 0) {
                return -1; // Surrender
            }
            return choice;
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
        System.out.println("\n" + playerName + ", pilih karaktermu:");
        System.out.println("1. Fire Character  - High Attack, Low Defense");
        System.out.println("2. Water Character - Balanced, Good Defense");
        System.out.println("3. Earth Character - High Defense, Low Speed");
        System.out.println("4. Wind Character  - High Speed, Low Defense");
        
        System.out.print("\nPilihan (1-4): ");
        
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Input tidak valid, memilih Fire Character...");
            return new FireCharacter(playerName);
        }

        // Input nama karakter
        System.out.print("Masukkan nama karaktermu: ");
        String characterName = scanner.nextLine();
        if (characterName.trim().isEmpty()) {
            characterName = playerName + " Character";
        }

        // Buat karakter berdasarkan pilihan
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
                System.out.println("Pilihan tidak valid, memilih Fire Character...");
                return new FireCharacter(characterName);
        }
    }

    /**
     * Tanyakan apakah pemain ingin bermain lagi.
     */
    private void askPlayAgain() {
        System.out.print("\nBermain lagi? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        
        if (input.equals("y") || input.equals("yes")) {
            run();
        } else {
            System.out.println("\n👋 Terima kasih telah bermain! Sampai jumpa!\n");
            scanner.close();
        }
    }
}
