/**
 * 
 */
package de.vt.cantstop.ui.console;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.vt.cantstop.model.Dice;
import de.vt.cantstop.model.GameState;
import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.model.PairChoiceInfo;
import de.vt.cantstop.model.Player;
import de.vt.cantstop.model.TwoDicesPair;
import de.vt.cantstop.service.GameService;
/**
 * @author buxi
 *
 */
public class CantStopMainConsolApp {
	private GameService gameServices;
	private static final String[] pairChoose =new String[]{"A", "B", "C"};
	private static final String SPACE = " ";
	private Locale locale;
	private static final Locale defaultLocale = Locale.ENGLISH;
	private ApplicationContext context; 
	private static Log log = LogFactory.getLog(CantStopMainConsolApp.class);
			
	private CantStopMainConsolApp() {
		locale = defaultLocale;
		context = new ClassPathXmlApplicationContext("cantstopGameBeans.xml");
		gameServices = (GameService)context.getBean("gameServices");
	}
	
	private String getMessage(String key) {
		return context.getMessage(key, null, locale);
	}
	

	private String getMessage(String key, Object[] parameters) {
		return context.getMessage(key, parameters, locale);
	}

	public static void main(String[] args) throws Exception {
		CantStopMainConsolApp mainApp = new CantStopMainConsolApp();
		mainApp.doGame();
	}
	
	private void doGame() throws Exception{
		String action = "";
		GameTransferObject gameControllerTO = gameServices.startGame();
		gameControllerTO = gameServices.startTurn();
		log.info("Game turn started");
		Map<String, String> pairIdTranslate = null;
		// Java 7 feature: ARM
		try (Scanner in = new Scanner(System.in);) {
		    do {
		    	gameControllerTO = gameServices.getAllGameInformation();
		    	StringBuffer messages = new StringBuffer(); 
		    	// Java 7 feature: Strings in switch case
		    	switch (action) {
				case "1": 
					if (GameState.IN_ROUND.equals(gameControllerTO.gameState)) {
						gameControllerTO = gameServices.finishTurn("");
					}
					else {
						messages.append(getMessage("FINISH_ROUND_NOT_ALLOWED"));
					}
					break;
				
				case "2":
					gameControllerTO = gameServices.throwDices();
					break;
				
				case "3":
					changeLocale();
					messages.append(getMessage("LOCALE_CHANGED", new Object[]{locale}));
					break;
				case "4":
					gameServices.saveState();
					messages.append("State saved");
					break;
				case "5": 
					gameServices.loadState();
					messages.append("Saved state loaded");
					action = "";
					continue;
				case "A" :
				case "a" :
				case "B" :
				case "b" :
				case "C" :
				case "c" :
					int chosenPairNum = 0;
					if ("a".equals(action) || "A".equals(action)) {
						chosenPairNum = 0;
					} else if ("b".equals(action) || "B".equals(action)){
						chosenPairNum = 1;
					} else if ("c".equals(action) || "C".equals(action)){
						chosenPairNum = 2;
					}
					if (GameState.DICES_THROWN.equals(gameControllerTO.gameState) && 
							gameControllerTO.choosablePairsWithId.size() > 0 && 
							chosenPairNum < gameControllerTO.choosablePairsWithId.size()
							) {
						log.debug("chosenPairNum:"+chosenPairNum);
						TwoDicesPair chosenPair = gameControllerTO.choosablePairsWithId.get(pairIdTranslate.get(Integer.toString(chosenPairNum)));
						log.debug("chosenPair:"+chosenPair);
						int wayNumber = getWayNumberFromUser(chosenPair, in);
						gameControllerTO = gameServices.executePairs(pairIdTranslate.get(Integer.toString(chosenPairNum)), wayNumber);
					}
					break;
				default:
					break;
				} 
		    	/* BUILDING SCREEN START */
		    	System.out.println(messages.toString());
				if (!gameControllerTO.messages.isEmpty()) {
					System.out.println(getMessage("GAME_MESSAGE", new Object[]{getMessage(gameControllerTO.messages.get(0).getMessage())}));
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
					
			    	for (int i = 0; i < gameControllerTO.choosablePairsWithId.size(); i++) {
			    		System.out.print(StringUtils.center(pairChoose[i], 15));
					}
			    	System.out.println();
			    	// temporarily translating webIds to consoleIds 
			    	pairIdTranslate = new HashMap<>();
			    	for (Entry<String, TwoDicesPair> pairEntry : gameControllerTO.getChoosablePairsWithId().entrySet()) {
						System.out.print(StringUtils.center(pairEntry.getValue().display(), 15));
						pairIdTranslate.put(Integer.toString(choosablePairNumber), pairEntry.getKey());
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
		    		System.out.println(getMessage("STATE_GAME_WIN", new Object[]{gameControllerTO.actualPlayer.getName()} ));
		    		break;
		    	}
		    	/* BUILDING SCREEN END */
	
		    	// Getting input from User
		    	
		    	if (!GameState.DICES_THROWN.equals(gameControllerTO.gameState)) {
		    		System.out.println(getMessage("ENTER_ACTION_NUMBER"));
		    	}
			    action = in.next();      
			    System.out.println(getMessage("ENTERED_ACTION", new Object[]{action}));
			    gameControllerTO = null;
			    
		    } while (!"0".equals(action));
		}
	    System.out.println(getMessage("GAME_EXIT"));
	}

	/**
	 * @param chosenPair
	 * @return way number chosen from user
	 */
	protected int getWayNumberFromUser(TwoDicesPair chosenPair, Scanner in) {
		int wayNumber = -1;
		if (PairChoiceInfo.WITHWAYINFO.equals(chosenPair.getPairChoiceInfo())) {
			// Java 7 feature: ARM
			do {
				System.out.print(getMessage("ENTER_WAYNUMBER",
						new Object[] { chosenPair.getFirstPair().getSum(),
								chosenPair.getSecondPair().getSum() }));
				wayNumber = in.nextInt();
				System.out.println(getMessage("WAYNUMBER_ENTERED",
						new Object[] { wayNumber }));
			} while (wayNumber != chosenPair.getFirstPair().getSum()
					&& wayNumber != chosenPair.getSecondPair().getSum());
		}
		return wayNumber;
	}
	/**
	 * 
	 */
	public void changeLocale() {
		if (Locale.GERMAN.equals(locale)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.GERMAN;
		}
	}
	
}
