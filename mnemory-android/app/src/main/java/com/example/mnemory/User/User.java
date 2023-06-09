package com.example.mnemory.User;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String username;

    private String password;
    private String email;

    private List<String> preferences;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences){
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Example constructor
    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.preferences = new ArrayList<>();
    }

//    public boolean equals(UserDTO userDTO){
//         return this.getUsername().equals(userDTO.getUsername()) &&
//                this.getEmail().equals(userDTO.getEmail()) &&
//                    this.getPassword().equals(userDTO.getPassword());
//
//    }


}
