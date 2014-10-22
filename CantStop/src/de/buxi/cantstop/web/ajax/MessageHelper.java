package de.buxi.cantstop.web.ajax;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

import de.buxi.cantstop.model.GameTransferObject;

public class MessageHelper {

	public static GameTransferObject decorateWithErrorString(
			GameTransferObject allGameInformation, Locale locale, ApplicationContext ac) {
		 allGameInformation.setErrorMessageString(ac.getMessage(allGameInformation.getErrorMessage(), null, locale));
		 return allGameInformation;
	}
	
}
