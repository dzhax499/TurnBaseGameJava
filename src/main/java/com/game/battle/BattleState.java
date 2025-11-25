package com.game.battle;

/**
 * Enum untuk merepresentasikan status pertarungan.
 * Digunakan untuk tracking state battle secara real-time.
 */
public enum BattleState {
    WAITING,        // Menunggu untuk memulai pertarungan
    PLAYER1_TURN,   // Giliran pemain 1
    PLAYER2_TURN,   // Giliran pemain 2
    FINISHED        // Pertarungan selesai (ada pemenang atau surrender)
}
