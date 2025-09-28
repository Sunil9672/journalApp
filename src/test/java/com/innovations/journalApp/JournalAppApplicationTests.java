package com.innovations.journalApp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.repository.UserRepository;
import com.innovations.journalApp.service.UserService;

//@SpringBootTest
class JournalAppApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@ParameterizedTest
	@CsvSource({ "ram", "Sunil" })
	void userNameTest(String name) {
		User user = null;
		try {
			user = userRepository.findByUserName(name);
		} catch (Exception e) {
		}

		assertNotNull(user, "Failed for userName: " + name);
	}

	@ParameterizedTest
	@ValueSource(strings = { "ram", "Shan" })
	void userValueNameTest(String name) {

		User user = null;
		try {
			user = userRepository.findByUserName(name);
		} catch (Exception e) {

		}

		assertNotNull(user, "Failed for userName: " + name);
	}

	@ParameterizedTest
	@ArgumentsSource(UserArgumentSource.class)
	void userNameStoreTest(User user) {

		if (user.getRoles().contains("ADMIN")) {
			assertTrue(userService.saveAdminUser(user), "Failed for userName: " + user.getUserName());
		} else {
			assertTrue(userService.saveNewUser(user), "Failed for userName: " + user.getUserName());
		}

	}

}
