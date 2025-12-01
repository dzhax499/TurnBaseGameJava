package com.game;

import com.game.battle.Battle;
import com.game.characters.*;
import java.util.Scanner;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
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
        LOGGER.info("\n");
        LOGGER.info("╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                                                              ║");
        LOGGER.info("║         🎮 SELAMAT DATANG DI TURN-BASED BATTLE GAME 🎮       ║");
        LOGGER.info("║                                                              ║");
        LOGGER.info("║  Sistem Pertarungan 1 vs 1 dengan Elemental Advantage        ║");
        LOGGER.info("║                                                              ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝");
        LOGGER.info("");
        pause("Tekan ENTER untuk melanjutkan...");
    }

    /**
     * Menu utama aplikasi.
     */
    private static void mainMenu() {
        boolean running = true;

        while (running) {
            clearScreen();
            LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
            LOGGER.info("║                      MENU UTAMA                             ║");
            LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

            LOGGER.info("1. 🎮 Mulai Pertarungan Baru");
            LOGGER.info("2. 📊 Lihat Statistik Pertarungan");
            LOGGER.info("3. ℹ️  Panduan Karakter & Elemen");
            LOGGER.info("4. 🎲 Lihat Elemental Matchup Chart");
            LOGGER.info("5. 🏆 Leaderboard");
            LOGGER.info("6. ❌ Keluar\n");

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
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                 PERSIAPAN PERTARUNGAN                         ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        // Pemain 1 memilih karakter
        LOGGER.info("⚔️  PEMAIN 1 - PILIH KARAKTER MU");
        BaseCharacter player1 = selectCharacter("Pemain 1");

        // Pemain 2 memilih karakter
        LOGGER.info("\n⚔️  PEMAIN 2 - PILIH KARAKTER MU");
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
            // Tentukan loser dengan benar (bukan menggunakan getOpponentPlayer yang tidak
            // reliable)
            BaseCharacter loser = (winner == battle.getPlayer1()) ? battle.getPlayer2() : battle.getPlayer1();
            battleStats.recordLoss(loser.getName());
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
        LOGGER.info("\n" + playerName + ", PILIH KARAKTERMU:\n");

        LOGGER.info("┌─────────────────────────────────────────────────────────────┐");
        LOGGER.info("│ 1. 🔥 FIRE CHARACTER (Penyerang)                             │");
        LOGGER.info("│    HP: 100 | ATK: 35⭐ | DEF: 15 | SPD: 30                   │");
        LOGGER.info("│    Keahlian: Fireball (Burn Effect)                         │");
        LOGGER.info("├─────────────────────────────────────────────────────────────┤");
        LOGGER.info("│ 2. 💧 WATER CHARACTER (Seimbang)                            │");
        LOGGER.info("│    HP: 120⭐| ATK: 25 | DEF: 25⭐| SPD: 10                    │");
        LOGGER.info("│    Keahlian: Ice Blast (Freeze Effect)                      │");
        LOGGER.info("├─────────────────────────────────────────────────────────────┤");
        LOGGER.info("│ 3. 🌍 EARTH CHARACTER (Tangki)                              │");
        LOGGER.info("│    HP: 140⭐| ATK: 25 | DEF: 30⭐| SPD: 5                     │");
        LOGGER.info("│    Keahlian: Rock Throw (Solid Damage)                      │");
        LOGGER.info("├─────────────────────────────────────────────────────────────┤");
        LOGGER.info("│ 4. 💨 WIND CHARACTER (Cepat)                                │");
        LOGGER.info("│    HP: 90 | ATK: 30 | DEF: 10 | SPD: 50⭐                    │");
        LOGGER.info("│    Keahlian: Air Slash (High Speed)                         │");
        LOGGER.info("└─────────────────────────────────────────────────────────────┘\n");

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
        LOGGER.info("\n✅ " + playerName + " memilih " + character.getName() + "!");
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
                LOGGER.info("❄️  " + battle.getCurrentPlayerName() + " terkena Freeze!");
                LOGGER.info("    Tidak bisa bergerak turn ini!\n");
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
                LOGGER.info("\n⚠️  Konfirmasi Surrender!");
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
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                  PERTARUNGAN SELESAI!                        ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        BaseCharacter winner = battle.getWinner();
        if (winner != null) {
            LOGGER.info("🎉 PEMENANG: " + winner.getName() + " 🎉\n");
            LOGGER.info("📊 Statistik Pemenang:");
            LOGGER.info("   HP Tersisa: " + winner.getHealthPoints() + "/" + winner.getMaxHealthPoints());
            LOGGER.info("   FP Tersisa: " + winner.getFocusPoints() + "/" + winner.getMaxFocusPoints());
            LOGGER.info("");
        }

        LOGGER.info("📈 Detail Pertarungan:");
        LOGGER.info("   Total Turn: " + battle.getTurnCount());
        LOGGER.info("   Total Aksi: " + battle.getBattleLog().getActionCount());
        LOGGER.info("");

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
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                    BATTLE LOG LENGKAP                        ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");
        battle.displayBattleLog();
        pause("Tekan ENTER untuk kembali...");
    }

    /**
     * Menampilkan statistik pertarungan.
     */
    private static void displayBattleStats() {
        clearScreen();
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                  STATISTIK PERTARUNGAN                       ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        if (battleStats.getTotalBattles() == 0) {
            LOGGER.info("Belum ada pertarungan yang dimainkan.\n");
        } else {
            LOGGER.info("📊 Total Pertarungan: " + battleStats.getTotalBattles());
            LOGGER.info("");
            LOGGER.info("🏆 Pemenang Terbanyak:");
            battleStats.displayTopWinners();
            LOGGER.info("");
        }

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan panduan karakter dan elemen.
     */
    private static void displayCharacterGuide() {
        clearScreen();
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                  PANDUAN KARAKTER & ELEMEN                   ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        LOGGER.info("🔥 FIRE CHARACTER - Penyerang Agresif");
        LOGGER.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  • Kekuatan: Attack Power tertinggi (35)");
        LOGGER.info("  • Kelemahan: Defense rendah");
        LOGGER.info("  • Skill Spesial: Fireball - Burn effect (5 damage/turn)");
        LOGGER.info("  • Strategi: Main agresif, serang pertama\n");

        LOGGER.info("💧 WATER CHARACTER - Karakter Seimbang");
        LOGGER.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  • Kekuatan: HP & Defense seimbang, Freeze ability");
        LOGGER.info("  • Kelemahan: Speed rendah");
        LOGGER.info("  • Skill Spesial: Ice Blast - Freeze effect (skip turn)");
        LOGGER.info("  • Strategi: Control game dengan freeze, defensive play\n");

        LOGGER.info("🌍 EARTH CHARACTER - Tank Pertahanan");
        LOGGER.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  • Kekuatan: HP tertinggi (140), Defense tertinggi (30)");
        LOGGER.info("  • Kelemahan: Speed sangat rendah");
        LOGGER.info("  • Skill Spesial: Rock Throw - Solid damage");
        LOGGER.info("  • Strategi: Bertahan lama, guard heavy\n");

        LOGGER.info("💨 WIND CHARACTER - Speedster");
        LOGGER.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  • Kekuatan: Speed tertinggi (50), jalan duluan");
        LOGGER.info("  • Kelemahan: HP & Defense terendah");
        LOGGER.info("  • Skill Spesial: Air Slash - High speed combo");
        LOGGER.info("  • Strategi: Hit and run, manfaatkan kecepatan\n");

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan elemental matchup chart.
     */
    private static void displayElementalChart() {
        clearScreen();
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║               ELEMENTAL ADVANTAGE CHART                      ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        LOGGER.info("ELEMENTAL CYCLE:");
        LOGGER.info("━━━━━━━━━━━━━━━━━\n");

        LOGGER.info("  🔥 FIRE");
        LOGGER.info("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        LOGGER.info("  💨 WIND              💧 WATER\n");

        LOGGER.info("  💨 WIND");
        LOGGER.info("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        LOGGER.info("  🌍 EARTH             🔥 FIRE\n");

        LOGGER.info("  🌍 EARTH");
        LOGGER.info("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        LOGGER.info("  💧 WATER             💨 WIND\n");

        LOGGER.info("  💧 WATER");
        LOGGER.info("    ↓ Strong vs ↓       ↓ Weak vs ↓");
        LOGGER.info("  🔥 FIRE              🌍 EARTH\n");

        LOGGER.info("DAMAGE MULTIPLIER:");
        LOGGER.info("━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  ✅ Advantage (Strong vs): 1.5x damage");
        LOGGER.info("  ⚖️  Neutral: 1.0x damage");
        LOGGER.info("  ❌ Disadvantage (Weak vs): 0.75x damage\n");

        LOGGER.info("CONTOH:");
        LOGGER.info("━━━━━━━");
        LOGGER.info("  Water vs Fire: Sangat efektif! (1.5x) 💧 > 🔥");
        LOGGER.info("  Fire vs Water: Kurang efektif... (0.75x) 🔥 < 💧");
        LOGGER.info("  Fire vs Wind: Sangat efektif! (1.5x) 🔥 > 💨\n");

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan leaderboard (top winners).
     */
    private static void displayLeaderboard() {
        clearScreen();
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                   🏆 LEADERBOARD 🏆                         ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");

        if (battleStats.getTotalBattles() == 0) {
            LOGGER.info("Belum ada pertarungan yang dimainkan.\n");
        } else {
            LOGGER.info("📊 TOP PEMENANG:\n");
            battleStats.displayTopWinners();
            LOGGER.info("");
        }

        pause("Tekan ENTER untuk kembali ke menu utama...");
    }

    /**
     * Menampilkan goodbye message.
     */
    private static void displayGoodbye() {
        clearScreen();
        LOGGER.info("\n╔══════════════════════════════════════════════════════════════╗");
        LOGGER.info("║                                                              ║");
        LOGGER.info("║         👋 TERIMA KASIH TELAH BERMAIN! SAMPAI JUMPA! 👋      ║");
        LOGGER.info("║                                                              ║");
        String totalBattles = String.format("║              Total Pertarungan: %-32d║", battleStats.getTotalBattles());
        LOGGER.info(totalBattles);
        LOGGER.info("║                                                              ║");
        LOGGER.info("╚══════════════════════════════════════════════════════════════╝\n");
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
