package com.journalapp.journalApp.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.journalapp.journalApp.entities.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId>
{
	 Optional<JournalEntry> findById(ObjectId id);
	
}
