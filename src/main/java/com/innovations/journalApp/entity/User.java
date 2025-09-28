package com.innovations.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User{
	@Id
	private ObjectId id;
	@Version
	private long version;
	@NonNull
	@Indexed(unique = true)
	private String userName;
	@NonNull
	private String password;
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();

	private List<String> roles = new ArrayList<>();
}
