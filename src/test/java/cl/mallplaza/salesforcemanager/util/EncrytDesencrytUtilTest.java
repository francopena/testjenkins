package cl.mallplaza.salesforcemanager.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.crypto.Cipher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cl.mallplaza.salesforcemanager.exception.EncrytDesencrytException;

@RunWith(SpringRunner.class)
public class EncrytDesencrytUtilTest {

	@InjectMocks
	private EncrytDesencrytUtil encrytDesencrytUtil;
	
	@Spy
	private EncrytDesencrytUtil encrytDesencrytUtilSpy;
	
	@Mock
	private Cipher cipher;

	
	private static String value = "juan.vasquez@zentagroup.com";
	
	private static String encriptValue = "01fYiazJG3g2GiOE9z4RSR/8G2oD3FXPEz9q0OdRBHg=";

	private static String wordForKeyField = "wordForKey";
	private static String wordForKeyValue = "wordKey";
	private static String encodingField = "encoding";
	private static String encodingValue = "UTF-8";

	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
	}	
	
	@Test
	public void encriptTest() {
		//prepare
		ReflectionTestUtils.setField(encrytDesencrytUtil, wordForKeyField, wordForKeyValue);
		ReflectionTestUtils.setField(encrytDesencrytUtil, encodingField, encodingValue);
		
		//act
		String response = encrytDesencrytUtil.encript(value);
		
		//assert
		assertNotNull(response);
		assertEquals(encriptValue, response);
	}

	/*****************************
	 * EncrytDesencrytException caused by UnsupportedEncodingException
	 * @throws UnsupportedEncodingException 
	 */
	@Test(expected = EncrytDesencrytException.class)
	public void encript_UnsupportedEncodingException_Test() {
		//prepare
		ReflectionTestUtils.setField(encrytDesencrytUtil, wordForKeyField, wordForKeyValue);
		ReflectionTestUtils.setField(encrytDesencrytUtil, "encoding", "");
		
		//act
		encrytDesencrytUtil.encript(value);
		
	}

	/*****************************
	 * EncrytDesencrytException caused by UnsupportedEncodingException
	 * 
	 */
	@Test
	public void encript_IllegalBlockSizeException_Test() {
		//prepare
		ReflectionTestUtils.setField(encrytDesencrytUtil, wordForKeyField, wordForKeyValue);
		ReflectionTestUtils.setField(encrytDesencrytUtil, encodingField, encodingValue);
		
		
		//act
		encrytDesencrytUtil.encript(value);
		
	}


	/*********************************************************************************************************
	 * desencriptTest
	 */

	@Test
	public void desencriptTest() {
		//prepare
		ReflectionTestUtils.setField(encrytDesencrytUtil, wordForKeyField, wordForKeyValue);
		ReflectionTestUtils.setField(encrytDesencrytUtil, encodingField, encodingValue);
		
		//act
		String response = encrytDesencrytUtil.desencript(encriptValue);
		
		//assert
		assertNotNull(response);
		assertEquals(value, response);
	}
}
