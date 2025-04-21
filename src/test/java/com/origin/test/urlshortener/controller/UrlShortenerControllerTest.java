package com.origin.test.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.origin.test.urlshortener.exceptions.InvalidShortenURLCodeException;
import com.origin.test.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlShortenerService shortenerService;

    @Test
    void shouldRedirectToOriginalURL_whenShortCodeExists() throws Exception {
        String shortCode = "g512b4";
        String originalURL = "https://www.originenergy.com.au/electricity-gas/plans.html";

        when(shortenerService.getOriginalUrl(shortCode)).thenReturn(originalURL);

        mockMvc.perform(get(String.format("/api/v1/%s", shortCode)))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", originalURL));
    }

    @Test
    void shouldReturn404_whenShortCodeDoesNotExists() throws Exception {
        String shortCode = "g512b4";

        when(shortenerService.getOriginalUrl(shortCode))
                .thenThrow(new InvalidShortenURLCodeException(String.format("Invalid short code [%s]", shortCode)));
        mockMvc.perform(get(String.format("/api/v1/%s", shortCode)))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldCreateShortURL() throws Exception {

        String shortCode = "g512b4";
        String originalURL = "https://www.originenergy.com.au/electricity-gas/plans.html";
        ShortenUrlRequest requestBody = new ShortenUrlRequest();
        requestBody.setOriginalUrl(originalURL);

        when(shortenerService.shortenUrl(originalURL)).thenReturn(shortCode);

        mockMvc.perform(post("/api/v1/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string(shortCode));

    }
}