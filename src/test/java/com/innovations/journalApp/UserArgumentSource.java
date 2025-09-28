package com.innovations.journalApp;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.innovations.journalApp.entity.User;

public class UserArgumentSource implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

		return Stream.of(
				Arguments.of(
						User.builder().userName("Ghanshyam").password("ramram").roles(Arrays.asList("USER")).build()),
				Arguments.of(User.builder().userName("RadheRani").password("krishna")
						.roles(Arrays.asList("ADMIN", "USER")).build()));
	}
}
