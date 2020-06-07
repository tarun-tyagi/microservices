package com.rainbowforest.userservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowforest.userservice.model.User;
import com.rainbowforest.userservice.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping (value = "/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping (value = "/users", params = "name")
    public User getUserByName(@RequestParam("name") String userName){
        return userService.getUserByName(userName);
    }

    @GetMapping (value = "/users/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PostMapping (value = "/users")
    public ResponseEntity<User> addUser(@RequestBody User user, HttpServletRequest request) {
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
