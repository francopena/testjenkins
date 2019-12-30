package cl.mallplaza.salesforcemanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringRunner;

import cl.mallplaza.salesforcemanager.client.SalesforceClient;
import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailResponseDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.dto.ToRequestDto;
import cl.mallplaza.salesforcemanager.dto.TokenSalesforceResponseDto;
import cl.mallplaza.salesforcemanager.exception.EncrytDesencrytException;
import cl.mallplaza.salesforcemanager.exception.SalesforceClientException;
import cl.mallplaza.salesforcemanager.exception.SalesforceServiceException;
import cl.mallplaza.salesforcemanager.service.impl.SalesforceServiceImpl;
import cl.mallplaza.salesforcemanager.util.EncrytDesencrytUtil;

@RunWith(SpringRunner.class)
public class SalesforceServiceImplTest {
	
	@InjectMocks
	private SalesforceServiceImpl salesforceServiceImpl;
	
	@Spy
	private SalesforceServiceImpl salesforceServiceImplSpy;
	
	@Mock
	private EncrytDesencrytUtil encrytDesencrytUtil;

	@Mock
	private SalesforceClient salesforceClient;

	@Mock
	private Converter<SendMailRequestDto, SendMailSalesforceRequestDto> converter;

	@Mock
	private SendMailRequestDto sendMailRequestDto;
	
	@Mock
	private SendMailSalesforceRequestDto sendMailSalesforceRequestDto;
	
	@Mock
	private SendMailSalesforceResponseDto sendMailSalesforceResponseDto;
	
	@Mock
	private TokenSalesforceResponseDto tokenSalesforceResponseDto;
	
	private List<String> addressesList = new ArrayList<>();

