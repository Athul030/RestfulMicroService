package com.athul.restful_web_services;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/3")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/hello")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("hello");
    }

    @GetMapping("/hello/{name}")
    public HelloWorldBean helloWorldBeanPath(@PathVariable String name){
//        return new HelloWorldBean("hello"+name);
        return new HelloWorldBean(String.format("Hello %s",name));
    }

}
