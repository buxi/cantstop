package de.buxi.cantstop.web;

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
public class CantstopController {
	private GameService gameService;
	private Log log = LogFactory.getLog(CantstopController.class);

	@Autowired
	public CantstopController(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "play" })
	public String setupForm(@RequestParam("playerId") String playerId,Model model) throws GameException {
		log.info("do.gamestart:Incoming playerId:" + playerId);
		model.addAttribute("playerId", playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "play";
	}

	@RequestMapping({ "do.gamestart" })
	public String doGameStart(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		gameService.startGame();
		log.info("do.gamestart:Incoming playerId:" + playerId);
		model.addAttribute("gameInfo", gameService.startTurn());
		model.addAttribute("playerId", playerId);
		return "play";
	}

	@RequestMapping({ "do.finishgame" })
	public String doFinishGame(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.finishgame:Incoming playerId:" + playerId);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "gameover";
	}

	@RequestMapping({ "do.finishturn" })
	public String doFinishTurn(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.finishturn:Incoming playerId:" + playerId);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.finishTurn());
		}
		else {
			model.addAttribute("errorMsg", "Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}

	@RequestMapping({ "do.throw" })
	public String doThrowDices(@RequestParam("playerId") String playerId,
			Model model) throws GameException {
		log.info("do.throw: Incoming playerId:" + playerId);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.throwDices());
		}
		else {
			model.addAttribute("errorMsg", "Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}
	
	@RequestMapping({ "do.executePair" })
	public String doExecutePair(@RequestParam("playerId") String playerId,
			@RequestParam("chosenPairId") String chosenPairId, 
			@RequestParam("wayNumber") String wayNumber, 
			Model model) throws GameException {
		log.info("do.throw: Incoming playerId:" + playerId + "; chosenPairId:" + chosenPairId + "; wayNumber:"+wayNumber);
		if (gameService.getAllGameInformation().getActualPlayerId().equals(playerId)) {
			model.addAttribute("gameInfo", gameService.executePairs(chosenPairId, Integer.valueOf(wayNumber)));
		}
		else {
			model.addAttribute("errorMsg", "Other player is in turn:" + gameService.getAllGameInformation().actualPlayer.getName());
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}
}