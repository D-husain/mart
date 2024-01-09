package com.FastKart.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myConfig {
	

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
		

		 @Bean
		    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		        http
		            .authorizeHttpRequests(authorize -> authorize
		                .requestMatchers("/admin").hasRole("ADMIN")
		                .requestMatchers("/").hasRole("USER")
		                .requestMatchers("/**").permitAll()
		                .requestMatchers("/css/**").permitAll()
		                .requestMatchers("/image/**").permitAll()
		            )
		            .formLogin(form -> form
		                .loginPage("/login")
		                .defaultSuccessUrl("/")
		                .permitAll()
		            )
		            
		            .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
		                .failureHandler((request, response, exception) -> {
		                    response.sendRedirect("/login?error=true");
		                })
		            )
	            
		            .csrf(AbstractHttpConfigurer::disable);

		        return http.build();
		    }
		
}
