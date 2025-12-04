package com.game.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import com.game.utils.ResourceLoader;

/**
 * Enhanced Main Menu Panel dengan animasi dan visual yang lebih baik.
 */
public class MainMenuPanel extends JPanel {
    private transient BufferedImage backgroundIMG;
    
    
    
    private transient CharacterSelectionListener listener;
    private String arial = "Arial";


    public interface CharacterSelectionListener {
        void onCharacterSelected(String characterType);
    }

    public MainMenuPanel() {
        setLayout(null);
        backgroundIMG = ResourceLoader.loadImage("/images/bg.jpg");
        initializeComponents();
    }

    private void initializeComponents() {
        JButton howToPlayButton;
        JButton exitButton;
        JButton startButton;
        // Game Title
        JLabel titleLabel = new JLabel("TURN-BASED BATTLE", SwingConstants.CENTER);
        titleLabel.setFont(new Font(arial, Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(150, 80, 520, 60);
        add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Elemental Warriors Arena", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(arial, Font.ITALIC, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBounds(150, 145, 520, 35);
        add(subtitleLabel);

        // Menu buttons
        int buttonWidth = 280;
        int buttonHeight = 55;
        int startX = (820 - buttonWidth) / 2;
        int startY = 240;
        int spacing = 25;

        // Start Game Button
        startButton = createMenuButton("START GAME", new Color(50, 180, 80));
        startButton.setBounds(startX, startY, buttonWidth, buttonHeight);
        startButton.addActionListener(e -> {
            if (listener != null) {
                listener.onCharacterSelected("START");
            }
        });
        add(startButton);

        // How to Play Button
        howToPlayButton = createMenuButton("HOW TO PLAY", new Color(80, 120, 200));
        howToPlayButton.setBounds(startX, startY + buttonHeight + spacing, buttonWidth, buttonHeight);
        howToPlayButton.addActionListener(e -> showHowToPlay());
        add(howToPlayButton);

        // Exit Button
        exitButton = createMenuButton("EXIT", new Color(200, 80, 80));
        exitButton.setBounds(startX, startY + (buttonHeight + spacing) * 2, buttonWidth, buttonHeight);
        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit Game",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        add(exitButton);

        // Version/Credits
        JLabel creditsLabel = new JLabel("v1.0 | TUBES PBO (067 | 071 | 073)", SwingConstants.CENTER);
        creditsLabel.setFont(new Font(arial, Font.PLAIN, 12));
        creditsLabel.setForeground(new Color(200, 200, 200));
        creditsLabel.setBounds(250, 520, 320, 20);
        add(creditsLabel);

        // Elemental Icons Display
        JLabel elementsLabel = new JLabel("🔥 💧 🌍 💨", SwingConstants.CENTER);
        elementsLabel.setFont(new Font(arial, Font.PLAIN, 40));
        elementsLabel.setBounds(250, 190, 320, 50);
        add(elementsLabel);
    }

    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font(arial, Font.BOLD, 20));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = color;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            }
        });

        return button;
    }

    private void showHowToPlay() {
        String message = "=== HOW TO PLAY ===\n\n" +
                "OBJECTIVE:\n" +
                "Reduce your opponent's HP to 0 to win!\n\n" +
                "GAMEPLAY:\n" +
                "• Each player takes turns selecting skills\n" +
                "• Skills cost FP (Focus Points) to use\n" +
                "• FP regenerates each turn cycle\n" +
                "• Turn order is determined by Speed stat\n\n" +
                "ELEMENTAL SYSTEM:\n" +
                "Fire > Wind > Earth > Water > Fire\n" +
                "• Advantage: 1.5x damage\n" +
                "• Disadvantage: 0.75x damage\n\n" +
                "MECHANICS:\n" +
                "• Dodge: Higher speed = higher dodge chance\n" +
                "• Critical: Speed affects crit chance\n" +
                "• Status Effects: Burn, Freeze, etc.\n\n" +
                "CHARACTER TYPES:\n" +
                "🔥 FIRE - High damage, low defense\n" +
                "💧 WATER - Balanced, freeze ability\n" +
                "🌍 EARTH - High HP/defense, slow\n" +
                "💨 WIND - Fast, low HP\n\n" +
                "Good luck in battle!";

        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBackground(new Color(40, 40, 40));
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 400));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "How to Play",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setCharacterSelectionListener(CharacterSelectionListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        if (backgroundIMG != null) {
            g2.drawImage(backgroundIMG, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Gradient background
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(20, 20, 40),
                    0, getHeight(), new Color(60, 20, 80));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dark overlay for readability
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Decorative frame
        g2.setColor(new Color(255, 215, 0, 100)); // Gold
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(40, 40, getWidth() - 80, getHeight() - 80, 20, 20);

        g2.dispose();
    }
}