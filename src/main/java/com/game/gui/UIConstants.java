package com.game.gui;

import java.awt.Color;

/**
 * Centralized constants for UI layout and styling.
 * Eliminates magic numbers from GUI classes.
 */
public class UIConstants {
    
    // Window Dimensions
    public static final int WINDOW_WIDTH = 820;
    public static final int WINDOW_HEIGHT = 620;
    
    // Turn Label
    public static final int TURN_LABEL_X = 280;
    public static final int TURN_LABEL_Y = 15;
    public static final int TURN_LABEL_WIDTH = 260;
    public static final int TURN_LABEL_HEIGHT = 30;
    public static final int TURN_LABEL_FONT_SIZE = 22;
    
    // Bottom Panel
    public static final int BOTTOM_PANEL_Y = 450;
    public static final int BOTTOM_PANEL_HEIGHT = 170;
    
    // Skill Panel Layout
    public static final int SKILL_PANEL_BORDER_THICKNESS = 2;
    public static final int SKILL_BTN_WIDTH = 260;
    public static final int SKILL_BTN_HEIGHT = 60;
    public static final int SKILL_BTN_START_X = 20;
    public static final int SKILL_BTN_START_Y = 20;
    public static final int SKILL_BTN_GAP_X = 20;
    public static final int SKILL_BTN_GAP_Y = 15;
    
    // Surrender Button
    public static final int SURRENDER_BTN_X = 620;
    public static final int SURRENDER_BTN_Y = 45;
    public static final int SURRENDER_BTN_WIDTH = 160;
    public static final int SURRENDER_BTN_HEIGHT = 80;
    public static final int SURRENDER_BTN_FONT_SIZE = 14;
    public static final int SURRENDER_BORDER_THICKNESS = 2;
    
    // Text Panel
    public static final int TEXT_AREA_X = 30;
    public static final int TEXT_AREA_Y = 20;
    public static final int TEXT_AREA_WIDTH = 650;
    public static final int TEXT_AREA_HEIGHT = 130;
    public static final int TEXT_AREA_FONT_SIZE = 18;
    
    // Continue Button
    public static final int CONTINUE_BTN_X = 700;
    public static final int CONTINUE_BTN_Y = 50;
    public static final int CONTINUE_BTN_WIDTH = 80;
    public static final int CONTINUE_BTN_HEIGHT = 70;
    public static final int CONTINUE_BTN_FONT_SIZE = 24;
    
    // Colors
    public static final Color COLOR_YELLOW = Color.YELLOW;
    public static final Color COLOR_WHITE = Color.WHITE;
    public static final Color COLOR_RED = Color.RED;
    public static final Color COLOR_GREEN = Color.GREEN;
    public static final Color COLOR_CYAN = Color.CYAN;
    public static final Color COLOR_ORANGE = Color.ORANGE;
    
    public static final Color PANEL_BG_COLOR = new Color(30, 30, 50);
    public static final Color TEXT_PANEL_BG_COLOR = new Color(20, 20, 20);
    
    public static final Color SKILL_1_COLOR = new Color(220, 50, 50);
    public static final Color SKILL_2_COLOR = new Color(255, 165, 0);
    public static final Color SKILL_3_COLOR = new Color(50, 200, 50);
    public static final Color SKILL_4_COLOR = new Color(150, 50, 200);
    
    public static final Color SURRENDER_BTN_COLOR = new Color(100, 100, 100);
    public static final Color CONTINUE_BTN_COLOR = new Color(50, 50, 200);
    
    public static final Color HP_BAR_BG_COLOR = new Color(40, 40, 40);
    public static final Color FP_BAR_FILL_COLOR = new Color(0, 100, 150);
    
    public static final Color OVERLAY_COLOR = new Color(0, 0, 0, 100);
    public static final Color PLAYER_PLACEHOLDER_COLOR = new Color(100, 150, 255);
    public static final Color ENEMY_PLACEHOLDER_COLOR = new Color(255, 100, 100);
    
    // Character Display
    public static final int CHAR_SPRITE_SIZE = 120;
    public static final int CHAR_SPRITE_Y_OFFSET = 60;
    public static final int CHAR_STATS_Y = 360;
    public static final int CHAR_MID_Y = 220;
    
    public static final int P1_SPRITE_X = 80;
    public static final int P1_STATS_X = 30;
    
    public static final int P2_SPRITE_X = 620;
    public static final int P2_STATS_X = 550;
    
    public static final int VS_LABEL_Y = 240;
    public static final int VS_FONT_SIZE = 36;
    
    public static final int STAT_BAR_WIDTH = 240;
    public static final int HP_BAR_HEIGHT = 25;
    public static final int FP_BAR_HEIGHT = 20;
    public static final int STAT_LABEL_OFFSET = 35;
    
    // Private constructor to prevent instantiation
    private UIConstants() {}
}
