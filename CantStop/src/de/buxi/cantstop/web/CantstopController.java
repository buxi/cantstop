package de.buxi.cantstop.web;

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
	private int queries;

	@Autowired
	public CantstopController(GameService gameService) {
		this.gameService = gameService;
		this.queries = 0;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupForm() {
	}

	@RequestMapping(method = RequestMethod.POST)
	public String sumbitForm(@RequestParam("courtName") String courtName,
			Model model) {
		//model.addAttribute("reservations", reservations);
		model.addAttribute("queries", queries);
		return "reservationQuery";
	}
}
