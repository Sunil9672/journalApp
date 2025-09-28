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
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@GetMapping("/all-user")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> entryList = userService.getAll();

		if (entryList != null && entryList.size() > 0) {
			return new ResponseEntity<List<User>>(entryList, HttpStatus.OK);
		}

		return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("create-admin-user")
	public ResponseEntity<?> creatAdminUser(@RequestBody User user) {
		userService.saveAdminUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
