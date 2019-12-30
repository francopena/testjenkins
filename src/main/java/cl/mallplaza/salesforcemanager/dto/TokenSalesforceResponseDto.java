package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * it is a data transfer class (data transfer object), Class responsible 
 * for transporting the response data to the request to obtain the token 
 * of the Salesforce mass mail service provider.
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
public class TokenSalesforceResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "access_token")
	private String accessToken;
	@JsonProperty(value = "token_type")
	private String tokenType;
	@JsonProperty(value = "expires_in")
	private String expiresIn;
	@JsonProperty(value = "scope")
	private String scope;
	@JsonProperty(value = "soap_instance_url")
	private String soapInstanceUrl;
	@JsonProperty(value = "rest_instance_url")
	private String restInstanceUrl;
}
