package de.vt.cantstop.model;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Transfer Object for last used pair info
 * @author buxi
 *
 */
public class UsedPairInfoTO implements Serializable{
	Log log = LogFactory.getLog(UsedPairInfoTO.class);
	
	private static final long serialVersionUID = -2101635875264162451L;
	private TwoDicesPair chosenPair;
	private int chosenWayNumber;
	private Player player;
	
	/**
	 * @param chosenPair
	 * @param chosenWayNumber
	 * @param player
	 */
	public UsedPairInfoTO(TwoDicesPair chosenPair, int chosenWayNumber,
			Player player) {
		super();
		try{
			//  serializing the chosenPair to store dice states before dices.reset 
			// TODO is it really a good solution ???? 
			//serialize pair
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ObjectOutputStream os = new ObjectOutputStream(bos);
		    os.writeObject(chosenPair);
		    byte[] serializedPair = bos.toByteArray();
		    os.close();
		    ByteArrayInputStream bis = new ByteArrayInputStream(serializedPair);
		    ObjectInputStream oInputStream = new ObjectInputStream(bis);
		    TwoDicesPair deserializedPair= (TwoDicesPair) oInputStream.readObject();			
		    
			this.chosenPair = deserializedPair;
			this.chosenWayNumber = chosenWayNumber;
			this.player = player;
		}
		catch (IOException | ClassNotFoundException e) {
			log.error("should not happen"+e);
		}
	}

	/**
	 * @return the chosenPair
	 */
	public TwoDicesPair getChosenPair() {
		return chosenPair;
	}

	/**
	 * @return the chosenWayNumber
	 */
	public int getChosenWayNumber() {
		return chosenWayNumber;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
}
