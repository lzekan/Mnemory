package com.example.mnemory.Controller;

import com.example.mnemory.Entity.Word;
import com.example.mnemory.Entity.WordsForSentence;
import com.example.mnemory.Service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@RestController
@AllArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/api/dictionary")
    private String getSentenceFromDictionary(@RequestParam List<String> userWords, @RequestParam List<String> wordTypes){

        StringBuilder sentence = new StringBuilder();

        for(int i = 0; i < userWords.size(); i++){
            String firstLetter = userWords.get(i).substring(0, 1);
            Word word = getWordFromDictionary(firstLetter, wordTypes.get(i));

            if(word.getWordType().equals("imenica")){
                word.setWordName(word.getDefaultWord());
            }

            if(word.getWordType().equals("glagol")){
                word.setWordName(word.getDefaultWord().substring(0, word.getDefaultWord().length() - 2));
            }

            sentence.append(word.getWordName() + " ");
        }

        sentence = sentence.replace(0, 1, sentence.substring(0, 1).toUpperCase());
        return sentence.toString();
    }

    private Word getWordFromDictionary(String firstletter, String wordtype){

        List<String> wordsFirstLetter = dictionaryService.getWordsByFirstLetter(firstletter);
        List<Word> wordsThatFit = new ArrayList<>();
        Random rand = new Random();

        for(String word : wordsFirstLetter){
            String[] wordArr = word.split(",");

            if(wordArr[2].equals(wordtype)){
               wordsThatFit.add(new Word(wordArr[0], wordArr[1], wordArr[2]));
            }
        }

        return wordsThatFit.get(Math.abs(rand.nextInt(wordsThatFit.size())));

    }





}
