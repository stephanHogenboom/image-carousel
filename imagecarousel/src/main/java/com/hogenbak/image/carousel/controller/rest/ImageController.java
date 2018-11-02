package com.hogenbak.image.carousel.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageController {

    @GetMapping("/images/{imageId}")
    public void getImageById(@PathVariable String imageId, HttpServletResponse response) {
        Path samedi = Paths.get("/imagecarousel/src/main/resources/samedi.jpg");
        try {
            response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.getOutputStream().write(Files.readAllBytes(samedi));
        } catch (IOException e) {
            System.out.println("shit gone seriously wrong brah");
            e.printStackTrace();
        }
    }
}
