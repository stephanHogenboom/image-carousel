package com.hogenbak.image.carousel.controller.rest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    @GetMapping("/images/{imageId}")
    @CrossOrigin
    public void getImageById(@PathVariable String imageId, HttpServletResponse response) {
        Path samedi = Paths.get("/imagecarousel/src/main/resources/samedi.jpg");
        try {
            System.out.println("getting that image brosef!");
            response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.getOutputStream().write(Files.readAllBytes(samedi));
        } catch (IOException e) {
            System.out.println("shit gone seriously wrong brah");
            e.printStackTrace();
        }
    }

    @PostMapping("/images/new/")
    @CrossOrigin
    public void uploadNewImage(@RequestParam(value="name") String name, HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream is = request.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(9000);
            IOUtils.copy(is, baos);
            Files.createDirectories(Paths.get("images/test/"));
            Files.write(Paths.get(String.format("images/test/%s", name)), baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
