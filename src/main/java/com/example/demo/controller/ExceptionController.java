package com.example.demo.controller;

import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Mappings.EXCEPTION)
public class ExceptionController {

    @RequestMapping(value = "/noFound",method = RequestMethod.GET)
    public void noFound(){
        throw new NotFoundException("error.noFoundException");
    }

    @RequestMapping(value = "/duplicate",method = RequestMethod.GET)
    public void duplicate(){
        throw new DuplicateException("error.duplicateException");
    }
}
