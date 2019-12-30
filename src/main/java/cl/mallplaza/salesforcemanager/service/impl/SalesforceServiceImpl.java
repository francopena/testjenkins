package cl.mallplaza.salesforcemanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import cl.mallplaza.salesforcemanager.client.SalesforceClient;
import cl.mallplaza.salesforcemanager.dto.SendMailErrorDto;
import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailResponseDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.dto.SendMailStatusEnum;
import cl.mallplaza.salesforcemanager.dto.TokenSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.exception.SalesforceClientException;
import cl.mallplaza.salesforcemanager.exception.SalesforceServiceException;
import cl.mallplaza.salesforcemanager.service.SalesforceService;
import cl.mallplaza.salesforcemanager.util.EncrytDesencrytUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for handling requests to the mass mail service provider Salesforce.
 * For its correct operation, an instance of {@link SalesforceClient} must be injected to make the requests to 
 * the mail service provider, and another instance of {@link Converter}  of type Conversion from 
 * {@link SendMailRequestDto} to {@link SendMailSalesforceRequestDto}
 * for the transformation of the data received to the request to be made
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 */
@Slf4j
@Service
public class SalesforceServiceImpl implements SalesforceService {

	@Autowired
	private SalesforceClient salesforceClient;
	
	@Autowired
	private Converter<SendMailRequestDto, SendMailSalesforceRequestDto> converter;
	
	@Autowired
	private EncrytDesencrytUtil encrytDesencrytUtil;

	/**
	 * This method makes the request and obtaining the token from the mail service 
	 * provider, and subsequently the request for sending the mail.
	 * 
	 * @param requestDto of type {@link SendMailRequestDto} 
	 * 
	 * @return {@link SendMailResponseDto}
	 * 
	 * @throws SalesforceClientException caused because the token request response is 
	 * empty or null
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 */
	@Override
	public List<SendMailResponseDto> sendMail(SendMailRequestDto requestDto) {
		log.info("[SalesforceServiceImpl] - send mail started ".concat(requestDto.toString()));
		List<SendMailResponseDto> responseSendMail = new ArrayList<>();
		
		for(String toAddress: requestDto.getToAddresses()) {
			
			String finalAddress = toAddress;
			SendMailResponseDto responseByMail = SendMailResponseDto.builder().email(finalAddress).build();
			try {
				finalAddress = verifyEmailFormat(finalAddress);
			} catch (Exception e) {
				responseByMail.setError(SendMailErrorDto.builder()
						.code("503")
						.description(e.getMessage())
						.build()
						);
				responseByMail.setStatus(SendMailStatusEnum.NOT_SENT.getDescription());
				
				responseSendMail.add(responseByMail);
				continue;
			}
			
			SendMailSalesforceRequestDto sendMailSalesforceRequestDto = this.converter.convert(requestDto);
			sendMailSalesforceRequestDto.getTo().setAddress(finalAddress);
			sendMailSalesforceRequestDto.getTo().setSubscriberKey(finalAddress);
			
			TokenSalesforceResponseDto responseToken = this.salesforceClient.getSalesforceToken();
			
			SendMailSalesforceResponseDto responseClient = null;
			
			if(responseToken != null && responseToken.getAccessToken() != null) {
				try {
					responseClient = this.salesforceClient.sendMail(sendMailSalesforceRequestDto, responseToken.getAccessToken(), 
																						responseToken.getTokenType(), requestDto.getTemplateId());
					responseByMail.setRequestId(responseClient.getRequestId());
					responseByMail.setStatus(SendMailStatusEnum.SENT.getDescription());
				} catch (Exception e) {
					responseByMail.setError(SendMailErrorDto.builder()
															.code("503")
															.description(e.getMessage())
															.build()
															);
					responseByMail.setStatus(SendMailStatusEnum.NOT_SENT.getDescription());
				}
			} else {
				log.error("[SalesforceServiceImpl] - Token null ");
				responseByMail.setError(SendMailErrorDto.builder()
						.code("503")
						.description("Token for sending, returns null")
						.build()
						);
				responseByMail.setStatus(SendMailStatusEnum.NOT_SENT.getDescription());
			}
			
			responseSendMail.add(responseByMail);
		}

		return responseSendMail;
	}
	
	/**
	 * This method is responsible for validating that the value in the received field has the 
	 * format of an email address, if it does not have the format try to describe it, since there 
	 * are options that come encrypted, then validate the format and If it is mail, it returns the 
	 * value but then generates exceptions.
	 * 
	 * @param email of type {@link String} 
	 * 
	 * @return {@link String}
	 * 
	 * @throws SalesforceServiceException caused because it cannot be decrypted 
	 * or because in no case is it formatted as an email address
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 28/11/2019
	 */
	public String verifyEmailFormat(String email) {
		String finalAddress = email;

		log.info(email);
		log.info(encrytDesencrytUtil.encript(email));
		EmailValidator validator = EmailValidator.getInstance(); 		
		if(!validator.isValid(email)) {
			try {
				String desencrytEmail = encrytDesencrytUtil.desencript(email);
				if(validator.isValid(desencrytEmail)) {
					finalAddress = desencrytEmail;
				} else {
					log.error("[SalesforceServiceImpl] - The value obtained is not in the format of an email address. value: " + email);
					throw new SalesforceServiceException("The value obtained is not in the format of an email address.");
				}
			} catch (Exception e) {
				log.error("[SalesforceServiceImpl] - Error decrypting or no email address format. value: " + email);
				throw new SalesforceServiceException("Error decrypting or no email address format.");
			}
		}
		return finalAddress;
	}

}
