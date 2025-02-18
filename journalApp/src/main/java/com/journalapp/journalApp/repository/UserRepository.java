package com.journalapp.journalApp.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalapp.journalApp.entities.JournalEntry;
import com.journalapp.journalApp.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
	User findByUserName(String username);
}
