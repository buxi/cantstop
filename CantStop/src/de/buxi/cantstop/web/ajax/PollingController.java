package de.buxi.cantstop.web.ajax;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

@Controller
public class PollingController implements ApplicationContextAware {
	private Log log = LogFactory.getLog(PollingController.class);
	private GameService gameService;
	private ApplicationContext ac;
	
	@Autowired
	public PollingController(GameService gameService) {
		this.gameService = gameService;
	}
	
	// 
	/**
	 * method to return all game related information to an ajax polling function
	 * response is returned as JSON, because jackson jars are on classpath
	 * @param model
	 * @return GameTransferObject in JSON format
	 * @throws GameException
	 */
	@RequestMapping(value= "pollingAJAX", method = RequestMethod.POST)
	public @ResponseBody GameTransferObject poll(Model model, HttpServletRequest request) throws GameException {
		//log.debug("polling called");
		GameTransferObject gameInfo = gameService.getAllGameInformation();
		model.addAttribute("gameInfo", gameInfo);
		Locale locale = LocaleContextHolder.getLocale();
		MessageHelper.decorateWithErrorString(gameInfo, locale, ac);
		return gameInfo;
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
