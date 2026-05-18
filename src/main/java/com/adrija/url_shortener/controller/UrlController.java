package com.adrija.url_shortener.controller;

import com.adrija.url_shortener.models.Url;
import com.adrija.url_shortener.repositories.UrlReposiitory;
import com.adrija.url_shortener.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;
    private final UrlReposiitory urlRepository;

    public UrlController(UrlService urlService, UrlReposiitory urlRepository) {
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) {
        String shortCode = generateUniqueCode();

        Url url = new Url();
        url.originalUrl = request.url();
        url.shortCode = shortCode;
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);

        return ResponseEntity.created(URI.create("/" + shortCode))
                .body(new ShortenResponse(shortCode, request.url()));
    }

    @GetMapping("/urls")
    public ResponseEntity<List<Url>> getAllUrls() {
        return ResponseEntity.ok(urlRepository.findAll());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String shortCode) {
        Optional<Url> optionalUrl = urlRepository.findByShortCode(shortCode);
        return optionalUrl
                .map(url -> ResponseEntity.status(302).location(URI.create(url.originalUrl)).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/shorten/{shortCode}")
    public ResponseEntity<ShortenResponse> updateOriginalUrl(@PathVariable String shortCode, @RequestBody ShortenRequest request){
        Optional<Url> url = urlRepository.findByShortCode(shortCode);
        if (url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Url existingUrl = url.get();
        existingUrl.originalUrl = request.url();
        existingUrl.setUpdatedAt(LocalDateTime.now());
        urlRepository.save(existingUrl);
        return ResponseEntity.ok(new ShortenResponse(existingUrl.shortCode, existingUrl.originalUrl));
    }

    @DeleteMapping("/shorten/{shortCode}")
    public ResponseEntity<?> deleteUrl(@PathVariable String shortCode) {
        Optional<Url> url = urlRepository.findByShortCode(shortCode);
        if (url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        urlRepository.delete(url.get());
        return ResponseEntity.noContent().build();
    }

    private String generateUniqueCode() {
        String code = urlService.generateShortCode();
        while (urlRepository.findByShortCode(code).isPresent()) {
            code = urlService.generateShortCode();
        }
        return code;
    }

    public static record ShortenRequest(String url) {
    }

    public static record ShortenResponse(String shortCode, String originalUrl) {
    }
}
