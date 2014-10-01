/**
 * 
 */
package de.buxi.cantstop.model;

import java.io.Serializable;

/**
 * @author buxi
 *
 */
public enum GameState implements Serializable {
	INIT, 
	IN_GAME, 
	IN_ROUND, 
	ROUND_FINISHED, 
	DICES_THROWN, 
	WRONG_PAIR_CHOSEN, 
	PAIR_USED, 
	NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED, 
	GAME_WIN,
	GAME_FINISHED;
}