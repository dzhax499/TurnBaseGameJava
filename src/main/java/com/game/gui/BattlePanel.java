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
        turnLabel.setFont(new Font("Arial", Font.BOLD, UIConstants.TURN_LABEL_FONT_SIZE));
        turnLabel.setForeground(UIConstants.COLOR_YELLOW);
        turnLabel.setBounds(UIConstants.TURN_LABEL_X, UIConstants.TURN_LABEL_Y,
                UIConstants.TURN_LABEL_WIDTH, UIConstants.TURN_LABEL_HEIGHT);
        add(turnLabel);

        // Initialize Bottom Panel (CardLayout)
        bottomCardLayout = new CardLayout();
        bottomPanel = new JPanel(bottomCardLayout);
        bottomPanel.setBounds(0, UIConstants.BOTTOM_PANEL_Y,
                UIConstants.WINDOW_WIDTH, UIConstants.BOTTOM_PANEL_HEIGHT);
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
        skillPanel.setBackground(UIConstants.PANEL_BG_COLOR);
        skillPanel.setBorder(BorderFactory.createMatteBorder(UIConstants.SKILL_PANEL_BORDER_THICKNESS, 0, 0, 0,
                UIConstants.COLOR_WHITE));

        int btnWidth = UIConstants.SKILL_BTN_WIDTH;
        int btnHeight = UIConstants.SKILL_BTN_HEIGHT;
        int startX = UIConstants.SKILL_BTN_START_X;
        int startY = UIConstants.SKILL_BTN_START_Y;
        int gapX = UIConstants.SKILL_BTN_GAP_X;
        int gapY = UIConstants.SKILL_BTN_GAP_Y;

        // Skill 1 (Top Left)
        skill1Button = createSkillButton("Skill 1", "Basic", UIConstants.SKILL_1_COLOR,
                startX, startY, btnWidth, btnHeight);
        skill1Button.addActionListener(e -> useSkill(0));
        skillPanel.add(skill1Button);

        // Skill 2 (Top Right)
        skill2Button = createSkillButton("Skill 2", "Special", UIConstants.SKILL_2_COLOR,
                startX + btnWidth + gapX, startY, btnWidth, btnHeight);
        skill2Button.addActionListener(e -> useSkill(1));
        skillPanel.add(skill2Button);

        // Skill 3 (Bottom Left)
        skill3Button = createSkillButton("Skill 3", "Support", UIConstants.SKILL_3_COLOR,
                startX, startY + btnHeight + gapY, btnWidth, btnHeight);
        skill3Button.addActionListener(e -> useSkill(2));
        skillPanel.add(skill3Button);

        // Skill 4 (Bottom Right)
        skill4Button = createSkillButton("Skill 4", "Ultimate", UIConstants.SKILL_4_COLOR,
                startX + btnWidth + gapX, startY + btnHeight + gapY, btnWidth, btnHeight);
        skill4Button.addActionListener(e -> useSkill(3));
        skillPanel.add(skill4Button);

        // Surrender Button (Far Right)
        surrenderButton = new JButton("SURRENDER");
        surrenderButton.setFont(new Font("Arial", Font.BOLD, UIConstants.SURRENDER_BTN_FONT_SIZE));
        surrenderButton.setBackground(UIConstants.SURRENDER_BTN_COLOR);
        surrenderButton.setForeground(UIConstants.COLOR_WHITE);
        surrenderButton.setFocusPainted(false);
        surrenderButton.setBorder(
                BorderFactory.createLineBorder(UIConstants.COLOR_WHITE, UIConstants.SURRENDER_BORDER_THICKNESS));
        surrenderButton.setBounds(UIConstants.SURRENDER_BTN_X, UIConstants.SURRENDER_BTN_Y,
                UIConstants.SURRENDER_BTN_WIDTH, UIConstants.SURRENDER_BTN_HEIGHT);
        surrenderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        surrenderButton.addActionListener(e -> surrender());
        skillPanel.add(surrenderButton);
    }

    private void createTextPanel() {
        textPanel = new JPanel(null);
        textPanel.setBackground(UIConstants.TEXT_PANEL_BG_COLOR);
        textPanel.setBorder(BorderFactory.createMatteBorder(UIConstants.SKILL_PANEL_BORDER_THICKNESS, 0, 0, 0,
                UIConstants.COLOR_WHITE));

        // Text Area
        battleTextArea = new JTextArea();
        battleTextArea.setEditable(false);
        battleTextArea.setFont(new Font("Monospaced", Font.BOLD, UIConstants.TEXT_AREA_FONT_SIZE));
        battleTextArea.setForeground(UIConstants.COLOR_WHITE);
        battleTextArea.setBackground(UIConstants.TEXT_PANEL_BG_COLOR);
        battleTextArea.setLineWrap(true);
        battleTextArea.setWrapStyleWord(true);
        battleTextArea.setBounds(UIConstants.TEXT_AREA_X, UIConstants.TEXT_AREA_Y,
                UIConstants.TEXT_AREA_WIDTH, UIConstants.TEXT_AREA_HEIGHT);
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
        continueButton.setFont(new Font("Arial", Font.BOLD, UIConstants.CONTINUE_BTN_FONT_SIZE));
        continueButton.setBackground(UIConstants.CONTINUE_BTN_COLOR);
        continueButton.setForeground(UIConstants.COLOR_WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(
                BorderFactory.createLineBorder(UIConstants.COLOR_WHITE, UIConstants.SKILL_PANEL_BORDER_THICKNESS));
        continueButton.setBounds(UIConstants.CONTINUE_BTN_X, UIConstants.CONTINUE_BTN_Y,
                UIConstants.CONTINUE_BTN_WIDTH, UIConstants.CONTINUE_BTN_HEIGHT);
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
        button.setForeground(UIConstants.COLOR_WHITE);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(UIConstants.COLOR_WHITE, UIConstants.SKILL_PANEL_BORDER_THICKNESS));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(UIConstants.COLOR_WHITE);
        button.add(titleLabel, BorderLayout.NORTH);

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(UIConstants.COLOR_WHITE);
        button.add(descLabel, BorderLayout.CENTER);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(color.brighter());
                    button.setBorder(BorderFactory.createLineBorder(UIConstants.COLOR_YELLOW, 3));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createLineBorder(UIConstants.COLOR_WHITE,
                        UIConstants.SKILL_PANEL_BORDER_THICKNESS));
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
            titleLabel.setForeground(UIConstants.COLOR_WHITE);
            button.add(titleLabel, BorderLayout.NORTH);

            JLabel descLabel = new JLabel(skillDescriptions[i], SwingConstants.CENTER);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            descLabel.setForeground(UIConstants.COLOR_WHITE);
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
            g2.setColor(UIConstants.PANEL_BG_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark overlay
        g2.setColor(UIConstants.OVERLAY_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw characters if available
        if (player1 != null && player2 != null) {
            drawCharacterArea(g2);
        }

        g2.dispose();
    }

    private void drawCharacterArea(Graphics2D g2) {
        int midY = UIConstants.CHAR_MID_Y;

        // Player 1 (Left) - Horizontal Position
        drawCharacterSprite(g2, heroSprite, UIConstants.P1_SPRITE_X, midY - UIConstants.CHAR_SPRITE_Y_OFFSET, true);
        drawCharacterStats(g2, player1, UIConstants.P1_STATS_X, UIConstants.CHAR_STATS_Y, true);

        // VS Label
        g2.setFont(new Font("Arial", Font.BOLD, UIConstants.VS_FONT_SIZE));
        g2.setColor(UIConstants.COLOR_RED);
        String vsText = "VS";
        int vsWidth = g2.getFontMetrics().stringWidth(vsText);
        g2.drawString(vsText, (getWidth() - vsWidth) / 2, UIConstants.VS_LABEL_Y);

        // Player 2 (Right) - Horizontal Position
        drawCharacterSprite(g2, enemySprite, UIConstants.P2_SPRITE_X, midY - UIConstants.CHAR_SPRITE_Y_OFFSET, false);
        drawCharacterStats(g2, player2, UIConstants.P2_STATS_X, UIConstants.CHAR_STATS_Y, false);
    }

    private void drawCharacterSprite(Graphics2D g2, BufferedImage sprite, int x, int y, boolean isPlayer) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, UIConstants.CHAR_SPRITE_SIZE, UIConstants.CHAR_SPRITE_SIZE, null);
        } else {
            g2.setColor(isPlayer ? UIConstants.PLAYER_PLACEHOLDER_COLOR : UIConstants.ENEMY_PLACEHOLDER_COLOR);
            g2.fillOval(x, y, UIConstants.CHAR_SPRITE_SIZE, UIConstants.CHAR_SPRITE_SIZE);
        }
    }

    private void drawCharacterStats(Graphics2D g2, BaseCharacter character, int x, int y, boolean isPlayer) {
        // Name
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(UIConstants.COLOR_WHITE);
        g2.drawString(character.getName(), x, y);

        // HP Bar
        y += 25;
        drawStatBar(g2, "HP", character.getHealthPoints(), character.getMaxHealthPoints(),
                x, y, UIConstants.STAT_BAR_WIDTH, UIConstants.HP_BAR_HEIGHT, UIConstants.COLOR_GREEN,
                UIConstants.COLOR_RED);

        // FP Bar
        y += 35;
        drawStatBar(g2, "FP", character.getFocusPoints(), character.getMaxFocusPoints(),
                x, y, UIConstants.STAT_BAR_WIDTH, UIConstants.FP_BAR_HEIGHT, UIConstants.COLOR_CYAN,
                UIConstants.FP_BAR_FILL_COLOR);

        // Status Icons
        y += 25;
        drawStatusIcons(g2, character, x, y);
    }

    private void drawStatBar(Graphics2D g2, String label, int current, int max,
            int x, int y, int width, int height, Color goodColor, Color badColor) {
        // Label
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(UIConstants.COLOR_WHITE);
        g2.drawString(label + ":", x, y + height - 5);

        int barX = x + UIConstants.STAT_LABEL_OFFSET;
        int barWidth = width - UIConstants.STAT_LABEL_OFFSET;

        // Background
        g2.setColor(UIConstants.HP_BAR_BG_COLOR);
        g2.fillRect(barX, y, barWidth, height);

        // Fill
        float percent = (float) current / max;
        int fillWidth = (int) (barWidth * percent);

        Color fillColor = percent > 0.5 ? goodColor : (percent > 0.2 ? UIConstants.COLOR_ORANGE : badColor);
        g2.setColor(fillColor);
        g2.fillRect(barX, y, fillWidth, height);

        // Border
        g2.setColor(UIConstants.COLOR_WHITE);
        g2.drawRect(barX, y, barWidth, height);

        // Text
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(UIConstants.COLOR_WHITE);
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
}
