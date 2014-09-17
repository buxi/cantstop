/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public enum GameState {
	INIT, 
	IN_GAME, 
	IN_ROUND, 
	ROUND_FINISHED, 
	DICES_THROWN, 
	WRONG_PAIR_CHOSEN, 
	PAIR_USED, 
	NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED, 
	GAME_WIN; 
}