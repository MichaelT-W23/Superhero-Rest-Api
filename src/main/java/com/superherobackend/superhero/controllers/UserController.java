package com.superherobackend.superhero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superherobackend.superhero.models.User;
import com.superherobackend.superhero.models.UserAddRequest;
import com.superherobackend.superhero.models.UserAuthRequest;
import com.superherobackend.superhero.models.UserDTO;
import com.superherobackend.superhero.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        
        if (user != null) {
            UserDTO userDTO = new UserDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserAuthRequest request) {
        User authenticatedUser = userService.authenticate(request.getUsername(), request.getPassword());
        
        if (authenticatedUser != null) {
            UserDTO userDTO = new UserDTO(authenticatedUser);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserAddRequest request) {
        userService.addUser(request.getName(), request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }
    
}
