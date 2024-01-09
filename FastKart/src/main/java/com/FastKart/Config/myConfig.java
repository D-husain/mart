package com.FastKart.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class myConfig {
	
		@Autowired
		private DataSource dataSource;

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
		public PersistentTokenRepository persistentTokenRepository() {
			JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
			tokenRepo.setDataSource(dataSource);
			return tokenRepo;
		}

		 @Bean
		    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		        http
		            .authorizeHttpRequests(authorize -> authorize
		                .requestMatchers("/admin/**").hasRole("ADMIN")
		                .requestMatchers("/user/**").hasRole("USER")
		                .requestMatchers("/**").permitAll()
		                .requestMatchers("/css/**").permitAll()
		                .requestMatchers("/image/**").permitAll()
		                .requestMatchers("/do_register").permitAll()
		                .requestMatchers("/").permitAll()
		            )
		            .formLogin(form -> form
		                .loginPage("/login")
		                .defaultSuccessUrl("/")
		                .permitAll()
		            )
		            
		            .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
		                .failureHandler((request, response, exception) -> {
		                    String errorMessage = "Invalid username or password";
		                    request.getSession().setAttribute("errorMessage", errorMessage);
		                    response.sendRedirect("/login?error=true");
		                })
		            )
		            .logout(logout -> logout
		                .logoutUrl("/logout")
		                .logoutSuccessUrl("/login?logout=true")
		                .invalidateHttpSession(true)
		                .deleteCookies("JSESSIONID")
		            )
		            
		            .csrf(AbstractHttpConfigurer::disable);

		        return http.build();
		    }
		
}
