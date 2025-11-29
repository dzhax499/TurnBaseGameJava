package com.game.gui;

import com.game.battle.Battle;
import com.game.battle.BattleAction;
import com.game.characters.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main Application Controller untuk Turn-Based Battle Game.
 * Menggunakan CardLayout untuk navigasi antar panel.
 * 
 * Responsibilities:
 * - Manage panel switching (Menu -> Character Selection -> Battle)
 * - Coordinate communication antara Battle logic dan GUI
 * - Handle application lifecycle
 */
public class GameWindow {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainContainer;

    // Panels
    private MainMenuPanel menuPanel;
    private CharacterSelectionPanel selectionPanel;
    private BattlePanel battlePanel;

    // Game state
    private BaseCharacter player1;
    private BaseCharacter player2;
    private Battle currentBattle;
    private int playerSelectionStep = 0; // 0 = player1, 1 = player2

    // Card names
    private static final String MENU_CARD = "MENU";
    private static final String SELECTION_CARD = "SELECTION";
    private static final String BATTLE_CARD = "BATTLE";

    public GameWindow() {
        initialize();
    }

    private void initialize() {
        // Setup main frame
        frame = new JFrame("Turn-Based Battle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup CardLayout
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        // Set preferred size to ensure content area is exactly 820x620
        mainContainer.setPreferredSize(new Dimension(820, 620));

        // Initialize panels
        initializePanels();

        // Add panels to container
        mainContainer.add(menuPanel, MENU_CARD);
        mainContainer.add(selectionPanel, SELECTION_CARD);
        mainContainer.add(battlePanel, BATTLE_CARD);

        frame.add(mainContainer);

        // Pack frame to fit content
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Show menu first
        cardLayout.show(mainContainer, MENU_CARD);

        frame.setVisible(true);
    }

    private void initializePanels() {
        // Main Menu Panel
        menuPanel = new MainMenuPanel();
        menuPanel.setCharacterSelectionListener(type -> startCharacterSelection());

        // Character Selection Panel
        selectionPanel = new CharacterSelectionPanel();
        selectionPanel.setSelectionListener(new CharacterSelectionPanel.SelectionListener() {
            @Override
            public void onCharacterSelected(String characterType, String characterName) {
                handleCharacterSelection(characterType, characterName);
            }

            @Override
            public void onBackToMenu() {
                returnToMenu();
            }
        });

        // Battle Panel
        battlePanel = new BattlePanel();
        battlePanel.setSkillActionListener(skillIndex -> handleSkillAction(skillIndex));

        // Setup Continue Button Listener
        battlePanel.setContinueButtonListener(e -> handleContinueAction());
    }

    private void startCharacterSelection() {
        playerSelectionStep = 0;
        player1 = null;
        player2 = null;
        selectionPanel.setPlayerNumber(1);
        cardLayout.show(mainContainer, SELECTION_CARD);
    }

    private void handleCharacterSelection(String characterType, String characterName) {
        BaseCharacter character = createCharacter(characterType, characterName);

        if (playerSelectionStep == 0) {
            // Player 1 selected
            player1 = character;
            playerSelectionStep = 1;
            selectionPanel.setPlayerNumber(2);
        } else {
            // Player 2 selected
            player2 = character;
            startBattle();
        }
    }

    private BaseCharacter createCharacter(String type, String name) {
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

    private void startBattle() {
        // Create battle instance
        currentBattle = new Battle(player1, player2);

        // Setup battle panel
        battlePanel.updateCharacters(player1, player2);
        battlePanel.setSkillButtonsEnabled(false); // Disable first

        // Start battle
        currentBattle.start();

        // Update UI with initial state
        updateBattleUI();

        // Enable buttons
        battlePanel.setSkillButtonsEnabled(true);

        // Show skills initially
        battlePanel.showSkills();

        // Switch to battle screen
        cardLayout.show(mainContainer, BATTLE_CARD);
    }

    private void handleSkillAction(int skillIndex) {
        if (currentBattle == null || currentBattle.isBattleFinished()) {
            return;
        }

        // Handle Surrender (index -1)
        if (skillIndex == -1) {
            currentBattle.surrender();
            handleBattleEnd();
            return;
        }

        // Execute action with 1-based index (skillIndex from UI is 0-based)
        boolean success = currentBattle.executePlayerAction(skillIndex + 1);

        if (!success) {
            // Action failed (e.g. not enough FP), stay on skill screen
            return;
        }

        // Update UI (HP/FP changes)
        updateBattleUI();

        // Get the last action log to display
        List<BattleAction> actions = currentBattle.getBattleLog().getLastActions(1);
        if (!actions.isEmpty()) {
            BattleAction lastAction = actions.get(0);
            String message = formatBattleMessage(lastAction);
            battlePanel.showBattleText(message);
        } else {
            // Fallback
            battlePanel.showBattleText("Action executed.");
        }
    }

    private String formatBattleMessage(BattleAction action) {
        StringBuilder sb = new StringBuilder();
        sb.append(action.getActor()).append(" used ").append(action.getActionName()).append("!\n");

        if (action.getDamageDealt() > 0) {
            sb.append(action.getTarget()).append(" took ").append(action.getDamageDealt()).append(" damage!\n");
        }

        if (action.getHealingDone() > 0) {
            sb.append(action.getActor()).append(" recovered ").append(action.getHealingDone()).append(" HP!\n");
        }

        if (action.getDescription() != null && !action.getDescription().isEmpty()) {
            sb.append(action.getDescription());
        }

        return sb.toString();
    }

    private void handleContinueAction() {
        // Check if battle finished
        if (currentBattle.isBattleFinished()) {
            handleBattleEnd();
            return;
        }

        // End turn
        currentBattle.endTurn();

        // Update UI for next turn
        updateBattleUI();

        // Show skills for next player
        battlePanel.showSkills();
        battlePanel.setSkillButtonsEnabled(true);
    }

    private void updateBattleUI() {
        // Update Characters (HP, FP, Status)
        battlePanel.updateCharacters(player1, player2);

        // Update skill buttons for current player
        BaseCharacter currentPlayer = currentBattle.getCurrentPlayer();
        String[] skillNames = new String[4];
        String[] skillDescriptions = new String[4];

        for (int i = 0; i < currentPlayer.getSkills().size() && i < 4; i++) {
            skillNames[i] = currentPlayer.getSkills().get(i).getName();
            skillDescriptions[i] = "FP: " + currentPlayer.getSkills().get(i).getFpCost();
        }

        // Fill empty slots
        for (int i = currentPlayer.getSkills().size(); i < 4; i++) {
            skillNames[i] = "---";
            skillDescriptions[i] = "---";
        }

        battlePanel.updateSkillButtons(skillNames, skillDescriptions);
    }

    private void handleBattleEnd() {
        battlePanel.setSkillButtonsEnabled(false);

        BaseCharacter winner = currentBattle.getWinner();
        String message = "BATTLE FINISHED!\nWinner: " + (winner != null ? winner.getName() : "None");

        // Show result in text panel first
        battlePanel.showBattleText(message);

        // Show dialog with options
        SwingUtilities.invokeLater(() -> {
            int choice = JOptionPane.showOptionDialog(
                    frame,
                    message + "\n\nWhat would you like to do?",
                    "Battle Finished!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[] { "New Battle", "Back to Menu" },
                    "New Battle");

            if (choice == 0) {
                // New battle
                resetGameState();
                startCharacterSelection();
            } else {
                // Back to menu
                resetGameState();
                returnToMenu();
            }
        });
    }

    /**
     * Reset all game state properly to prevent bugs on restart
     */
    private void resetGameState() {
        // Clear references
        player1 = null;
        player2 = null;
        currentBattle = null;
        playerSelectionStep = 0;

        // Reset battle panel UI state
        battlePanel.setSkillButtonsEnabled(false);
    }

    private void returnToMenu() {
        // Reset game state completely
        resetGameState();

        // Show menu
        cardLayout.show(mainContainer, MENU_CARD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}