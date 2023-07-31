package it.dercole.prototypes.game_login_soap;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

//Tomcat will call the default constructor and initialize our HttpSession with redis
public class HttpSessionInitializer extends AbstractHttpSessionApplicationInitializer {
	
}
