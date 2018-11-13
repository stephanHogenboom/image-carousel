package com.hogenbak.image.carousel.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogenbak.image.carousel.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(ImageController.class.getName());
    private final Path imagesBasePath = Paths
            .get(Optional.ofNullable(System.getenv("IMAGES_BASE_DIR")).orElse( "/images/test/"));
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/images/{imageId}")
    @CrossOrigin
    public void getImageById(@PathVariable String imageId, HttpServletResponse response) {
        response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try {
            if (Files.exists(imagesBasePath)) {
                Path imagePath = imagesBasePath.resolve(Paths.get(imageId));
                if (Files.exists(imagePath)) {
                    response.getOutputStream().write(Files.readAllBytes(imagePath));
                } else {
                    logger.warn(String.format("image with name %s not found", imageId));
                    response.setStatus(404);
                }
            }else {
                logger.error(String.format("map images does not exist with name %s not found", imageId));
                response.setStatus(500);
            }
        } catch (IOException e) {
            //TODO add logger to framework
            response.setStatus(500);
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("images/name/all")
    @CrossOrigin
    public void getImageNames(HttpServletResponse response) {
        if (Files.exists(imagesBasePath)) {
            try {
                List<String> filesNames = Files.list(imagesBasePath).map(Path::toString).collect(Collectors.toList());
                String fileNamesAsJson = mapper.writeValueAsString(filesNames);
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.getOutputStream().write(fileNamesAsJson.getBytes());
            } catch (IOException e) {
                response.setStatus(500);
                logger.error("something went horribly wrong while returning image names");
            }
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
