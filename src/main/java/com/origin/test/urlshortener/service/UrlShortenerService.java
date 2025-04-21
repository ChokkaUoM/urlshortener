package com.origin.test.urlshortener.service;

import java.util.Optional;

public interface UrlShortenerService {
    String shortenUrl(String originalUrl);
    Optional<String> getOriginalUrl(String shortCode);
}
