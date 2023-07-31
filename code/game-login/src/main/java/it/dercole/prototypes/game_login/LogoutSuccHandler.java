package it.dercole.prototypes.game_login;

import java.io.IOException;
import java.io.Writer;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dercole.prototypes.game_login.model.HttpStatusData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutSuccHandler implements LogoutSuccessHandler {

	public LogoutSuccHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		//Set 401 unauthorized so that browsers clear the auth cache
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		HttpStatusData jsonData = new HttpStatusData(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), "Logged out.");
		ObjectMapper mapper = new ObjectMapper();
		Writer writer = response.getWriter();
		writer.write(mapper.valueToTree(jsonData).toPrettyString());

	}

}
