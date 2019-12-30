package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * it is a data transfer class (data transfer object), Class responsible 
 * for transporting the data of the request for sending mail from the endpoint 
 * exposed in the {@link cl.mallplaza.salesforcemanager.controller.SalesforceController#authSendMail(SendMailRequestDto) SalesforceController.authSendMail(SendMailRequestDto)} controller, 
 * to the service responsible for managing the sending of mail {@link cl.mallplaza.salesforcemanager.service.SalesforceService#sendMail(SendMailRequestDto) SalesforceService.sendMail(SendMailRequestDto)}
 * 
 * @see Serializable
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 * */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMailRequestDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	@NotEmpty
	@NotBlank
	private String templateId; 

	@NotNull
	@NotEmpty
	@NotBlank
	private String fromAddress;

	@NotNull
	@NotEmpty
	@NotBlank
	private String fromName;

	@NotNull
	@NotEmpty
	@NotBlank
	private List<String> toAddresses;
	private Map<String, String> customProperties;
}
