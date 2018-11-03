package com.hogenbak.image.carousel.controller.rest;

import com.hogenbak.image.carousel.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/images/{imageId}")
    @CrossOrigin
    public void getImageById(@PathVariable String imageId, HttpServletResponse response) {
        Path samedi = Paths.get("/imagecarousel/src/main/resources/samedi.jpg");
        try {
            response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.getOutputStream().write(Files.readAllBytes(samedi));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/images/new/")
    @CrossOrigin
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String fileName = fileStorageService.storeFile(file);
        } catch (Exception e) {
            System.out.println("The file was not a multi part file!" + e.getMessage());
        }
    }
}
