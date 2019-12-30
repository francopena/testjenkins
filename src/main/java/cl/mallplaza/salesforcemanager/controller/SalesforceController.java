package cl.mallplaza.salesforcemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cl.mallplaza.salesforcemanager.dto.ResponseSendMailList;
import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailResponseDto;
import cl.mallplaza.salesforcemanager.service.SalesforceService;

/**
 * Rest controller, which exposes the endpoints of the operations available for 
 * data transformation from pub/sub to salesforce mass mail provider.
 * For its correct operation the injection of an instance of {@link SalesforceService} is required.
 * 
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 */

@RestController
public class SalesforceController {

	@Autowired
	private SalesforceService salesforceService;
	

	/**
	 * Method for verifying the rules of ingress.
	 * @return {@link ResponseEntity} of {@link String} type.
	 *
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
 	 **/
	@GetMapping("/")
	public ResponseEntity<String> responseOk(){
		return ResponseEntity.ok("ok");
	}
	
	/**
	 * Method that receives the request of sending mail, with structure in JSON format, 
	 * and processes using the {@link SalesforceService} service, and obtains the response of 
	 * successful or failed execution.
	 * 
	 * @param requestDto of type {@link SendMailRequestDto} and param required.
	 * 
	 * @return ResponseEntity of type {@link SendMailResponseDto}
	 * 
	 * @see SalesforceService
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 * */
	@PostMapping("/notification/mail")
	public ResponseEntity<ResponseSendMailList> authSendMail(@RequestHeader(name = "X-cmRef", required = true ) String xCmRef,
																	@RequestHeader(name = "X-rhsRef", required = true ) String xRhsRef,
																	@RequestHeader(name = "X-usrTx", required = false ) String xUsrTx,
																	@RequestHeader(name = "X-chRef", required = true ) String xChRef,
																	@RequestHeader(name = "X-prRef", required = false ) String xPrRef,
																	@RequestHeader(name = "X-country", required = true ) String xCountry,
																	@RequestHeader(name = "X-commerce", required = true ) String xCommerce,
																	@RequestBody(required = true) SendMailRequestDto requestDto){
		
		return ResponseEntity.ok(ResponseSendMailList.builder().responseList(this.salesforceService.sendMail(requestDto)).build());
	}
}
