package de.buxi.cantstop.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

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
	public String sumbitForm(@RequestParam("playerName") String playerName,
			Model model) throws GameException {
		String playerId = "";
		if (gameService.getAllGameInformation().getPlayerList().size()>4) {
			log.warn("Too many player");
		}
		else {
			 playerId = gameService.addPlayer(playerName);
			 log.info("New player generated with id:" + playerId);
		}
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		model.addAttribute("playerId", playerId);
		return "play";
	}
}
