package com.kissheart.community.community.Contrller;

import org.springframework.web.bind.annotation.RequestMapping;

public class indexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
