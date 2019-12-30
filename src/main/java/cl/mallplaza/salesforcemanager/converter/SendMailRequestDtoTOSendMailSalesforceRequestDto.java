package cl.mallplaza.salesforcemanager.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cl.mallplaza.salesforcemanager.dto.ContactAttributesRequestDto;
import cl.mallplaza.salesforcemanager.dto.FromRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.ToRequestDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Class for the transfer of information from two different data structures, the request for 
 * sending mail is taken, and it is converted to the request for the mass mail service provider 
 * Salesforce.
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 * 
 * @see Converter
 **/
@Slf4j
@Component
public class SendMailRequestDtoTOSendMailSalesforceRequestDto implements Converter<SendMailRequestDto, SendMailSalesforceRequestDto>{

	@Override
	public SendMailSalesforceRequestDto convert(SendMailRequestDto source) {
		log.debug("[SendMailRequestDtoTOSendMailSalesforceRequestDto] - Convert Start.");
		Map<String, String> subscriberAttributes = new HashMap<>();
		if(source.getCustomProperties() != null) {
			subscriberAttributes.putAll(source.getCustomProperties());
		}
		return SendMailSalesforceRequestDto.builder()
											.from(FromRequestDto.builder()
																	.address(source.getFromAddress())
																	.name(source.getFromName())
																	.build())
											
											.to(ToRequestDto.builder()
															.contactAttributes(ContactAttributesRequestDto.builder()
																										.subscriberAttributes(subscriberAttributes)
																										.build())
															.build())
											.build();
	}
}
