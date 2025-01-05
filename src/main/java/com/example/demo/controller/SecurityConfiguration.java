package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.MyUserDetailService;


@Configuration
@EnableWebSecurity //Enabling Spring Security
public class SecurityConfiguration {
	
	 @Autowired
	 private MyUserDetailService userDetailService;
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
	               .formLogin(formLogin-> formLogin.permitAll())
	               .build();
	                
	    }
//	    @Bean
//	 	commentedoutafter Inmemoryexample
//	    public UserDetailsService userDetailsService() {
//	    	//Creating Normal user for the access with username,password and the role which was defined in line 25-27 
//	    	UserDetails normalUser = User.builder() 
//	                .username("gc")
//	             // Encoding of the password to make is secured and avoid easy access to it: using Password Encoder:below
//	                .password() //Used online Bcrypt encoder for now
//	                .roles("USER")
//	                .build();
//	        UserDetails adminUser = User.builder()
//	                .username("admin")
//	                .password("$2a$12$4MVGfzHJ2C370at3MTGHdeX6z/kon2X5KbVWZTGfqjWBhj.KnQBuC")//Used online Bcrypt encoder for now
//	                .roles("ADMIN", "USER")//Because admin can have ADMIN and USER role
//	                .build();
//	        return new InMemoryUserDetailsManager(normalUser, adminUser); //Accepts UserDetail as the argument
//	    }
	    
	    //PasswordEncoder to create secure password: has many methods. This is one of them
	    
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public UserDetailsService userDetailsService() { //Connecting with the SecurityConfiguration using DI line 24)
	        return userDetailService;
	    }
	    
	    @Bean
	    public AuthenticationProvider authenticationProvider() {
	    	 //DataAccessObject loading Users from the Database or dataaccess layer and authentication
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //From SpringSecurityCore
	        provider.setUserDetailsService(userDetailService);
	        provider.setPasswordEncoder(passwordEncoder()); //Tell what kind of passwordencoder used
	        return provider;
	    }
}
