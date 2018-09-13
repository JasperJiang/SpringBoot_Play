package com.example.demo.controller;


import com.example.demo.vo.ValidInputVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Mappings.VALIDATOR)
public class ValidatorController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResponseEntity validatorTest(@Validated @RequestBody ValidInputVo validInputVo){
        return new ResponseEntity(validInputVo, HttpStatus.OK);
    }
}
