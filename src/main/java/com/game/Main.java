package com.game;

import com.game.battle.Battle;
import com.game.characters.*;
import com.game.utils.GameStrings;
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
        LOGGER.info(GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        LOGGER.info(GameStrings.WELCOME_TITLE_TEXT);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        LOGGER.info(GameStrings.WELCOME_SUBTITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM);
        LOGGER.info("");
        pause(GameStrings.PROMPT_ENTER);
    }

    /**
     * Menu utama aplikasi.
     */
    private static void mainMenu() {
        boolean running = true;

        while (running) {
            displayMenuOptions();
            int choice = getIntInput(1, 6);
            running = handleMenuChoice(choice);
        }
    }

    private static void displayMenuOptions() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.MENU_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        LOGGER.info(GameStrings.MENU_OPTION_1);
        LOGGER.info(GameStrings.MENU_OPTION_2);
        LOGGER.info(GameStrings.MENU_OPTION_3);
        LOGGER.info(GameStrings.MENU_OPTION_4);
        LOGGER.info(GameStrings.MENU_OPTION_5);
        LOGGER.info(GameStrings.MENU_OPTION_6 + "\n");

        System.out.print(GameStrings.PROMPT_CHOICE_1_6);
    }

    private static boolean handleMenuChoice(int choice) {
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
                return false;
        }
        return true;
    }

    /**
     * Memulai pertarungan baru.
     */
    private static void startNewBattle() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.PREP_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        // Pemain 1 memilih karakter
        LOGGER.info(GameStrings.P1_SELECT_CHAR);
        BaseCharacter player1 = selectCharacter("Pemain 1");

        // Pemain 2 memilih karakter
        LOGGER.info("\n" + GameStrings.P2_SELECT_CHAR);
        BaseCharacter player2 = selectCharacter("Pemain 2");

        // Buat battle baru
        Battle battle = new Battle(player1, player2);

        // Mulai pertarungan
        runBattleLoop(battle);

        // Update statistics
        updateBattleStatistics(battle);
    }

    private static void updateBattleStatistics(Battle battle) {
        BaseCharacter winner = battle.getWinner();
        if (winner != null) {
            battleStats.recordWin(winner.getName());
            BaseCharacter loser = (winner == battle.getPlayer1()) ? battle.getPlayer2() : battle.getPlayer1();
            battleStats.recordLoss(loser.getName());
        }
    }

    /**
     * Memilih karakter untuk pemain.
     */
    private static BaseCharacter selectCharacter(String playerName) {
        clearScreen();
        LOGGER.info("\n" + String.format(GameStrings.CHAR_SELECT_HEADER, playerName) + "\n");

        displayCharacterSelectionMenu();

        LOGGER.info(GameStrings.PROMPT_CHAR_CHOICE);
        int choice = getIntInput(1, 4);

        LOGGER.info(GameStrings.PROMPT_CHAR_NAME);
        scanner.nextLine(); // Clear buffer
        String characterName = scanner.nextLine().trim();
        if (characterName.isEmpty()) {
            characterName = String.format(GameStrings.DEFAULT_CHAR_NAME, playerName);
        }

        BaseCharacter character = CharacterFactory.createCharacter(choice, characterName);

        clearScreen();
        LOGGER.info("\n" + String.format(GameStrings.CHAR_SELECTED_MSG, playerName, character.getName()));
        pause(GameStrings.PROMPT_ENTER);

        return character;
    }

    private static void displayCharacterSelectionMenu() {
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
    }

    /**
     * Loop utama pertarungan.
     */
    private static void runBattleLoop(Battle battle) {
        battle.start();
        pause(GameStrings.PROMPT_START_BATTLE);

        while (!battle.isBattleFinished()) {
            clearScreen();
            battle.displayStatus();

            if (!battle.getCurrentPlayer().canMove()) {
                LOGGER.info(String.format(GameStrings.MSG_FREEZE_SKIP, battle.getCurrentPlayerName()));
                LOGGER.info(GameStrings.MSG_FREEZE_DESC + "\n");
                pause(GameStrings.PROMPT_SKIP_TURN);
                battle.endTurn();
                continue;
            }

            battle.displayAvailableSkills();
            int choice = getActionChoice(battle);

            if (choice == 0) {
                if (confirmSurrender(battle)) {
                    battle.surrender();
                    break;
                }
                continue;
            }

            if (battle.executePlayerAction(choice)) {
                if (battle.isBattleFinished()) {
                    break;
                }
                pause(GameStrings.PROMPT_ENTER);
                battle.endTurn();
            } else {
                pause(GameStrings.PROMPT_RETRY_ACTION);
            }
        }

        displayBattleResult(battle);
    }

    private static boolean confirmSurrender(Battle battle) {
        clearScreen();
        LOGGER.info("\n" + GameStrings.MSG_SURRENDER_CONFIRM);
        System.out.print(String.format(GameStrings.PROMPT_SURRENDER_CONFIRM, battle.getCurrentPlayerName()));
        String confirm = scanner.nextLine().toLowerCase();
        return confirm.equals("y") || confirm.equals("yes");
    }

    private static int getActionChoice(Battle battle) {
        System.out.print("\n" + String.format(GameStrings.PROMPT_ACTION_CHOICE, battle.getCurrentPlayerName()));
        return getIntInput(0, 4);
    }

    /**
     * Menampilkan hasil pertarungan.
     */
    private static void displayBattleResult(Battle battle) {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.RESULT_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        BaseCharacter winner = battle.getWinner();
        if (winner != null) {
            LOGGER.info(String.format(GameStrings.MSG_WINNER_ANNOUNCEMENT, winner.getName()) + "\n");
            LOGGER.info(GameStrings.STATS_WINNER_HEADER);
            LOGGER.info(String.format(GameStrings.STAT_HP_REMAINING, winner.getHealthPoints(),
                    winner.getMaxHealthPoints()));
            LOGGER.info(
                    String.format(GameStrings.STAT_FP_REMAINING, winner.getFocusPoints(), winner.getMaxFocusPoints()));
            LOGGER.info("");
        }

        LOGGER.info(GameStrings.STATS_DETAIL_HEADER);
        LOGGER.info(String.format(GameStrings.STAT_TOTAL_TURN, battle.getTurnCount()));
        LOGGER.info(String.format(GameStrings.STAT_TOTAL_ACTION, battle.getBattleLog().getActionCount()));
        LOGGER.info("");

        System.out.print(GameStrings.PROMPT_VIEW_LOG);
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y") || input.equals("yes")) {
            displayBattleLog(battle);
        }

        pause(GameStrings.PROMPT_ENTER);
    }

    private static void displayBattleLog(Battle battle) {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.LOG_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");
        battle.displayBattleLog();
        pause(GameStrings.PROMPT_ENTER);
    }

    /**
     * Menampilkan statistik pertarungan.
     */
    private static void displayBattleStats() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.STATS_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        if (battleStats.getTotalBattles() == 0) {
            LOGGER.info(GameStrings.MSG_NO_BATTLES + "\n");
        } else {
            LOGGER.info(String.format(GameStrings.STAT_TOTAL_BATTLES, battleStats.getTotalBattles()));
            LOGGER.info("");
            LOGGER.info(GameStrings.HEADER_TOP_WINNERS);
            battleStats.displayTopWinners();
            LOGGER.info("");
        }

        pause(GameStrings.PROMPT_ENTER);
    }

    /**
     * Menampilkan panduan karakter dan elemen.
     */
    private static void displayCharacterGuide() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.GUIDE_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        displayFireCharacterInfo();
        displayWaterCharacterInfo();
        displayEarthCharacterInfo();
        displayWindCharacterInfo();

        pause(GameStrings.PROMPT_ENTER);
    }

    private static void displayFireCharacterInfo() {
        displayCharacterSection(
                GameStrings.GUIDE_FIRE_TITLE,
                "Attack Power tertinggi (35)",
                "Defense rendah",
                "Fireball - Burn effect (5 damage/turn)",
                "Main agresif, serang pertama");
    }

    private static void displayWaterCharacterInfo() {
        displayCharacterSection(
                GameStrings.GUIDE_WATER_TITLE,
                "HP & Defense seimbang, Freeze ability",
                "Speed rendah",
                "Ice Blast - Freeze effect (skip turn)",
                "Control game dengan freeze, defensive play");
    }

    private static void displayEarthCharacterInfo() {
        displayCharacterSection(
                GameStrings.GUIDE_EARTH_TITLE,
                "HP tertinggi (140), Defense tertinggi (30)",
                "Speed sangat rendah",
                "Rock Throw - Solid damage",
                "Bertahan lama, guard heavy");
    }

    private static void displayWindCharacterInfo() {
        displayCharacterSection(
                GameStrings.GUIDE_WIND_TITLE,
                "Speed tertinggi (50), jalan duluan",
                "HP & Defense terendah",
                "Air Slash - High speed combo",
                "Hit and run, manfaatkan kecepatan");
    }

    private static void displayCharacterSection(String title, String strength, String weakness, String skill,
            String strategy) {
        LOGGER.info(title);
        LOGGER.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  • Kekuatan: " + strength);
        LOGGER.info("  • Kelemahan: " + weakness);
        LOGGER.info("  • Skill Spesial: " + skill);
        LOGGER.info("  • Strategi: " + strategy + "\n");
    }

    /**
     * Menampilkan elemental matchup chart.
     */
    private static void displayElementalChart() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.CHART_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        LOGGER.info(GameStrings.CHART_CYCLE_HEADER);
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

        LOGGER.info(GameStrings.CHART_MULTIPLIER_HEADER);
        LOGGER.info("━━━━━━━━━━━━━━━━━━━");
        LOGGER.info("  ✅ Advantage (Strong vs): 1.5x damage");
        LOGGER.info("  ⚖️  Neutral: 1.0x damage");
        LOGGER.info("  ❌ Disadvantage (Weak vs): 0.75x damage\n");

        LOGGER.info(GameStrings.CHART_EXAMPLE_HEADER);
        LOGGER.info("━━━━━━━");
        LOGGER.info("  Water vs Fire: Sangat efektif! (1.5x) 💧 > 🔥");
        LOGGER.info("  Fire vs Water: Kurang efektif... (0.75x) 🔥 < 💧");
        LOGGER.info("  Fire vs Wind: Sangat efektif! (1.5x) 🔥 > 💨\n");

        pause(GameStrings.PROMPT_ENTER);
    }

    /**
     * Menampilkan leaderboard (top winners).
     */
    private static void displayLeaderboard() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.LEADERBOARD_HEADER_TITLE);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");

        if (battleStats.getTotalBattles() == 0) {
            LOGGER.info(GameStrings.MSG_NO_BATTLES + "\n");
        } else {
            LOGGER.info("📊 TOP PEMENANG:\n");
            battleStats.displayTopWinners();
            LOGGER.info("");
        }

        pause(GameStrings.PROMPT_ENTER);
    }

    /**
     * Menampilkan goodbye message.
     */
    private static void displayGoodbye() {
        clearScreen();
        LOGGER.info("\n" + GameStrings.WELCOME_TITLE_BORDER);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        LOGGER.info(GameStrings.GOODBYE_MSG);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        String totalBattles = String.format(GameStrings.GOODBYE_STATS, battleStats.getTotalBattles());
        LOGGER.info(totalBattles);
        LOGGER.info(GameStrings.WELCOME_TITLE_EMPTY);
        LOGGER.info(GameStrings.WELCOME_TITLE_BOTTOM + "\n");
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
                System.out.print(String.format(GameStrings.ERR_INVALID_INPUT, min, max));
                return getIntInput(min, max);
            }
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.print(String.format(GameStrings.ERR_INVALID_INPUT, min, max));
            return getIntInput(min, max);
        }
    }
}
