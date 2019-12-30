package cl.mallplaza.salesforcemanager.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * it is a data transfer class (data transfer object), Class responsible 
 * for transporting the request data for obtaining the token of the 
 * Salesforce mass mail service provider.
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
public class TokenSalesforceRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "grant_type")
	private String grantType;
	@JsonProperty(value = "client_id")
	private String clientId;
	@JsonProperty(value = "client_secret")
	private String clientSecret;
	@JsonProperty(value = "scope")
	private String scope;
	@JsonProperty(value = "account_id")
	private String accountId;
}
