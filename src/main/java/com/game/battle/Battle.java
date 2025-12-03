package com.game.battle;

import com.game.battle.subsystems.*;
import com.game.characters.BaseCharacter;
import java.util.logging.Logger;

/**
 * FACADE PATTERN - Battle Controller
 * Delegates semua business logic ke subsystems.
 */
public class Battle {

    private static final Logger LOGGER = Logger.getLogger(Battle.class.getName());

    // Player data
    private BaseCharacter player1;
    private BaseCharacter player2;

    // Subsystems (Facade Pattern)
    private TurnManager turnManager;
    private CombatResolver combatResolver;
    private VictoryConditionChecker victoryChecker;
    private BattleLog battleLog;
    private BattleDisplay display; // Display delegate (dari temanmu)

    public Battle(BaseCharacter player1, BaseCharacter player2) {
        // Validation
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Player tidak boleh null");
        }

        this.player1 = player1;
        this.player2 = player2;

        // Initialize subsystems
        this.turnManager = new TurnManager();
        this.combatResolver = new CombatResolver();
        this.victoryChecker = new VictoryConditionChecker();
        this.battleLog = new BattleLog();
        this.display = new BattleDisplay(this); // BattleDisplay butuh reference ke Battle
    }

    // ====================================================================
    // FACADE METHODS - Delegates to Subsystems
    // ====================================================================

    public void start() {
        // Delegate to TurnManager untuk intro dan turn order
        turnManager.displayBattleIntro(player1, player2, LOGGER);
        
        // Determine turn order
        turnManager.determineTurnOrder(player1, player2);
        
        // Start battle
        turnManager.startBattle();
        
        // Display turn info
        turnManager.displayTurnOrderInfo(player1, player2, LOGGER);
    }

    public void displayStatus() {
        // Delegate to BattleDisplay
        display.displayStatus();
    }

    public void displayAvailableSkills() {
        // Delegate to BattleDisplay
        display.displayAvailableSkills();
    }

    public boolean executePlayerAction(int skillIndex) {
        BaseCharacter attacker = getCurrentPlayer();
        BaseCharacter defender = getOpponentPlayer();

        // Delegate to CombatResolver
        return combatResolver.executeAction(attacker, defender, skillIndex, battleLog, LOGGER);
    }

    public void surrender() {
        BaseCharacter surrenderer = getCurrentPlayer();
        BaseCharacter opponent = getOpponentPlayer();

        // Delegate to VictoryConditionChecker
        victoryChecker.handleSurrender(surrenderer, opponent, battleLog, LOGGER);
        
        // Finish battle in TurnManager
        turnManager.finishBattle();
    }

    public void endTurn() {
        // Delegate to TurnManager
        turnManager.switchTurn();
        
        // Regenerate FP after full cycle
        if (turnManager.getCurrentState() == BattleState.PLAYER1_TURN) {
            turnManager.regenerateFP(player1, player2);
        }
    }

    public boolean isBattleFinished() {
        // Delegate to VictoryConditionChecker
        return victoryChecker.isBattleFinished(
            player1, 
            player2, 
            turnManager.getCurrentState()
        );
    }

    public BaseCharacter getWinner() {
        // Delegate to VictoryConditionChecker
        return victoryChecker.determineWinner(player1, player2);
    }

    public void displayBattleLog() {
        battleLog.displayFullLog();
    }

    // ====================================================================
    // GETTERS - Public API
    // ====================================================================

    public BaseCharacter getPlayer1() {
        return player1;
    }

    public BaseCharacter getPlayer2() {
        return player2;
    }

    public BattleState getCurrentState() {
        return turnManager.getCurrentState();
    }

    public int getTurnCount() {
        return turnManager.getTurnCount();
    }

    public BattleLog getBattleLog() {
        return battleLog;
    }

    public BaseCharacter getCurrentPlayer() {
        BattleState state = turnManager.getCurrentState();
        if (state == BattleState.PLAYER1_TURN) {
            return player1;
        } else if (state == BattleState.PLAYER2_TURN) {
            return player2;
        }
        throw new IllegalStateException("Invalid battle state: " + state);
    }

    public BaseCharacter getOpponentPlayer() {
        return turnManager.getCurrentState() == BattleState.PLAYER1_TURN ? player2 : player1;
    }

    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    public boolean hasStarted() {
        return turnManager.getCurrentState() != BattleState.WAITING;
    }
}