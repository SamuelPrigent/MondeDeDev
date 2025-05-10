package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l’entité {@link Article}. Hérite des méthodes
 * CRUD de {@link JpaRepository}.
 */

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}