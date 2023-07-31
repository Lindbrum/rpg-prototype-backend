package it.dercole.prototypes.game_login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;

import it.dercole.prototypes.game_login.dao.PlayerAccountRepository;
import it.dercole.prototypes.game_login.service.PlayerAccountDetailsServiceImpl;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

	
	@Bean
 	public WebSecurityCustomizer webSecurityCustomizer() {
 		return (web) -> web.ignoring()
 		// Spring Security should completely ignore URLs for docs
 				//.requestMatchers("/auth/docs/**", "/auth/signup");
 		.requestMatchers("/auth/docs/**", "/h2-console/**");
 	}

	
	// @formatter:off
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/auth/signup").permitAll()
                                .anyRequest().authenticated()
                )
                //Setup CSRF for use in swagger-ui.
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                //Allow frames loading from same origin (required to run h2-console)
                .headers((headers) -> headers
                		.frameOptions().sameOrigin()
                )
                .requestCache((requestCache) -> requestCache
                                .requestCache(new NullRequestCache())
                )
                .httpBasic((httpCustomizer) -> httpCustomizer
                                .authenticationEntryPoint(new AuthEntrypoint())
                )
                .anonymous(anonymous -> anonymous.authorities("GUEST"))
                .sessionManagement((sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .logout((logout) -> logout
                				.logoutUrl("/auth/logout")
                                .logoutSuccessHandler(new LogoutSuccHandler())
                                .permitAll()
                )
                .build();
	}
	// @formatter:on
	
	//Setup the data source used in authentication
//	@Bean
//	DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//			.setType(EmbeddedDatabaseType.H2)
//			//.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION) //TODO Extended default schema to include email
//			.build();
//	}
	
	@Bean
	public UserDetailsService userDetailsService(PlayerAccountRepository repository) {
		return new PlayerAccountDetailsServiceImpl(repository);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(PlayerAccountRepository repository) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService(repository));
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth, PlayerAccountRepository repository) throws Exception {
		auth.authenticationProvider(authenticationProvider(repository));
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////		auth.inMemoryAuthentication()
////				.withUser(User.withUsername("user").password("{noop}password").roles("USER").build());
//		auth.jdbcAuthentication()
////		.dataSource(dataSource())
//		.passwordEncoder(passwordEncoder())
//		.withUser(PlayerAccountDetails.withUsername("ifrit").passwordEncoder((password)-> password).password("{bcrypt}$2a$10$pk5h5KNGecTHPoDrG6/ruewQqlIEpgkzKC1/gGRGQbr4SS/cwYScm").role("ADMIN").build());
//	}

}
