package com.game.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;
import com.game.characters.BaseCharacter;
import com.game.utils.ResourceLoader;

/**
 * Enhanced Battle Panel dengan visual Pokemon-style.
 * Fitur:
 * - Toggleable bottom panel (Skills vs Battle Text)
 * - 2x2 Skill Grid
 * - Status Effect Icons
 * - Clean layout
 */
public class BattlePanel extends JPanel {
    private BufferedImage background;
    private BufferedImage heroSprite;
    private BufferedImage enemySprite;

    // Battle State
    private BaseCharacter player1;
    private BaseCharacter player2;
    private String currentTurn = "Player1";

    // Bottom Panel Components
    private JPanel bottomPanel;
    private CardLayout bottomCardLayout;
    private JPanel skillPanel;
    private JPanel textPanel;

    // Skill Buttons
    private JButton skill1Button;
    private JButton skill2Button;
    private JButton skill3Button;
    private JButton skill4Button;
    private JButton surrenderButton;

    // Battle Text
    private JTextArea battleTextArea;
    private JButton continueButton;

    // Typewriter Effect
    private Timer typewriterTimer;
    private String fullText;
    private int currentCharIndex;
    private boolean isAnimating;
    private static final int TYPEWRITER_DELAY_MS = 30; // Delay per karakter

    // Constants
    private static final String CARD_SKILLS = "SKILLS";
    private static final String CARD_TEXT = "TEXT";

