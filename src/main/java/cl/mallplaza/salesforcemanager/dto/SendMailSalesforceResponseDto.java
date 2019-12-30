package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It is a data transfer class (data transfer object), Class responsible 
 * for transporting the data of the response regarding the request for 
 * sending mail to the mass mail service provider
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
public class SendMailSalesforceResponseDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "requestId")
	private String requestId; 
	@JsonProperty(value = "responses")
	private List<SalesforceResponseDetailDto> responses;
	
}
