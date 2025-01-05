package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MyUser;
import com.example.demo.repository.MyUserRepository;

@RestController
public class RegistrationController {

	@Autowired
	private MyUserRepository myUserRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register/user") //creating user
	public MyUser createUser(@RequestBody MyUser user) { //Receive a user and save to the DB and return
		user.setPassword(passwordEncoder.encode(user.getPassword())); //Encoding the password before saving
		return myUserRepository.save(user);
	}
}