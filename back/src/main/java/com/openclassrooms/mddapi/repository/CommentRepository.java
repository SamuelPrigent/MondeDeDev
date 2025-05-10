package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité {@link Comment}. Fournit les
 * opérations CRUD de base via {@link JpaRepository} et une méthode
 * personnalisée pour récupérer les commentaires d'un {@link Article} donné.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByArticle(Article article);
}