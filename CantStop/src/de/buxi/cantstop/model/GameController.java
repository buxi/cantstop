package de.buxi.cantstop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Dieses Objekt kontrolliert das gesamte Game
 * Regeln sind hier geprüft und durchgesetzt
 * Es ist ein Singleton
 * @author buxi
 *
 */
public class GameController implements Serializable{
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -6919670618517317954L;
	public static final int DEFAULT_FIRST_PLAYER_NUM = 0;
	private Map<Color, Player> playerMap;
	private List<Player> playerOrder;  
	private Board board;
	private DiceManager diceManager;
	private Map<Color, Collection<Marker>> allMarkers;
	private List<Climber> climbers;
	private int actualPlayerNumber;
	private GameState gameState;
	private Collection<TwoDicesPair> wrongPairs;
	private String errorMessage;
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public GameController(Map<Color, Player>players, Board board,
			DiceManager diceManager,
			Map<Color, Collection<Marker>> markers,
			List<Climber> climbers) {
		super();
		this.playerMap = players;
		this.playerOrder = new ArrayList<Player>(4);
		determineFirstPlayer();
		determinePlayerOrderStandard();  
		//this.playerFactory = playerFactory;
		this.board = board;
		this.diceManager = diceManager;
		this.allMarkers = markers;
		this.climbers = climbers;
		
		// distributes Markers
		Map<Color, Collection<Marker>> playerMarkers = new HashMap<Color, Collection<Marker>>();
		for (Player player: playerOrder) {
			playerMarkers.put(player.getColor(), this.allMarkers.get(player.getColor()));
			this.allMarkers.remove(player.getColor());
		}
		distributeMarkers(playerMarkers);

		this.gameState = GameState.INIT;
		this.wrongPairs = new ArrayList<TwoDicesPair>(3);
	}

	/**
	 * @return the wrongPairs
	 */
	public Collection<TwoDicesPair> getWrongPairs() {
		return wrongPairs;
	}
	
	/**
	 * @return the gameState
	 */
	public GameState getGameStatus() {
		return gameState;
	}

	/**
	 * place the players in standard order
	 */
	private void determinePlayerOrderStandard() {
		//tested in GameControllerSetupTest
		Set<Entry<Color, Player>> players = playerMap.entrySet();
		int i = 0;
		for (Entry<Color, Player> entry : players) {
			playerOrder.add(entry.getValue());
			entry.getValue().setOrder(i);
			i++; 
		}
	}

	/**
	 * distribute markers onto players, player color and marker color will be the same
	 * @param markers to distribute
	 */
	protected void distributeMarkers(Map<Color, Collection<Marker>> markers) {
		for (Color markerColor : markers.keySet()) {
			Player player = this.playerMap.get(markerColor);
			if (player == null ) {
				throw new PlayerNotFoundException(markerColor + " Player no found");
			}
			player.addMarkers(markers.get(player.getColor()));
		}
	}
	
