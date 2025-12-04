package com.game.battle;

/**
 * Enum untuk merepresentasikan status/state pertarungan.
 * 
 * <p>
 * Battle state digunakan untuk tracking fase pertarungan secara real-time
 * dan menentukan pemain mana yang sedang aktif atau apakah pertarungan sudah
 * selesai.
 * 
 * <p>
 * Alur state normal:
 * 
 * <pre>
 * WAITING → PLAYER1_TURN ⇄ PLAYER2_TURN → FINISHED
 * </pre>
 * 
 * @author TurnBaseGameJava Team
 * @version 1.2
 * @see Battle
 * @see TurnManager
 */
public enum BattleState {
    /** Menunggu untuk memulai pertarungan - state awal */
    WAITING,

    /** Giliran pemain 1 untuk melakukan aksi */
    PLAYER1_TURN,

    /** Giliran pemain 2 untuk melakukan aksi */
    PLAYER2_TURN,

    /** Pertarungan selesai - ada pemenang atau surrender */
    FINISHED
}
