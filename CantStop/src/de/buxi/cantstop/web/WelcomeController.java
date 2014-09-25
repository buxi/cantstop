package de.buxi.cantstop.web;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
@RequestMapping(value = {"","/"})
public class WelcomeController {
	private Log log = LogFactory.getLog(WelcomeController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String welcome(Model model) {
		Date today = new Date();
		log.debug("welcome called");
		model.addAttribute("today", today);
		return "welcome";
	}
}
