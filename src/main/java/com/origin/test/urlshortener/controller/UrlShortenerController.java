package com.origin.test.urlshortener.controller;

import com.origin.test.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody @Valid ShortenUrlRequest shortenUrlRequest) {
        String shortUrl = urlShortenerService.shortenUrl(shortenUrlRequest.getOriginalUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Object> redirect(@PathVariable String shortCode) {
        return urlShortenerService.getOriginalUrl(shortCode)
                .map(url -> ResponseEntity.status(302).location(URI.create(url)).build())
                .orElse(ResponseEntity.notFound().build()); //TODO Handle exceptions in ExceptionHandler
    }

}
