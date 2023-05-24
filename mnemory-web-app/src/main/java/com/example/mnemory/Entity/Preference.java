package com.example.mnemory.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Preference {

    @Id
    private Long idPreference;
    private String preferenceExample;
    private String preferenceType;

    public void setIdPreference(Long idPreference) {
        this.idPreference = idPreference;
    }

    public Long getIdPreference() {
        return idPreference;
    }
}
