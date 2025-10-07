package com.innovations.journalApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.innovations.journalApp.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.innovations.journalApp.entity.JournalEntry;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.repository.JournalEntryRepository;
import com.innovations.journalApp.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public boolean saveNewUser(User user) {
		boolean saved = false;
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setJournalEntries(new ArrayList<>());
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			saved = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saved;
	}

	public boolean saveAdminUser(User user) {
		boolean saved = false;
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setJournalEntries(new ArrayList<>());
			user.setRoles(Arrays.asList("ADMIN", "USER"));
			userRepository.save(user);
			saved = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saved;
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public void deleteByUserName(String username) {
		userRepository.deleteByUserName(username);
	}

	public void deleteUser(ObjectId myId) {
		userRepository.deleteById(myId);
	}
}
