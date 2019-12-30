package cl.mallplaza.salesforcemanager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import cl.mallplaza.salesforcemanager.dto.TokenSalesforceRequestDto;

/**
 * Configuration class for requests to the Salesforce service provider, in this class 
 * a bean of the {@link RestTemplate} class is created, as well as a bean of the standard 
 * request for obtaining the token {@link TokenSalesforceRequestDto}.
 * <p>For optimal operation, five variables from the properties file are required.
 * 
 * <pre class="code">
 * 	properties.salesforce.token.request.grant-type
 * </pre>
 * <pre class="code">
 *  properties.salesforce.token.request.client-id
 * </pre>
 * <pre class="code">
 * 	properties.salesforce.token.request.client-secret
 * </pre>
 * <pre class="code">
 * 	properties.salesforce.token.request.scope
 * </pre>
 * <pre class="code">
 * 	properties.salesforce.token.request.account-id
 * </pre>
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 * 
 */
@Configuration
public class SalesforceClientConfiguration {
	
	@Value("${properties.salesforce.token.request.grant-type}")
	private String grantType;
	
	@Value("${properties.salesforce.token.request.client-id}")
	private String clientId;
	
	@Value("${properties.salesforce.token.request.client-secret}")
	private String clientSecret;
	
	@Value("${properties.salesforce.token.request.scope}")
	private String scope;
	
	@Value("${properties.salesforce.token.request.account-id}") 
	private String accountId;
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public TokenSalesforceRequestDto TokenSalesforceRequestDto() {
		return TokenSalesforceRequestDto.builder()
											.accountId(accountId)
											.clientId(clientId)
											.clientSecret(clientSecret)
											.grantType(grantType)
											.scope(scope)
											.build();
	}
}
