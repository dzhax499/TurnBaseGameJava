package com.game.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.game.utils.ResourceLoader;

/**
 * Enhanced Battle Panel dengan visual yang lebih baik dan integrasi penuh.
 * Menampilkan HP, FP, status effects, dan battle log.
 */
public class BattlePanel extends JPanel {
    private BufferedImage background;
    private BufferedImage heroSprite;
    private BufferedImage enemySprite;

    // Battle State
    private String player1Name = "Player1";
    private String player2Name = "Player2";
    private int player1HP = 100;
    private int player1MaxHP = 100;
    private int player1FP = 50;
    private int player1MaxFP = 50;
    private int player2HP = 100;
    private int player2MaxHP = 100;
    private int player2FP = 50;
    private int player2MaxFP = 50;
    private String currentTurn = "Player1";

    // Skill Buttons
    private JButton skill1Button;
    private JButton skill2Button;
    private JButton skill3Button;
    private JButton skill4Button;
    private JButton surrenderButton;

    // Battle Log
    private JTextArea battleLog;
    private JScrollPane logScrollPane;

    // Turn indicator
    private JLabel turnLabel;

    // Listener untuk skill action
    private SkillActionListener skillListener;

    public interface SkillActionListener {
        void onSkillUsed(int skillIndex);
    }

    public BattlePanel() {
        setLayout(null);
        background = ResourceLoader.loadImage("/images/tes_image.jpg");
        heroSprite = ResourceLoader.loadImage("/images/tes_image.jpg");
        enemySprite = ResourceLoader.loadImage("/images/tes_image.jpg");

        initializeComponents();
    }

    private void initializeComponents() {
        // Turn Indicator
        turnLabel = new JLabel("PLAYER 1 TURN", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 22));
        turnLabel.setForeground(Color.YELLOW);
        turnLabel.setBounds(280, 15, 260, 30);
        add(turnLabel);

