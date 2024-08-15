package com.example.blog_api.service;

import com.example.blog_api.model.Article;
import com.example.blog_api.repository.ArticleRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;  // Kullanıcı ID'yi almak için

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        try {
            ObjectId userId = userService.getCurrentUserId();
            MDC.put("userId", String.valueOf(userId));  // Loglara kullanıcı ID'sini ekler

            logger.info("Creating article for user ID: " + userId);
            return articleRepository.save(article);
        } catch (Exception e) {
            logger.error("Failed to create article for user ID: " + MDC.get("userId"), e);
            throw new RuntimeException("Failed to connect to the database. Please try again later.", e);
        } finally {
            MDC.clear();  // Temizlik
        }
    }

    public Optional<Article> updateArticle(String id, Article articleDetails) {
        try {
            ObjectId userId = userService.getCurrentUserId();
            MDC.put("userId", String.valueOf(userId));  // Loglara kullanıcı ID'sini ekler

            Optional<Article> article = articleRepository.findById(id);
            if (article.isPresent()) {
                Article existingArticle = article.get();
                existingArticle.setTitle(articleDetails.getTitle());
                existingArticle.setContent(articleDetails.getContent());
                existingArticle.setAuthor(articleDetails.getAuthor());
                existingArticle.setTags(articleDetails.getTags());
                existingArticle.setPublishedAt(articleDetails.getPublishedAt());

                logger.info("Updating article for user ID: " + userId);
                return Optional.of(articleRepository.save(existingArticle));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Failed to update article for user ID: " + MDC.get("userId"), e);
            throw new RuntimeException("Failed to update the article. Please try again later.", e);
        } finally {
            MDC.clear();  // Temizlik
        }
    }

    public boolean deleteArticle(String id) {
        try {
            ObjectId userId = userService.getCurrentUserId();
            MDC.put("userId", String.valueOf(userId));  // Loglara kullanıcı ID'sini ekler

            if (articleRepository.existsById(id)) {
                articleRepository.deleteById(id);
                logger.info("Deleted article for user ID: " + userId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Failed to delete article for user ID: " + MDC.get("userId"), e);
            throw new RuntimeException("Failed to delete the article. Please try again later.", e);
        } finally {
            MDC.clear();  // Temizlik
        }
    }
}
