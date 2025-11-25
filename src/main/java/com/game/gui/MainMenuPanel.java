package com.game.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.game.utils.ResourceLoader;

/**
 * Panel untuk Main Menu - Pemilihan karakter di awal game
 */
public class MainMenuPanel extends JPanel {
    private BufferedImage background;
    private JButton fireButton;
    private JButton waterButton;
    private JButton windButton;
    private JButton earthButton;
    private CharacterSelectionListener listener;

    public interface CharacterSelectionListener {
        void onCharacterSelected(String characterType);
    }

    public MainMenuPanel() {
        setLayout(null); // Absolute positioning untuk kontrol penuh
        background = ResourceLoader.loadImage("/images/tes_image.jpg");

        initializeComponents();
    }

    private void initializeComponents() {
        // Title Label
        JLabel titleLabel = new JLabel("PILIH KARAKTER ANDA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 50, 400, 50);
        add(titleLabel);

        // Character Selection Buttons dengan style yang menarik
        int buttonWidth = 150;
        int buttonHeight = 200;
        int startX = 60;
        int startY = 150;
        int spacing = 30;

        // Fire Character Button
        fireButton = createCharacterButton("FIRE", Color.RED, startX, startY, buttonWidth, buttonHeight);
        fireButton.addActionListener(e -> selectCharacter("FIRE"));
        add(fireButton);

        // Water Character Button
        waterButton = createCharacterButton("WATER", Color.CYAN, startX + buttonWidth + spacing, startY, buttonWidth,
                buttonHeight);
        waterButton.addActionListener(e -> selectCharacter("WATER"));
        add(waterButton);

        // Wind Character Button
        windButton = createCharacterButton("WIND", Color.GREEN, startX + (buttonWidth + spacing) * 2, startY,
                buttonWidth, buttonHeight);
        windButton.addActionListener(e -> selectCharacter("WIND"));
        add(windButton);

        // Earth Character Button
        earthButton = createCharacterButton("EARTH", new Color(139, 69, 19), startX + (buttonWidth + spacing) * 3,
                startY, buttonWidth, buttonHeight);
        earthButton.addActionListener(e -> selectCharacter("EARTH"));
        add(earthButton);

        // Info Label
        JLabel infoLabel = new JLabel("Setiap karakter memiliki 4 skill unik!", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(200, 400, 400, 30);
        add(infoLabel);
    }

    private JButton createCharacterButton(String name, Color color, int x, int y, int width, int height) {
        JButton button = new JButton("<html><center>" + name + "<br><br>▼</center></html>");
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void selectCharacter(String characterType) {
        if (listener != null) {
            listener.onCharacterSelected(characterType);
        }
    }

    public void setCharacterSelectionListener(CharacterSelectionListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Draw background
        if (background != null) {
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }

        // Dark overlay untuk readability
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}