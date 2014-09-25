package de.buxi.cantstop.web;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/play")
public class CantstopController {
	private GameService gameService;

	@Autowired
	public CantstopController(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupForm(Model model) {
		try {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String sumbitForm(@RequestParam("courtName") String courtName,
			Model model) {
		
		try {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "play";
	}
}
