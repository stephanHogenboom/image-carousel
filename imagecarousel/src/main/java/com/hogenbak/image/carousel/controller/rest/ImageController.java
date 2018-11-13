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
        Path imageFolderPath = Paths.get("/images/test/");
        response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try {
            if (Files.exists(imageFolderPath)) {
                Path imagePath = imageFolderPath.resolve(Paths.get(imageId));
                if (Files.exists(imagePath)) {
                    response.getOutputStream().write(Files.readAllBytes(imagePath));
                } else {
                    response.setStatus(404);
                }

            }else {
                response.setStatus(400);
            }
        } catch (IOException e) {

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
