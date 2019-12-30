package cl.mallplaza.salesforcemanager.exception;

public class EncrytDesencrytException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8867522911423217129L;

	private String code;
	
	public EncrytDesencrytException(String message) {
		super(message);
	}
	
	public EncrytDesencrytException(String message, Throwable e ) {
		super(message);
		this.addSuppressed(e);
	}
	
	public EncrytDesencrytException(String code, String message, Throwable e ) {
		super(message);
		this.code = code;
		this.addSuppressed(e);
	}

	
	public String getCode() {
		return this.code;
	}
}
