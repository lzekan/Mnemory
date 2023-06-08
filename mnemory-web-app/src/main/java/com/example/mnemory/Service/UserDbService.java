package com.example.mnemory.Service;

import com.example.mnemory.DTO.UserDbDTO;
import com.example.mnemory.Entity.UserDb;
import com.example.mnemory.Repository.UserDbRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDbService {
    private final UserDbRepository userDbRepository;

    public UserDb findUser(long iduser) {
        return userDbRepository.findUserDbById(iduser);
    }

    public UserDb findUserByUsername(String username) {
        return userDbRepository.findUserDbByUsername(username);
    }

    public UserDb findUserByEmail(String email){
        return userDbRepository.findUserDbByEmail(email);
    }

    public Integer addNewUser(UserDbDTO newUser) {
        UserDb potentialUser = null;

        potentialUser = findUserByEmail(newUser.getEmail());

        if(potentialUser != null) {
            return null;
        }

        potentialUser = findUserByUsername(newUser.getUsername());

        if(potentialUser != null) {
            return null;
        }

        UserDb createNewUser = UserDb.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .build();


        userDbRepository.save(createNewUser);
        return Math.toIntExact(createNewUser.getId());
    }
}
