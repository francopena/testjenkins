package cl.mallplaza.salesforcemanager.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cl.mallplaza.salesforcemanager.exception.BadRequestException;
import cl.mallplaza.salesforcemanager.exception.ForbiddenException;
import cl.mallplaza.salesforcemanager.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;

/**
 * classes with content aspect oriented programming (AOP), the objective of this classes is to ensure and 
 * manage the security in the requests made to the exposed endpoints.
 * The execution of this class requires of one variable of the properties file:
 * 
 * <p>1.- This property must contain the fixed token that will be used to identify the requests that are made.
 * <pre class="code">
 * 		properties.authorization.request.token
 * </pre>
 *  
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 * 
 */

@Slf4j
@Aspect
@Component
public class AuthenticationAspect {

	@Value("${properties.authorization.request.token}")
	private String tokenTemporal;
	
	/**
	 * Method for validation of any request made to any method that its name begins with 'auth', 
	 * with any input or output parameter and that belongs to any class within the package 
	 * {@code cl.mallplaza.salesforcemanager.controller}. And will be executed before the process of 
	 * the method of which the request is made, if the validation fails, it ensures that the method 
	 * will not be executed, as it is a precondition
	 * 
	 * <p>for the optimal operation of this method the token must be of type 'Bearer' and must be 
	 * in the header of the request with the key {@code Authorization}.
	 * 
	 * @param joinPoint of type {@link JoinPoint}
	 * 
	 * @throws BadRequestException Indicates that the 'authorization' header was not found and which is the mandatory.
	 * 
	 * @throws ForbiddenException Indicates that the token does not exist or is not valid. Or the validation response 
	 * contains an email or client ID other than those declared in the properties {@code properties.pub-sub.service-account.email} 
	 * and {@code properties.pub-sub.service-account.client-id}.
	 * 
	 * @throws UnauthorizedException The token validation response is null, therefore it is not authorized for execution
	 * 
	 * @since 1.0
	 * 
	 * @author Juan Manuel Vasquez - Zenta
	 * 	
	 * @version 26/08/2019
	 * 
	 */
	@Before("execution(* cl.mallplaza.salesforcemanager.controller.*.auth*(..))")
	public void validateAuthentication(JoinPoint joinPoint) {
		log.info("[AuthenticationAspect] - Validate Authentication.");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String authorization = request.getHeader("Authorization");
		if(authorization != null) {
			authorization = authorization.replace("Bearer", "").replace(" ", "");
		} else {
			throw new BadRequestException("[Authentication] - Token is requerided.");
		}

		if("".equals(authorization)) {
			log.error("[AuthenticationAspect] - Authentication failure.");
			throw new UnauthorizedException("Authentication failure.");
		}
		
		if(!tokenTemporal.equals(authorization)) {
			throw new ForbiddenException("has no authorization.");
		}
		log.info("[AuthenticationAspect] - Authentication success.");
	}
}
