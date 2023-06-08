package com.example.mnemory.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDb {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



    public UserDb(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String toString(){
        return id + " " + username + " " + email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isEqual(UserDb other){
        return this.username.equals(other.getUsername())
                && this.email.equals(other.getEmail())
                    && this.password.equals(other.getPassword());
    }
}
