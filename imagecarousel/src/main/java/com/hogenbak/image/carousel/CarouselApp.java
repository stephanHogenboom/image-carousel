package com.hogenbak.image.carousel;

import com.hogenbak.image.carousel.service.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class CarouselApp {
    public static void main(String[] args) {
        SpringApplication.run(CarouselApp.class, args);
    }
}
