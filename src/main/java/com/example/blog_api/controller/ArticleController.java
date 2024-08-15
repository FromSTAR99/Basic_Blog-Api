
package com.example.blog_api.controller;

import com.example.blog_api.model.Article;
import com.example.blog_api.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping
    public List<Article> getAllArticles() {
        try {
            return articleService.getAllArticles();
        } catch (Exception e) {
            logger.error("Error fetching articles", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch articles", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable String id) {
        try {
            Optional<Article> article = articleService.getArticleById(id);
            return article.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error fetching article with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            Article createdArticle = articleService.createArticle(article);
            return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating article", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article articleDetails) {
        try {
            Optional<Article> updatedArticle = articleService.updateArticle(id, articleDetails);
            return updatedArticle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error updating article with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable String id) {
        try {
            boolean isDeleted = articleService.deleteArticle(id);
            return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting article with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
