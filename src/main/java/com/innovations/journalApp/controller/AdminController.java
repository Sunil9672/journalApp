package com.innovations.journalApp.controller;

import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
