package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * it is a data transfer class (data transfer object), Class responsible 
 * for transporting the sender's data to be sent by the mass mail service 
 * provider. And it is part of the body of the {@link SendMailSalesforceRequestDto} 
 * class that would be the complete request.
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
public class FromRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2817266918529316587L;
	@JsonProperty(value = "Address")
	private String address;
	@JsonProperty(value = "Name")
	private String name;
}
