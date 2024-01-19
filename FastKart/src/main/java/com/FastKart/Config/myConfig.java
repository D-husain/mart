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
		
		@Bean
		public UserDetailsService adminDetailsService(){
			return new adminDetailsImple();
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
		    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		    return daoAuthenticationProvider;
		}

		@Bean
		public DaoAuthenticationProvider adminAuthenticationProvider() {
		    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		    daoAuthenticationProvider.setUserDetailsService(adminDetailsService());
		    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		    return daoAuthenticationProvider;
		}

		

		@Bean
		public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
		    http
		    .authenticationProvider(authenticationProvider())
		        .authorizeHttpRequests(authorize -> authorize
		            .requestMatchers("/user/**").hasRole("USER")
		            .requestMatchers("/**","/css/**", "/image/**", "/do_register", "/").permitAll()
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

		@Bean
		public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
		    http
		    .authenticationProvider(adminAuthenticationProvider())
		        .authorizeHttpRequests(authorize -> authorize
		            .requestMatchers("/admin/**").hasRole("ADMIN")
		            .requestMatchers("/admin/assets/css/**", "/admin/assets/image/**", "/do_register").permitAll()
		        )
		        .formLogin(form -> form
		            .loginPage("/admin/login")
		            .defaultSuccessUrl("/admin/dashboard")
		            .permitAll()
		        )
		        .oauth2Login(oauth2Login -> oauth2Login.loginPage("/admin/login")
		            .failureHandler((request, response, exception) -> {
		                response.sendRedirect("/admin/login?error=true");
		            })
		        )
		        .csrf(AbstractHttpConfigurer::disable);

		    return http.build();
		}

		
}
