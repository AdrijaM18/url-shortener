package com.adrija.url_shortener.repositories;

import com.adrija.url_shortener.models.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;


public interface UrlReposiitory extends MongoRepository<Url, String> {
    Optional<Url> findByShortCode(String shortCode);
}
