package de.buxi.cantstop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.buxi.cantstop.service.TooManyPlayerException;

/**
 * It controls the whole game
 * @author buxi
 *
 */
public class GameController implements Serializable{
			
	Log log = LogFactory.getLog(GameController.class);
	
	private static final long serialVersionUID = -6919670618517317954L;
	public static final int DEFAULT_FIRST_PLAYER_NUM = 0;
	public static final int MINIMUM_PLAYER_NUMBER = 2;
	public static final int MAXIMUM_PLAYER_NUMBER = 4;
	private Map<Color, Player> playerMap;
	private List<Player> playersInOrder;  
	private Board board;
	private DiceManager diceManager;
	private Map<Color, Collection<Marker>> allMarkers;
	private List<Climber> climbers;
	private int actualPlayerNumber;
	private GameState gameState;
	private Collection<TwoDicesPair> wrongPairs;
	private String errorMessage;

	private UsedPairInfoTO lastUsedPairInfo;
	
	/**
	 * @return the lastThrow
	 */
	public List<Dice> getLastThrow() {
		return diceManager.getLastThrow();
	}

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
		this.playersInOrder = new ArrayList<Player>(4);
		this.board = board;
		this.diceManager = diceManager;
		this.allMarkers = markers;
		this.climbers = climbers;
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
	public void determinePlayerOrderStandard() {
		//tested in GameControllerSetupTest
		this.playersInOrder = new ArrayList<Player>(4);
		Set<Entry<Color, Player>> players = playerMap.entrySet();
		int i = 0;
		for (Entry<Color, Player> entry : players) {
			playersInOrder.add(entry.getValue());
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
				throw new PlayerNotFoundException(markerColor + " Player not found");
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
	 * @return the playersInOrder
	 */
	public List<Player> getPlayersInOrder() {
		return playersInOrder;
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
	 * gives the free climbers to the actual player
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
		this.lastUsedPairInfo = null;
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
		this.errorMessage = null;
		endGameturn();
		return this.doGetTransferObject();
	}

	/**
	 * @throws NoMarkerIsAvailableException
	 * @throws RopePointInvalidUsageException
	 * @throws InvalidClimberMovementException
	 * @throws NoClimberOnWayException
	 * @throws InvalidWayNumberException
	 */
	protected void endGameturn() throws NoMarkerIsAvailableException,
			RopePointInvalidUsageException, InvalidClimberMovementException,
			NoClimberOnWayException, InvalidWayNumberException {
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
				return;
			}
		}
		
		this.nextPlayer();
		distributeFreeClimbers();
		// notify diceManager the throw was used, needs to be reset
		this.diceManager.reset();
		this.gameState = GameState.IN_ROUND;
	}
	
	/**
	 * choose the next player and gives diceManager and climbers
	 */
	protected void nextPlayer() {
		Player oldPlayer = getActualPlayer(); 
		this.actualPlayerNumber++;
		if (actualPlayerNumber > playersInOrder.size()-1) {
			actualPlayerNumber = 0;
		}
		diceManager.giveDices(getActualPlayer());
		getActualPlayer().addClimbers(oldPlayer.removeClimbers());
	}

