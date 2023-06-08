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

    @GetMapping("api/preference/exampleByType")
    private String getPreferenceExampleByType(@RequestParam("type") String type){

        List<String> allExamples = preferenceService.getPreferenceExampleByType(type);
        Random rand = new Random();
        int upperbound = allExamples.size();
        return allExamples.get(rand.nextInt(upperbound)).split(",")[1];

    }

    @GetMapping("api/preference/allPreferences")
    private List<String> getAllPreferences(){
        return preferenceService.getAllPreferences();
    }

    @PostMapping("api/preference/addPreferences")
    private String addPreferencesToUser(@RequestParam("idUser") int idUser, @RequestParam("preferences") List<String> preferences){
        return preferenceService.addPreferencesToUser(idUser, preferences);
    }

    @PostMapping("api/preference/removePreference")
    private String removePreferenceFromUser(@RequestParam("idUser") int idUser, @RequestParam("preference") String preference){
        return preferenceService.removePreferenceFromUser(idUser, preference);
    }

}
