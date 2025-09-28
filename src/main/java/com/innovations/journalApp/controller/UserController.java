package com.innovations.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovations.journalApp.entity.JournalEntry;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.roles.Roles;
import com.innovations.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<User> getUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		if (userInDb != null) {
			return new ResponseEntity<User>(userInDb, HttpStatus.OK);
		}

		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		User userInDb = userService.findByUserName(userName);
		if (userInDb != null) {
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveNewUser(userInDb);
		}

		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		if (userInDb != null) {
			userService.deleteByUserName(userName);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<User>(userInDb, HttpStatus.NOT_FOUND);
	}
}
