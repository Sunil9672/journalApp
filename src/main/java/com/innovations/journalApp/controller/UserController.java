package com.innovations.journalApp.controller;

import com.innovations.journalApp.entity.QuoteResponse;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.service.QuoteService;
import com.innovations.journalApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	UserService userService;

    private final QuoteService quoteService;;

	@GetMapping
	public ResponseEntity<?> getUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

        String quote = quoteService.getQuoteOfTheDay();

        if (quote != null) {
            return new ResponseEntity<>("Hello "+userInDb.getUserName()+", quote of the day for you: " + quote, HttpStatus.OK);
        } else if(userInDb != null) {
            return new ResponseEntity<>("Hello "+userInDb.getUserName(), HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
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
