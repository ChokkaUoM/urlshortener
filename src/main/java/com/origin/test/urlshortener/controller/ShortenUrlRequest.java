package com.origin.test.urlshortener.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {

    @NotBlank(message = "URL must not be blank")
    private String originalUrl;

}
