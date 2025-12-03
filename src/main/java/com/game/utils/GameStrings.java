package com.game.utils;

/**
 * Centralized string constants for the application.
 * Facilitates internationalization and consistency.
 */
public class GameStrings {

    // Welcome Screen
    public static final String WELCOME_TITLE_BORDER = "╔══════════════════════════════════════════════════════════════╗";
    public static final String WELCOME_TITLE_EMPTY = "║                                                              ║";
    public static final String WELCOME_TITLE_TEXT = "║         🎮 SELAMAT DATANG DI TURN-BASED BATTLE GAME 🎮       ║";
    public static final String WELCOME_SUBTITLE = "║  Sistem Pertarungan 1 vs 1 dengan Elemental Advantage        ║";
    public static final String WELCOME_TITLE_BOTTOM = "╚══════════════════════════════════════════════════════════════╝";
    public static final String PROMPT_ENTER = "Tekan ENTER untuk melanjutkan...";

    // Main Menu
    public static final String MENU_HEADER_TITLE = "║                      MENU UTAMA                             ║";
    public static final String MENU_OPTION_1 = "1. 🎮 Mulai Pertarungan Baru";
    public static final String MENU_OPTION_2 = "2. 📊 Lihat Statistik Pertarungan";
    public static final String MENU_OPTION_3 = "3. ℹ️  Panduan Karakter & Elemen";
    public static final String MENU_OPTION_4 = "4. 🎲 Lihat Elemental Matchup Chart";
    public static final String MENU_OPTION_5 = "5. 🏆 Leaderboard";
    public static final String MENU_OPTION_6 = "6. ❌ Keluar";
    public static final String PROMPT_CHOICE_1_6 = "Pilihan (1-6): ";

    // Battle Preparation
    public static final String PREP_HEADER_TITLE = "║                 PERSIAPAN PERTARUNGAN                         ║";
    public static final String P1_SELECT_CHAR = "⚔️  PEMAIN 1 - PILIH KARAKTER MU";
    public static final String P2_SELECT_CHAR = "⚔️  PEMAIN 2 - PILIH KARAKTER MU";
    public static final String CHAR_SELECT_HEADER = "%s, PILIH KARAKTERMU:";
    public static final String PROMPT_CHAR_CHOICE = "Pilihan Karakter (1-4): ";
    public static final String PROMPT_CHAR_NAME = "Masukkan nama karaktermu: ";
    public static final String DEFAULT_CHAR_NAME = "%s's Character";
    public static final String CHAR_SELECTED_MSG = "✅ %s memilih %s!";

    // Battle Flow
    public static final String BATTLE_START_TITLE = "║   PERTARUNGAN DIMULAI!             ║";
    public static final String BATTLE_VS = "⚔️  vs";
    public static final String TURN_INFO = "TURN %d - %s GILIRAN";
    public static final String PROMPT_START_BATTLE = "Tekan ENTER untuk memulai pertarungan...";
    public static final String PROMPT_SKIP_TURN = "Tekan ENTER untuk skip turn...";
    public static final String PROMPT_ACTION_CHOICE = "👉 %s, pilih skill (1-4) atau 0 untuk menyerah: ";
    public static final String PROMPT_RETRY_ACTION = "Tekan ENTER untuk memilih ulang...";
    public static final String MSG_FREEZE_SKIP = "❄️  %s terkena Freeze!";
    public static final String MSG_FREEZE_DESC = "    Tidak bisa bergerak turn ini!";
    public static final String MSG_SURRENDER_CONFIRM = "⚠️  Konfirmasi Surrender!";
    public static final String PROMPT_SURRENDER_CONFIRM = "Apakah %s benar-benar ingin menyerah? (y/n): ";
    public static final String MSG_SURRENDERED = "💔 %s menyerah!";
    public static final String MSG_WINNER = "🎉 %s MENANG!";
    public static final String MSG_INVALID_SKILL = "❌ Pilihan skill tidak valid!";
    public static final String MSG_INSUFFICIENT_FP = "❌ FP tidak cukup! Pilih skill lain.";

    // Battle Results
    public static final String RESULT_HEADER_TITLE = "║                  PERTARUNGAN SELESAI!                        ║";
    public static final String MSG_WINNER_ANNOUNCEMENT = "🎉 PEMENANG: %s 🎉";
    public static final String STATS_WINNER_HEADER = "📊 Statistik Pemenang:";
    public static final String STAT_HP_REMAINING = "   HP Tersisa: %d/%d";
    public static final String STAT_FP_REMAINING = "   FP Tersisa: %d/%d";
    public static final String STATS_DETAIL_HEADER = "📈 Detail Pertarungan:";
    public static final String STAT_TOTAL_TURN = "   Total Turn: %d";
    public static final String STAT_TOTAL_ACTION = "   Total Aksi: %d";
    public static final String PROMPT_VIEW_LOG = "Ingin melihat battle log lengkap? (y/n): ";
    public static final String LOG_HEADER_TITLE = "║                    BATTLE LOG LENGKAP                        ║";

    // Statistics
    public static final String STATS_HEADER_TITLE = "║                  STATISTIK PERTARUNGAN                       ║";
    public static final String MSG_NO_BATTLES = "Belum ada pertarungan yang dimainkan.";
    public static final String STAT_TOTAL_BATTLES = "📊 Total Pertarungan: %d";
    public static final String HEADER_TOP_WINNERS = "🏆 Pemenang Terbanyak:";

    // Character Guide
    public static final String GUIDE_HEADER_TITLE = "║                  PANDUAN KARAKTER & ELEMEN                   ║";
    public static final String GUIDE_FIRE_TITLE = "🔥 FIRE CHARACTER - Penyerang Agresif";
    public static final String GUIDE_WATER_TITLE = "💧 WATER CHARACTER - Karakter Seimbang";
    public static final String GUIDE_EARTH_TITLE = "🌍 EARTH CHARACTER - Tank Pertahanan";
    public static final String GUIDE_WIND_TITLE = "💨 WIND CHARACTER - Speedster";

    // Elemental Chart
    public static final String CHART_HEADER_TITLE = "║               ELEMENTAL ADVANTAGE CHART                      ║";
    public static final String CHART_CYCLE_HEADER = "ELEMENTAL CYCLE:";
    public static final String CHART_MULTIPLIER_HEADER = "DAMAGE MULTIPLIER:";
    public static final String CHART_EXAMPLE_HEADER = "CONTOH:";

    // Leaderboard
    public static final String LEADERBOARD_HEADER_TITLE = "║                   🏆 LEADERBOARD 🏆                         ║";

    // Goodbye
    public static final String GOODBYE_MSG = "║         👋 TERIMA KASIH TELAH BERMAIN! SAMPAI JUMPA! 👋      ║";
    public static final String GOODBYE_STATS = "║              Total Pertarungan: %-32d║";

    // Input Validation
    public static final String ERR_INVALID_INPUT = "Input tidak valid! Masukkan angka antara %d-%d: ";

    private GameStrings() {
    }
}
