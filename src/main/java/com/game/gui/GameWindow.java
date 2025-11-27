package com.game.gui;

import com.game.battle.Battle;
import com.game.characters.*;
import javax.swing.*;
import java.awt.*;

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
		frame.setSize(820, 620);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// Setup CardLayout
		cardLayout = new CardLayout();
		mainContainer = new JPanel(cardLayout);

		// Initialize panels
		initializePanels();

		// Add panels to container
		mainContainer.add(menuPanel, MENU_CARD);
		mainContainer.add(selectionPanel, SELECTION_CARD);
		mainContainer.add(battlePanel, BATTLE_CARD);

		frame.add(mainContainer);

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

		// IMPORTANT: Reset battle panel completely
		battlePanel.clearBattleLog();
		battlePanel.setSkillButtonsEnabled(false); // Disable first

		// Setup battle panel
		battlePanel.updateplayer1Name(player1.getName());
		battlePanel.updateplayer2Name(player2.getName());
		battlePanel.updatePlayerHP(player1.getHealthPoints(), player1.getMaxHealthPoints());
		battlePanel.updateEnemyHP(player2.getHealthPoints(), player2.getMaxHealthPoints());

		// Start battle
		currentBattle.start();

		// Update UI with initial state
		updateBattleUI();

		// CRITICAL FIX: Enable buttons AFTER everything is set up
		battlePanel.setSkillButtonsEnabled(true);

		// Switch to battle screen
		cardLayout.show(mainContainer, BATTLE_CARD);

		battlePanel.addBattleLogMessage("=== Battle Started! ===");
		battlePanel.addBattleLogMessage(player1.getName() + " vs " + player2.getName());
		battlePanel.addBattleLogMessage("Turn: " + currentBattle.getTurnCount());
		battlePanel.addBattleLogMessage(currentBattle.getCurrentPlayerName() + "'s turn!");
	}

	private void handleSkillAction(int skillIndex) {
		if (currentBattle == null || currentBattle.isBattleFinished()) {
			return;
		}

		// Disable buttons during action
		battlePanel.setSkillButtonsEnabled(false);

		// Execute action with 1-based index (skillIndex from UI is 0-based)
		boolean success = currentBattle.executePlayerAction(skillIndex + 1);

		if (!success) {
			// Action failed, re-enable buttons
			battlePanel.setSkillButtonsEnabled(true);
			battlePanel.addBattleLogMessage("Action failed! Try again.");
			return;
		}

		// Update UI
		updateBattleUI();

		// Check if battle finished
		if (currentBattle.isBattleFinished()) {
			handleBattleEnd();
			return;
		}

		// End turn and update UI
		currentBattle.endTurn();
		updateBattleUI();

		battlePanel.addBattleLogMessage("---");
		battlePanel.addBattleLogMessage("Turn: " + currentBattle.getTurnCount());
		battlePanel.addBattleLogMessage(currentBattle.getCurrentPlayerName() + "'s turn!");

		// Re-enable buttons for next player
		battlePanel.setSkillButtonsEnabled(true);
	}

	private void updateBattleUI() {
		// Update HP
		battlePanel.updatePlayerHP(player1.getHealthPoints(), player1.getMaxHealthPoints());
		battlePanel.updateEnemyHP(player2.getHealthPoints(), player2.getMaxHealthPoints());

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
		if (winner != null) {
			battlePanel.addBattleLogMessage("===================");
			battlePanel.addBattleLogMessage("BATTLE FINISHED!");
			battlePanel.addBattleLogMessage("Winner: " + winner.getName());
			battlePanel.addBattleLogMessage("===================");

			// Show dialog with options
			SwingUtilities.invokeLater(() -> {
				int choice = JOptionPane.showOptionDialog(
						frame,
						"Winner: " + winner.getName() + "\n\nWhat would you like to do?",
						"Battle Finished!",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						new String[] { "New Battle", "Back to Menu" },
						"New Battle");

				if (choice == 0) {
					// New battle - CRITICAL: Reset everything properly
					resetGameState();
					startCharacterSelection();
				} else {
					// Back to menu
					resetGameState();
					returnToMenu();
				}
			});
		}
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
		battlePanel.clearBattleLog();
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