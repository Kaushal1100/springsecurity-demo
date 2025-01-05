package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.MyUser;
import com.example.demo.repository.MyUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private MyUserRepository repository;
	@Override
	//Taking username from the user in this function and search in the database
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Check the repository with the userName
		Optional <MyUser> user=repository.findByUsername(username);
		if(user.isPresent()) { //if present provide the details
			var userObj=user.get();
			return User.builder()
	                .username(userObj.getUsername())
	                .password(userObj.getPassword())//Used online Bcrypt encoder for now
	                .roles(getRoles(userObj))//Because admin can have ADMIN and USER role
	                .build();
		}else {
			throw new UsernameNotFoundException(username);
		}
	
	}
	private String[] getRoles(MyUser user) {
		if(user.getRole()==null) {
			return new String[]{"USER"};
		}
		return user.getRole().split(",");
	}

}
