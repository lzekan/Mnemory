package com.example.mnemory.Entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDb {

    private String username;
    private String password;
    private String email;
    @jakarta.persistence.Id
    private Long idUser;

    public UserDb(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String toString(){
        return idUser + " " + username + " " + email;
    }

}
