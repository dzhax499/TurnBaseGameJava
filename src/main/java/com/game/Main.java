package com.game;

import com.game.battle.Battle;
import com.game.characters.*;
import java.util.Scanner;

/**
 * Main Application Class untuk Turn-Based Battle Game.
 * Aplikasi dimulai dari sini dengan menu utama lengkap.
 * 
 * Features:
 * - Menu utama dengan berbagai opsi
 * - Character selection interaktif
 * - Full battle system dengan input user
 * - Battle history dan statistics
 * - Replay option
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static BattleStats battleStats = new BattleStats();
    private static Battle lastBattle = null;

    /**
     * Main entry point untuk aplikasi.
     */
    public static void main(String[] args) {
        displayWelcome();
        mainMenu();
        scanner.close();
    }

    /**
     * Menampilkan welcome screen.
     */
    private static void displayWelcome() {
        clearScreen();
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║         🎮 SELAMAT DATANG DI TURN-BASED BATTLE GAME 🎮       ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Sistem Pertarungan 1 vs 1 dengan Elemental Advantage        ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        pause("Tekan ENTER untuk melanjutkan...");
    }

    /**
     * Menu utama aplikasi.
     */
    private static void mainMenu() {
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                      MENU UTAMA                             ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

            System.out.println("1. 🎮 Mulai Pertarungan Baru");
            System.out.println("2. 📊 Lihat Statistik Pertarungan");
            System.out.println("3. ℹ️  Panduan Karakter & Elemen");
            System.out.println("4. 🎲 Lihat Elemental Matchup Chart");
            System.out.println("5. 🏆 Leaderboard");
            System.out.println("6. ❌ Keluar\n");

            System.out.print("Pilihan (1-6): ");
            int choice = getIntInput(1, 6);

            switch (choice) {
                case 1:
                    startNewBattle();
                    break;
                case 2:
                    displayBattleStats();
                    break;
                case 3:
                    displayCharacterGuide();
                    break;
                case 4:
                    displayElementalChart();
                    break;
                case 5:
                    displayLeaderboard();
                    break;
                case 6:
                    displayGoodbye();
                    running = false;
                    break;
            }
        }
    }

    /**
     * Memulai pertarungan baru.
     */
    private static void startNewBattle() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 PERSIAPAN PERTARUNGAN                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // Pemain 1 memilih karakter
        System.out.println("⚔️  PEMAIN 1 - PILIH KARAKTER MU");
        BaseCharacter player1 = selectCharacter("Pemain 1");

        // Pemain 2 memilih karakter
        System.out.println("\n⚔️  PEMAIN 2 - PILIH KARAKTER MU");
        BaseCharacter player2 = selectCharacter("Pemain 2");

        // Buat battle baru
        Battle battle = new Battle(player1, player2);
        lastBattle = battle;

        // Mulai pertarungan
        runBattleLoop(battle);

        // Update statistics
        BaseCharacter winner = battle.getWinner();
        if (winner != null) {
            battleStats.recordWin(winner.getName());
            battleStats.recordLoss(battle.getOpponentPlayer().getName());
        }
    }

    /**
     * Memilih karakter untuk pemain.
     * 
     * @param playerName Nama pemain
     * @return Karakter yang dipilih
     */
    private static BaseCharacter selectCharacter(String playerName) {
        clearScreen();
        System.out.println("\n" + playerName + ", PILIH KARAKTERMU:\n");
        
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. 🔥 FIRE CHARACTER (Penyerang)                             │");
        System.out.println("│    HP: 100 | ATK: 35⭐ | DEF: 15 | SPD: 30                   │");
        System.out.println("│    Keahlian: Fireball (Burn Effect)                         │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.println("│ 2. 💧 WATER CHARACTER (Seimbang)                            │");
        System.out.println("│    HP: 120⭐| ATK: 25 | DEF: 25⭐| SPD: 10                    │");
        System.out.println("│    Keahlian: Ice Blast (Freeze Effect)                      │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.println("│ 3. 🌍 EARTH CHARACTER (Tangki)                              │");
        System.out.println("│    HP: 140⭐| ATK: 25 | DEF: 30⭐| SPD: 5                     │");
        System.out.println("│    Keahlian: Rock Throw (Solid Damage)                      │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.println("│ 4. 💨 WIND CHARACTER (Cepat)                                │");
        System.out.println("│    HP: 90 | ATK: 30 | DEF: 10 | SPD: 50⭐                    │");
        System.out.println("│    Keahlian: Air Slash (High Speed)                         │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.print("Pilihan Karakter (1-4): ");
        int choice = getIntInput(1, 4);

        System.out.print("Masukkan nama karaktermu: ");
        scanner.nextLine(); // Clear buffer
        String characterName = scanner.nextLine().trim();
        if (characterName.isEmpty()) {
            characterName = playerName + "'s Character";
        }

        BaseCharacter character = createCharacter(choice, characterName);
        
        clearScreen();
        System.out.println("\n✅ " + playerName + " memilih " + character.getName() + "!");
        pause("Tekan ENTER untuk melanjutkan...");
        
        return character;
    }

    /**
     * Membuat karakter berdasarkan pilihan.
     */
    private static BaseCharacter createCharacter(int choice, String name) {
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
     * Loop utama pertarungan.
     */
    private static void runBattleLoop(Battle battle) {
        // Mulai pertarungan
        battle.start();
        pause("Tekan ENTER untuk memulai pertarungan...");

        // Main battle loop
        while (!battle.isBattleFinished()) {
            clearScreen();
            battle.displayStatus();

            // Cek apakah pemain saat ini bisa bergerak
            if (!battle.getCurrentPlayer().canMove()) {
                System.out.println("❄️  " + battle.getCurrentPlayerName() + " terkena Freeze!");
                System.out.println("    Tidak bisa bergerak tahun ini!\n");
                pause("Tekan ENTER untuk skip turn...");
                battle.endTurn();
                continue;
            }

            // Tampilkan skill yang tersedia
            battle.displayAvailableSkills();

            // Input dari pemain
            int choice = getActionChoice(battle);

            if (choice == 0) {
                // Surrender
                clearScreen();
                System.out.println("\n⚠️  Konfirmasi Surrender!");
                System.out.print("Apakah " + battle.getCurrentPlayerName() + " benar-benar ingin menyerah? (y/n): ");
                String confirm = scanner.nextLine().toLowerCase();
                
                if (confirm.equals("y") || confirm.equals("yes")) {
                    battle.surrender();
                    break;
                }
                continue;
            }

            // Eksekusi skill
            if (battle.executePlayerAction(choice)) {
                // Aksi berhasil, lanjut ke next turn
                if (battle.isBattleFinished()) {
                    break;
                }
                pause("Tekan ENTER untuk melanjutkan...");
                battle.endTurn();
            } else {
                // Aksi gagal, pemain harus memilih lagi
                pause("Tekan ENTER untuk memilih ulang...");
            }
        }

        // Tampilkan hasil pertarungan
        displayBattleResult(battle);
    }

    /**
     * Mendapatkan pilihan aksi dari pemain.
     */
    private static int getActionChoice(Battle battle) {
        System.out.print("\n👉 " + battle.getCurrentPlayerName() + ", pilih skill (1-4) atau 0 untuk menyerah: ");
        return getIntInput(0, 4);
    }

    /**
     * Menampilkan hasil pertarungan.
     */
    private static void displayBattleResult(Battle battle) {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  PERTARUNGAN SELESAI!                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        BaseCharacter winner = battle.getWinner();
        if (winner != null) {
            System.out.println("🎉 PEMENANG: " + winner.getName() + " 🎉\n");
            System.out.println("📊 Statistik Pemenang:");
            System.out.println("   HP Tersisa: " + winner.getHealthPoints() + "/" + winner.getMaxHealthPoints());
            System.out.println("   FP Tersisa: " + winner.getFocusPoints() + "/" + winner.getMaxFocusPoints());
            System.out.println();
        }

        System.out.println("📈 Detail Pertarungan:");
        System.out.println("   Total Turn: " + battle.getTurnCount());
        System.out.println("   Total Aksi: " + battle.getBattleLog().getActionCount());
        System.out.println();

        System.out.print("Ingin melihat battle log lengkap? (y/n): ");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y") || input.equals("yes")) {
            displayBattleLog(battle);
        }

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan battle log.
     */
    private static void displayBattleLog(Battle battle) {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    BATTLE LOG LENGKAP                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
        battle.displayBattleLog();
        pause("Tekan ENTER untuk kembali...");
    }

    /**
     * Menampilkan statistik pertarungan.
     */
    private static void displayBattleStats() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  STATISTIK PERTARUNGAN                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        if (battleStats.getTotalBattles() == 0) {
            System.out.println("Belum ada pertarungan yang dimainkan.\n");
        } else {
            System.out.println("📊 Total Pertarungan: " + battleStats.getTotalBattles());
            System.out.println();
            System.out.println("🏆 Pemenang Terbanyak:");
            battleStats.displayTopWinners();
            System.out.println();
        }

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan panduan karakter dan elemen.
     */
    private static void displayCharacterGuide() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  PANDUAN KARAKTER & ELEMEN                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("🔥 FIRE CHARACTER - Penyerang Agresif");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  • Kekuatan: Attack Power tertinggi (35)");
        System.out.println("  • Kelemahan: Defense rendah");
        System.out.println("  • Skill Spesial: Fireball - Burn effect (5 damage/turn)");
        System.out.println("  • Strategi: Main agresif, serang pertama\n");

        System.out.println("💧 WATER CHARACTER - Karakter Seimbang");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  • Kekuatan: HP & Defense seimbang, Freeze ability");
        System.out.println("  • Kelemahan: Speed rendah");
        System.out.println("  • Skill Spesial: Ice Blast - Freeze effect (skip turn)");
        System.out.println("  • Strategi: Control game dengan freeze, defensive play\n");

        System.out.println("🌍 EARTH CHARACTER - Tank Pertahanan");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  • Kekuatan: HP tertinggi (140), Defense tertinggi (30)");
        System.out.println("  • Kelemahan: Speed sangat rendah");
        System.out.println("  • Skill Spesial: Rock Throw - Solid damage");
        System.out.println("  • Strategi: Bertahan lama, guard heavy\n");

        System.out.println("💨 WIND CHARACTER - Speedster");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  • Kekuatan: Speed tertinggi (50), jalan duluan");
        System.out.println("  • Kelemahan: HP & Defense terendah");
        System.out.println("  • Skill Spesial: Air Slash - High speed combo");
        System.out.println("  • Strategi: Hit and run, manfaatkan kecepatan\n");

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan elemental matchup chart.
     */
    private static void displayElementalChart() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               ELEMENTAL ADVANTAGE CHART                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("ELEMENTAL CYCLE:");
        System.out.println("━━━━━━━━━━━━━━━━━\n");

        System.out.println("  🔥 FIRE");
        System.out.println("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        System.out.println("  💨 WIND              💧 WATER\n");

        System.out.println("  💨 WIND");
        System.out.println("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        System.out.println("  🌍 EARTH             🔥 FIRE\n");

        System.out.println("  🌍 EARTH");
        System.out.println("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        System.out.println("  💧 WATER             💨 WIND\n");

        System.out.println("  💧 WATER");
        System.out.println("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        System.out.println("  🔥 FIRE              🌍 EARTH\n");

        System.out.println("DAMAGE MULTIPLIER:");
        System.out.println("━━━━━━━━━━━━━━━━━━━");
        System.out.println("  ✅ Advantage (Strong vs): 1.5x damage");
        System.out.println("  ⚖️  Neutral: 1.0x damage");
        System.out.println("  ❌ Disadvantage (Weak vs): 0.75x damage\n");

        System.out.println("CONTOH:");
        System.out.println("━━━━━━━");
        System.out.println("  Water vs Fire: Sangat efektif! (1.5x) 💧 > 🔥");
        System.out.println("  Fire vs Water: Kurang efektif... (0.75x) 🔥 < 💧");
        System.out.println("  Fire vs Wind: Sangat efektif! (1.5x) 🔥 > 💨\n");

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan leaderboard (top winners).
     */
    private static void displayLeaderboard() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🏆 LEADERBOARD 🏆                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        if (battleStats.getTotalBattles() == 0) {
            System.out.println("Belum ada pertarungan yang dimainkan.\n");
        } else {
            System.out.println("📊 TOP PEMENANG:\n");
            battleStats.displayTopWinners();
            System.out.println();
        }

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan goodbye message.
     */
    private static void displayGoodbye() {
        clearScreen();
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║         👋 TERIMA KASIH TELAH BERMAIN! SAMPAI JUMPA! 👋      ║");
        System.out.println("║                                                              ║");
        System.out.println("║              Total Pertarungan: " + String.format("%-32d", battleStats.getTotalBattles()) + "║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Membersihkan layar terminal.
     */
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Jika clear screen gagal, print beberapa newline
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Menampilkan pause message dan menunggu input.
     */
    private static void pause(String message) {
        System.out.print("\n" + message);
        scanner.nextLine();
    }

    /**
     * Mendapatkan integer input dari user dengan validasi range.
     */
    private static int getIntInput(int min, int max) {
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.print("Input tidak valid! Masukkan angka antara " + min + "-" + max + ": ");
                return getIntInput(min, max);
            }
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.print("Input tidak valid! Masukkan angka antara " + min + "-" + max + ": ");
            return getIntInput(min, max);
        }
    }
}
