package com.innovations.journalApp;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.repository.UserRepository;
import com.innovations.journalApp.service.UserDetailsServiceImpl;


class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;
    
    @BeforeEach
    void initMock() {
    	MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("ram")
                        .password("adsfdfs")
                        .roles(new ArrayList<>())
                        .build());

        UserDetails user = userDetailsServiceImpl.loadUserByUsername("ram");

        assertNotNull(user);
    }
}
