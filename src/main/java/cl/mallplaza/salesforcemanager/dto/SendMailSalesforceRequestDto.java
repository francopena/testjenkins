package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * it is a data transfer class (data transfer object), Class responsible 
 * for transporting the data to make the request to send mail to the mass 
 * mail provider.
 * 
 * @see FromRequestDto
 * @see ToRequestDto
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
public class SendMailSalesforceRequestDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "From")
	private FromRequestDto from; 

	@JsonProperty(value = "To")
	private ToRequestDto to;
	
}
