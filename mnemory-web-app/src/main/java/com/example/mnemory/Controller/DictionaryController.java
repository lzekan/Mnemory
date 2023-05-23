package com.example.mnemory.Controller;

import com.example.mnemory.Entity.Word;
import com.example.mnemory.Service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/api/dictionary")
    private Word getWordFromDictionary(@RequestParam("firstletter") String firstletter, @RequestParam("wordtype") String wordtype){

        List<String> wordsFirstLetter = dictionaryService.getWordsByFirstLetter(firstletter);
        List<Word> wordsThatFit = new ArrayList<>();
        Random rand = new Random();

        for(String word : wordsFirstLetter){
            String[] wordArr = word.split(",");

            if(wordArr[2].equals(wordtype)){
               wordsThatFit.add(new Word(wordArr[0], wordArr[1], wordArr[2]));
            }
        }

        return wordsThatFit.get(rand.nextInt(wordsThatFit.size()));

    }


}
