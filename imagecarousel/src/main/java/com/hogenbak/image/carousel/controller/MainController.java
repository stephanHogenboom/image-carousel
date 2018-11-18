package com.hogenbak.image.carousel.controller;

import com.hogenbak.image.carousel.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping("/")
    public String getMainMenu(Model model) {
        List<String> imageNames = fileStorageService.getImagesNamesInBaseFolder();
        model.addAttribute("imageNames", imageNames);
        return "main_menu";
    }
}
