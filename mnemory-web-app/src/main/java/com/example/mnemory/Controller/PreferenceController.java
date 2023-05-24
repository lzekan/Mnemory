package com.example.mnemory.Controller;

import com.example.mnemory.Service.PreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
public class PreferenceController {
    private final PreferenceService preferenceService;

    @GetMapping("/preference/exampleByType")
    private String getPreferenceExampleByType(@RequestParam("type") String type){

        List<String> allExamples = preferenceService.getPreferenceExampleByType(type);
        Random rand = new Random();
        int upperbound = allExamples.size();
        return allExamples.get(rand.nextInt(upperbound)).split(",")[1];

    }

    @GetMapping("/preference/allPreferences")
    private List<String> getAllPreferences(){
        return preferenceService.getAllPreferences();
    }

    @PostMapping("/preference/addPreference")
    private String addPreferenceToUser(@RequestParam("idUser") int idUser, @RequestParam("idPref") int idPref){
        return preferenceService.addPreferenceToUser(idUser, idPref);
    }

    @PostMapping("/preference/removePreference")
    private String removePreferenceFromUser(@RequestParam("idUser") int idUser, @RequestParam("idPref") int idPref){
        return preferenceService.removePreferenceFromUser(idUser, idPref);
    }

}
