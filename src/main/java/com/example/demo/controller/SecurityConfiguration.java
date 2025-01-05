package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity //Enabling Spring Security
public class SecurityConfiguration {
	
	
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        return httpSecurity
	                .csrf(AbstractHttpConfigurer::disable)
	                .authorizeHttpRequests(registry -> { //providing necessary customization using the default Spring security
	                    registry.requestMatchers("/home", "/register/**").permitAll(); //Allowing home page to be accessible by everyone
	                    registry.requestMatchers("/admin/**").hasRole("ADMIN"); //Allowing users with ADMIN role to access the page starting with /admin 
	                    registry.requestMatchers("/user/**").hasRole("USER"); //Normal user
	                    registry.anyRequest().authenticated(); //For other unspecified requests
	                })
	               
	                .build();
	    }

}
