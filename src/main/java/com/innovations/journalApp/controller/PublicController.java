package com.innovations.journalApp.controller;

import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	UserService userService;

	@GetMapping("health-check")
	public String healthCheck() {
		return "Ok";
	}

	@PostMapping("create-user")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		userService.saveNewUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
