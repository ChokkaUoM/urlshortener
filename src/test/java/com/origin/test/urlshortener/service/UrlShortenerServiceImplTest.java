package com.origin.test.urlshortener.service;

import com.origin.test.urlshortener.exceptions.InvalidShortenURLCodeException;
import com.origin.test.urlshortener.model.UrlMapping;
import com.origin.test.urlshortener.repository.UrlMappingRepository;
import com.origin.test.urlshortener.util.ShortCodeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceImplTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    private UrlShortenerServiceImpl urlShortenerService;

    private static final String SHORTER_DOMAIN_URL = "http://short.ly/";

    @BeforeEach
    void setUp() {
        urlShortenerService = new UrlShortenerServiceImpl();
        ReflectionTestUtils.setField(urlShortenerService, "urlMappingRepository", urlMappingRepository);
        ReflectionTestUtils.setField(urlShortenerService, "shortCodeGenerator", shortCodeGenerator);
        ReflectionTestUtils.setField(urlShortenerService, "shortenerUrlDomain", SHORTER_DOMAIN_URL);
    }

    @Test
    void shortenUrlTestingForNewUrl() {
        String originalUrl = "https://www.originenergy.com.au/electricity-gas/plans.html";
        String generatedCode = "abc123";
        when(shortCodeGenerator.generateShortCode()).thenReturn(generatedCode);
        when(urlMappingRepository.findByShortCode(any())).thenReturn(Optional.empty());
        String shortenUrl = urlShortenerService.shortenUrl(originalUrl);
        Assertions.assertNotNull(shortenUrl);
        String expectedShortenUrl = String.format("%s%s", SHORTER_DOMAIN_URL, generatedCode);
        Assertions.assertEquals(expectedShortenUrl, shortenUrl);
    }

    @Test
    void getOriginalUrlForExitingUrl() {
        String shortCode = "abc123";
        String originalUrl = "https://www.originenergy.com.au/electricity-gas/plans.html";
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());
        when(urlMappingRepository.findByShortCode(any())).thenReturn(Optional.of(urlMapping));
        String returnedUrl = urlShortenerService.getOriginalUrl(shortCode);
        Assertions.assertNotNull(returnedUrl);
        Assertions.assertEquals(originalUrl, returnedUrl);
    }

    @Test
    void getOriginalUrlForNonExitingUrl() {
        String shortCode = "abc123";
        when(urlMappingRepository.findByShortCode(any())).thenReturn(Optional.empty());
        Assertions.assertThrows( InvalidShortenURLCodeException.class, () -> urlShortenerService.getOriginalUrl(shortCode));

    }
}