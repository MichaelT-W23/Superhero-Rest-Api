package com.superherobackend.superhero.models;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String name;
    private String username;
    
    // Get user without password 
    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.username = user.getUsername();
    }
}
