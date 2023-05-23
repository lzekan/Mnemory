package com.example.mnemory.Service;

import com.example.mnemory.Repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public List<String> getTemplateByLength(int length){
        return templateRepository.getTemplateByLength(length);
    }
}
