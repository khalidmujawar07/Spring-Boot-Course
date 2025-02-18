package com.journalapp.journalApp.service;


import com.journalapp.journalApp.entities.User;
import com.journalapp.journalApp.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService
{
    @Autowired
    private UserRepository userRepository;


    public void saveEntry(User user)
    {
 
    	userRepository.save(user);
    }

    public List<User> getAllData()
    {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id)
    {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id)
    {
    	userRepository.deleteById(id);
    }
    
    public User findByUserName(String userName) 
    {
    	return userRepository.findByUserName(userName);
    }
    
}