	private static String token_test = "token";
	private static String token_type_test = "token_type";
	private static String request_id = "123-456-789";
	private static String code_exception = "503";
	private static String correoEncryt = "0qM+S0i2hGZ7Pzgu/F53hAHQy8Ww3p+6uWtU7GRnmCQ=";
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.addressesList.add("test@test.com");
		this.addressesList.add("test2@test2.com");
		this.addressesList.add("test3@test3.com"); 
    }
	
	@Test
	public void sendMailTest() {
		//prepare
		doReturn(addressesList).when(sendMailRequestDto).getToAddresses();
		doReturn("").when(sendMailRequestDto).getTemplateId();
		doReturn(new ToRequestDto()).when(sendMailSalesforceRequestDto).getTo();
		doReturn(sendMailSalesforceRequestDto).when(converter).convert(sendMailRequestDto);
		doReturn(tokenSalesforceResponseDto).when(salesforceClient).getSalesforceToken();
		doReturn(token_test).when(tokenSalesforceResponseDto).getAccessToken();
		doReturn(token_type_test).when(tokenSalesforceResponseDto).getTokenType();
		doReturn(sendMailSalesforceResponseDto).when(salesforceClient).sendMail(Mockito.any(SendMailSalesforceRequestDto.class), 
																				Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		doReturn(request_id).when(sendMailSalesforceResponseDto).getRequestId();
		
		//act
		List<SendMailResponseDto> sendMailResponseDto = salesforceServiceImpl.sendMail(sendMailRequestDto);
		
		//assert
		assertNotNull(sendMailResponseDto);
		assertNotNull(sendMailResponseDto.get(0));
		assertNotNull(sendMailResponseDto.get(0).getRequestId());
		assertEquals(request_id, sendMailResponseDto.get(0).getRequestId());
	}
	
	
	/*****************
	 * verify email exception
	 */
	@Test
	public void sendMailTest_verifyEmail() {
		//prepare
		List<String> addressesList = new ArrayList<>();
		addressesList.add("correo");
		doReturn(addressesList).when(sendMailRequestDto).getToAddresses();
		SalesforceServiceException exception = Mockito.mock(SalesforceServiceException.class);
		doThrow(exception).when(salesforceServiceImplSpy).verifyEmailFormat(Mockito.anyString());
		
		//act
		List<SendMailResponseDto> sendMailResponseDto = salesforceServiceImpl.sendMail(sendMailRequestDto);
		
		//assert
		assertNotNull(sendMailResponseDto);
		assertNotNull(sendMailResponseDto.get(0));
		assertNotNull(sendMailResponseDto.get(0).getError());
		assertNotNull(sendMailResponseDto.get(0).getError().getDescription());
		assertEquals("Error decrypting or no email address format.", sendMailResponseDto.get(0).getError().getDescription());
	}
	
	@Test
	public void sendMailTestTokenNull() {
		//prepare
		doReturn(addressesList).when(sendMailRequestDto).getToAddresses();
		doReturn("").when(sendMailRequestDto).getTemplateId();
		doReturn(new ToRequestDto()).when(sendMailSalesforceRequestDto).getTo();
		doReturn(sendMailSalesforceRequestDto).when(converter).convert(sendMailRequestDto);
		doReturn(null).when(salesforceClient).getSalesforceToken();
		doReturn(token_test).when(tokenSalesforceResponseDto).getAccessToken();
		doReturn(token_type_test).when(tokenSalesforceResponseDto).getTokenType();
		doReturn(sendMailSalesforceResponseDto).when(salesforceClient).sendMail(Mockito.any(SendMailSalesforceRequestDto.class), 
																				Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		doReturn(request_id).when(sendMailSalesforceResponseDto).getRequestId();
		
		//act
		List<SendMailResponseDto> sendMailResponseDto = salesforceServiceImpl.sendMail(sendMailRequestDto);
		
		//assert
		assertNotNull(sendMailResponseDto);
		assertNotNull(sendMailResponseDto.get(0));
		assertNull(sendMailResponseDto.get(0).getRequestId());
		assertNotNull(sendMailResponseDto.get(0).getError());
		assertEquals(code_exception, sendMailResponseDto.get(0).getError().getCode());
	}
	
	@Test
	public void sendMailTestTokenAccessTokenNull() {
		//prepare
		doReturn(addressesList).when(sendMailRequestDto).getToAddresses();
		doReturn("").when(sendMailRequestDto).getTemplateId();
		doReturn(new ToRequestDto()).when(sendMailSalesforceRequestDto).getTo();
		doReturn(sendMailSalesforceRequestDto).when(converter).convert(sendMailRequestDto);
		doReturn(tokenSalesforceResponseDto).when(salesforceClient).getSalesforceToken();
		doReturn(null).when(tokenSalesforceResponseDto).getAccessToken();
		doReturn(token_type_test).when(tokenSalesforceResponseDto).getTokenType();
		doReturn(sendMailSalesforceResponseDto).when(salesforceClient).sendMail(Mockito.any(SendMailSalesforceRequestDto.class), 
																				Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		doReturn(request_id).when(sendMailSalesforceResponseDto).getRequestId();
		
		//act
		List<SendMailResponseDto> sendMailResponseDto = salesforceServiceImpl.sendMail(sendMailRequestDto);
		
		//assert
		assertNotNull(sendMailResponseDto);
		assertNotNull(sendMailResponseDto.get(0));
		assertNull(sendMailResponseDto.get(0).getRequestId());
		assertNotNull(sendMailResponseDto.get(0).getError());
		assertEquals(code_exception, sendMailResponseDto.get(0).getError().getCode());
	}
	
	@Test
	public void sendMailTestCatch() {
		//prepare
		doReturn(addressesList).when(sendMailRequestDto).getToAddresses();
		doReturn("").when(sendMailRequestDto).getTemplateId();
		doReturn(new ToRequestDto()).when(sendMailSalesforceRequestDto).getTo();
		doReturn(sendMailSalesforceRequestDto).when(converter).convert(sendMailRequestDto);
		doReturn(tokenSalesforceResponseDto).when(salesforceClient).getSalesforceToken();
		doReturn(token_test).when(tokenSalesforceResponseDto).getAccessToken();
		doReturn(token_type_test).when(tokenSalesforceResponseDto).getTokenType();
		SalesforceClientException exception = Mockito.mock(SalesforceClientException.class);
		doThrow(exception).when(salesforceClient).sendMail(Mockito.any(SendMailSalesforceRequestDto.class), 
																				Mockito.anyString(), Mockito.anyString(), Mockito.anyString());	
		//act
		List<SendMailResponseDto> sendMailResponseDto = salesforceServiceImpl.sendMail(sendMailRequestDto);
		
		//assert
		assertNotNull(sendMailResponseDto);
		assertNotNull(sendMailResponseDto.get(0));
		assertNull(sendMailResponseDto.get(0).getRequestId());
		assertNotNull(sendMailResponseDto.get(0).getError());
		assertEquals(code_exception, sendMailResponseDto.get(0).getError().getCode());
	}
	
/************************
 * verifyEmailFormat
 */
	@Test
	public void verifyEmailFormatTest() {
		//prepare
		doReturn("correo@correo.com").when(encrytDesencrytUtil).desencript(Mockito.anyString());
		
		//act
		String correo = salesforceServiceImpl.verifyEmailFormat(correoEncryt);
		
		//assert
		assertNotNull(correo);
		assertEquals("correo@correo.com", correo);
	}
	
/************************
 * verifyEmailFormat
 */
	@Test
	public void verifyEmailFormatTest2() {
		
		//act
		String correo = salesforceServiceImpl.verifyEmailFormat("correoSinEncryt@correo.com");
		
		//assert
		assertNotNull(correo);
		assertEquals("correoSinEncryt@correo.com", correo);
	}
	
	/************************
	 * verifyEmailFormat encryt fail
	 */
	@Test(expected = SalesforceServiceException.class)
	public void verifyEmailFormatTest_Exception() {
		//prepare
		doReturn("fail").when(encrytDesencrytUtil).desencript(Mockito.anyString());
		
		//act
		salesforceServiceImpl.verifyEmailFormat(correoEncryt);
		
	}
	
	/************************
	 * verifyEmailFormat encriyt exception
	 */
	@Test(expected = SalesforceServiceException.class)
	public void verifyEmailFormatTest_Exception_encript() {
		//prepare
		EncrytDesencrytException exception = Mockito.mock(EncrytDesencrytException.class);
		doThrow(exception).when(encrytDesencrytUtil).desencript(Mockito.anyString());
		
		//act
		salesforceServiceImpl.verifyEmailFormat(correoEncryt);
		
	}
	
	
}
