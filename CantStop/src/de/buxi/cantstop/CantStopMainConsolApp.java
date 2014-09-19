/**
 * 
 */
package de.buxi.cantstop;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotAvailableClimberException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.PairChoiceInfo;
import de.buxi.cantstop.model.Player;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.TwoDicesPair;
/**
 * @author buxi
 *
 */
public class CantStopMainConsolApp {

	/**
	 * @param args
	 * @throws DiceNotThrownException 
	 * @throws RopePointInvalidUsageException 
	 * @throws NoMarkerIsAvailableException 
	 * @throws NotAvailableClimberException 
	 * @throws InvalidWayNumberException 
	 * @throws InvalidClimberMovementException 
	 * @throws NullClimberException 
	 * @throws NoClimberOnWayException 
	 */
	public static void main(String[] args) throws DiceNotThrownException, RopePointInvalidUsageException, NoMarkerIsAvailableException, NotAvailableClimberException, InvalidWayNumberException, InvalidClimberMovementException, NullClimberException, NoClimberOnWayException {
		// TODO log exceptions
		ApplicationContext context =
				new ClassPathXmlApplicationContext("cantstopGameBeans.xml");
		//TODO !!!!! zuruck zum normalBoard
		GameController gameController = (GameController)context.getBean("gameController");
		//GameController gameController = (GameController)context.getBean("testGameController");
		String action = "";
		GameTransferObject gameControllerTO = null;
		gameController.doGameStarten();
		gameController.doStartGameRound();
	    do {
	    	gameControllerTO = gameController.doGetTransferObject();
	    	System.out.println("---------------------------------------------------------");
	    	
	    	switch (action) {
			case "1": 
				if (GameState.IN_ROUND.equals(gameControllerTO.gameState)) {
					gameController.doEndGameRound();
					gameControllerTO = gameController.doGetTransferObject();
				}
				else {
					System.out.println("Beenden des Gamezugs ist nicht erlaubt");
				}
				break;
			case "2":
				gameController.doThrow();
				gameControllerTO = gameController.doGetTransferObject();
				break;
			case "A" :
			case "a" :
				if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) && gameControllerTO.possiblePairs.size()>0) {
					TwoDicesPair gewaehltePairung = gameControllerTO.possiblePairs.get(0);
					if (!PairChoiceInfo.NOTCHOOSABLE.equals(gewaehltePairung.getPairChoiceInfo())) {
						int wayNumber = getWayNumberVonUser(gewaehltePairung);
						gameController.doExecutePairs(gewaehltePairung, wayNumber);
						gameControllerTO = gameController.doGetTransferObject();
					}
					else {
						System.out.println("Paar ist nicht waehlbar!");
					}
				}
				break;
			case "B" :
			case "b" :
				if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) && gameControllerTO.possiblePairs.size()>1) {
					TwoDicesPair gewaehltePairung = gameControllerTO.possiblePairs.get(1);
					if (!PairChoiceInfo.NOTCHOOSABLE.equals(gewaehltePairung.getPairChoiceInfo())) {
						int wayNumber = getWayNumberVonUser(gewaehltePairung);
						gameController.doExecutePairs(gewaehltePairung, wayNumber);
						gameControllerTO = gameController.doGetTransferObject();
					}
					else {
						System.out.println("Paar ist nicht waehlbar!");
					}
				}
				break;
			case "C" :
			case "c" :
				if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) && gameControllerTO.possiblePairs.size()>2) {
					TwoDicesPair gewaehltePairung = gameControllerTO.possiblePairs.get(2);
					if (!PairChoiceInfo.NOTCHOOSABLE.equals(gewaehltePairung.getPairChoiceInfo())) {
						int wayNumber = getWayNumberVonUser(gewaehltePairung);
						gameController.doExecutePairs(gewaehltePairung, wayNumber);
						gameControllerTO = gameController.doGetTransferObject();
					}
					else {
						System.out.println("Kommando oder Paar ist nicht waehlbar!");
					}
				}
				break;
			default:
				break;
			} 
	    	
	    	System.out.println(gameControllerTO.boardDisplay);;
	    	int actuellePlayer = gameControllerTO.actualPlayerNumber;
	    	List<Player> playerList = gameControllerTO.playerList;
	    	for (int i = 0; i < playerList.size(); i++) {
				if (i == actuellePlayer) {
					System.out.print("DRAN ----> ");
				}
				else {
					System.out.print("           ");
				}
				System.out.println(playerList.get(i).display());
			}
	    	
	    	// warten auf Player
	    	if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) || 
	    		GameState.WRONG_PAIR_CHOSEN.equals(gameControllerTO.gameState)) {
		    	Collection<Dice> dices = gameControllerTO.dices;
		    	System.out.println(gameControllerTO.actualPlayer.getName() + " hat thrown:" + dices);
		    	gameControllerTO = gameController.doGetTransferObject();
				System.out.println("Mögliche Pairungen:" + gameControllerTO.possiblePairs);
				if (gameControllerTO.possiblePairs.size()>0) {
					System.out.print("                          A              ");
				}
				if (gameControllerTO.possiblePairs.size()>1) {
					System.out.print("B              ");
				}
				if (gameControllerTO.possiblePairs.size()>2) {
					System.out.print("C");
				}
	    	}
			System.out.println();
			System.out.println("Error message:"+gameControllerTO.errorMessage);
			System.out.println(gameControllerTO.wrongPairs);
	    	System.out.println("Game status:"+gameControllerTO.gameState);
	    	System.out.println("MENU 0:Game Ende, 1:Gamezug beenden 2:Throw");
	    	if (gameControllerTO.possiblePairs != null && gameControllerTO.possiblePairs.size() > 0) {
	    		System.out.print("Pairen Auswahl: A");
	    		if (gameControllerTO.possiblePairs.size()>1) {
	    			System.out.print(" B");
	    		}
	    		if (gameControllerTO.possiblePairs.size()>2) {
	    			System.out.print(" C");
	    		}
	    		System.out.println();
	    	}
	    	
	    	if (GameState.GAME_WIN.equals(gameControllerTO.gameState)) {
	    		System.out.println("Game gewonnen:" + gameController.getActualPlayer().getName() + " Gratulation!");
	    		break;
	    	}
	    	Scanner in = new Scanner(System.in);
		    System.out.print("Geben Sie eine Actionsnummer ein: ");
		    action = in.next();      
		    System.out.println("Sie eingaben : " + action);
		    gameControllerTO = null;
	    } while (!"0".equals(action));
	    System.out.println("Auf Wiedersehen!");
	}

	/**
	 * @param gewaehltePairung
	 * @return
	 * @throws DiceNotThrownException
	 */
	protected static int getWayNumberVonUser(TwoDicesPair gewaehltePairung)
			throws DiceNotThrownException {
		int wayNumber = -1;
		if (PairChoiceInfo.WITHWAYINFO.equals(gewaehltePairung.getPairChoiceInfo())) {
			Scanner in = new Scanner(System.in);
		    System.out.print("Geben Sie eine wegnummer ein (" + gewaehltePairung.getFirstPair().getSum()+","+ gewaehltePairung.getSecondPair().getSum()  + "): ");
		    wayNumber = in.nextInt();      
		    System.out.println("Sie eingaben: " + wayNumber);
		}
		return wayNumber;
	}

}
