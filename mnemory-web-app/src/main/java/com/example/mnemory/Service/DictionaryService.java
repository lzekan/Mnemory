package com.example.mnemory.Service;

import com.example.mnemory.Entity.Word;
import com.example.mnemory.Repository.DictionaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public List<String> getWordsByFirstLetter(String firstletter){
        return dictionaryRepository.getWordsByFirstLetter(firstletter);
    }


}
