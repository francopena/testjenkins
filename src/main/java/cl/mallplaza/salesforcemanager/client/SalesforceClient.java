package cl.mallplaza.salesforcemanager.client;

import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.dto.TokenSalesforceResponseDto;

/**
 * The purpose of this class is to manage the requests to the salesforce mass mail provider,
 * For more information, see the implementation described below.
 * 
 * @see {@link cl.mallplaza.salesforcemanager.client.impl.SalesforceClientImpl SalesforceClientImpl}
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 *
 */
public interface SalesforceClient {

	/**
	 * Method to obtain the token from salesforce, for making requests to the Salesforce mass mail service provider.
	 *
	 * @return TokenSalesforceResponseDto
	 * 
	 * @see {@link cl.mallplaza.salesforcemanager.client.impl.SalesforceClientImpl#getSalesforceToken() SalesforceClientImpl.getSalesforceToken()}
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 */
	public TokenSalesforceResponseDto getSalesforceToken();

	/**
	 * Method that is responsible for creating the request for sending mail, 
	 * and executing the request, in addition to processing the response and creating the filtered return.
	 *
	 * @param requestDto of type {@link SendMailSalesforceRequestDto}
	 * 
	 * @param token of type String
	 * 
	 * @param tokenType of type String
	 *
	 * @return {@link SendMailSalesforceResponseDto}
	 * 
	 * @see {@link cl.mallplaza.salesforcemanager.client.impl.SalesforceClientImpl#sendMail(SendMailSalesforceRequestDto, String, String) SalesforceClientImpl.sendMail(SendMailSalesforceRequestDto, String, String)}
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 */
	public SendMailSalesforceResponseDto sendMail(SendMailSalesforceRequestDto requestDto, String token, String tokenType, String templateId);
}
