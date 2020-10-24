package com.suidls.demo1;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloMethod(){

        return "Hello World";
    }



}
