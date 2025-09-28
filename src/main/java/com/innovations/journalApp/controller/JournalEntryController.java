package com.innovations.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.innovations.journalApp.entity.JournalEntry;
import com.innovations.journalApp.entity.User;
import com.innovations.journalApp.service.JournalEntryService;
import com.innovations.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryController {

	@Autowired
	UserService userService;

	@Autowired
	JournalEntryService journalEntryService;

	@GetMapping("/all")
	public ResponseEntity<List<JournalEntry>> getAllJournalEntry() {

        log.info("In get Mapping getAllJournalEntry");

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		List<JournalEntry> entryList = userInDb.getJournalEntries();

		if (entryList != null && entryList.size() > 0) {
			return new ResponseEntity<List<JournalEntry>>(entryList, HttpStatus.OK);
		}
		return new ResponseEntity<List<JournalEntry>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{myId}")
	public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId myId) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		List<JournalEntry> collected = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());

		if (collected.size() != 0) {
			return new ResponseEntity<JournalEntry>(collected.get(0), HttpStatus.OK);
		}

		return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry journalEntry) {

        log.info("Got a journal entry request, with body {}", journalEntry);

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();

		int maxRetries = 3;
		int currRetry = 0;
		while (currRetry < maxRetries) {

			try {
				journalEntryService.saveEntry(userName, journalEntry);
				return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
			} catch (OptimisticLockingFailureException exection) {
				System.out.println("Observed optimistic locking exception failure");
				currRetry++;
				if (currRetry == maxRetries) {
					System.out.println("Reached max retries while saving the entry: " + journalEntry);
					break;
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

		}

		return new ResponseEntity<JournalEntry>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("{myId}")
	public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

		ResponseEntity<JournalEntry> responseEntity = null;

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		System.out.println("Reached here: " + myId + ", " + newEntry + ", user: " + userInDb);

		List<JournalEntry> collected = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());

		System.out.println(collected);
		
		if (collected.size() > 0) {

			JournalEntry old = collected.get(0);

			if (newEntry.getTitle() != null && newEntry.getTitle().equals("")
					|| newEntry.getContent() != null && newEntry.getContent().equals("")) {
				return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
			} else {
				old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
						: old.getTitle());
				old.setContent(
						newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
								: old.getContent());

				old.setCreatedTime(LocalDateTime.now());
				journalEntryService.saveEntry(old);
				responseEntity = new ResponseEntity<JournalEntry>(old, HttpStatus.OK);
			}
		} else {
			responseEntity = new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@DeleteMapping("{myId}")
	public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User userInDb = userService.findByUserName(userName);

		List<JournalEntry> collected = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());

		if (collected.size() == 0) {
			return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
		}

		int maxRetries = 3;
		int currRetry = 0;
		while (currRetry < maxRetries) {
			try {
				journalEntryService.deleteEntry(userName, myId);
				return new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
			} catch (OptimisticLockingFailureException exection) {

				System.out.println("Observed optimistic locking exception failure");

				currRetry++;
				if (currRetry == maxRetries) {
					System.out.println("Reached max retries while deleting the entry with Id: " + myId);
					break;
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

		}

		return new ResponseEntity<JournalEntry>(HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
