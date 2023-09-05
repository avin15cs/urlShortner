package com.landmark.urlShortner.repository;

import com.landmark.urlShortner.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortUrl(String shortUrl);

    Optional<UrlMapping> findByLongUrl(String shortUrl);
    List<UrlMapping> findByExpirationDateBefore(LocalDateTime date);
}

