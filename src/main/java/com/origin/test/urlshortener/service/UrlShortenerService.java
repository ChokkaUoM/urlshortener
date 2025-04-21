package com.origin.test.urlshortener.service;

public interface UrlShortenerService {
    String shortenUrl(String originalUrl);

    String getOriginalUrl(String shortCode);
}
