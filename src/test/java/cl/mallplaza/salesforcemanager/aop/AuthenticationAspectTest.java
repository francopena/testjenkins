package cl.mallplaza.salesforcemanager.aop;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cl.mallplaza.salesforcemanager.exception.BadRequestException;
import cl.mallplaza.salesforcemanager.exception.ForbiddenException;
import cl.mallplaza.salesforcemanager.exception.UnauthorizedException;


@RunWith(SpringRunner.class)
public class AuthenticationAspectTest {

	@InjectMocks
	private AuthenticationAspect authenticationAspect;

	@Mock
	private JoinPoint joinPoint;

	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void validateAuthentication() {
		//prepare
		String token = new String("token");
		ReflectionTestUtils.setField(authenticationAspect, "tokenTemporal", "token");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", token);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		//exec
		this.authenticationAspect.validateAuthentication(joinPoint);
	}
	
	/*****************************************************
	 * captura de error BadRequestException
	 * *************************************************/
	@Test(expected = BadRequestException.class)
	public void validateAuthentication_BadRequestException() {
		//prepare
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		//exec
		this.authenticationAspect.validateAuthentication(joinPoint);
	}
	
	/*****************************************************
	 * captura de error UnauthorizedException
	 * *************************************************/
	@Test(expected = UnauthorizedException.class)
	public void validateAuthentication_UnauthorizedException() {
		//prepare
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", "");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		//exec
		this.authenticationAspect.validateAuthentication(joinPoint);
	}
	
	/*****************************************************
	 * captura de error ForbiddenException
	 * *************************************************/
	@Test(expected = ForbiddenException.class)
	public void validateAuthentication_ForbiddenException() {
		//prepare
		String token = new String("token");
		ReflectionTestUtils.setField(authenticationAspect, "tokenTemporal", "token_properties");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", token);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		//exec
		this.authenticationAspect.validateAuthentication(joinPoint);
	}
}
