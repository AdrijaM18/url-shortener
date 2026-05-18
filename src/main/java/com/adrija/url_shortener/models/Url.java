package com.adrija.url_shortener.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;  
import lombok.Data;
import java.time.LocalDateTime;

@Document(collection = "urls") //tells Spring Data MongoDB to map this class to the "urls" collection in MongoDB
@Data
public class Url {
    @Id
    public String id; //the unique identifier for the URL document in MongoDB
    public String originalUrl; 
    public String shortCode;
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt;
}
