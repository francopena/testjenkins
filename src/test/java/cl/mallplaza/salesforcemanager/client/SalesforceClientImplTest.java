package cl.mallplaza.salesforcemanager.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.mallplaza.salesforcemanager.client.impl.SalesforceClientImpl;
import cl.mallplaza.salesforcemanager.dto.SalesforceResponseDetailDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.dto.TokenSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.TokenSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.exception.HttpStatusBadRequestSalesforceException;
import cl.mallplaza.salesforcemanager.exception.HttpStatusForbiddenSalesforceException;
import cl.mallplaza.salesforcemanager.exception.SalesforceClientException;

@RunWith(SpringRunner.class)
public class SalesforceClientImplTest {

	@InjectMocks
	private SalesforceClientImpl salesforceClientImpl;

	@Spy
	private RestTemplate restTemplate;
	
	@Mock
	private TokenSalesforceRequestDto requestToken;
	
	@Mock
	private TokenSalesforceResponseDto responseBody;
	
	@Mock
	private ResponseEntity<TokenSalesforceResponseDto> responseEntityToken;
	
	@Mock
	private ResponseEntity<SendMailSalesforceResponseDto> responseEntitySendMail;
	
	@Mock
	private SendMailSalesforceRequestDto sendMailSalesforceRequestDto;
	
	@Mock
	private SendMailSalesforceResponseDto sendMailSalesforceResponseDto;

