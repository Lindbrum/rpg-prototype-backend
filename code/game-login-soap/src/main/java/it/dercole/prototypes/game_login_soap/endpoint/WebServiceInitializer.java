package it.dercole.prototypes.game_login_soap.endpoint;

import org.springframework.ws.transport.http.support.AbstractAnnotationConfigMessageDispatcherServletInitializer;

import it.dercole.prototypes.game_login_soap.HttpSessionConfig;
import it.dercole.prototypes.game_login_soap.SecurityConfig;

//Class configuring initialization of root and dispatcher servlet contexts
public class WebServiceInitializer extends AbstractAnnotationConfigMessageDispatcherServletInitializer {

	public WebServiceInitializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class, HttpSessionConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { WebServiceConfig.class };
	}

}
