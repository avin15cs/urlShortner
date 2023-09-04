package com.landmark.urlShortner;

import java.time.LocalDateTime;

class ShortenRequest {

    private String longUrl;
    private LocalDateTime expirationDate;

    // Getters and setters

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
