package de.buxi.cantstop.web.ajax;

import de.buxi.cantstop.model.GameTransferObject;

/**
 * @author buxi
 * Transfer Object in JSON format for AJAX requests (except polling)  
 */
public class JsonResponse {
	// TODO should be enum?
	public final static String ERROR = "ERROR";
	public final static String SUCCESS = "SUCCESS";
	
	/**
	 * call status: ERROR, SUCCESS
	 */
	private String status;
	
	/**
	 *  returning result object of the called method
	 */
	private Object methodResult;
	
	/**
	 * Game Transfer Object with actual game state
	 */
	private GameTransferObject gto;
	
	/**
	 * localized errorMessage 
	 */
	private String errorMessage; 

	/**
	 * Default setting is status = SUCCESS, errorCode = 0
	 */
	public JsonResponse() {
		super();
		this.status = SUCCESS;
		this.methodResult = null;
		this.gto = null;
		this.errorMessage = "";
	}
	
	/**
	 * @return the errorCode
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the gto
	 */
	public GameTransferObject getGto() {
		return gto;
	}
	
	/**
	 * @param gto the gto to set
	 */
	public void setGto(GameTransferObject gto) {
		this.gto = gto;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getMethodResult() {
		return methodResult;
	}
	public void setMethodResult(Object result) {
		this.methodResult = result;
	}
}
