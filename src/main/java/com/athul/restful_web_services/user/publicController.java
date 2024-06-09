package com.athul.restful_web_services.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class publicController {

    @GetMapping("/public")
    public String show(){
        return "Public11";
    }

}
