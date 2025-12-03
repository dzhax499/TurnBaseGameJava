package com.game.battle.subsystems;

import com.game.battle.BattleAction;
import com.game.battle.BattleLog;
import com.game.battle.BattleState;
import com.game.characters.BaseCharacter;
import com.game.utils.GameStrings;
import java.util.logging.Logger;

/**
 * Subsystem untuk memeriksa kondisi kemenangan.
 */
public class VictoryConditionChecker {

    private BaseCharacter surrenderWinner;

    public VictoryConditionChecker() {
        this.surrenderWinner = null;
    }

    public void handleSurrender(BaseCharacter surrenderer, BaseCharacter opponent, 
                               BattleLog battleLog, Logger logger) {
        this.surrenderWinner = opponent;

        logger.info(String.format("\n" + GameStrings.MSG_SURRENDERED, surrenderer.getName()));
        logger.info(String.format(GameStrings.MSG_WINNER, opponent.getName()) + "\n");

        BattleAction action = new BattleAction(
                surrenderer.getName(),
                "SURRENDER",
                "Menyerah",
                "");
        battleLog.addAction(action);
    }

    public boolean isBattleFinished(BaseCharacter player1, BaseCharacter player2, BattleState currentState) {
        return (currentState == BattleState.FINISHED)
                || (player1.getHealthPoints() <= 0 || player2.getHealthPoints() <= 0);
    }

    public BaseCharacter determineWinner(BaseCharacter player1, BaseCharacter player2) {
        // Cek jika winner sudah ditentukan (surrender case)
        if (surrenderWinner != null) {
            return surrenderWinner;
        }

        // Cek berdasarkan HP
        if (player1.getHealthPoints() <= 0) {
            return player2;
        } else if (player2.getHealthPoints() <= 0) {
            return player1;
        }

        return null;
    }
}
