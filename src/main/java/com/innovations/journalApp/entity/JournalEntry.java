package com.innovations.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "journal_entry")
@Data
public class JournalEntry {

	@Id
	private ObjectId id;
	private String title;
	private String content;
	private LocalDateTime createdTime;
}
