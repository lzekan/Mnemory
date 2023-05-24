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

    public boolean checkIfAlreadyHasPreference(int idUser, int idPreference){
        String has = preferenceRepository.checkIfUserAlreadyHasPreference(idUser, idPreference);

        if(has != null){
            return true;
        }

        return false;
    }

    public String addPreferenceToUser(int idUser, int idPreference){

        if(!checkIfAlreadyHasPreference(idUser, idPreference)){

            preferenceRepository.addPreferenceToUser(idUser, idPreference);
            return "Korisnikova preferirana tema uspjesno ubacena u bazu.";
        }

        return "Korisnik vec preferira navedenu temu.";
    }

    public String removePreferenceFromUser(int idUser, int idPreference){

        if(checkIfAlreadyHasPreference(idUser, idPreference)){
            preferenceRepository.removePreferenceFromUser(idUser, idPreference);
            return "Uspjesno uklonjena preferirana tema.";
        }

        return "Korisnik ne preferira navedenu temu.";

    }
}
