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

    public String updateUser(UserDb updatedUser) {

        UserDb currentUser = userDbRepository.findUserDbById(updatedUser.getId());

        if(currentUser.isEqual(updatedUser)){
            return "Niste unijeli nijedan drukčiji podatak.";
        }

        if(findUserByUsername(updatedUser.getUsername()) != null &&
                findUserByUsername(updatedUser.getUsername()).getId() != currentUser.getId()){
            return "Korisničko ime se već koristi.";
        }

        if(findUserByEmail(updatedUser.getEmail()) != null &&
                findUserByEmail(updatedUser.getEmail()).getId() != currentUser.getId()){
            return "E-mail adresa je već u uporabi.";
        }

        userDbRepository.updateUserById(Math.toIntExact(updatedUser.getId()), updatedUser.getUsername(),
                updatedUser.getPassword(), updatedUser.getEmail());

        return "Korisnikovi podatci su uspješno ažurirani.";



    }
}
