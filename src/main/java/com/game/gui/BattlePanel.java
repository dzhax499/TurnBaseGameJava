package com.game.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.game.utils.ResourceLoader;

/**
 * Panel untuk Battle Screen dengan 4 tombol skill
 * Implementasi Observer Pattern untuk update HP secara otomatis
 */
public class BattlePanel extends JPanel {
    private BufferedImage background;
    private BufferedImage heroSprite;
    private BufferedImage enemySprite;

    // Battle State (dummy data untuk sekarang, nanti diganti dengan Battle object)
    private String playerName = "Player";
    private String enemyName = "Enemy";
    private int playerHP = 100;
    private int playerMaxHP = 100;
    private int enemyHP = 100;
    private int enemyMaxHP = 100;
    private String currentTurn = "PLAYER";

    // Skill Buttons
    private JButton skill1Button;
    private JButton skill2Button;
    private JButton skill3Button;
    private JButton skill4Button;

    // Battle Log
    private JTextArea battleLog;
    private JScrollPane logScrollPane;

    // Listener untuk skill action
    private SkillActionListener skillListener;

    public interface SkillActionListener {
        void onSkillUsed(int skillIndex);
    }

    public BattlePanel() {
        setLayout(null); // Absolute positioning
        background = ResourceLoader.loadImage("/images/tes_image.jpg");
        heroSprite = ResourceLoader.loadImage("/images/tes_image.jpg");
        enemySprite = ResourceLoader.loadImage("/images/tes_image.jpg");

        initializeComponents();
    }

    private void initializeComponents() {
        // Battle Log Area (top right)
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        battleLog.setBackground(new Color(30, 30, 30, 200));
        battleLog.setForeground(Color.WHITE);
        battleLog.setText("Battle Started!\nYour turn!\n");

        logScrollPane = new JScrollPane(battleLog);
        logScrollPane.setBounds(520, 20, 260, 200);
        logScrollPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        add(logScrollPane);

        // Skill Buttons Panel (bottom)
        int buttonWidth = 180;
        int buttonHeight = 70;
        int buttonY = 450;
        int startX = 50;
        int spacing = 20;

        skill1Button = createSkillButton("Skill 1", "Basic Attack", Color.RED,
                startX, buttonY, buttonWidth, buttonHeight);
        skill1Button.addActionListener(e -> useSkill(0));
        add(skill1Button);

        skill2Button = createSkillButton("Skill 2", "Special Move", Color.ORANGE,
                startX + (buttonWidth + spacing), buttonY, buttonWidth, buttonHeight);
        skill2Button.addActionListener(e -> useSkill(1));
        add(skill2Button);

        skill3Button = createSkillButton("Skill 3", "Heal/Buff", Color.GREEN,
                startX + (buttonWidth + spacing) * 2, buttonY, buttonWidth, buttonHeight);
        skill3Button.addActionListener(e -> useSkill(2));
        add(skill3Button);

        skill4Button = createSkillButton("Skill 4", "Ultimate", Color.MAGENTA,
                startX + (buttonWidth + spacing) * 3, buttonY, buttonWidth, buttonHeight);
        skill4Button.addActionListener(e -> useSkill(3));
        add(skill4Button);

        // Turn Indicator Label
        JLabel turnLabel = new JLabel("YOUR TURN", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        turnLabel.setForeground(Color.YELLOW);
        turnLabel.setBounds(300, 10, 200, 30);
        add(turnLabel);
    }

    private JButton createSkillButton(String title, String description, Color color,
            int x, int y, int width, int height) {
        JButton button = new JButton("<html><center><b>" + title + "</b><br>" +
                "<small>" + description + "</small></center></html>");
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
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
        // Disable buttons sementara (nanti akan di-enable kembali saat player turn)
        setSkillButtonsEnabled(false);
    }

    public void setSkillButtonsEnabled(boolean enabled) {
        skill1Button.setEnabled(enabled);
        skill2Button.setEnabled(enabled);
        skill3Button.setEnabled(enabled);
        skill4Button.setEnabled(enabled);
    }

    public void updateSkillButtons(String[] skillNames, String[] skillDescriptions) {
        if (skillNames.length >= 4) {
            skill1Button.setText("<html><center><b>" + skillNames[0] + "</b><br><small>" +
                    skillDescriptions[0] + "</small></center></html>");
            skill2Button.setText("<html><center><b>" + skillNames[1] + "</b><br><small>" +
                    skillDescriptions[1] + "</small></center></html>");
            skill3Button.setText("<html><center><b>" + skillNames[2] + "</b><br><small>" +
                    skillDescriptions[2] + "</small></center></html>");
            skill4Button.setText("<html><center><b>" + skillNames[3] + "</b><br><small>" +
                    skillDescriptions[3] + "</small></center></html>");
        }
    }

    public void setSkillActionListener(SkillActionListener listener) {
        this.skillListener = listener;
    }

    // Update methods untuk Observer Pattern
    public void updatePlayerHP(int hp, int maxHP) {
        this.playerHP = hp;
        this.playerMaxHP = maxHP;
        repaint();
    }

    public void updateEnemyHP(int hp, int maxHP) {
        this.enemyHP = hp;
        this.enemyMaxHP = maxHP;
        repaint();
    }

    public void updatePlayerName(String name) {
        this.playerName = name;
        repaint();
    }

    public void updateEnemyName(String name) {
        this.enemyName = name;
        repaint();
    }

    public void addBattleLogMessage(String message) {
        battleLog.append(message + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }

    public void clearBattleLog() {
        battleLog.setText("");
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
            g2.setColor(new Color(40, 40, 60));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark overlay
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw character sprites
        int midY = 250;

        // Player sprite (left side)
        if (heroSprite != null) {
            g2.drawImage(heroSprite, 80, midY - heroSprite.getHeight() / 2,
                    100, 100, null);
        } else {
            g2.setColor(Color.BLUE);
            g2.fillOval(80, midY - 50, 100, 100);
        }

        // Enemy sprite (right side)
        if (enemySprite != null) {
            g2.drawImage(enemySprite, getWidth() - 180, midY - enemySprite.getHeight() / 2,
                    100, 100, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillOval(getWidth() - 180, midY - 50, 100, 100);
        }

        // Draw HP bars and names
        drawCharacterInfo(g2, playerName, playerHP, playerMaxHP, 50, 370, true);
        drawCharacterInfo(g2, enemyName, enemyHP, enemyMaxHP, getWidth() - 250, 370, false);

        g2.dispose();
    }

    private void drawCharacterInfo(Graphics2D g2, String name, int hp, int maxHP,
            int x, int y, boolean isPlayer) {
        // Name
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        g2.drawString(name, x, y);

        // HP Bar background
        int barWidth = 200;
        int barHeight = 25;
        g2.setColor(new Color(50, 50, 50));
        g2.fillRect(x, y + 5, barWidth, barHeight);

        // HP Bar fill
        float hpPercent = (float) hp / maxHP;
        int fillWidth = (int) (barWidth * hpPercent);

        Color hpColor;
        if (hpPercent > 0.6f) {
            hpColor = Color.GREEN;
        } else if (hpPercent > 0.3f) {
            hpColor = Color.ORANGE;
        } else {
            hpColor = Color.RED;
        }

        g2.setColor(hpColor);
        g2.fillRect(x, y + 5, fillWidth, barHeight);

        // HP Bar border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y + 5, barWidth, barHeight);

        // HP Text
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        String hpText = hp + " / " + maxHP;
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(hpText);
        g2.setColor(Color.WHITE);
        g2.drawString(hpText, x + barWidth / 2 - textWidth / 2, y + 23);
    }
}