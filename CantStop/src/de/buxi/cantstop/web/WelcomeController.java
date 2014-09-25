package de.buxi.cantstop.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.service.GameService;
import de.buxi.cantstop.service.TooManyPlayerException;

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
			Model model) {
		
		try {
			String playerId = "";
			if (gameService.getAllGameInformation().getPlayerList().size()>4) {
				log.warn("Too many player");
			}
			else {
				 playerId = gameService.addPlayer(playerName);
			}
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
			model.addAttribute("newPlayerId", playerId);
			
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "play";
	}
}
