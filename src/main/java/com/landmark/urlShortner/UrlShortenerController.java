package com.landmark.urlShortner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> shortenUrl(@Valid @RequestBody ShortenRequest shortenRequest) {
        // Generate a unique short URL and save it
        String shortUrl = urlShortenerService.shortenUrl(shortenRequest.getLongUrl(), shortenRequest.getExpirationDate());

        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {
        String longUrl = urlShortenerService.redirect(shortUrl);

        if (longUrl != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(longUrl);
            return redirectView;
        } else {
            throw new NotFoundException("Short URL not found or expired");
        }
    }
}
