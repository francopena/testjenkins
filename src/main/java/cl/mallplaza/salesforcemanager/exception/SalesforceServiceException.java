package cl.mallplaza.salesforcemanager.exception;

public class SalesforceServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144594057039289010L;
	private String message;
	private String code;
	
	public SalesforceServiceException(String message) {
		super();
		this.message = message;
	}
	
	public SalesforceServiceException(String message, Throwable e ) {
		super();
		this.message = message;
		this.addSuppressed(e);
	}
	
	public SalesforceServiceException(String code, String message, Throwable e ) {
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
