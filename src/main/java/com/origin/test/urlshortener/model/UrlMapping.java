package com.origin.test.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime expiredAt;
}
