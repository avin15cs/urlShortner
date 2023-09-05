package com.landmark.urlShortner.service;

import com.landmark.urlShortner.UrlMapping;
import com.landmark.urlShortner.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlCleanUpTask {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every day
    public void cleanupExpiredUrls() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<UrlMapping> expiredUrls = urlMappingRepository.findByExpirationDateBefore(currentDate);
        urlMappingRepository.deleteAll(expiredUrls);
    }
}

