package com.example.mnemory.Controller;

import com.example.mnemory.Entity.Word;
import com.example.mnemory.Entity.WordsForSentence;
import com.example.mnemory.Service.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/api/dictionary")
    private String getSentenceFromDictionary(@RequestParam List<String> userWords, @RequestParam List<String> wordTypes,
                                             @RequestParam List<String> userPreferences){

        StringBuilder sentence = new StringBuilder();
        boolean foundPref = false;

        if(userPreferences.size() == 0) {
            foundPref = true;
        }


        Collections.shuffle(userPreferences);

        for(int i = 0; i < userWords.size(); i++){

            String firstLetter = userWords.get(i).substring(0, 1);
            String wordType = wordTypes.get(i);

            if(wordType.equals("imenica")){

                if(!foundPref){
                    System.out.println("imenica, jos nema pref");

                    for(String preference : userPreferences){

                        String personalizedWord = getPersonalizedWordFromDictionary(firstLetter, preference);
                        System.out.println(personalizedWord);

                        if(!personalizedWord.equals("")){
                            sentence.append(personalizedWord + " ");
                            foundPref = true;
                            break;

                        }

                    }

                    if(!foundPref){
                        Word word = getWordFromDictionary(firstLetter, wordType);
                        sentence.append(word.getWordName() + " ");
                    }

                }
                else {
                    System.out.println("imenica, vec naso pref");
                    Word word = getWordFromDictionary(firstLetter, wordType);
                    sentence.append(word.getWordName() + " ");
                }

            } else {
                System.out.println(wordType);
                Word word = getWordFromDictionary(firstLetter, wordType);
                sentence.append(word.getWordName() + " ");
            }

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

        return wordsThatFit.get(Math.abs(rand.nextInt(wordsThatFit.size() == 0 ? 1 : wordsThatFit.size())));

    }

    private String getPersonalizedWordFromDictionary(String firstletter, String preferencetype){

        List<String> personalizedWordsThatFit = dictionaryService.getPersonalizedWordsByFirstLetter(firstletter, preferencetype);

        if(personalizedWordsThatFit.size() == 0) {
            return "";
        }

        Random rand = new Random();
        return personalizedWordsThatFit.get(Math.abs(rand.nextInt(personalizedWordsThatFit.size())));

    }





}
