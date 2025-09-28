package com.innovations.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.management.RuntimeErrorException;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innovations.journalApp.entity.JournalEntry;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.repository.JournalEntryRepository;

@Component
@Log4j2
public class JournalEntryService {

	@Autowired
	JournalEntryRepository journalEntryRepository;

	@Autowired
	UserService userService;

	@Transactional
	public void saveEntry(String userName, JournalEntry journalEntry) {

		try {
			User user = userService.findByUserName(userName);
			journalEntry.setCreatedTime(LocalDateTime.now());
			JournalEntry entry = journalEntryRepository.save(journalEntry);

            System.out.println("JournalEntry saved: " + journalEntry);
            System.out.println("userEntry is: " + user);

			user.getJournalEntries().add(entry);

			userService.save(user);

		} catch (Exception e) {
			System.out.println("Got exception here: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAllEntries(String userName) {

		User user = userService.findByUserName(userName);

		List<JournalEntry> journalEntries = user.getJournalEntries();

		return journalEntries;
	}

	public JournalEntry getEntry(String userName, ObjectId myId) {
		User user = userService.findByUserName(userName);
		List<JournalEntry> journalEntries = user.getJournalEntries();

		JournalEntry entry = journalEntryRepository.findById(myId).orElse(null);

		if (entry != null && journalEntries.contains(entry)) {
			return entry;
		}

		return null;
	}

	public JournalEntry getEntry(ObjectId myId) {
		return journalEntryRepository.findById(myId).orElse(null);
	}

	@Transactional
	public void deleteEntry(String userName, ObjectId myId) {

		try {
			User user = userService.findByUserName(userName);
			List<JournalEntry> journalEntries = user.getJournalEntries();

			if (journalEntries.removeIf(x -> x.getId().equals(myId))) {
				journalEntryRepository.deleteById(myId);
				userService.save(user);
			}

		} catch (Exception e) {
			System.out.println("An error occurred here in transactional process");
			throw new RuntimeException(e);
		}
	}
}
