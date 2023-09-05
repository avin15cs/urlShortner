package com.landmark.urlShortner.service;

import com.landmark.urlShortner.UrlMapping;
import com.landmark.urlShortner.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;
    private final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int MAX_ATTEMPTS = 3; // Maximum attempts to generate a unique short URL

//    @Autowired
//    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
//        this.urlMappingRepository = urlMappingRepository;
//    }

    public String shortenUrl(String longUrl, LocalDateTime expirationDate) {
        // Validate input URL
        if (!isValidUrl(longUrl)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        // Check if the long URL already exists in the database
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByLongUrl(longUrl);

        if (existingMapping.isPresent()) {
            return existingMapping.get().getShortUrl(); // Return the existing shortened URL
        } else {
            String shortUrl = generateShortUrl();

            UrlMapping urlMapping = new UrlMapping();
            urlMapping.setShortUrl(shortUrl);
            urlMapping.setLongUrl(longUrl);
            if(Objects.nonNull(expirationDate)){
                urlMapping.setExpirationDate(expirationDate);
            }

            else {
                urlMapping.setExpirationDate(LocalDateTime.now().plusYears(1));
            }

            urlMappingRepository.save(urlMapping);

            return shortUrl;
        }
    }

    public String redirect(String shortUrl) {
        Optional<UrlMapping> urlMappingOpt = urlMappingRepository.findByShortUrl(shortUrl);

        if (urlMappingOpt.isPresent()) {
            UrlMapping urlMapping = urlMappingOpt.get();
            if (urlMapping.getExpirationDate().isAfter(LocalDateTime.now())) {
                return urlMapping.getLongUrl();
            }
        }

        return null; // Return null if the short URL is not found or expired
    }

    private String generateShortUrl() {
        Random random = new Random();

        // Try to generate a unique short URL within a limited number of attempts
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            StringBuilder shortUrl = new StringBuilder();

            for (int i = 0; i < 6; i++) {
                shortUrl.append(BASE62_CHARACTERS.charAt(random.nextInt(BASE62_CHARACTERS.length())));
            }

            // Check if the generated short URL is already in use
            if (!urlMappingRepository.findByShortUrl(shortUrl.toString()).isPresent()) {
                return shortUrl.toString();
            }
        }

        throw new RuntimeException("Unable to generate a unique short URL");
    }

    private boolean isValidUrl(String url) {
        try {
            new URI(url).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}