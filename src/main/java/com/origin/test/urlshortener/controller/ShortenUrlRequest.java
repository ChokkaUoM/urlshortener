package com.origin.test.urlshortener.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {

    @NotBlank(message = "URL must not be blank")
    private String originalUrl;
    //TODO Add validation for domain


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
