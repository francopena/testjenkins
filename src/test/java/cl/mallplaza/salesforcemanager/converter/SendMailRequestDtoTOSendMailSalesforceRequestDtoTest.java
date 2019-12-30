package cl.mallplaza.salesforcemanager.converter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import cl.mallplaza.salesforcemanager.dto.SendMailRequestDto;
import cl.mallplaza.salesforcemanager.dto.SendMailSalesforceRequestDto;

@RunWith(SpringRunner.class)
public class SendMailRequestDtoTOSendMailSalesforceRequestDtoTest {

	@InjectMocks
	private SendMailRequestDtoTOSendMailSalesforceRequestDto converter;
	
	@Mock
	private SendMailRequestDto source;
	
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void convert() {
		//prepare
		doReturn("FromAddress").when(source).getFromAddress();
		doReturn("FromName").when(source).getFromName();
		doReturn(new HashMap<>()).when(source).getCustomProperties();
		
		//act
		SendMailSalesforceRequestDto response = converter.convert(source);
		
		//assert
		assertNotNull(response);
		assertNotNull(response.getTo());
		assertNotNull(response.getTo().getContactAttributes());
		
	}
	
	@Test
	public void convert_getSubscriberAttributes_null() {
		//prepare
		doReturn("FromAddress").when(source).getFromAddress();
		doReturn("FromName").when(source).getFromName();
		doReturn(null).when(source).getCustomProperties();
		
		//act
		SendMailSalesforceRequestDto response = converter.convert(source);
		
		//assert
		assertNotNull(response);
		assertNotNull(response.getTo());
	}
}
