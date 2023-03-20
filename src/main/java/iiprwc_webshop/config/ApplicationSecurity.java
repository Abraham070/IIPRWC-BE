package iiprwc_webshop.config;

import iiprwc_webshop.DAO.UserRepository;
import iiprwc_webshop.services.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurity {

	@Autowired private UserRepository userRepo;
	@Autowired private JwtTokenFilter jwtTokenFilter;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(
				username -> userRepo.findByEmail(username)
				.orElseThrow(() ->
						new UsernameNotFoundException("User " + username + " not found.")
				)
		);

		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeRequests(auth->{
					auth.antMatchers("/**").permitAll();
					auth.antMatchers("/api/**").authenticated();
				})
				.exceptionHandling(a -> a.authenticationEntryPoint(
					(request, response, ex) -> {
						response.sendError(
								HttpServletResponse.SC_UNAUTHORIZED,
								ex.getMessage()
						);
					}
				))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