	private static String token_test = "token";
	private static String token_type_test = "token_type";
	private static String template_id = "templateId_test";
	private static String request_id = "123-456-789";
	private static String url_token_field = "urlToken";
	private static String url_token_value = "url_token_value";
	private static String url_send_field = "sendUrl";
	private static String url_send_value = "url_send_value";
	
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getSalesforceTokenTest() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_token_field, url_token_value);
		doReturn(responseEntityToken).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<TokenSalesforceRequestDto>>any(),
															Mockito.eq(TokenSalesforceResponseDto.class));
		TokenSalesforceResponseDto body= TokenSalesforceResponseDto.builder()
																	.accessToken("access")
																	.build();
		doReturn(body).when(responseEntityToken).getBody();
		
		//act
		TokenSalesforceResponseDto response = salesforceClientImpl.getSalesforceToken();
		
		//Assert
		assertNotNull(response);
		assertNotNull(response.getAccessToken());
		assertEquals("access", response.getAccessToken());
	}
	
	/***********************************************************
	 * captura de error HttpStatusForbiddenSalesforceException
	 * ********************************************************/
	@Test(expected = HttpStatusForbiddenSalesforceException.class)
	public void getSalesforceTokenTest_HttpStatusForbiddenSalesforceException() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_token_field, url_token_value);
		HttpClientErrorException exception = Mockito.mock(HttpClientErrorException.class);
		doThrow(exception).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<TokenSalesforceRequestDto>>any(),
															Mockito.eq(TokenSalesforceResponseDto.class));		
		//act
		salesforceClientImpl.getSalesforceToken();
	}
	
	/***********************************************************
	 * captura de error InternalError
	 * ********************************************************/
	@Test(expected = InternalError.class)
	public void getSalesforceTokenTest_InternalError() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_token_field, url_token_value);
		SalesforceClientException exception = Mockito.mock(SalesforceClientException.class);
		doThrow(exception).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<TokenSalesforceRequestDto>>any(),
															Mockito.eq(TokenSalesforceResponseDto.class));		
		//act
		salesforceClientImpl.getSalesforceToken();
	}
	
	/*********************************************************************************************************************/
	@Test
	public void sendMailTest() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		doReturn(responseEntitySendMail).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
															Mockito.eq(SendMailSalesforceResponseDto.class));
		SalesforceResponseDetailDto detail = SalesforceResponseDetailDto.builder()
																		.hasErrors(false)
																		.build();
		List<SalesforceResponseDetailDto> list = new ArrayList<>();
		list.add(detail);
		SendMailSalesforceResponseDto body= SendMailSalesforceResponseDto.builder()
																	.requestId(request_id)
																	.responses(list)
																	.build();
		doReturn(body).when(responseEntitySendMail).getBody();
		
		//act
		SendMailSalesforceResponseDto response = salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
		
		//Assert
		assertNotNull(response);
		assertNotNull(response.getResponses());
		SalesforceResponseDetailDto responseError= response.getResponses()
															.stream()
															.filter(detailResponse -> detailResponse.getHasErrors().equals(false))
															.findAny()
															.orElse(null);
		assertNotNull(responseError);
		assertEquals(false, responseError.getHasErrors());
	}
	
	/***********************************************************
	 * captura de error SalesforceClientException
	 * ********************************************************/
	@Test(expected = SalesforceClientException.class)
	public void sendMailTest_SalesforceClientException() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		doReturn(responseEntitySendMail).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
															Mockito.eq(SendMailSalesforceResponseDto.class));
		List<String> messages = new ArrayList<String>();
		messages.add("error, Bad Address");
		SalesforceResponseDetailDto detail = SalesforceResponseDetailDto.builder()
																		.hasErrors(true)
																		.messages(messages)
																		.build();
		List<SalesforceResponseDetailDto> list = new ArrayList<>();
		list.add(detail);
		SendMailSalesforceResponseDto body= SendMailSalesforceResponseDto.builder()
																	.requestId(request_id)
																	.responses(list)
																	.build();
		doReturn(body).when(responseEntitySendMail).getBody();
		
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}
	
	/***********************************************************
	 * captura de error HttpStatusBadRequestSalesforceException
	 * ********************************************************/
	@Test(expected = HttpStatusBadRequestSalesforceException.class)
	public void sendMailTest_HttpStatusBadRequestSalesforceException() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		HttpClientErrorException exception = Mockito.mock(HttpClientErrorException.class);
		doReturn(HttpStatus.BAD_REQUEST).when(exception).getStatusCode();
		doThrow(exception).when(restTemplate).exchange(Mockito.anyString(), 
														Mockito.<HttpMethod>any(), 
														Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
														Mockito.eq(SendMailSalesforceResponseDto.class));
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}
	
	/***********************************************************
	 * captura de error InternalError
	 * ********************************************************/
	@Test(expected = InternalError.class)
	public void sendMailTest_InternalError() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		SalesforceClientException exception = Mockito.mock(SalesforceClientException.class);
		doThrow(exception).when(restTemplate).exchange(Mockito.anyString(), 
														Mockito.<HttpMethod>any(), 
														Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
														Mockito.eq(SendMailSalesforceResponseDto.class));		
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}

	
	/***********************************************************
	 * captura de error SalesforceClientException
	 * ********************************************************/
	@Test(expected = SalesforceClientException.class)
	public void sendMailTest_SalesforceClientException_getResponses() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		doReturn(responseEntitySendMail).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
															Mockito.eq(SendMailSalesforceResponseDto.class));
	
		SendMailSalesforceResponseDto body= SendMailSalesforceResponseDto.builder()
																	.requestId(request_id)
																	.responses(null)
																	.build();
		doReturn(body).when(responseEntitySendMail).getBody();
		
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}

	
	/***********************************************************
	 * captura de error SalesforceClientException
	 * ********************************************************/
	@Test(expected = SalesforceClientException.class)
	public void sendMailTest_SalesforceClientException_getBody() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		doReturn(responseEntitySendMail).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
															Mockito.eq(SendMailSalesforceResponseDto.class));
		
		doReturn(null).when(responseEntitySendMail).getBody();
		
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}

	
	/***********************************************************
	 * captura de error SalesforceClientException
	 * ********************************************************/
	@Test(expected = SalesforceClientException.class)
	public void sendMailTest_SalesforceClientException_ResponseNull() {
		//prepare
		ReflectionTestUtils.setField(salesforceClientImpl, url_send_field, url_send_value);
		doReturn(null).when(restTemplate).exchange(Mockito.anyString(), 
															Mockito.<HttpMethod>any(), 
															Mockito.<HttpEntity<SendMailSalesforceRequestDto>>any(),
															Mockito.eq(SendMailSalesforceResponseDto.class));		
		//act
		salesforceClientImpl.sendMail(sendMailSalesforceRequestDto,token_test, token_type_test, template_id);
	}
}
