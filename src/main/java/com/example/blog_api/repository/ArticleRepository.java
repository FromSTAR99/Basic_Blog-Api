package com.example.blogapi.repository;

import com.example.blogapi.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {
}
