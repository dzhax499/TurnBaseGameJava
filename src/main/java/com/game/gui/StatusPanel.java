package com.game.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.game.utils.ResourceLoader;

/**
 * Panel that displays background and simple sprites/status for the game.
 * It loads images from `src/main/src/java/resources/images/` using ResourceLoader.
 */
public class StatusPanel extends JPanel {
    private BufferedImage background;
    private BufferedImage hero;
    private BufferedImage enemy;

    public StatusPanel() {
        // Try to load common images. Use leading slash for classpath resource.
        background = ResourceLoader.loadImage("/images/tes_image.jpg");
        hero = ResourceLoader.loadImage("/images/tes_image.jpg");
        enemy = ResourceLoader.loadImage("/images/tes_image.jpg");
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        // draw background if available
        if (background != null) {
            g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            // fallback
            g2.setColor(new Color(30, 30, 30));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.WHITE);
            g2.drawString("Background not found: /images/tes_image.jpg", 10, 20);
        }

        // draw hero and enemy sprites with simple positioning
        int midY = getHeight() / 2;
        if (hero != null) {
            int hw = hero.getWidth();
            int hh = hero.getHeight();
            g2.drawImage(hero, 100, midY - hh / 2, null);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(90, midY - 30, 60, 60);
            g2.setColor(Color.WHITE);
            g2.drawString("Hero", 105, midY + 5);
        }

        if (enemy != null) {
            int ew = enemy.getWidth();
            int eh = enemy.getHeight();
            g2.drawImage(enemy, getWidth() - 160, midY - eh / 2, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(getWidth() - 160, midY - 30, 60, 60);
            g2.setColor(Color.WHITE);
            g2.drawString("Enemy", getWidth() - 140, midY + 5);
        }

        // draw a simple UI area on top
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(0, getHeight() - 80, getWidth(), 80);
        g2.setColor(Color.WHITE);
        g2.drawString("Player HP: 100/100", 10, getHeight() - 50);
        g2.drawString("Enemy HP: 80/100", getWidth() - 120, getHeight() - 50);

        g2.dispose();
    }
}
