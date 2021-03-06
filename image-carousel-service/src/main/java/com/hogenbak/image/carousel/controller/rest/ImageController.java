package com.hogenbak.image.carousel.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogenbak.image.carousel.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.hogenbak.image.carousel.util.EnvUtils.getEnvOrDefault;


@RestController
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(ImageController.class.getName());
    private final Path imagesBasePath = Paths.get(getEnvOrDefault("IMAGES_BASE_DIR", "images/test"));
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
            } else {
                logger.error(String.format("directory images does not exist with name %s not found", imageId));
                response.setStatus(500);
            }
        } catch (IOException e) {
            response.setStatus(500);
            logger.error(e.getMessage());
        }
    }

    @GetMapping("/images/name/all")
    @CrossOrigin
    public void getImageNames(HttpServletResponse response) {
        System.out.println("hi");
        if (Files.exists(imagesBasePath)) {
            try {
                List<String> filesNames = Files
                        .list(imagesBasePath)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());

                String fileNamesAsJson = mapper.writeValueAsString(filesNames);
                response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                response.getOutputStream().write(fileNamesAsJson.getBytes());
            } catch (IOException e) {
                response.setStatus(500);
                logger.error("something went horribly wrong while returning image names");
            }
        } else {
            System.out.println("did not exist");
        }
    }

    @PostMapping("/images/new/")
    @CrossOrigin
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           HttpServletResponse response) throws IOException {
        try {
            String fileName = fileStorageService.storeFile(file);
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            response.sendRedirect("/");
        } catch (Exception e) {
            System.out.println("The file was not a multi part file!" + e.getMessage());
            response.setStatus(500);
        }
    }

    @PostMapping("/images/new/{fileName}")
    @CrossOrigin
    public void uploadFileByName(@PathVariable String imageName, HttpServletRequest request,
                           HttpServletResponse response) {
        try{
            InputStream is = request.getInputStream();
            fileStorageService.saveImage(is, imageName);
        } catch (IOException e) {
            logger.error("Error saving file:", e);
            response.setStatus(500);
        }
    }

    @DeleteMapping("/images/delete/{fileName")
    @CrossOrigin
    public void deleteImageByName(@PathVariable String imageName, HttpServletResponse response) {
        try {
            fileStorageService.deleteImageByFileName(imageName);
        } catch (IOException e) {
            logger.error("failed to delete image :s", e, imageName);
            response.setStatus(500);
        }
    }
}
