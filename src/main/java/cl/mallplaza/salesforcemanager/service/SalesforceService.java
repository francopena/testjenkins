package cl.mallplaza.salesforcemanager.service;

import java.util.List;

import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailResponseDto;

/**
 * Service responsible for handling requests to the mass mail service provider Salesforce.
 * 
 * @see {@link cl.mallplaza.salesforcemanager.service.impl.SalesforceServiceImpl SalesforceServiceImpl}
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 */
public interface SalesforceService {

	/**
	 * Method for sending mail through salesforce
	 * 
	 * @see {@link cl.mallplaza.salesforcemanager.service.impl.SalesforceServiceImpl#sendMail(SendMailRequestDto) SalesforceServiceImpl.sendMail(SendMailRequestDto)}
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 */
	public List<SendMailResponseDto> sendMail(SendMailRequestDto requestDto);
}
