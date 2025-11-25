package com.game.gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GameWindow {
    private JFrame frame;
    
    private StatusPanel statusPanel;
    
    public GameWindow() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame("Turn Based Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        statusPanel = new StatusPanel();
        frame.add(statusPanel, BorderLayout.CENTER);
        
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void refresh() {
        if (statusPanel != null) statusPanel.repaint();
    }
    
    public static void main(String[] args) {
        // Launch the window for quick manual testing
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
