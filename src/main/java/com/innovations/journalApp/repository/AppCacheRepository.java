package com.innovations.journalApp.repository;

import com.innovations.journalApp.cache.AppCache;
import com.innovations.journalApp.entity.AppCacheEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

public interface AppCacheRepository extends MongoRepository<AppCacheEntity, ObjectId> {
}
