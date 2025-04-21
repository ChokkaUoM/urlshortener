package com.origin.test.urlshortener.service;

import com.origin.test.urlshortener.model.UrlMapping;
import com.origin.test.urlshortener.repository.UrlMappingRepository;
import com.origin.test.urlshortener.util.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Value("${url.shortener.domain}")
    private String shortenerUrlDomain;

    @Value("${url.shortener.code.expiration.days}")
    private int shortenUrlExpirationDays;

    @Autowired
    private ShortCodeGenerator shortCodeGenerator;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Override
    public String shortenUrl(String originalUrl) {

        String shortCode = shortCodeGenerator.generateShortCode();
        //Avoid collision
        while (urlMappingRepository.findByShortCode(shortCode).isPresent()) {
            shortCode = shortCodeGenerator.generateShortCode();
        }

        LocalDateTime createdDate = LocalDateTime.now();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(createdDate);
        urlMapping.setExpiredAt(createdDate.plusDays(shortenUrlExpirationDays));

        urlMappingRepository.save(urlMapping);

        return String.format("%s%s", shortenerUrlDomain, shortCode);
    }

    @Override
    public Optional<String> getOriginalUrl(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode)
                .map(UrlMapping::getOriginalUrl);
    }
}
