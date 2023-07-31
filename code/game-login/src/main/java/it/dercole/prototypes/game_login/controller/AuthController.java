package it.dercole.prototypes.game_login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dercole.prototypes.game_login.exception.MalformedRequestException;
import it.dercole.prototypes.game_login.model.HttpStatusData;
import it.dercole.prototypes.game_login.model.PlayerAccount;
import it.dercole.prototypes.game_login.service.PlayerAccountDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	//Service (injected)
	private final PlayerAccountDetailsServiceImpl playerAccountService;
	
	public AuthController(PlayerAccountDetailsServiceImpl playerAccountServiceImpl) {
		this.playerAccountService = playerAccountServiceImpl;
	}
	
	@Operation(summary = "Login a new player. Note that the endpoint doesn't expect a body or query parameters as the authentication is handled by Spring Security.\n"
			+ "Instead you are expected to use a basic authentication header providing username and password with realm \"Lindbrum game\"")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "The player has been authenticated.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HttpStatusData.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "401",
				  description = "Authentication failed or not initiated.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@PostMapping("/login")
	public ResponseEntity<HttpStatusData> login(){
		
		//Authentication is handled by Spring Security in a servlet filter,
		//returning 401 unauthorized before reaching this endpoint if authentication fails.
		//Thus, we just answer with 200 OK
		return new ResponseEntity<>(new HttpStatusData(HttpStatus.OK, HttpStatus.OK.value(), "Logged in. Welcome!"), HttpStatus.OK);
	}
	
	//Logout is handled entirely in Spring Security
	//This endpoint is only here to expose the info in swagger-ui
	@PostMapping("/logout")
	public void logout() {
		//nothing
	}

	@Operation(summary = "Register a new player.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "The account has been successfully created.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HttpStatusData.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Incomplete request body (missing one or more of the schema fields)", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "409",
				  description = "An account already exists under the username and/or email provided.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@PostMapping("/signup")
	public ResponseEntity<HttpStatusData> signup(@RequestBody PlayerAccount account) {
		
		//check if the body is complete
		if(account.getUsername() == null || account.getUsername().isBlank() 
				|| account.getPassword() == null || account.getPassword().isBlank()
				|| account.getPasswordConfirm() == null || account.getPasswordConfirm().isBlank()
				|| account.getEmail() == null || account.getEmail().isBlank()) {
			
			throw new MalformedRequestException("The request body is incomplete and could not be processed. Fields 'username', 'password', 'passwordConfirm' and 'email' are all required.");
		}
		
		LOGGER.info("**** 'AuthEndpoint' RECEIVED A REQUEST FOR 'signup(account={}'", account.toString());
		
		//Create the new account
//		PlayerAccountDetails accountDetails = (PlayerAccountDetails) PlayerAccountDetails.withUsername(account.getUsername())
//				.password(account.getPassword())
//				.email(account.getEmail())
//				.role("USER")
//				.build();
		
//		LOGGER.info("***Password (length={}): {}", accountDetails.getPassword().length(), accountDetails.getPassword());
//		playerAccountService.create(accountDetails.getAccount());
		
		playerAccountService.create(account);
		
		return ResponseEntity.ok(new HttpStatusData(HttpStatus.OK, HttpStatus.OK.value(), "Account successfully created. Welcome!"));
	}
}
