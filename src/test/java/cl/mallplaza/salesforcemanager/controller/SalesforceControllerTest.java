package cl.mallplaza.salesforcemanager.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import cl.mallplaza.salesforcemanager.dto.ResponseSendMailList;
import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailResponseDto;
import cl.mallplaza.salesforcemanager.service.SalesforceService;

@RunWith(SpringRunner.class)
public class SalesforceControllerTest {

	@InjectMocks
	private SalesforceController salesforceController;
	
	@Spy
	private SalesforceService salesforceService;
	
	@Mock
	private SendMailRequestDto sendMailRequestDto;
	

	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void sendMailAddress() {
		//prepare
		SendMailResponseDto responseService = SendMailResponseDto.builder()
																.requestId("123-456-789")
																.build();
		List<SendMailResponseDto> responseServiceList = new ArrayList<>();
		responseServiceList.add(responseService);
		doReturn(responseServiceList).when(salesforceService).sendMail(Mockito.any(SendMailRequestDto.class));
		
		//act
		ResponseEntity<ResponseSendMailList> response = salesforceController.authSendMail("xCommerce","xCountry","xPrRef","xChRef","xUsrTx","xRhsRef","xCmRef",sendMailRequestDto);
		
		//assert
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getResponseList());
		assertEquals("123-456-789", response.getBody().getResponseList().get(0).getRequestId());
	}
	
	@Test
	public void responseOkTest() {
		//prepare
		//act
		ResponseEntity<String> response = salesforceController.responseOk();
		
		//assert
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
