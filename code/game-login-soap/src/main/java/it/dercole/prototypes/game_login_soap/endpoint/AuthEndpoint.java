package it.dercole.prototypes.game_login_soap.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import it.dercole.prototypes.game_login_soap.exception.WrongPasswordException;
import it.dercole.prototypes.game_login_soap.model.PlayerAccountDetails;
import it.dercole.prototypes.game_login_soap.service.PlayerAccountDetailsServiceImpl;
import it.dercole.prototypes.wsdltypes.ObjectFactory;
import it.dercole.prototypes.wsdltypes.Signup;
import it.dercole.prototypes.wsdltypes.SignupData;
import it.dercole.prototypes.wsdltypes.SignupResponse;

@Endpoint
public class AuthEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthEndpoint.class);
	
	private final PlayerAccountDetailsServiceImpl playerAccountService;
	
	public AuthEndpoint(PlayerAccountDetailsServiceImpl playerAccountServiceImpl) {
		this.playerAccountService = playerAccountServiceImpl;
	}

	@PayloadRoot(namespace = "http://dercole.it/prototypes/wsdltypes", localPart = "signup")
	@ResponsePayload
	public SignupResponse signup(@RequestPayload Signup req) {
		LOGGER.info("**** 'AuthEndpoint' RECEIVED A REQUEST FOR 'login(username={}, password={}, confirm={}, email={}'"
				, req.getUsername(), req.getPassword(), req.getPasswordConfirm(), req.getEmail());
		
		//Check that the password matches
		if(!req.getPassword().equals(req.getPasswordConfirm())) {
			throw new WrongPasswordException("Password confirmation doesn't match.");
		}
		
		//Create the new account
		PlayerAccountDetails account = (PlayerAccountDetails) PlayerAccountDetails.withUsername(req.getUsername())
				.password(req.getPassword())
				.email(req.getEmail())
				.role("USER")
				.build();
		
		LOGGER.info("***Password (length={}): {}", account.getPassword().length(), account.getPassword());
		playerAccountService.create(account.getAccount());
		
		//Prepare the response
		ObjectFactory factory = new ObjectFactory();
		SignupResponse response = factory.createSignupResponse();
		SignupData responseData = factory.createSignupData();
		responseData.setMessage("Account successfully registered!");
		response.setReturn(responseData);
		
		return response;
	}
	

//	@PayloadRoot(namespace = "http://dercole.it/prototypes/wsdltypes", localPart = "login")
//	@ResponsePayload
//	public LoginResponse login(@RequestPayload Login request) {
//		LOGGER.info("**** 'AuthEndpoint' RECEIVED A REQUEST FOR 'login(username={}, password={}'", request.getUsername(), request.getPassword());
//
//		//Lookup the account
//		PlayerAccount account = playerAccountService.lookup(request.getUsername());
//		
//		//Check that the password match using BCrypt, if not return 404
//		if(!BCrypt.checkpw(request.getPassword(), account.getPassword())) {
//			throw new WrongPasswordException();
//		}
//		//Password is correct, prepare the session
//		
//		LoginData res = null;
//		
//		
//
//		ObjectFactory factory = new ObjectFactory();
//		LoginResponse response = factory.createLoginResponse();
//		response.setReturn(res);
//
//		LOGGER.info("**** 'AuthEndpoint' IS GOING TO SEND THE RESULT OF THE 'sum()' OPERATION ='{}'",
//				response.getReturn());
//
//		return response;		
//	}
}
