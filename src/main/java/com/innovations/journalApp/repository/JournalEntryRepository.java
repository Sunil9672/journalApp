package com.innovations.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.innovations.journalApp.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
