package com.game.gui;

import com.game.characters.BaseCharacter;
import com.game.characters.FireCharacter;
import com.game.characters.WaterCharacter;
import com.game.skills.Skill;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window that hosts the BattlePanel and manages a simple
 * two-player turn-based flow (local).
 */
public class GameWindow {
	private JFrame frame;
	private BattlePanel battlePanel;

	private BaseCharacter player1;
	private BaseCharacter player2;
	private boolean isPlayerOneTurn = true;

	public GameWindow() {
		setupCharacters();
		initialize();
		applyInitialState();
	}

	private void setupCharacters() {
		// Sample characters for two players — replace or let player select later
		player1 = new FireCharacter("Player 1");
		player2 = new WaterCharacter("Player 2");
	}

	private void initialize() {
		frame = new JFrame("Turn Based Game - 2 Players");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		battlePanel = new BattlePanel();
		frame.add(battlePanel, BorderLayout.CENTER);

		// Hook skill actions
		battlePanel.setSkillActionListener(skillIndex -> onSkillUsed(skillIndex));

		frame.setSize(820, 620);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void applyInitialState() {
		// Set names and HP to panel
		battlePanel.updatePlayerName(player1.getName());
		battlePanel.updateEnemyName(player2.getName());
		battlePanel.updatePlayerHP(player1.getHealthPoints(), player1.getMaxHealthPoints());
		battlePanel.updateEnemyHP(player2.getHealthPoints(), player2.getMaxHealthPoints());

		// Load initial skill names for player1
		updateSkillButtonsForCurrentPlayer();
		battlePanel.setSkillButtonsEnabled(true);
	}

	private void updateSkillButtonsForCurrentPlayer() {
		BaseCharacter current = isPlayerOneTurn ? player1 : player2;
		Skill[] skills = current.getSkills().toArray(new Skill[0]);
		String[] names = new String[4];
		String[] descs = new String[4];
		for (int i = 0; i < 4; i++) {
			if (i < skills.length && skills[i] != null) {
				names[i] = skills[i].getName();
				descs[i] = "";
			} else {
				names[i] = "-";
				descs[i] = "-";
			}
		}
		battlePanel.updateSkillButtons(names, descs);
	}

	private void onSkillUsed(int skillIndex) {
		BaseCharacter current = isPlayerOneTurn ? player1 : player2;
		BaseCharacter target = isPlayerOneTurn ? player2 : player1;

		java.util.List<Skill> skills = current.getSkills();
		if (skillIndex < 0 || skillIndex >= skills.size()) {
			battlePanel.addBattleLogMessage("Invalid skill index.");
			return;
		}

		Skill s = skills.get(skillIndex);
		if (s == null) {
			battlePanel.addBattleLogMessage("No skill assigned to this slot.");
			return;
		}

		// Use the skill
		s.use(current, target);

		// Update UI
		battlePanel.addBattleLogMessage(current.getName() + " used " + s.getName() + " on " + target.getName());
		battlePanel.updatePlayerHP(player1.getHealthPoints(), player1.getMaxHealthPoints());
		battlePanel.updateEnemyHP(player2.getHealthPoints(), player2.getMaxHealthPoints());

		// Check for victory / life status
		if (!player2.isAlive()) {
			battlePanel.addBattleLogMessage(player1.getName() + " wins!");
			battlePanel.setSkillButtonsEnabled(false);
			return;
		}
		if (!player1.isAlive()) {
			battlePanel.addBattleLogMessage(player2.getName() + " wins!");
			battlePanel.setSkillButtonsEnabled(false);
			return;
		}

		// Switch turn to other player
		isPlayerOneTurn = !isPlayerOneTurn;
		updateSkillButtonsForCurrentPlayer();
		battlePanel.setSkillButtonsEnabled(true);
		battlePanel.addBattleLogMessage("It's now " + (isPlayerOneTurn ? player1.getName() : player2.getName()) + "'s turn.");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameWindow());
	}
}