package cl.mallplaza.salesforcemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class HttpStatusForbiddenSalesforceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144594057039289010L;
	private String message;
	private String code;
	
	public HttpStatusForbiddenSalesforceException(String message) {
		super();
		this.message = message;
	}
	
	public HttpStatusForbiddenSalesforceException(String message, Throwable e ) {
		super();
		this.message = message;
		this.addSuppressed(e);
	}
	
	public HttpStatusForbiddenSalesforceException(String code, String message, Throwable e ) {
		super();
		this.code = code;
		this.message = message;
		this.addSuppressed(e);
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	public String getCode() {
		return this.code;
	}
}
