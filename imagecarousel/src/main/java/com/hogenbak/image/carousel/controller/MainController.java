package com.hogenbak.image.carousel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String getMainMenu() {
        return "main_menu";
    }

}
