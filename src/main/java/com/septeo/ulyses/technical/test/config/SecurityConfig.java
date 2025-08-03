package com.septeo.ulyses.technical.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//REQUERIMIENTO 1: Implement Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// REQUERIMIENTO 2
				.requestMatchers(HttpMethod.GET, "/**").permitAll()
				// REQUERIMIENTO 3
				.requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN").requestMatchers(HttpMethod.PUT, "/**")
				.authenticated().requestMatchers(HttpMethod.DELETE, "/**").authenticated())
				.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails userAdmin = User.withUsername("admin").password(passwordEncoder.encode("password")).roles("ADMIN")
				.build();

		UserDetails userCustomer = User.withUsername("cliente").password(passwordEncoder.encode("password"))
				.roles("USER").build();

		UserDetails userManager = User.withUsername("gerente").password(passwordEncoder.encode("password"))
				.roles("MANAGER").build();

		return new InMemoryUserDetailsManager(userAdmin, userCustomer, userManager);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
