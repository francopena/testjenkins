package cl.mallplaza.salesforcemanager.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Configuration class, to prepare the parameters used by the swagger framework.
 * 
 * @since 1.0
 * 
 * @author Juan Manuel Vasquez - Zenta
 * 	
 * @version 26/08/2019
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {                                    
    
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        		   .select()                                       
        		      .apis(RequestHandlerSelectors.basePackage("cl.mallplaza.salesforcemanager.controller"))
        		      .paths(PathSelectors.any())                     
        		      .build()
        		      .apiInfo(apiInfo());                                         
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      		.addResourceLocations("classpath:/META-INF/resources/");
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Salesforce Manager Util - API Proxy", 
	      "API para la gestion de envio de correos masivos, a traves del proveedor salesforce.", 
	      "", 
	      "", 
	      new Contact("Nelson Oliva", "www.mallplaza.com", "nelson.oliva@mallplaza.com"), 
	      "License of API", "http://www.mallplaza.com", Collections.emptyList());
	}
}
