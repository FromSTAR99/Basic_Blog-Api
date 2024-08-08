package com.example.blogapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.List;
import java.util.Date;

@Data
@Document(collection = "articles")
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private List<String> tags;
    private Date publishedAt = new Date();
}
