package com.journalapp.journalApp.service;


import com.journalapp.journalApp.entities.JournalEntry;
import com.journalapp.journalApp.entities.User;
import com.journalapp.journalApp.repository.JournalEntryRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService
{
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
    	try 
    	{
//        	finding in DB and saving to journalEntry
        	User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved =  journalEntryRepository.save(journalEntry);
          
//            Saving in User
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
            
		} 
    	catch (Exception e) 
    	{
			System.out.println(e);
			throw new RuntimeException("An error occuring while saving the Entry...", e);
		}
    	
    	

    }
    
    public void save(JournalEntry journalEntry)
    {
 

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllData()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName)
    {
    	User user = userService.findByUserName(userName);
    	user.getJournalEntries().removeIf(x -> x.getId().equals(id));
    	userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
