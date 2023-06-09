package com.example.mnemory.Service;

import com.example.mnemory.Repository.PreferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public List<String> getPreferenceExampleByType(String preferenceType){
        return preferenceRepository.getPreferenceExampleByType(preferenceType);
    }

    public List<String> getAllPreferences(){
        return preferenceRepository.getAllPreferences();
    }

    public boolean checkIfAlreadyHasPreference(int idUser, String preference){
        String has = preferenceRepository.checkIfUserAlreadyHasPreference(idUser, preference);

        if(has != null){
            return true;
        }

        return false;
    }

    public String addPreferencesToUser(int idUser, List<String> preferences){

        for(String pref : preferences){
            if(!checkIfAlreadyHasPreference(idUser, pref)){
                preferenceRepository.addPreferenceToUser(idUser, pref);
                continue;
            }

            return "Korisnik već preferira navedenu temu.";
        }

        return "Korisnikova preferirana tema uspjesno ubacena u bazu.";
    }

    public String removePreferenceFromUser(int idUser, String preference){

        if(checkIfAlreadyHasPreference(idUser, preference)){
            preferenceRepository.removePreferenceFromUser(idUser, preference);
            return "Uspjesno uklonjena preferirana tema.";
        }

        return "Korisnik ne preferira navedenu temu.";

    }

    public List<String> getPreferencesById(int idUser){
        return preferenceRepository.getPreferencesById(idUser);
    }
}
