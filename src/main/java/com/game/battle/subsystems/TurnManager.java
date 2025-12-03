package com.game.battle.subsystems;

import com.game.battle.BattleState;
import com.game.characters.BaseCharacter;
import com.game.utils.GameStrings;
import com.utils.Constants;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Subsystem untuk mengelola turn order, switching, dan counting.
 */
public class TurnManager {

    private BattleState currentState;
    private BattleState firstTurn;
    private int turnCount;
    private static final Random random = new Random();

    public TurnManager() {
        this.currentState = BattleState.WAITING;
        this.turnCount = 0;
    }

    public void displayBattleIntro(BaseCharacter player1, BaseCharacter player2, Logger logger) {
        logger.info("\n" + GameStrings.BATTLE_START_TITLE);
        logger.info("⚔️  " + player1.getName() + " (HP: " + player1.getHealthPoints() + "/"
                + player1.getMaxHealthPoints() + ")");
        logger.info(GameStrings.BATTLE_VS);
        logger.info("⚔️  " + player2.getName() + " (HP: " + player2.getHealthPoints() + "/"
                + player2.getMaxHealthPoints() + ")\n");
    }

    public void determineTurnOrder(BaseCharacter player1, BaseCharacter player2) {
        if (player1.getSpeed() > player2.getSpeed()) {
            firstTurn = BattleState.PLAYER1_TURN;
        } else if (player2.getSpeed() > player1.getSpeed()) {
            firstTurn = BattleState.PLAYER2_TURN;
        } else {
            // Coin flip
            firstTurn = random.nextBoolean() ? BattleState.PLAYER1_TURN : BattleState.PLAYER2_TURN;
        }
    }

    public void startBattle() {
        this.currentState = firstTurn;
        this.turnCount = 1;
    }

    public void displayTurnOrderInfo(BaseCharacter player1, BaseCharacter player2, Logger logger) {
        logger.info("🎲 Penentuan Giliran:");
        if (player1.getSpeed() > player2.getSpeed()) {
            logger.info("   " + player1.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else if (player2.getSpeed() > player1.getSpeed()) {
            logger.info("   " + player2.getName() + " memiliki speed lebih tinggi! Jalan duluan!\n");
        } else {
            logger.info("   Kecepatan sama! Hasil Coin Flip menentukan...\n");
        }
    }

    public void switchTurn() {
        if (currentState == BattleState.PLAYER1_TURN) {
            currentState = BattleState.PLAYER2_TURN;
        } else if (currentState == BattleState.PLAYER2_TURN) {
            currentState = BattleState.PLAYER1_TURN;
            turnCount++;
        }
    }

    public void regenerateFP(BaseCharacter player1, BaseCharacter player2) {
        int fpRegenAmount = Constants.FP_REGEN_PER_TURN;
        player1.regenFocusPoints(fpRegenAmount);
        player2.regenFocusPoints(fpRegenAmount);
    }

    public void finishBattle() {
        this.currentState = BattleState.FINISHED;
    }

    // Getters
    public BattleState getCurrentState() {
        return currentState;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
