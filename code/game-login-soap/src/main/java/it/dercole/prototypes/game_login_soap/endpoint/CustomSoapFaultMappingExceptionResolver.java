package it.dercole.prototypes.game_login_soap.endpoint;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

public class CustomSoapFaultMappingExceptionResolver extends SoapFaultMappingExceptionResolver {

	public CustomSoapFaultMappingExceptionResolver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		
		SoapFaultDefinition defaultFaultDefinition  = new SoapFaultDefinition();
		defaultFaultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		defaultFaultDefinition.setFaultStringOrReason("500 - Unexpected error has occured.");
		setDefaultFault(defaultFaultDefinition);
		
	}
	
	

}
