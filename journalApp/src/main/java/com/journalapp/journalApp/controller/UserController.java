package com.journalapp.journalApp.controller;

import com.journalapp.journalApp.entities.User;
import com.journalapp.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController

{

	
	
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){

        List<User> allUsers = userService.getAllData();
        if(allUsers !=null && !allUsers.isEmpty())
        {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void createUser(@RequestBody User user){
       userService.saveEntry(user);
    }


    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable String userName)
    {
    User userInDb = userService.findByUserName(userName);
    if(userInDb != null)
    {
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
    }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

