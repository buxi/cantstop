package de.buxi.cantstop.web.ajax;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import de.buxi.cantstop.model.GameTransferObject;

public class MessageHelper {

	public static GameTransferObject decorateWithErrorString(
			GameTransferObject allGameInformation, Locale locale, ApplicationContext ac) {
		String errorMessage = allGameInformation.getErrorMessage();
		if (StringUtils.isNotBlank(errorMessage)) {
			allGameInformation.setErrorMessageString(ac.getMessage(errorMessage, null, locale));	
		}
		return allGameInformation;
	}
}
