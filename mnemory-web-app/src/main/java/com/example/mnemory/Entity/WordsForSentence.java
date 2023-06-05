package com.example.mnemory.Entity;

import java.util.List;

public class WordsForSentence {
    private List<String> wordTypes;
    private List<String> words;

    public WordsForSentence(List<String> wordTypes, List<String> words){
        this.wordTypes = wordTypes;
        this.words = words;
    }

    public List<String> getWordTypes() {
        return wordTypes;
    }

    public List<String> getWords() {
        return words;
    }

}
