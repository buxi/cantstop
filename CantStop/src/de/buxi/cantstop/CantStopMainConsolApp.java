/**
 * 
 */
package de.buxi.cantstop;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
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
	private ApplicationContext context;
	private Locale locale;
	
	private static final Locale defaultLocale = Locale.ENGLISH;
	private static final String[] pairChoose =new String[]{"A", "B", "C"};
	private static final String SPACE = " ";
	
	private CantStopMainConsolApp() {
		locale = defaultLocale;
		context = new ClassPathXmlApplicationContext("cantstopGameBeans.xml");
	}
	
	private String getMessage(String key) {
		return context.getMessage(key, null, locale);
	}
	

	private String getMessage(String key, Object[] parameters) {
		return context.getMessage(key, parameters, locale);
	}

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
		CantStopMainConsolApp mainApp = new CantStopMainConsolApp();
		mainApp.doGame();
	}
	private void doGame() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, NotAvailableClimberException, NullClimberException {
		// TODO log exceptions
		
		GameController gameController = (GameController)context.getBean("gameController");
		String action = "";
		GameTransferObject gameControllerTO = null;
		gameController.doGameStarten();
		gameController.doStartGameRound();
	    do {
	    	gameControllerTO = gameController.doGetTransferObject();
	    	switch (action) {
			case "1": 
				if (GameState.IN_ROUND.equals(gameControllerTO.gameState)) {
					gameController.doEndGameRound();
					gameControllerTO = gameController.doGetTransferObject();
				}
				else {
					System.out.println(getMessage("FINISH_ROUND_NOT_ALLOWED"));
				}
				break;
			
			case "2":
				gameController.doThrow();
				gameControllerTO = gameController.doGetTransferObject();
				break;
			
			case "3":
				changeLocale();
				break;

			case "A" :
			case "a" :
			case "B" :
			case "b" :
			case "C" :
			case "c" :
				int chosenPairNum = 0;
				if (action.equals('a') || action.equals('A')) {
					chosenPairNum = 0;
				} else if (action.equals('b') || action.equals('B')){
					chosenPairNum = 1;
				} else if (action.equals('c') || action.equals('B')){
					chosenPairNum = 2;
				}
				if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) && 
						gameControllerTO.choosablePairs.size()>0 && 
						chosenPairNum < gameControllerTO.choosablePairs.size()
						) {
					TwoDicesPair chosenPair = gameControllerTO.possiblePairs.get(chosenPairNum);
					int wayNumber = getWayNumberFromUser(chosenPair);
					gameController.doExecutePairs(chosenPair, wayNumber);
					gameControllerTO = gameController.doGetTransferObject();
				}
				break;
			default:
				break;
			} 

			if (StringUtils.isNoneEmpty(gameControllerTO.errorMessage)) {
				System.out.println(getMessage("GAME_MESSAGE", new Object[]{getMessage(gameControllerTO.errorMessage)}));
			}
	    	System.out.print(getMessage("GAME_STATUS"));
	    	System.out.println(getMessage("STATE_"+gameControllerTO.gameState.toString()));

	    	//display board
	    	System.out.println(gameControllerTO.boardDisplay);
	    	
	    	//display players
	    	int actuellePlayer = gameControllerTO.actualPlayerNumber;
	    	List<Player> playerList = gameControllerTO.playerList;
	    	for (int i = 0; i < playerList.size(); i++) {
	    		String inTurnMessage = getMessage("IN_TURN") + "----> ";
				if (i == actuellePlayer) {
					System.out.print(inTurnMessage);
				}
				else {
					System.out.print(StringUtils.leftPad("", inTurnMessage.length()));
				}
				System.out.println(playerList.get(i).display());
			}
	    	
	    	
	    	//display menu
	    	System.out.println(getMessage("ACTIONTITLE"));
	    	System.out.println(getMessage("ACTION_MENU"));
	    	
	    	// displaying pairs to choose
	    	int choosablePairNumber = 0;
	    	if (GameState.DICES_THROWN.equals(gameControllerTO.gameState)) {
		    	Collection<Dice> dices = gameControllerTO.dices;
		    	
		    	//displaying dices
		    	System.out.println(getMessage("PLAYER_THROWN", new Object[]{gameControllerTO.actualPlayer.getName(), dices}));
				
		    	for (int i = 0; i < gameControllerTO.choosablePairs.size(); i++) {
		    		System.out.print(StringUtils.center(pairChoose[i], 15));
				}
		    	System.out.println();
		    	for (TwoDicesPair pair : gameControllerTO.choosablePairs) {
					
					System.out.print(StringUtils.center(pair.display(), 15));
					choosablePairNumber++;
				}
		    	System.out.println();
	    	}
	    	if (choosablePairNumber>0) {
	    		System.out.print(getMessage("CHOOSE_A_PAIR"));
	    		System.out.print(SPACE + pairChoose[0]);
	    		if (choosablePairNumber > 1) {
	    			System.out.print(SPACE + pairChoose[1]);
	    		}
	    		if (choosablePairNumber > 2) {
	    			System.out.print(SPACE + pairChoose[2]);
	    		}
	    		System.out.println();
	    	}
	    	
	    	if (GameState.GAME_WIN.equals(gameControllerTO.gameState)) {
	    		System.out.println(getMessage("STATE_GAME_WIN", new Object[]{gameController.getActualPlayer().getName()} ));
	    		break;
	    	}
	    	Scanner in = new Scanner(System.in);
	    	if (!GameState.DICES_THROWN.equals(gameControllerTO.gameState)) {
	    		System.out.println(getMessage("ENTER_ACTION_NUMBER"));
	    	}
		    action = in.next();      
		    System.out.println(getMessage("ENTERED_ACTION", new Object[]{action}));
		    gameControllerTO = null;
	    } while (!"0".equals(action));
	    System.out.println(getMessage("GAME_EXIT"));
	}

	/**
	 * 
	 */
	protected void changeLocale() {
		if (Locale.GERMAN.equals(locale)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.GERMAN;
		}
		System.out.println(getMessage("LOCALE_CHANGED", new Object[]{locale}));
	}
	/**
	 * @param gewaehltePairung
	 * @return
	 * @throws DiceNotThrownException
	 */
	protected int getWayNumberFromUser(TwoDicesPair gewaehltePairung)
			throws DiceNotThrownException {
		int wayNumber = -1;
		if (PairChoiceInfo.WITHWAYINFO.equals(gewaehltePairung.getPairChoiceInfo())) {
			do {
				Scanner in = new Scanner(System.in);
			    System.out.print(getMessage("ENTER_WAYNUMBER", new Object[]{gewaehltePairung.getFirstPair().getSum(), gewaehltePairung.getSecondPair().getSum()}));
			    wayNumber = in.nextInt();      
			    System.out.println(getMessage("WAYNUMBER_ENTERED", new Object[]{wayNumber}));
			} while (wayNumber != gewaehltePairung.getFirstPair().getSum() && wayNumber != gewaehltePairung.getSecondPair().getSum());
		}
		return wayNumber;
	}

}
