package com.example.mnemory.Controller;

import com.example.mnemory.DTO.UserDbDTO;
import com.example.mnemory.Entity.UserDb;
import com.example.mnemory.Service.UserDbService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class UserDbController {
    @Autowired
    private final UserDbService userDbService;

    @GetMapping("/api/userById")
    private UserDb findUser(@RequestParam("iduser") long idUser) {
        return userDbService.findUser(idUser);
    }

    @GetMapping("/api/userByUsername")
    private UserDb findUserByUsername(@RequestParam("username") String username) {
        UserDb user = userDbService.findUserByUsername(username);
        return user;
    }

    @GetMapping("/api/userByEmail")
    private UserDb findUserByEmail(@RequestParam("email") String email){
        return userDbService.findUserByEmail(email);
    }

    @PostMapping("/api/user/add")
    private String addNewUser(@RequestBody UserDbDTO userDbDTO) {
        return userDbService.addNewUser(userDbDTO);
    }


}