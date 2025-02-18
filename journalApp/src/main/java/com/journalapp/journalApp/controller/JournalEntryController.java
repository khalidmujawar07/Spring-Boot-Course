package com.journalapp.journalApp.controller;

import com.journalapp.journalApp.entities.JournalEntry;
import com.journalapp.journalApp.entities.User;
import com.journalapp.journalApp.service.JournalEntryService;
import com.journalapp.journalApp.service.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController
{
    @Autowired
    private JournalEntryService journalEntryService;
    
    @Autowired
    private UserService userService;



//    Get All ----------------------------------------------------------------------------
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName)
    {
    	User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all !=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }





//  Post ------------------------------------------------------------------------------------
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName)
    {
        try
        {
        	
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }





// Get By Id ----------------------------------------------------------------------------------
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId)
    {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent())
        {
           return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }






//  Delete By Id --------------------------------------------------------------------------------
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName)
    {
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




//    Update By Id --------------------------------------------------------------------------------
	@PutMapping("id/{userName}/{myId}")
	public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry,
			@PathVariable String userName) {
		JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
		if (oldEntry != null) {
			oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
					: oldEntry.getTitle());
			;
			oldEntry.setContent(
					newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
							: oldEntry.getContent());

			journalEntryService.save(oldEntry);
			return new ResponseEntity<>(oldEntry, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

