package com.origin.test.urlshortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShortCodeGenerator {

    @Value("${url.shortener.code.length}")
    private int shortCodeLength;

    @Value("${url.shortener.code.chars}")
    private String encodingCharacters;

    public String generateShortCode() {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < shortCodeLength; i++) {
            shortCode.append(
                    encodingCharacters.charAt(random.nextInt(encodingCharacters.length()))
            );
        }

        return shortCode.toString();
    }

}
