package de.vt.cantstop.ajax;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import de.vt.cantstop.messageutils.MessageHelper;
import de.vt.cantstop.model.GameState;
import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.service.GameException;
import de.vt.cantstop.service.GameService;


@Controller
public class WelcomeJoinController implements ApplicationContextAware {
	private Log log = LogFactory.getLog(WelcomeJoinController.class);
	private GameService gameService;
	private ApplicationContext ac;

	@Autowired
	public WelcomeJoinController(GameService gameService) {
		this.gameService = gameService;
	}
	
	/**
	 * Shows welcome page with join possibility
	 * @param model
	 * @return 
	 * @throws GameException
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String welcome(Model model, Locale locale) throws GameException {
		log.info("welcome, locale:"+locale);
		model.addAttribute("gameInfo", gameService.getAllGameInformation());
		return "welcomejoin";
	}
	
	/**
	 * Called from welcomejoin.jsp
	 * @param playerName incoming parameter 
	 * @param locale need to give back localized error messages
	 * @return <code>JsonResponse</code> 
	 * @throws GameException
	 */
	@RequestMapping(value="do.addplayerAJAX", method = RequestMethod.POST)
	public @ResponseBody JsonResponse addPlayer(@RequestParam("playerName") String playerName, Locale locale, HttpServletRequest request) throws GameException {
		JsonResponse response = new JsonResponse();
		
		String playerId = "";
		GameTransferObject gameInfo = gameService.getAllGameInformation();
		log.info("Incoming playerName:" + playerName);
		
		if (StringUtils.isEmpty(playerName)) {
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.NAMEISREQUIRED", null, locale)); 
			response.setGto(MessageHelper.decorateWithLocalizedMessage(gameService.getAllGameInformation(), locale, ac));
			return response;
		} 
		
		if (gameInfo.getPlayerList().size() == 4) {
			log.warn("Too many player");
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.TOOMANYPLAYER", null, locale)); 
		} 
		else if (!GameState.INIT.equals(gameInfo.getGameState()) && 
				 !GameState.ENOUGH_PLAYER.equals(gameInfo.getGameState())) {
			log.error("Game have already started, gameState:" + gameInfo.getGameState());
			response.setStatus(JsonResponse.ERROR);
			response.setErrorMessage(ac.getMessage("ERROR.GAMEALREADYSTARTED", null, locale)); 
		} else {
			playerId = gameService.addPlayer(playerName);
			log.info("New player generated with id:" + playerId);
			response.setMethodResult(playerId);
			request.getSession().invalidate();
		    request.getSession();
		}
		response.setGto(MessageHelper.decorateWithLocalizedMessage(gameService.getAllGameInformation(), locale, ac));
		return response;
	}

	/* 
	 * Needed to access localized messages
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}	
}
