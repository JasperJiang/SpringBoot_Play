package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(Mappings.ADMIN)
public class AdminController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity adminTest(){
        return new ResponseEntity("Success",HttpStatus.OK);
    }

}