	/**
	 * @return actual Player
	 */
	public Player getActualPlayer() {
		return playersInOrder.get(this.actualPlayerNumber);
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
	 * @throws NotEnoughPlayerException 
	 */
	public GameTransferObject doGameStart() throws  InvalidWayNumberException, NotEnoughPlayerException, DiceNotThrownException {
		checkGameStatus(Arrays.asList(GameState.INIT));
		if (this.playerMap.keySet().size() < GameController.MINIMUM_PLAYER_NUMBER) {
			throw new NotEnoughPlayerException("Not enough player");
		}
		determineFirstPlayer();
		determinePlayerOrderStandard(); 
		// distributes Markers
		Map<Color, Collection<Marker>> playerMarkers = new HashMap<Color, Collection<Marker>>();
		for (Player player: playersInOrder) {
			playerMarkers.put(player.getColor(), this.allMarkers.get(player.getColor()));
			this.allMarkers.remove(player.getColor());
		}
		distributeMarkers(playerMarkers);

		this.gameState = GameState.IN_GAME;
		 
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
		return diceManager.getDicesClone();
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
	
	public Map<String, TwoDicesPair> getPairsToChooseWithId() throws DiceNotThrownException, InvalidWayNumberException {
		Map<String, TwoDicesPair> result = new HashMap<String, TwoDicesPair>();
		List<TwoDicesPair> possiblePairs = this.getPairsToChoose();
		for (TwoDicesPair twoDicesPair : possiblePairs) {
			result.put(twoDicesPair.generateId(), twoDicesPair);
		}
		return result;
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
		this.errorMessage = null;
		diceManager.throwAllDices();
		this.gameState=GameState.DICES_THROWN;
		this.wrongPairs = new ArrayList<TwoDicesPair>(3);
		// checks pairs if they are choosable
		if (!isTherePossiblePair(this.getPossiblePairs())) {
			// GameRound must be finished
			this.gameState=GameState.NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED;
			this.errorMessage = "STATE_NO_OTHER_PAIR_AVAILABLE_ROUND_FINISHED";
			log.info("turn finished, no available pair");
			endGameturn();
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
	 * @return true is there is minimum one CHOOSABLE or WITHWAYINFO pair
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
	 * Executing the chosen pair(with chosen wayNumber) on board
	 * @param wayNumber if a way should be chosen
	 * @return transfer object
	 */
	public GameTransferObject doExecutePairs(TwoDicesPair chosenPair, int wayNumber) throws DiceNotThrownException, RopePointInvalidUsageException, NotAvailableClimberException, InvalidWayNumberException, InvalidClimberMovementException, NoMarkerIsAvailableException, NullClimberException, NoClimberOnWayException {
		checkGameStatus(Arrays.asList(GameState.DICES_THROWN));
		this.errorMessage = null;
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
				log.warn(e.getMessage());
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
				log.warn(e.getMessage());

			}
			try {
				choseWay(player, way2);
			} catch (NotAvailableClimberException e) {
				log.warn(e.getMessage());
			}
		}
		
		// minimum one Pair successful
		this.gameState=GameState.IN_ROUND;
		this.lastUsedPairInfo = new UsedPairInfoTO(chosenPair, chosenWayNumber, player);
		// notify diceManager the throw was used, needs to be reset
		this.diceManager.reset();
		errorMessage = "STATE_PAIR_USED";
	}

	/**
	 * Places oder moves climber on the way
	 * if there is no marker on the way then places the climber position 0
	 * if there is a marker on the way then places the climber after the marker 
	 * if there is already a climber on the way then moves it forward (also in the hut)
	 * @param player
	 * @param way
	 * @throws NotAvailableClimberException
	 */
	protected void choseWay(Player player, Way way)
			throws RopePointInvalidUsageException,
			NotAvailableClimberException, InvalidClimberMovementException, NullClimberException {
		// fixing bug2
		if (way.isFree() && !way.isClimberInHut()) {
			if (!way.isClimberOnRope() ) {
				// no climber on the way
				if (way.isMarkerOnRope(player.getColor())) {
					way.placeClimberAfterMarker(player.getOneClimber(), player.getColor());
				} else {
					way.placeClimberOntoFirstRopePoint(player.getOneClimber());	
				}
				
			} else {
				// climber on the way
				way.moveClimber();
			}
		}
	}
	
	

	
	/**
	 * Adds a player to the player list
	 * @param playerName
	 * @return playerId (currently the orderId) 
	 * @throws TooManyPlayerException
	 */
	public String doAddPlayer(String playerName) throws TooManyPlayerException {
		// TODO id should be generated in a better way (for example with Spring)
		Color playerColor = getAFreeColor();
		int playerId = playerMap.keySet().size();
		Player newPlayer = new Player(playerId, playerName, playerColor);
		playerMap.put(playerColor, newPlayer);
		determinePlayerOrderStandard();
		actualPlayerNumber = playerId;
		return Integer.toString(playerId);
	}

	/**
	 * used in game init phase
	 * returns a free color, which is not allocated to any players 
	 * @return a not allocated Color
	 * @throws TooManyPlayerException when there is no free Color left 
	 */
	protected Color getAFreeColor() throws TooManyPlayerException {
		Collection<Color> remainingColorSet = new HashSet<Color>(this.allMarkers.keySet());
		remainingColorSet.removeAll(this.getPlayerMap().keySet());
		if (remainingColorSet.isEmpty()) {
			throw new TooManyPlayerException("No available color");
		}
		Color[] remainingColors = remainingColorSet.toArray(new Color[0]);
		Color playerColor = remainingColors[0];
		return playerColor;
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
		to.actualPlayer = null;
		to.actualPlayerNumber = -1;
		if (!GameState.INIT.equals(gameState) ) {
			to.actualPlayer = this.getActualPlayer();
			to.actualPlayerNumber = this.getActualPlayerNumber();
		}
		to.boardDisplay = this.getBoard().display();
		to.playerList = this.getPlayersInOrder();
		to.errorMessage = this.errorMessage;
		to.possiblePairs = null;
		to.choosablePairsWithId = null;
		to.dices = null;
		to.lastThrow = this.getLastThrow();
		to.lastUsedPairInfo = this.lastUsedPairInfo;
		if (GameState.DICES_THROWN.equals(gameState) ) {
			to.possiblePairs = this.getPossiblePairs();
			to.choosablePairsWithId = this.getPairsToChooseWithId();
			to.dices = this.getDices();
		}
		to.board = this.getBoard();
		return to;
	}

	/**
	 * removes a player from game
	 * @param playerId which player has the game finished 
	 * @return 
	 * @throws InvalidWayNumberException 
	 * @throws DiceNotThrownException 
	 */
	public GameTransferObject doEndGame(String playerId) throws DiceNotThrownException, InvalidWayNumberException {
		this.gameState = GameState.GAME_FINISHED;
		log.info("game was finished by player:" + playerId);
		this.errorMessage = "GAMEWASFINISHED";
		return this.doGetTransferObject();		
	}
}
