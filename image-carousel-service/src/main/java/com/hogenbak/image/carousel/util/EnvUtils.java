package com.hogenbak.image.carousel.util;

import java.util.Optional;

public class EnvUtils {
    public static String getEnvOrDefault(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key)).orElse( defaultValue);
    }
}
