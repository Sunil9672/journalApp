package com.innovations.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.innovations.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
	public User findByUserName(String userName);

	public void deleteByUserName(String userName);
}
