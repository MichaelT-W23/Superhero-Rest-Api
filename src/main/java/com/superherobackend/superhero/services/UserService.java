package com.superherobackend.superhero.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superherobackend.superhero.models.User;
import com.superherobackend.superhero.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticate(String username, String unhashedPassword) {
        User user = userRepository.findByUsername(username);
        
        if (user != null && BCrypt.checkpw(unhashedPassword, user.getHashedPassword())) {
            return user;
        } 
        
        return null;
    }

    public void addUser(String name, String username, String unhashedPassword) {
        String hashedPassword = BCrypt.hashpw(unhashedPassword, BCrypt.gensalt(12));

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setHashedPassword(hashedPassword);

        userRepository.save(user);
    }
    
    public Long getMaxUserId() {
        return userRepository.findMaxUserId();
    }

    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
