package com.example.mnemory.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Word {
    private String wordName;
    private String defaultWord;

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    private String wordType;

    public String getWordName() {
        return wordName;
    }

    public String getDefaultWord() {
        return defaultWord;
    }

    public String getWordType() {
        return wordType;
    }

    @Id
    private Long id;

    public Word(String wordname, String defaultword, String wordtype){
        this.wordName = wordname;
        this.defaultWord = defaultword;
        this.wordType = wordtype;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
