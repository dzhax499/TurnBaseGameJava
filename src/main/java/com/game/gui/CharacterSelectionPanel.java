package com.game.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.game.utils.ResourceLoader;

/**
 * Panel untuk memilih karakter dengan informasi lengkap.
 * Menampilkan stats, skills, dan elemen advantage untuk setiap karakter.
 */
public class CharacterSelectionPanel extends JPanel {
    private transient BufferedImage backgroundBuffer;
    private JLabel titleLabel;
    private JTextField nameField;
    private JTextArea infoArea;
    private transient SelectionListener listener;
    private int playerNumber;
    private static final String ARIAL_FONT = "Arial";

    public interface SelectionListener {
        void onCharacterSelected(String characterType, String characterName);

        void onBackToMenu();
    }

    public CharacterSelectionPanel() {
        setLayout(null);
        backgroundBuffer = ResourceLoader.loadImage("/images/bg.jpg");
        initializeComponents();
    }

    private void initializeComponents() {
        // Title
        JButton waterButton;
        JButton windButton;
        JButton earthButton;
        JButton backButton;
        JButton fireButton;
        JLabel instructionLabel;
        // Baris sekitar 42-45
        titleLabel = new JLabel("PLAYER 1 - SELECT YOUR CHARACTER", SwingConstants.CENTER);
        titleLabel.setFont(new Font(ARIAL_FONT, Font.BOLD, 18));
        titleLabel.setForeground(Color.YELLOW);
        // setBounds(posisiX, posisiY, lebar, tinggi)
        titleLabel.setBounds(50, 20, 450, 40);
        add(titleLabel);

        // Instruction
        instructionLabel = new JLabel("Enter your name and choose a character", SwingConstants.CENTER);
        instructionLabel.setFont(new Font(ARIAL_FONT, Font.PLAIN, 16));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBounds(50, 60, 450, 25);
        add(instructionLabel);

        // Name input
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font(ARIAL_FONT, Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(120, 95, 60, 30);
        add(nameLabel);

        nameField = new JTextField("Player 1");
        nameField.setFont(new Font(ARIAL_FONT, Font.PLAIN, 16));
        nameField.setBounds(185, 95, 200, 30);
        add(nameField);

        // Character buttons - NOW IN 2x2 GRID ON LEFT
        int buttonWidth = 160;
        int buttonHeight = 180;
        int startX = 50;
        int startY = 145;
        int spacingX = 25;
        int spacingY = 25;

        // Row 1: Fire and Water
        fireButton = createCharacterButton("FIRE", "/images/fireskill.png", new Color(220, 50, 50),
                startX, startY, buttonWidth, buttonHeight);
        fireButton.addActionListener(e -> selectCharacter("FIRE"));
        add(fireButton);

        waterButton = createCharacterButton("WATER", "/images/wterskill.png", new Color(50, 120, 220),
                startX + buttonWidth + spacingX, startY, buttonWidth, buttonHeight);
        waterButton.addActionListener(e -> selectCharacter("WATER"));
        add(waterButton);

        // Row 2: Wind and Earth
        windButton = createCharacterButton("WIND", "/images/windskill.png", new Color(80, 200, 120),
                startX, startY + buttonHeight + spacingY, buttonWidth, buttonHeight);
        windButton.addActionListener(e -> selectCharacter("WIND"));
        add(windButton);

        earthButton = createCharacterButton("EARTH", "/images/earthskill.png", new Color(139, 90, 43),
                startX + buttonWidth + spacingX, startY + buttonHeight + spacingY, buttonWidth, buttonHeight);
        earthButton.addActionListener(e -> selectCharacter("EARTH"));
        add(earthButton);

        // ======================================================
        // INFO AREAS - SPLIT INTO TWO BOXES (TOP & BOTTOM)
        // ======================================================

        // TOP INFO BOX - Character Details
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        infoArea.setBackground(new Color(30, 30, 30, 230));
        infoArea.setForeground(Color.WHITE);
        infoArea.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        infoArea.setText(getDefaultInfo());
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBounds(420, 145, 360, 195); // Top half: height 195
        add(scrollPane);

        // BOTTOM INFO BOX - Additional Info / Tips
        JTextArea bottomInfoArea = new JTextArea();
        bottomInfoArea.setEditable(false);
        bottomInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        bottomInfoArea.setBackground(new Color(30, 30, 50, 230));
        bottomInfoArea.setForeground(Color.CYAN);
        bottomInfoArea.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        bottomInfoArea.setText(getBottomInfoText());
        bottomInfoArea.setLineWrap(true);
        bottomInfoArea.setWrapStyleWord(true);

        JScrollPane bottomScrollPane = new JScrollPane(bottomInfoArea);
        bottomScrollPane.setBounds(420, 355, 360, 195); // Bottom half: height 195
        add(bottomScrollPane);

        // Back button - MOVED TO BOTTOM LEFT
        backButton = new JButton("← Back to Menu");
        backButton.setFont(new Font(ARIAL_FONT, Font.BOLD, 14));
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.setBounds(130, 560, 150, 35);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            if (listener != null) {
                listener.onBackToMenu();
            }
        });
        add(backButton);
    }

    private JButton createCharacterButton(String name, String imagePath, Color color,
            int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setLayout(null);

        // --- PERBAIKAN: MEMAKAI GAMBAR, BUKAN TEKS ---
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Load gambar
        BufferedImage originalImg = ResourceLoader.loadImage(imagePath);
        if (originalImg != null) {
            // Resize gambar ke 80x80 pixel agar pas
            Image scaledImg = originalImg.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            // Jika gambar tidak ketemu, baru tampilkan teks tanya tanya
            iconLabel.setText("?");
            iconLabel.setFont(new Font("Arial", Font.BOLD, 40));
            iconLabel.setForeground(Color.WHITE);
        }

        iconLabel.setBounds(0, 10, width, 90);
        button.add(iconLabel);
        // ---------------------------------------------

        // Label Nama
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(Color.WHITE); // Pastikan warna putih
        nameLabel.setBounds(0, 100, width, 30);
        button.add(nameLabel);

        // Label Stats (HP, Attack, dll)
        String[] stats = getCharacterStats(name);
        int statsY = 120;
        for (String stat : stats) {
            JLabel statLabel = new JLabel(stat, SwingConstants.CENTER);
            statLabel.setFont(new Font("Arial", Font.PLAIN, 8));
            statLabel.setForeground(Color.WHITE); // Pastikan warna putih
            statLabel.setBounds(0, statsY, width, 15);
            button.add(statLabel);
            statsY += 10;
        }

        // Efek saat mouse diarahkan (Hover)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
                updateInfo(name);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            }
        });

        return button;
    }

    private String[] getCharacterStats(String type) {
        switch (type) {
            case "FIRE":
                return new String[] { "HP: 100", "ATK: 35 ", "DEF: 15", "SPD: 30" };
            case "WATER":
                return new String[] { "HP: 120", "ATK: 25", "DEF: 25", "SPD: 10" };
            case "EARTH":
                return new String[] { "HP: 140", "ATK: 25", "DEF: 30", "SPD: 5" };
            case "WIND":
                return new String[] { "HP: 90", "ATK: 30", "DEF: 10", "SPD: 50" };
            default:
                return new String[] {};
        }
    }

    private void updateInfo(String characterType) {
        String info = "";

        switch (characterType) {
            case "FIRE":
                info = "🔥 FIRE CHARACTER - The Aggressive Attacker\n\n" +
                        "STATS:\n" +
                        "  HP: 100 | ATK: 35⭐ | DEF: 15 | SPD: 30\n\n" +
                        "SKILLS:\n" +
                        "  1. Attack - Basic physical attack\n" +
                        "  2. Guard - Increase defense temporarily\n" +
                        "  3. Heal - Restore HP\n" +
                        "  4. Fireball - Special fire attack with BURN effect\n\n" +
                        "STRENGTHS:\n" +
                        "  • Highest attack power\n" +
                        "  • Strong vs WIND (1.5x damage)\n" +
                        "  • Burn effect deals damage over time\n\n" +
                        "WEAKNESSES:\n" +
                        "  • Low defense\n" +
                        "  • Weak vs WATER (0.75x damage)\n\n" +
                        "PLAYSTYLE: Aggressive, high damage output";
                break;

            case "WATER":
                info = "💧 WATER CHARACTER - The Balanced Warrior\n\n" +
                        "STATS:\n" +
                        "  HP: 120⭐ | ATK: 25 | DEF: 25⭐ | SPD: 10\n\n" +
                        "SKILLS:\n" +
                        "  1. Attack - Basic physical attack\n" +
                        "  2. Guard - Increase defense temporarily\n" +
                        "  3. Heal - Restore HP\n" +
                        "  4. Ice Blast - Special water attack with FREEZE effect\n\n" +
                        "STRENGTHS:\n" +
                        "  • Balanced stats\n" +
                        "  • Strong vs FIRE (1.5x damage)\n" +
                        "  • Freeze effect skips opponent's turn\n\n" +
                        "WEAKNESSES:\n" +
                        "  • Low speed\n" +
                        "  • Weak vs EARTH (0.75x damage)\n\n" +
                        "PLAYSTYLE: Defensive, control-oriented";
                break;

            case "EARTH":
                info = "🌍 EARTH CHARACTER - The Mighty Tank\n\n" +
                        "STATS:\n" +
                        "  HP: 140⭐ | ATK: 25 | DEF: 30⭐ | SPD: 5\n\n" +
                        "SKILLS:\n" +
                        "  1. Attack - Basic physical attack\n" +
                        "  2. Guard - Increase defense temporarily\n" +
                        "  3. Heal - Restore HP\n" +
                        "  4. Rock Throw - Heavy earth attack\n\n" +
                        "STRENGTHS:\n" +
                        "  • Highest HP and Defense\n" +
                        "  • Strong vs WATER (1.5x damage)\n" +
                        "  • Can outlast opponents\n\n" +
                        "WEAKNESSES:\n" +
                        "  • Very slow\n" +
                        "  • Weak vs WIND (0.75x damage)\n\n" +
                        "PLAYSTYLE: Defensive tank, war of attrition";
                break;

            case "WIND":
                info = "💨 WIND CHARACTER - The Swift Speedster\n\n" +
                        "STATS:\n" +
                        "  HP: 90 | ATK: 30 | DEF: 10 | SPD: 50⭐\n\n" +
                        "SKILLS:\n" +
                        "  1. Attack - Basic physical attack\n" +
                        "  2. Guard - Increase defense temporarily\n" +
                        "  3. Heal - Restore HP\n" +
                        "  4. Air Slash - Swift wind attack\n\n" +
                        "STRENGTHS:\n" +
                        "  • Highest speed (goes first)\n" +
                        "  • Strong vs EARTH (1.5x damage)\n" +
                        "  • High dodge and crit chance\n\n" +
                        "WEAKNESSES:\n" +
                        "  • Low HP and Defense\n" +
                        "  • Weak vs FIRE (0.75x damage)\n\n" +
                        "PLAYSTYLE: Hit and run, glass cannon";
                break;
            default:
        }

        infoArea.setText(info);
    }

    private String getDefaultInfo() {
        return "ELEMENTAL ADVANTAGE SYSTEM:\n\n" +
                "Fire > Wind > Earth > Water > Fire\n\n" +
                "• Strong matchup: 1.5x damage\n" +
                "• Neutral matchup: 1.0x damage\n" +
                "• Weak matchup: 0.75x damage\n\n" +
                "Hover over a character to see detailed information!";
    }

    /**
     * Get text for bottom info box
     */
    private String getBottomInfoText() {
        return "💡 TIPS & STRATEGY:\n\n" +
                "• High HP = Tank (survive longer)\n" +
                "• High ATK = Damage dealer\n" +
                "• High DEF = Reduce damage taken\n" +
                "• High SPD = Move first, dodge more\n\n" +
                "⚡ BATTLE MECHANICS:\n" +
                "• FP regenerates each turn\n" +
                "• Speed affects turn order\n" +
                "• Critical hits deal 1.5x damage\n" +
                "• Status effects persist over turns";
    }

    private void selectCharacter(String characterType) {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a name!",
                    "Name Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (listener != null) {
            listener.onCharacterSelected(characterType, name);
        }
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
        titleLabel.setText("PLAYER " + playerNumber + " - SELECT YOUR CHARACTER");
        nameField.setText("Player " + playerNumber);
        infoArea.setText(getDefaultInfo());
    }

    public void setSelectionListener(SelectionListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Draw backgroundBuffer
        if (backgroundBuffer != null) {
            g2.drawImage(backgroundBuffer, 0, 0, getWidth(), getHeight(), null);
        } else {
            g2.setColor(new Color(20, 20, 40));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark overlay
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}