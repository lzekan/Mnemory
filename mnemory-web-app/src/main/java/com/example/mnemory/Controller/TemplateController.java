package com.example.mnemory.Controller;

import com.example.mnemory.Service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    @GetMapping("/api/template")
    private String getTemplateByLength(@RequestParam("length") int length){

        List<String> templates = templateService.getTemplateByLength(length);
        Random rand = new Random();
        int upperbound = templates.size();
        return templates.get(rand.nextInt(upperbound));

    }


}
