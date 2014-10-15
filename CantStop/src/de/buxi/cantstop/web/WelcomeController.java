package de.buxi.cantstop.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

@Controller
@RequestMapping(value = {"","/"})
public class WelcomeController {
	private Log log = LogFactory.getLog(WelcomeController.class);
	private GameService gameService;

	@Autowired
	public WelcomeController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String welcome(Model model) {
		return "welcome";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String submitForm(@RequestParam("playerName") String playerName,
			Model model) throws GameException {
		String playerId = "";
		GameTransferObject gameInfo = gameService.getAllGameInformation();
		
		if (GameState.GAME_FINISHED.equals(gameInfo.getGameState())) {
			// reinitialize the game after it was finished
			gameInfo =gameService.reinitializeGame();
		}
		
		if (gameInfo.getPlayerList().size() == 4) {
			log.warn("Too many player");
			model.addAttribute("errorMsg", "ERROR.TOOMANYPLAYER");
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			return "gameerrorinfo";
		} 
		else if (!GameState.INIT.equals(gameInfo.getGameState())) {
			log.error("Game have already started");
			model.addAttribute("errorMsg", "ERROR.GAMEALREADYSTARTED");
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			return "gameerrorinfo";
		} else {
			playerId = gameService.addPlayer(playerName);
			log.info("New player generated with id:" + playerId);
			model.addAttribute("playerId", playerId);
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			return "waitingforplayer";
		}
	}
}