	/**
	 * @return the playerMap
	 */
	public Map<Color, Player> getPlayerMap() {
		return playerMap;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return the diceManager
	 */
	public DiceManager getDiceManager() {
		return diceManager;
	}

	/**
	 * @return the allMarkers
	 */
	public Map<Color, Collection<Marker>> getAllMarkers() {
		return allMarkers;
	}

	/**
	 * @return the climbers
	 */
	public List<Climber> getClimbers() {
		return climbers;
	}
	
	/**
	 * @return the playerOrder
	 */
	public List<Player> getPlayerInOrder() {
		return playerOrder;
	}
	
	/**
	 * @return the actualPlayerNumber
	 */
	public int getActualPlayerNumber() {
		return actualPlayerNumber;
	}
	
	public Player getPlayer(Color color) {
		Player player = this.playerMap.get(color);
		if (player == null ) {
			throw new PlayerNotFoundException(color + " Player not found");
		}
		return player;
	}
	
	/**
	 * gives the free climbers to the aktual player
	 */
	protected void distributeFreeClimbers() {
		this.getActualPlayer().addClimbers(this.getClimbers());
		this.climbers.clear();
	}
	
	/**
	 * starts a new round of the game
	 * distributes the free climbers to the aktual player
	 * @return 
	 * @throws InvalidWayNumberException 
	 * @throws DiceNotThrownException 
	 */
	public GameTransferObject doStartGameTurn() throws DiceNotThrownException, InvalidWayNumberException {
		checkGameStatus(Arrays.asList(GameState.IN_GAME));
		distributeFreeClimbers();
		this.gameState = GameState.IN_ROUND;
		return this.doGetTransferObject();
	}
	
	/**
	 * @return 
	 * @throws RopePointInvalidUsageException 
	 * @throws NoMarkerIsAvailableException 
	 * @throws NoClimberOnWayException 
	 * @throws InvalidClimberMovementException 
	 * @throws InvalidWayNumberException 
	 * @throws DiceNotThrownException 
	 */
	public GameTransferObject doEndGameTurn() throws NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidWayNumberException, DiceNotThrownException {
		checkGameStatus(Arrays.asList(GameState.IN_ROUND, GameState.NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED));
		// marks the climbers
		Player actualPlayer = getActualPlayer();
		if (GameState.NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED.equals(gameState)) {
			this.climbers = board.removeClimbers();
		}
		else {
			this.climbers = board.markClimbers(actualPlayer);
			Collection<Hut> usedHuts = board.getBlockedHuts(actualPlayer);
			//remove other markers from the rope 
			Map<Color,Collection<Marker>> freeMarkers = board.removeMarkersFromBlockedWays(usedHuts);
			// free Markers back to player
			distributeMarkers(freeMarkers);
			// TODO GameEnde Condition
			if (usedHuts.size() == 3) {
				this.gameState = GameState.GAME_WIN;
				this.errorMessage="STATE_GAME_WIN";
				return this.doGetTransferObject();
			}
		}
		
		this.nextPlayer();
		distributeFreeClimbers();
		this.gameState = GameState.IN_ROUND;
		return this.doGetTransferObject();
	}
	
	/**
	 * choose the next player and gives diceManager and climbers
	 */
	protected void nextPlayer() {
		Player oldPlayer = getActualPlayer(); 
		this.actualPlayerNumber++;
		if (actualPlayerNumber > playerOrder.size()-1) {
			actualPlayerNumber = 0;
		}
		diceManager.giveDices(getActualPlayer());
		getActualPlayer().addClimbers(oldPlayer.removeClimbers());
	}

	/**
	 * @return actual Player
	 */
	public Player getActualPlayer() {
		return playerOrder.get(this.actualPlayerNumber);
	}
	
	/**
	 * @param expectedStatuses with or
	 */
	private void checkGameStatus(List<GameState> expectedStatuses) {
		if (!expectedStatuses.contains(this.getGameStatus())) {
			throw new InvalidGameStateException("actual status:"+this.getGameStatus()+" expected statuses:"+expectedStatuses);
		}
	}
	/**
	 * determine first player and gives DiceManager
	 * @return 
	 * @throws InvalidWayNumberException 
	 * @throws DiceNotThrownException 
	 */
	public GameTransferObject doGameStart() throws DiceNotThrownException, InvalidWayNumberException {
		checkGameStatus(Arrays.asList(GameState.INIT));
		this.gameState = GameState.IN_GAME;
		determineFirstPlayer();
		diceManager.giveDices(getActualPlayer());
		return this.doGetTransferObject();
	}

	

	/**
	 * DEFAULT_FIRST_PLAYER_NUM = 0
	 */
	protected void determineFirstPlayer() {
		this.actualPlayerNumber = DEFAULT_FIRST_PLAYER_NUM;
	}
	
	public Collection<Dice> getDices() {
		DiceManager diceManager = getDiceManager();
		return diceManager.getDices();
	}

	public List<TwoDicesPair> getPossiblePairs() throws DiceNotThrownException, InvalidWayNumberException {
		List<TwoDicesPair> possiblePairs = diceManager.getAllPossiblePairs();
		// decorate with choosable Information
		return addChoosableInformation(possiblePairs);
	}
	
	//TODO UNITTEST ?
	public List<TwoDicesPair> getPairsToChoose() throws DiceNotThrownException, InvalidWayNumberException {
		List<TwoDicesPair> possiblePairs = this.getPossiblePairs();
		List<TwoDicesPair> choosablePairs = new ArrayList<TwoDicesPair>();
		for (TwoDicesPair twoDicesPair : possiblePairs) {
			if (!PairChoiceInfo.NOTCHOOSABLE.equals(twoDicesPair.getPairChoiceInfo())) {
				choosablePairs.add(twoDicesPair);
			}
		}
		return choosablePairs;
	}
	
	/**
	 * Decorate the pairs with PairChoiceInfo
	 * @param possiblePairs
	 * @return
	 * @throws InvalidWayNumberException
	 * @throws DiceNotThrownException
	 */
	protected List<TwoDicesPair> addChoosableInformation(
			List<TwoDicesPair> possiblePairs) throws InvalidWayNumberException, DiceNotThrownException {
		Player player = this.getActualPlayer(); 
		for (TwoDicesPair twoDicePair : possiblePairs) {
			Way way1 = board.getWayByNumber(twoDicePair.getFirstSum());
			Way way2 = board.getWayByNumber(twoDicePair.getSecondSum());
			
			// blocked Ways can not be chosen
			if (way1.isBlocked() && way2.isBlocked() ||
				way1.isBlocked() && way2.isClimberInHut() ||
				way2.isBlocked() && way1.isClimberInHut() ||
				way1.isClimberInHut() && way2.isClimberInHut()
				) {
				twoDicePair.setPairChoiceInfo(PairChoiceInfo.NOTCHOOSABLE);
				continue;
			} 
			// all climber on the board
			if (player.getClimbers().size() == 0) {
				if ( !way1.isClimberOnRope() && !way2.isClimberOnRope()) {
					twoDicePair.setPairChoiceInfo(PairChoiceInfo.NOTCHOOSABLE);
					continue;
				}
				if (way1.isClimberInHut() && !way2.isClimberOnRope()) {
					twoDicePair.setPairChoiceInfo(PairChoiceInfo.NOTCHOOSABLE);
					continue;
				}
				if (way2.isClimberInHut() && !way1.isClimberOnRope()) {
					twoDicePair.setPairChoiceInfo(PairChoiceInfo.NOTCHOOSABLE);
					continue;
				}
			}
			// one climber in the hand
			if (player.getClimbers().size() == 1) {
					// WITHWAYINFO case
					/*  if already 2 Climbers were placed and
					    the pair's sum results no Way, where are the climbers */
					if (way1.isFree() && way2.isFree() && 
						!way1.isClimberOnRope() && !way2.isClimberOnRope() &&
						!way1.isClimberInHut() && !way2.isClimberInHut()) {
						if (way1.getNumber() == way2.getNumber()) {
							twoDicePair.setPairChoiceInfo(PairChoiceInfo.CHOOSABLE);
						}else {
							twoDicePair.setPairChoiceInfo(PairChoiceInfo.WITHWAYINFO);
						}
						continue;
					}
			}
			twoDicePair.setPairChoiceInfo(PairChoiceInfo.CHOOSABLE);
		}
		return possiblePairs;
	}

	/**
	 * @return 
	 * @throws InvalidWayNumberException 
	 * @throws DiceNotThrownException 
	 * @throws InvalidClimberMovementException 
	 * @throws NoClimberOnWayException 
	 * @throws RopePointInvalidUsageException 
	 * @throws NoMarkerIsAvailableException 
	 */
	public GameTransferObject doThrowDices() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException {
		checkGameStatus(Arrays.asList(GameState.IN_ROUND));
		this.errorMessage = "";
		diceManager.throwAllDices();
		this.gameState=GameState.DICES_THROWN;
		this.wrongPairs = new ArrayList<TwoDicesPair>(3);
		// checks pairs if they are choosable
		if (!isTherePossiblePair(this.getPossiblePairs())) {
			// GameRound must be finished
			this.gameState=GameState.NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED;
			this.errorMessage = "STATE_NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED";
			doEndGameTurn();
		}
		return this.doGetTransferObject();
	}
	
	/**
	 * JUST FOR TESTING
	 * same as doThrow() but we can set a DiceManager 
	 * @param diceManager 
	 */
	protected void testDoThrow(DiceManager diceManager) throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException {
		this.setDiceManager(diceManager);
		doThrowDices();
	}
	/**
	 * JUST FOR TESTING
	 * @param diceManager the diceManager to set
	 */
	private void setDiceManager(DiceManager diceManager) {
		this.diceManager = diceManager;
	}

	/**
	 * @return true is tehere is minimum one CHOOSABLE or WITHWAYINFO pair
	 * @throws DiceNotThrownException
	 * @throws InvalidWayNumberException
	 */
	protected boolean isTherePossiblePair(List<TwoDicesPair> possiblePairs)
			throws DiceNotThrownException, InvalidWayNumberException {
		boolean isThereChoosable = false;;
		for (TwoDicesPair twoDicePair : possiblePairs) {
			if (PairChoiceInfo.CHOOSABLE.equals(twoDicePair.getPairChoiceInfo()) ||
				PairChoiceInfo.WITHWAYINFO.equals(twoDicePair.getPairChoiceInfo())) {
				isThereChoosable = true;
			}
		}
		return isThereChoosable;
	}
	
	/**
	 * TODO UNITTEST
	 * @param wayNumber 
	 * @return 
	 */
	public GameTransferObject doExecutePairs(TwoDicesPair chosenPair, int wayNumber) throws DiceNotThrownException, RopePointInvalidUsageException, NotAvailableClimberException, InvalidWayNumberException, InvalidClimberMovementException, NoMarkerIsAvailableException, NullClimberException, NoClimberOnWayException {
		checkGameStatus(Arrays.asList(GameState.DICES_THROWN));
		this.errorMessage = "";
		if (!this.getPossiblePairs().contains(chosenPair)) {
			throw new InvalidPairsException("Pair:" + chosenPair + ", wayNumber:" + wayNumber);
		}
		if (PairChoiceInfo.NOTCHOOSABLE.equals(chosenPair.getPairChoiceInfo())) {
			throw new InvalidPairsException("Pair is not choosable:" + chosenPair + ", wayNumber:" + wayNumber);
		}  
		if (PairChoiceInfo.WITHWAYINFO.equals(chosenPair.getPairChoiceInfo()) &&
			(wayNumber != chosenPair.getFirstSum() && wayNumber != chosenPair.getSecondSum())) {
				throw new InvalidWayNumberException("Pair:" + chosenPair + ", wayNumber:" + wayNumber);
		}

		pairExecute(chosenPair, wayNumber, getActualPlayer());
		return this.doGetTransferObject();
	}
	/**
	 * moves climber
	 * @param chosenWayNumber 
	 * @param player
	 * @throws NotAvailableClimberException 
	 * @throws InvalidWayNumberException 
	 * @throws InvalidClimberMovementException 
	 * @throws NoMarkerIsAvailableException 
	 * @throws NullClimberException 
	 * @throws NoClimberOnWayException 
	 * @throws IllegalWayWahlenException
	 */
	protected void pairExecute(TwoDicesPair chosenPair, int chosenWayNumber, Player player) throws DiceNotThrownException, RopePointInvalidUsageException, InvalidWayNumberException, InvalidClimberMovementException, NoMarkerIsAvailableException, NullClimberException, NoClimberOnWayException {
		if (chosenWayNumber>0) {
			Way way = board.getWayByNumber(chosenWayNumber);
			try {
				choseWay(player, way);
			} catch (NotAvailableClimberException e) {
				System.out.println(e.toString());
			}
		}
		else {
			int wayNumber1 = chosenPair.getFirstSum();
			int wayNumber2 = chosenPair.getSecondSum();
			Way way1 = board.getWayByNumber(wayNumber1);
			Way way2= board.getWayByNumber(wayNumber2);
			try {
				choseWay(player, way1);
			} catch (NotAvailableClimberException e) {
				System.out.println(e.toString());
			}
			try {
				choseWay(player, way2);
			} catch (NotAvailableClimberException e) {
				//TODO Log error
				System.out.println(e.toString());
			}
		}
		
		// minimum one Pair successful
		this.gameState=GameState.IN_ROUND;
		errorMessage = "STATE_PAIR_USED";
	}

	/**
	 * Places oder moves climber on the way
	 * if there is no marker on the way then places the climber position 0
	 * if there is a marker on the way then places the climber after the marker 
	 * if there is already a climber on the way then moves it forward (also in the hut)
	 * @param player
	 * @param way1
	 * @throws NotAvailableClimberException
	 */
	protected void choseWay(Player player, Way way1)
			throws RopePointInvalidUsageException,
			NotAvailableClimberException, InvalidClimberMovementException, NullClimberException {
		if (way1.isFree()) {
			if (!way1.isClimberOnRope()) {
				// no climber the the way
				if (way1.isMarkerOnRope(player.getColor())) {
					way1.placeClimberAfterMarker(player.getOneClimber(), player.getColor());
				} else {
					way1.placeClimberOntoFirstRopePoint(player.getOneClimber());	
				}
				
			} else {
				// climber on the way
				way1.moveClimber();
			}
		}
	}
	
	/**
	 * generates a Transfer Object for client apps
	 * @return new transfer object
	 * @throws DiceNotThrownException
	 * @throws InvalidWayNumberException 
	 */
	public GameTransferObject doGetTransferObject() throws DiceNotThrownException, InvalidWayNumberException {
		GameTransferObject to = new GameTransferObject();
		to.gameState = this.gameState;
		to.actualPlayer = this.getActualPlayer();
		to.boardDisplay = this.getBoard().display();
		to.actualPlayerNumber = this.getActualPlayerNumber();
		to.playerList = this.getPlayerInOrder();
		to.errorMessage = this.errorMessage;
		to.possiblePairs = null;
		to.dices = null;
		if (GameState.DICES_THROWN.equals(gameState) ) {
			to.possiblePairs = this.getPossiblePairs();
			to.choosablePairs = this.getPairsToChoose();
			to.dices = this.getDices();
		}
		return to;
	}
}
