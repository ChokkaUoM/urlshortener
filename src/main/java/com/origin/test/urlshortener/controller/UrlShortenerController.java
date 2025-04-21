package com.origin.test.urlshortener.controller;

import com.origin.test.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody @Valid ShortenUrlRequest shortenUrlRequest) {
        String shortUrl = urlShortenerService.shortenUrl(shortenUrlRequest.getOriginalUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        var url = urlShortenerService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(url))
                .build();
    }

}
