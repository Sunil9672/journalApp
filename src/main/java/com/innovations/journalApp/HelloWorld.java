package com.innovations.journalApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@GetMapping("/helloWorld")
	public void helloWorld() {
		System.out.println("HELLO TO THE WORLD!");
	}
}
