package de.buxi.cantstop.web.ajax;

import java.util.Locale;

import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class PlayControllerAJAX implements ApplicationContextAware {
	private GameService gameService;
	private Log log = LogFactory.getLog(PlayControllerAJAX.class);
	private ApplicationContext ac;

	@Autowired
	public PlayControllerAJAX(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "playAJAX" })
	public String setupForm(@RequestParam("playerId") String playerId, Model model) throws GameException {
		log.info("setupForm:Incoming playerId:" + playerId);
		model.addAttribute("playerId", playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "playAJAX";
	}

	/**
	 * Called when enough player joined to the game
	 * Called from: welcomejoin.jsp
	 * @param playerId
	 * @param model
	 * @return
	 * @throws GameException
	 */
	@RequestMapping(value= "do.startgame", method = RequestMethod.POST)
	public String doStartGame(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		GameTransferObject to = gameService.getAllGameInformation();
		log.debug("do.startgame:Incoming playerId:" + playerId);
		
		if (GameState.ENOUGH_PLAYER.equals(to.getGameState())) {
			gameService.startGame();
			log.info("do.gamestart:Incoming playerId:" + playerId);
			model.addAttribute("gameInfo", gameService.startTurn());
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			model.addAttribute("playerId", playerId);
			return "playAJAX";
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "playAJAX";
	}
	
	/**
	 * Finishes the game and goes to an gameover.jsp page
	 * Called from: playAJAX.jsp
	 * @param playerId initiator of the action
	 * @param model
	 * @return
	 * @throws GameException
	 */
	@RequestMapping(value= "do.finishgame", method = RequestMethod.POST)
	public String doFinishGame(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.finishgame:Incoming playerId:" + playerId);
		gameService.finishGame(playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "gameover";
	}
	
	@RequestMapping({ "do.showGameOver" })
	public String doShowGameOver(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.showgameover:Incoming playerId:" + playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "gameover";
	}
	
	/**
	 * Finishes the game round
	 * Called from: playAJAX.jsp
	 * @param playerId
	 * @param model
	 * @param locale
	 * @return
	 * @throws GameException
	 */
	@RequestMapping(value= "do.finishturnAJAX", method = RequestMethod.POST)
	public @ResponseBody JsonResponse doFinishTurn(@RequestParam("playerId") String playerId,
			 Locale locale) throws GameException {
		log.info("do.finishturnAJAX:Incoming playerId:" + playerId);
		
		JsonResponse response = new JsonResponse();
		
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			gameService.finishTurn();
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.OTHERPLAYERINTURN", null, locale)); 
		}
				
		response.setGto(gameService.getAllGameInformation());
		return response;
	}

	@RequestMapping(value= "do.throwAJAX", method = RequestMethod.POST)
	public @ResponseBody JsonResponse doThrowDices(@RequestParam("playerId") String playerId, Locale locale) throws GameException {
		log.info("do.throwAJAX: Incoming playerId:" + playerId);
		
		JsonResponse response = new JsonResponse();
		
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			gameService.throwDices();
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.OTHERPLAYERINTURN", null, locale)); 
		}
				
		response.setGto(gameService.getAllGameInformation());
		return response;
	}
	
	@RequestMapping(value= "do.executePairAJAX", method = RequestMethod.POST)
	public @ResponseBody JsonResponse doExecutePair(@RequestParam("playerId") String playerId,
			@RequestParam("chosenPairId") String chosenPairId, 
			@RequestParam("wayNumber") String wayNumber, 
			Locale locale) throws GameException {
		log.info("do.executePairAJAX: Incoming playerId:" + playerId + "; chosenPairId:" + chosenPairId + "; wayNumber:"+wayNumber);
		
		JsonResponse response = new JsonResponse();
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			gameService.executePairs(chosenPairId, Integer.valueOf(wayNumber));
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.OTHERPLAYERINTURN", null, locale));
		}
		response.setGto(MessageHelper.decorateWithErrorString(gameService.getAllGameInformation(), locale, ac));
		return response;
	}
	
	/* 
	 * Needed to access localized messages
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}	
}