        // Battle Log Area (right side)
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        battleLog.setBackground(new Color(20, 20, 20, 220));
        battleLog.setForeground(Color.CYAN);
        battleLog.setText("=== Battle Log ===\n");
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);

        logScrollPane = new JScrollPane(battleLog);
        logScrollPane.setBounds(540, 60, 260, 300);
        logScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.CYAN, 2),
                "Battle Log",
                0,
                0,
                new Font("Arial", Font.BOLD, 12),
                Color.CYAN));
        add(logScrollPane);

        // Skill Buttons Panel (bottom)
        int buttonWidth = 160;
        int buttonHeight = 70;
        int buttonY = 490;
        int startX = 30;
        int spacing = 15;

        Color[] skillColors = {
                new Color(220, 50, 50), // Red
                new Color(255, 165, 0), // Orange
                new Color(50, 200, 50), // Green
                new Color(150, 50, 200) // Purple
        };

        skill1Button = createSkillButton("Skill 1", "Basic", skillColors[0],
                startX, buttonY, buttonWidth, buttonHeight);
        skill1Button.addActionListener(e -> useSkill(0));
        add(skill1Button);

        skill2Button = createSkillButton("Skill 2", "Special", skillColors[1],
                startX + (buttonWidth + spacing), buttonY, buttonWidth, buttonHeight);
        skill2Button.addActionListener(e -> useSkill(1));
        add(skill2Button);

        skill3Button = createSkillButton("Skill 3", "Support", skillColors[2],
                startX + (buttonWidth + spacing) * 2, buttonY, buttonWidth, buttonHeight);
        skill3Button.addActionListener(e -> useSkill(2));
        add(skill3Button);

        skill4Button = createSkillButton("Skill 4", "Ultimate", skillColors[3],
                startX + (buttonWidth + spacing) * 3, buttonY, buttonWidth, buttonHeight);
        skill4Button.addActionListener(e -> useSkill(3));
        add(skill4Button);

        // Surrender Button
        surrenderButton = new JButton("⚐ Surrender");
        surrenderButton.setFont(new Font("Arial", Font.BOLD, 14));
        surrenderButton.setBackground(new Color(100, 100, 100));
        surrenderButton.setForeground(Color.WHITE);
        surrenderButton.setFocusPainted(false);
        surrenderButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        surrenderButton.setBounds(720, 490, 80, 70);
        surrenderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        surrenderButton.addActionListener(e -> surrender());
        add(surrenderButton);
    }

    private JButton createSkillButton(String title, String description, Color color,
            int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        button.add(titleLabel, BorderLayout.NORTH);

        // Description label
        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        button.add(descLabel, BorderLayout.CENTER);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                    button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });

        return button;
    }

    private void useSkill(int skillIndex) {
        if (skillListener != null) {
            skillListener.onSkillUsed(skillIndex);
        }
    }

    private void surrender() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to surrender?",
                "Surrender",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            addBattleLogMessage(currentTurn + " surrendered!");
            // Trigger surrender logic through listener
        }
    }

    public void setSkillButtonsEnabled(boolean enabled) {
        skill1Button.setEnabled(enabled);
        skill2Button.setEnabled(enabled);
        skill3Button.setEnabled(enabled);
        skill4Button.setEnabled(enabled);
        surrenderButton.setEnabled(enabled);
    }

    public void updateSkillButtons(String[] skillNames, String[] skillDescriptions) {
        JButton[] buttons = { skill1Button, skill2Button, skill3Button, skill4Button };

        for (int i = 0; i < buttons.length && i < skillNames.length; i++) {
            JButton button = buttons[i];
            button.removeAll();
            button.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel(skillNames[i], SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            titleLabel.setForeground(Color.WHITE);
            button.add(titleLabel, BorderLayout.NORTH);

            JLabel descLabel = new JLabel(skillDescriptions[i], SwingConstants.CENTER);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            descLabel.setForeground(Color.WHITE);
            button.add(descLabel, BorderLayout.CENTER);

            button.revalidate();
            button.repaint();
        }
    }

    public void setSkillActionListener(SkillActionListener listener) {
        this.skillListener = listener;
    }

    // Update methods
    public void updatePlayerHP(int hp, int maxHP) {
        this.player1HP = hp;
        this.player1MaxHP = maxHP;
        repaint();
    }

    public void updateEnemyHP(int hp, int maxHP) {
        this.player2HP = hp;
        this.player2MaxHP = maxHP;
        repaint();
    }

    public void updatePlayerFP(int fp, int maxFP) {
        this.player1FP = fp;
        this.player1MaxFP = maxFP;
        repaint();
    }

    public void updateEnemyFP(int fp, int maxFP) {
        this.player2FP = fp;
        this.player2MaxFP = maxFP;
        repaint();
    }

    public void updateplayer1Name(String name) {
        this.player1Name = name;
        repaint();
    }

    public void updateplayer2Name(String name) {
        this.player2Name = name;
        repaint();
    }

    public void updateCurrentTurn(String playerName) {
        this.currentTurn = playerName;
        turnLabel.setText(playerName.toUpperCase() + " TURN");
        repaint();
    }

    public void addBattleLogMessage(String message) {
        battleLog.append(message + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }

    public void clearBattleLog() {
        battleLog.setText("=== Battle Log ===\n");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        if (background != null) {
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(new Color(30, 30, 50));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark overlay
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw character sprites and stats
        drawCharacterArea(g2);

        g2.dispose();
    }

    private void drawCharacterArea(Graphics2D g2) {
        int midY = 220;

        // Player 1 (left side)
        drawCharacterSprite(g2, heroSprite, 80, midY - 60, true);
        drawCharacterStats(g2, player1Name, player1HP, player1MaxHP, player1FP, player1MaxFP,
                30, 360, true);

        // VS Label
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.setColor(Color.RED);
        FontMetrics fm = g2.getFontMetrics();
        String vsText = "VS";
        int vsWidth = fm.stringWidth(vsText);
        g2.drawString(vsText, (getWidth() - vsWidth) / 2, 240);

        // Player 2 (right side)
        drawCharacterSprite(g2, enemySprite, getWidth() - 200, midY - 60, false);
        drawCharacterStats(g2, player2Name, player2HP, player2MaxHP, player2FP, player2MaxFP,
                getWidth() - 270, 360, false);
    }

    private void drawCharacterSprite(Graphics2D g2, BufferedImage sprite, int x, int y, boolean isPlayer) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, 120, 120, null);
        } else {
            // Placeholder
            g2.setColor(isPlayer ? new Color(100, 150, 255, 200) : new Color(255, 100, 100, 200));
            g2.fillOval(x, y, 120, 120);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            String text = isPlayer ? "P1" : "P2";
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x + 60 - textWidth / 2, y + 65);
        }
    }

    private void drawCharacterStats(Graphics2D g2, String name, int hp, int maxHP, int fp, int maxFP,
            int x, int y, boolean isPlayer) {
        // Name
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        g2.drawString(name, x, y);

        // HP Bar
        y += 25;
        drawStatBar(g2, "HP", hp, maxHP, x, y, 240, 25, Color.GREEN, Color.RED);

        // FP Bar
        y += 35;
        drawStatBar(g2, "FP", fp, maxFP, x, y, 240, 20, Color.CYAN, new Color(0, 100, 150));
    }

    private void drawStatBar(Graphics2D g2, String label, int current, int max,
            int x, int y, int width, int height,
            Color goodColor, Color badColor) {
        // Label
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(label + ":", x, y + height - 5);

        // Bar position
        int barX = x + 35;
        int barWidth = width - 35;

        // Background
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(barX, y, barWidth, height);

        // Fill bar
        float percent = (float) current / max;
        int fillWidth = (int) (barWidth * percent);

        // Color based on percentage
        Color fillColor;
        if (percent > 0.6f) {
            fillColor = goodColor;
        } else if (percent > 0.3f) {
            fillColor = Color.ORANGE;
        } else {
            fillColor = badColor;
        }

        g2.setColor(fillColor);
        g2.fillRect(barX, y, fillWidth, height);

        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, y, barWidth, height);

        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        String text = current + " / " + max;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2.setColor(Color.WHITE);
        g2.drawString(text, barX + barWidth / 2 - textWidth / 2, y + height - 6);
    }
}