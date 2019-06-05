package com.hogenbak.image.carousel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CMSController {

    @RequestMapping("/cms")
    public String index(){
        return "index";
    }
}
