package com.innovations.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("Username not found: " + username);
		}

		return org.springframework.security.core.userdetails.User.builder().username(user.getUserName())
				.password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
	};
}
