package de.buxi.cantstop.utils;

import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationContext;

import de.buxi.cantstop.model.GameMessage;
import de.buxi.cantstop.model.GameTransferObject;

public class MessageHelper {

	public static GameTransferObject decorateWithLocalizedMessage(
			GameTransferObject allGameInformation, Locale locale, ApplicationContext ac) {
		List<GameMessage> messages = allGameInformation.getMessages();
		for (GameMessage gameMessage : messages) {
			gameMessage.setLocalizedMessage(ac.getMessage(gameMessage.getMessage(), null, locale));	
		}
		return allGameInformation;
	}
}
