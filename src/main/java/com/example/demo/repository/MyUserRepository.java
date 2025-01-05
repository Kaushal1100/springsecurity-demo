package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser,Long> {

		//provide the function to find the stored user by userName
	 Optional<MyUser> findByUsername(String username);
}
