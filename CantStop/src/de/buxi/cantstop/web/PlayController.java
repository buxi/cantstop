package de.buxi.cantstop.web;

import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PlayController {
	private GameService gameService;
	private Log log = LogFactory.getLog(PlayController.class);

	@Autowired
	public PlayController(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "play" })
	public String setupForm(@RequestParam("playerId") String playerId, Model model) throws GameException {
		//log.info("do.gamestart:Incoming playerId:" + playerId);
		model.addAttribute("playerId", playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "play";
	}

	@RequestMapping({ "do.viewgameOld" })
	public String doWaitingForPlayer(Model model) throws GameException {
		log.info("do.viewgame");
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "play";
	}

	@RequestMapping({ "do.startgameOld" })
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
			return "play";
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "play";
	}
	
	@RequestMapping({ "do.waitingforplayerOld" })
	public String doWaitingForPlayer(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		GameTransferObject to = gameService.getAllGameInformation();
		log.debug("do.waitingforplayer:Incoming playerId:" + playerId);
		if (GameState.IN_ROUND.equals(to.getGameState())) {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			model.addAttribute("playerId", playerId);
			return "play";
		}
		else {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			model.addAttribute("playerId", playerId);
			return "waitingforplayer";
		}
		
	}
	
	@RequestMapping({ "do.finishgameOld" })
	public String doFinishGame(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.finishgame:Incoming playerId:" + playerId);
		gameService.finishGame(playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "gameover";
	}

	@RequestMapping({ "do.finishturnOld" })
	public String doFinishTurn(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.finishturn:Incoming playerId:" + playerId);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.finishTurn());
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			model.addAttribute("errorMsg", "ERROR.OTHERPLAYERINTURN");
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}

	@RequestMapping({ "do.throwOld" })
	public String doThrowDices(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.throw: Incoming playerId:" + playerId);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.throwDices());
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			model.addAttribute("errorMsg", "ERROR.OTHERPLAYERINTURN");
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}
	
	@RequestMapping({ "do.executePairOld" })
	public String doExecutePair(@RequestParam("playerId") String playerId,
			@RequestParam("chosenPairId") String chosenPairId, 
			@RequestParam("wayNumber") String wayNumber, 
			Model model) throws GameException {
		log.info("do.throw: Incoming playerId:" + playerId + "; chosenPairId:" + chosenPairId + "; wayNumber:"+wayNumber);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.executePairs(chosenPairId, Integer.valueOf(wayNumber)));
		}
		else {
			log.error("Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
			model.addAttribute("errorMsg", "ERROR.OTHERPLAYERINTURN");
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}
}