    // Listeners
    private SkillActionListener skillListener;
    private ActionListener continueListener;

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
        // Turn Indicator (Top Center)
        JLabel turnLabel = new JLabel("BATTLE START", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 22));
        turnLabel.setForeground(Color.YELLOW);
        turnLabel.setBounds(280, 15, 260, 30);
        add(turnLabel);

        // Initialize Bottom Panel (CardLayout)
        bottomCardLayout = new CardLayout();
        bottomPanel = new JPanel(bottomCardLayout);
        bottomPanel.setBounds(0, 450, 820, 170);
        bottomPanel.setOpaque(false);
        add(bottomPanel);

        // 1. Skill Panel (2x2 Grid + Surrender)
        createSkillPanel();
        bottomPanel.add(skillPanel, CARD_SKILLS);

        // 2. Text Panel (Battle Log + Continue)
        createTextPanel();
        bottomPanel.add(textPanel, CARD_TEXT);

        // Show skills by default
        showSkills();
    }

    private void createSkillPanel() {
        skillPanel = new JPanel(null);
        skillPanel.setBackground(new Color(30, 30, 50));
        skillPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.WHITE));

        int btnWidth = 260;
        int btnHeight = 60;
        int startX = 20;
        int startY = 20;
        int gapX = 20;
        int gapY = 15;

        // Skill 1 (Top Left)
        skill1Button = createSkillButton("Skill 1", "Basic", new Color(220, 50, 50),
                startX, startY, btnWidth, btnHeight);
        skill1Button.addActionListener(e -> useSkill(0));
        skillPanel.add(skill1Button);

        // Skill 2 (Top Right)
        skill2Button = createSkillButton("Skill 2", "Special", new Color(255, 165, 0),
                startX + btnWidth + gapX, startY, btnWidth, btnHeight);
        skill2Button.addActionListener(e -> useSkill(1));
        skillPanel.add(skill2Button);

        // Skill 3 (Bottom Left)
        skill3Button = createSkillButton("Skill 3", "Support", new Color(50, 200, 50),
                startX, startY + btnHeight + gapY, btnWidth, btnHeight);
        skill3Button.addActionListener(e -> useSkill(2));
        skillPanel.add(skill3Button);

        // Skill 4 (Bottom Right)
        skill4Button = createSkillButton("Skill 4", "Ultimate", new Color(150, 50, 200),
                startX + btnWidth + gapX, startY + btnHeight + gapY, btnWidth, btnHeight);
        skill4Button.addActionListener(e -> useSkill(3));
        skillPanel.add(skill4Button);

        // Surrender Button (Far Right)
        surrenderButton = new JButton("SURRENDER");
        surrenderButton.setFont(new Font("Arial", Font.BOLD, 14));
        surrenderButton.setBackground(new Color(100, 100, 100));
        surrenderButton.setForeground(Color.WHITE);
        surrenderButton.setFocusPainted(false);
        surrenderButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        surrenderButton.setBounds(620, 45, 160, 80);
        surrenderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        surrenderButton.addActionListener(e -> surrender());
        skillPanel.add(surrenderButton);
    }

    private void createTextPanel() {
        textPanel = new JPanel(null);
        textPanel.setBackground(new Color(20, 20, 20));
        textPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.WHITE));

        // Text Area
        battleTextArea = new JTextArea();
        battleTextArea.setEditable(false);
        battleTextArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        battleTextArea.setForeground(Color.WHITE);
        battleTextArea.setBackground(new Color(20, 20, 20));
        battleTextArea.setLineWrap(true);
        battleTextArea.setWrapStyleWord(true);
        battleTextArea.setBounds(30, 20, 650, 130);
        battleTextArea.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Click to skip typewriter animation
        final BattlePanel self = this;
        battleTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (self.isAnimating) {
                    self.skipTypewriterAnimation();
                }
            }
        });

        textPanel.add(battleTextArea);

        // Continue Button
        continueButton = new JButton("▼");
        continueButton.setFont(new Font("Arial", Font.BOLD, 24));
        continueButton.setBackground(new Color(50, 50, 200));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        continueButton.setBounds(700, 50, 80, 70);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(e -> {
            if (continueListener != null) {
                continueListener.actionPerformed(e);
            }
        });
        textPanel.add(continueButton);
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

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        button.add(titleLabel, BorderLayout.NORTH);

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(Color.WHITE);
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

    // ==========================================
    // Toggle Methods
    // ==========================================

    public void showSkills() {
        bottomCardLayout.show(bottomPanel, CARD_SKILLS);
    }

    public void showBattleText(String text) {
        battleTextArea.setText(text);
        bottomCardLayout.show(bottomPanel, CARD_TEXT);
    }

    /**
     * Menampilkan battle text dengan typewriter effect (Pokemon-style).
     */
    public void showBattleTextWithTypewriter(String text) {
        // Stop any existing animation
        if (typewriterTimer != null && typewriterTimer.isRunning()) {
            typewriterTimer.stop();
        }

        // Setup
        this.fullText = text;
        this.currentCharIndex = 0;
        this.isAnimating = true;

        // Clear text area
        battleTextArea.setText("");

        // Disable continue button during animation
        continueButton.setEnabled(false);

        // Show text panel
        bottomCardLayout.show(bottomPanel, CARD_TEXT);

        // Start typewriter animation
        typewriterTimer = new Timer(TYPEWRITER_DELAY_MS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCharIndex < fullText.length()) {
                    // Add next character
                    battleTextArea.append(String.valueOf(fullText.charAt(currentCharIndex)));
                    currentCharIndex++;
                } else {
                    // Animation complete
                    finishTypewriterAnimation();
                }
            }
        });

        typewriterTimer.start();
    }

    /**
     * Skip typewriter animation dan langsung tampilkan semua text.
     */
    private void skipTypewriterAnimation() {
        if (typewriterTimer != null && typewriterTimer.isRunning()) {
            typewriterTimer.stop();
        }

        if (fullText != null) {
            battleTextArea.setText(fullText);
        }
        finishTypewriterAnimation();
    }

    /**
     * Finish typewriter animation.
     */
    private void finishTypewriterAnimation() {
        if (typewriterTimer != null) {
            typewriterTimer.stop();
        }

        isAnimating = false;
        continueButton.setEnabled(true);
    }

    public void setContinueButtonListener(ActionListener listener) {
        this.continueListener = listener;
    }

    // ==========================================
    // Update Methods
    // ==========================================

    public void updateCharacters(BaseCharacter p1, BaseCharacter p2) {
        this.player1 = p1;
        this.player2 = p2;
        repaint();
    }

    public void updateSkillButtons(String[] skillNames, String[] skillDescriptions) {
        JButton[] buttons = { skill1Button, skill2Button, skill3Button, skill4Button };

        for (int i = 0; i < buttons.length && i < skillNames.length; i++) {
            JButton button = buttons[i];
            button.removeAll();
            button.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel(skillNames[i], SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setForeground(Color.WHITE);
            button.add(titleLabel, BorderLayout.NORTH);

            JLabel descLabel = new JLabel(skillDescriptions[i], SwingConstants.CENTER);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            descLabel.setForeground(Color.WHITE);
            button.add(descLabel, BorderLayout.CENTER);

            button.revalidate();
            button.repaint();
        }
    }

    public void setSkillButtonsEnabled(boolean enabled) {
        skill1Button.setEnabled(enabled);
        skill2Button.setEnabled(enabled);
        skill3Button.setEnabled(enabled);
        skill4Button.setEnabled(enabled);
        surrenderButton.setEnabled(enabled);
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
            // Trigger surrender logic through listener (handled by GameWindow via skill
            // index -1 or separate method)
            // For simplicity, we can use a special index or add a surrender listener
            // Using skill index -1 for surrender
            if (skillListener != null) {
                skillListener.onSkillUsed(-1);
            }
        }
    }

    public void setSkillActionListener(SkillActionListener listener) {
        this.skillListener = listener;
    }

    // ==========================================
    // Painting
    // ==========================================

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
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw characters if available
        if (player1 != null && player2 != null) {
            drawCharacterArea(g2);
        }

        g2.dispose();
    }

    private void drawCharacterArea(Graphics2D g2) {
        int midY = 220;

        // Player 1 (Left) - Horizontal Position
        drawCharacterSprite(g2, heroSprite, 80, midY - 60, true);
        drawCharacterStats(g2, player1, 30, 360, true);

        // VS Label
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.setColor(Color.RED);
        String vsText = "VS";
        int vsWidth = g2.getFontMetrics().stringWidth(vsText);
        g2.drawString(vsText, (getWidth() - vsWidth) / 2, 240);

        // Player 2 (Right) - Horizontal Position
        // Adjusted X to 500 to avoid overlap (though battle log is gone now, still good
        // to keep safe)
        drawCharacterSprite(g2, enemySprite, 620, midY - 60, false);
        drawCharacterStats(g2, player2, 550, 360, false);
    }

    private void drawCharacterSprite(Graphics2D g2, BufferedImage sprite, int x, int y, boolean isPlayer) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, 120, 120, null);
        } else {
            g2.setColor(isPlayer ? new Color(100, 150, 255) : new Color(255, 100, 100));
            g2.fillOval(x, y, 120, 120);
        }
    }

    private void drawCharacterStats(Graphics2D g2, BaseCharacter character, int x, int y, boolean isPlayer) {
        // Name
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        g2.drawString(character.getName(), x, y);

        // HP Bar
        y += 25;
        drawStatBar(g2, "HP", character.getHealthPoints(), character.getMaxHealthPoints(),
                x, y, 240, 25, Color.GREEN, Color.RED);

        // FP Bar
        y += 35;
        drawStatBar(g2, "FP", character.getFocusPoints(), character.getMaxFocusPoints(),
                x, y, 240, 20, Color.CYAN, new Color(0, 100, 150));

        // Status Icons
        y += 25;
        drawStatusIcons(g2, character, x, y);
    }

    private void drawStatBar(Graphics2D g2, String label, int current, int max,
            int x, int y, int width, int height, Color goodColor, Color badColor) {
        // Label
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(label + ":", x, y + height - 5);

        int barX = x + 35;
        int barWidth = width - 35;

        // Background
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(barX, y, barWidth, height);

        // Fill
        float percent = (float) current / max;
        int fillWidth = (int) (barWidth * percent);

        Color fillColor = percent > 0.5 ? goodColor : (percent > 0.2 ? Color.ORANGE : badColor);
        g2.setColor(fillColor);
        g2.fillRect(barX, y, fillWidth, height);

        // Border
        g2.setColor(Color.WHITE);
        g2.drawRect(barX, y, barWidth, height);

        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        String text = current + " / " + max;
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, barX + barWidth / 2 - textWidth / 2, y + height - 6);
    }

    private void drawStatusIcons(Graphics2D g2, BaseCharacter character, int x, int y) {
        List<String> effects = character.getActiveEffectNames();
        if (effects.isEmpty())
            return;

        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Use font supporting emojis
        int iconX = x;

        for (String effect : effects) {
            String icon = getEffectIcon(effect);
            g2.drawString(icon, iconX, y);

            // Draw text label small
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(effect, iconX + 25, y - 5);
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Reset font

            iconX += 80;
        }
    }

    private String getEffectIcon(String effectName) {
        if (effectName.contains("Burn"))
            return "🔥";
        if (effectName.contains("Freeze"))
            return "❄️";
        if (effectName.contains("Guard"))
            return "🛡️";
        return "⚡";
    }

    // Helper methods for GameWindow to update specific fields if needed
    // But updateCharacters() is preferred
    public void updatePlayerHP(int hp, int max) {
        repaint();
    }

    public void updateEnemyHP(int hp, int max) {
        repaint();
    }

    public void updatePlayerFP(int fp, int max) {
        repaint();
    }

    public void updateEnemyFP(int fp, int max) {
        repaint();
    }

    public void updateplayer1Name(String name) {
        repaint();
    }

    public void updateplayer2Name(String name) {
        repaint();
    }

    public void clearBattleLog() {
        /* No op as log is transient now */ }

    public void addBattleLogMessage(String msg) {
        /* No op */ }
}
