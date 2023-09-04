package com.landmark.urlShortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final String BASE62_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int MAX_ATTEMPTS = 3; // Maximum attempts to generate a unique short URL

    @Autowired
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String shortenUrl(String longUrl, LocalDateTime expirationDate) {
        // Validate input URL
        if (!isValidUrl(longUrl)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        // Generate a unique short URL using Base62 encoding
        String shortUrl = generateShortUrl();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setLongUrl(longUrl);
        urlMapping.setExpirationDate(expirationDate);

        urlMappingRepository.save(urlMapping);

        return shortUrl;
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