package com.example.blogapi.controller;

import com.example.blogapi.model.Article;
import com.example.blogapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable String id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable String id, @RequestBody Article articleDetails) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            Article existingArticle = article.get();
            existingArticle.setTitle(articleDetails.getTitle());
            existingArticle.setContent(articleDetails.getContent());
            existingArticle.setAuthor(articleDetails.getAuthor());
            existingArticle.setTags(articleDetails.getTags());
            existingArticle.setPublishedAt(articleDetails.getPublishedAt());

            return new ResponseEntity<>(articleRepository.save(existingArticle), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteArticle(@PathVariable String id) {
        try {
            articleRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
