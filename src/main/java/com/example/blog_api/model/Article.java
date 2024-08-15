package com.example.blog_api.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "articles")
public class Article {
    @Id
    private ObjectId id;

    private String title;

    private String content;

    private String author;

    private List<String> tags;

    private Date publishedAt = new Date();
}
