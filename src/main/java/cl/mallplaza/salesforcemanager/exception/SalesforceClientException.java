package cl.mallplaza.salesforcemanager.exception;

public class SalesforceClientException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144594057039289010L;
	private String message;
	private String code;
	
	public SalesforceClientException(String message) {
		super();
		this.message = message;
	}
	
	public SalesforceClientException(String message, Throwable e ) {
		super();
		this.message = message;
		this.addSuppressed(e);
	}
	
	public SalesforceClientException(String code, String message, Throwable e ) {
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
