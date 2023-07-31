package it.dercole.prototypes.game_login;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dercole.prototypes.game_login.model.HttpStatusData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Ovveride the Basic Authentication Entry Point to conform to REST
@Component
public class AuthEntrypoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
    	super.commence(request, response, authEx);
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        HttpStatusData json = new HttpStatusData(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value()
        		, "You are not authenticated. Please provide your username and password using basic authentication.");
        ObjectMapper mapper = new ObjectMapper();
        writer.println(mapper.valueToTree(json).toPrettyString());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("Lindbrum Game");
        super.afterPropertiesSet();
    }
}