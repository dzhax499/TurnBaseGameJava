package com.game.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Main Game Window dengan CardLayout untuk switch antara Main Menu dan Battle
 * Screen
 */
public class GameWindow {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private MainMenuPanel mainMenuPanel;
    private BattlePanel battlePanel;

    // Card names
    private static final String MAIN_MENU = "MAIN_MENU";
    private static final String BATTLE = "BATTLE";

    public GameWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Turn Based Game - Elemental Battle");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup CardLayout untuk switching panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize panels
        mainMenuPanel = new MainMenuPanel();
        battlePanel = new BattlePanel();

        // Setup character selection listener
        mainMenuPanel.setCharacterSelectionListener(new MainMenuPanel.CharacterSelectionListener() {
            @Override
            public void onCharacterSelected(String characterType) {
                startBattle(characterType);
            }
        });

        // Setup skill action listener
        battlePanel.setSkillActionListener(new BattlePanel.SkillActionListener() {
            @Override
            public void onSkillUsed(int skillIndex) {
                handleSkillUse(skillIndex);
            }
        });

        // Add panels to card layout
        cardPanel.add(mainMenuPanel, MAIN_MENU);
        cardPanel.add(battlePanel, BATTLE);

        frame.add(cardPanel, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Start with main menu
        showMainMenu();
    }

    public void showMainMenu() {
        cardLayout.show(cardPanel, MAIN_MENU);
    }

    public void showBattle() {
        cardLayout.show(cardPanel, BATTLE);
    }

    private void startBattle(String characterType) {
        System.out.println("Starting battle with character: " + characterType);

        // TODO: Nanti ini akan diganti dengan integrasi Battle class dari Orang A
        // dan Character/Skill dari Orang B

        // Dummy setup untuk testing
        battlePanel.updatePlayerName(characterType + " Hero");
        battlePanel.updateEnemyName("Enemy");
        battlePanel.updatePlayerHP(100, 100);
        battlePanel.updateEnemyHP(100, 100);
        battlePanel.clearBattleLog();
        battlePanel.addBattleLogMessage("=== BATTLE START ===");
        battlePanel.addBattleLogMessage("You chose: " + characterType);
        battlePanel.addBattleLogMessage("Your turn!");

        // Update skill buttons based on character type
        String[] skillNames = getSkillNames(characterType);
        String[] skillDescriptions = getSkillDescriptions(characterType);
        battlePanel.updateSkillButtons(skillNames, skillDescriptions);

        battlePanel.setSkillButtonsEnabled(true);

        // Switch to battle screen
        showBattle();
    }

    private void handleSkillUse(int skillIndex) {
        System.out.println("Skill " + (skillIndex + 1) + " used!");

        // TODO: Nanti ini akan memanggil Battle.nextTurn() dari Orang A

        // Dummy logic untuk testing
        battlePanel.addBattleLogMessage("You used Skill " + (skillIndex + 1) + "!");

        // Simulate damage
        int damage = 20 + (skillIndex * 5);
        battlePanel.addBattleLogMessage("Dealt " + damage + " damage!");

        // Update enemy HP (dummy)
        int currentEnemyHP = 100 - damage; // Simplified for demo
        battlePanel.updateEnemyHP(Math.max(0, currentEnemyHP), 100);

        // Enemy turn (dummy)
        battlePanel.addBattleLogMessage("---");
        battlePanel.addBattleLogMessage("Enemy's turn!");
        battlePanel.addBattleLogMessage("Enemy attacks!");
        battlePanel.addBattleLogMessage("You took 15 damage!");
        battlePanel.updatePlayerHP(85, 100);

        battlePanel.addBattleLogMessage("---");
        battlePanel.addBattleLogMessage("Your turn!");

        // Re-enable buttons for next turn
        battlePanel.setSkillButtonsEnabled(true);
    }

    // Helper methods untuk get skill data (nanti akan diganti dengan data dari
    // Character classes)
    private String[] getSkillNames(String characterType) {
        switch (characterType) {
            case "FIRE":
                return new String[] { "Fireball", "Flame Burst", "Fire Shield", "Inferno" };
            case "WATER":
                return new String[] { "Water Jet", "Tidal Wave", "Aqua Heal", "Tsunami" };
            case "WIND":
                return new String[] { "Wind Slash", "Tornado", "Speed Boost", "Hurricane" };
            case "EARTH":
                return new String[] { "Rock Throw", "Earthquake", "Stone Armor", "Meteor" };
            default:
                return new String[] { "Skill 1", "Skill 2", "Skill 3", "Skill 4" };
        }
    }

    private String[] getSkillDescriptions(String characterType) {
        switch (characterType) {
            case "FIRE":
                return new String[] { "Basic fire attack", "AOE damage", "Buff defense", "Ultimate fire" };
            case "WATER":
                return new String[] { "Basic water attack", "Strong attack", "Restore HP", "Ultimate water" };
            case "WIND":
                return new String[] { "Quick attack", "Medium damage", "Increase speed", "Ultimate wind" };
            case "EARTH":
                return new String[] { "Basic earth attack", "Stun enemy", "Increase defense", "Ultimate earth" };
            default:
                return new String[] { "Attack", "Special", "Support", "Ultimate" };
        }
    }

    public void refresh() {
        if (battlePanel != null) {
            battlePanel.repaint();
        }
        if (mainMenuPanel != null) {
            mainMenuPanel.repaint();
        }
    }

    // Getter methods untuk integrasi dengan Battle/Character classes nanti
    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}