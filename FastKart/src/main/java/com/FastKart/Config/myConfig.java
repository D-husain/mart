package com.FastKart.Config;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myConfig {

	// 3 important bean that is mendatory in every spring boot login configuration
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public UserDetailsService userDetailsService(){
			return new userDetailsImple();
			

		}

		public DaoAuthenticationProvider authenticationProvider() {

			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(this.userDetailsService());
			daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

			return daoAuthenticationProvider;
		}

	// configure method 

		
		/*
		 * protected void configure(AuthenticationManagerBuilder auth) throws Exception
		 * { auth.authenticationProvider(authenticationProvider()); }
		 */
		 

		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasRole("USER")
					.requestMatchers("/**").permitAll()
					.requestMatchers("/css/**").permitAll()
					.requestMatchers("/image/**").permitAll()
					.requestMatchers("/do_register").permitAll()
					.requestMatchers("/index").permitAll()
			)
			.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/")
					//.failureUrl("/login")
					//.failureHandler((request, response, exception) -> System.out.println(exception))
					.permitAll()
					
					 ) 
			.oauth2Login ( oauth2Login -> oauth2Login .loginPage("/login")
			
		            .failureHandler((request, response, exception) -> {
		                String errorMessage = "Invalid username or password";
		                request.getSession().setAttribute("errorMessage", errorMessage);
		                response.sendRedirect("/login?error=true");
		            })
			)
			
			.logout(logout -> logout
				       .logoutUrl("/logout")
				       .logoutSuccessUrl("/login")
				       .invalidateHttpSession(true)
				       .deleteCookies("JSESSIONID") 
			)
			
			.csrf(AbstractHttpConfigurer::disable);

			return http.build();

		}
		
}
