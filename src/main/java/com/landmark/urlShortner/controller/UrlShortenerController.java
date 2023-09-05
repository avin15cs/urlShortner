package com.landmark.urlShortner.controller;

import com.landmark.urlShortner.dto.ShortenRequestDTO;
import com.landmark.urlShortner.dto.ShortenedUrlDTO;
import com.landmark.urlShortner.exception.NotFoundException;
import com.landmark.urlShortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenedUrlDTO> shortenUrl(@Valid @RequestBody ShortenRequestDTO shortenRequest) {
        // Generate a unique short URL and save it
        String shortUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + urlShortenerService.shortenUrl(shortenRequest.getLongUrl(), shortenRequest.getExpirationDate()))
                .build()
                .toUriString();
        ShortenedUrlDTO response = new ShortenedUrlDTO();
        response.setShortUrl(shortUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {
        String longUrl = urlShortenerService.redirect(shortUrl);

        if (longUrl != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(longUrl);
            return redirectView;
        } else {
            // Handle invalid or expired short URLs
            throw new NotFoundException("Short URL not found or expired");
        }
    }
}
