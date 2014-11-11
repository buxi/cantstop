package de.vt.cantstop.model;


import java.io.Serializable;

public class Climber implements Serializable{
	private static final long serialVersionUID = 4637305808600310379L;
	private int id; /* fake property, otherwise JSON serialization fails with exception
	org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: No serializer found for class de.buxi.cantstop.model.Climber and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS) ) (through reference chain: de.buxi.cantstop.model.GameTransferObject["actualPlayer"]->de.buxi.cantstop.model.Player["climbers"]->java.util.ArrayList[0]); nested exception is org.codehaus.jackson.map.JsonMappingException: No serializer found for class de.buxi.cantstop.model.Climber and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS) ) (through reference chain: de.buxi.cantstop.model.GameTransferObject["actualPlayer"]->de.buxi.cantstop.model.Player["climbers"]->java.util.ArrayList[0])
	*/
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Climber []");
		return builder.toString();
	}

	public String display() {
		return "X";
	}
}
