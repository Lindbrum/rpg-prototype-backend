package it.dercole.prototypes.game_login_soap.endpoint;

import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		
		return new ServletRegistrationBean<>(servlet, "/*");
	}
	
	//Wsdl will be exposed at "<host>:<port>/auth.wsdl"
	@Bean(name = "auth")
	public Wsdl11Definition defaultWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/auth.wsdl"));
		return wsdl11Definition;
	}
	
	//Map authentication exceptions to SOAP faults
	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver() {
		CustomSoapFaultMappingExceptionResolver resolver = new CustomSoapFaultMappingExceptionResolver();
		Properties exceptionMappings = new Properties(2);
		exceptionMappings.setProperty(UsernameNotFoundException.class.getName(), String.format("%s,%s", SoapFaultDefinition.CLIENT.toString(), "401 - Incorrect credentials."));
		exceptionMappings.setProperty(AuthenticationException.class.getName(), String.format("%s,%s", SoapFaultDefinition.CLIENT.toString(), "401 - Incorrect credentials."));
		resolver.setExceptionMappings(exceptionMappings);
		
		return resolver;
	}
}