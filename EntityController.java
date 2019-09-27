package controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import model.EntityRequest;

@RestController
public class EntityController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired EntityGenerator entityGenerator;

    @RequestMapping("/greeting")
    public EntityRequest greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new EntityRequest("Core", "contact", "Person","Filename");
    }

//    @PostMapping("/getEntity")
    @RequestMapping(value = "/getEntity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "*")
    public String getEntity(@RequestBody EntityRequest request) throws  IOException{
        try {
            entityGenerator.generateEntity(request.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        System.out.println("Received entity request::::"+request.toString());
        return "Generated Entity Class File:- C:/EntityGenerator/JavaEntities/"+entityGenerator.getGeneratedClassName()+".java";
    }
}
