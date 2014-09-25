package de.buxi.cantstop.web;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotEnoughPlayerException;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class CantstopController {
	private GameService gameService;

	@Autowired
	public CantstopController(GameService gameService) {
		this.gameService = gameService;
	}

	@RequestMapping(method = RequestMethod.GET, value={"play"})
	public String setupForm(Model model) {
		try {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "play";
	}

	@RequestMapping({"do.gamestart"})
	public String doGameStart(@RequestParam("playerId") String playerId,
			Model model) {
		
		try {
			gameService.startGame();
			model.addAttribute("gameInfo", gameService.startTurn());
			
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotEnoughPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("playerId", playerId);
		return "play";
	}
	
	@RequestMapping({"do.finishgame"})
	public String doFinishGame(@RequestParam("playerId") String playerId,
			Model model) {
		
		try {
			model.addAttribute("gameInfo", gameService.getAllGameInformation());
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("playerId", playerId);
		return "gameover";
	}
	@RequestMapping({"do.finishturn"})
	public String doFinishTurn(@RequestParam("playerId") String playerId,
			Model model) {
		
		try {
			model.addAttribute("gameInfo", gameService.finishTurn());
		} catch (NoMarkerIsAvailableException | RopePointInvalidUsageException
				| NoClimberOnWayException | InvalidClimberMovementException
				| InvalidWayNumberException | DiceNotThrownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("playerId", playerId);
		return "play";
		
	}@RequestMapping({"do.throw"})
	public String doThrowDices(@RequestParam("playerId") String playerId,
			Model model) {
		
		try {
			model.addAttribute("gameInfo", gameService.throwDices());
		} catch (DiceNotThrownException | InvalidWayNumberException | NoMarkerIsAvailableException | RopePointInvalidUsageException | NoClimberOnWayException | InvalidClimberMovementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("playerId", playerId);
		return "play";
	}
}
