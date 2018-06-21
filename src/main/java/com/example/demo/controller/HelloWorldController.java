package com.example.demo.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping(value = "/{keyWord}/world",method = RequestMethod.GET)
    public String helloWorld(@PathVariable("keyWord") String keyWord){
        return keyWord;
    }

}
