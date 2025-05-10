package com.openclassrooms.mddapi.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.GetArticleDTO;
import com.openclassrooms.mddapi.dto.GetCommentDTO;
import com.openclassrooms.mddapi.dto.PostCommentDTO;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.models.Comment;
import java.util.List;
import org.springframework.http.MediaType;

/**
 * Contrôleur REST pour la gestion des articles et de leurs commentaires. Cette
 * classe expose des endpoints permettant de récupérer et créer des articles
 * ainsi que de gérer les commentaires associés. L'utilisateur est authentifié
 * via un jeton JWT présent dans l'en-tête <code>Authorization</code>.
 */
@RestController
@RequestMapping("/api")
public class ArticleController {

	/**
	 * Service métier gérant la logique autour des articles.
	 */
	@Autowired
	private ArticleService articleService;

	/**
	 * Récupère l'ensemble des articles disponibles.
	 *
	 * @return une {@link ResponseEntity} contenant la liste des articles sous forme
	 *         de {@link GetArticleDTO}
	 */
	@GetMapping({ "/articles", "/articles/" })
	public ResponseEntity<List<GetArticleDTO>> getAllArticles() {
		// Récupérer tous les articles depuis le service
		List<Article> articles = articleService.getArticles();
		// Transforme les articles en DTO
		List<GetArticleDTO> articlesDTO = articles.stream().map(GetArticleDTO::new).toList();
		// Retourner la liste dans la réponse
		return ResponseEntity.ok(articlesDTO);
	}

	/**
	 * Récupère les articles auxquels l'utilisateur est abonné.
	 *
	 * @param authHeader en-tête HTTP contenant le jeton <code>Bearer</code>
	 * @return une {@link ResponseEntity} contenant la liste filtrée des articles
	 */
	@GetMapping({ "/SubscribedArticles", "/SubscribedArticles/" })
	public ResponseEntity<List<GetArticleDTO>> getSubscribedArticles(@RequestHeader("Authorization") String authHeader) {
		// Extraire le token
		String token = authHeader.replace("Bearer ", "");
		List<GetArticleDTO> articlesDTO = articleService.getSubscribedArticles(token);
		return ResponseEntity.ok(articlesDTO);
	}

	/**
	 * Crée un nouvel article.
	 *
	 * @param createArticleDTO DTO contenant les informations de l'article à créer
	 *                         {@link CreateArticleDTO}
	 * @param authHeader       en-tête HTTP contenant le jeton <code>Bearer</code>
	 * @return l'article créé encapsulé dans une {@link ResponseEntity} ou un code
	 *         d'erreur si la validation échoue
	 */
	@PostMapping(value = { "/articles", "/articles/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createArticle(@RequestBody CreateArticleDTO createArticleDTO,
			@RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.replace("Bearer ", "");
			GetArticleDTO createdArticle = articleService.createArticle(createArticleDTO, token);
			return ResponseEntity.ok(createdArticle);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
		}
	}

	/**
	 * Récupère un article par son identifiant.
	 *
	 * @param id identifiant de l'article
	 * @return l'article correspondant ou un statut 404 s'il n'existe pas
	 */
	@GetMapping({ "/articles/{id}", "/articles/{id}/" })
	public ResponseEntity<GetArticleDTO> getArticlesById(@PathVariable Long id) {
		Optional<Article> articleOpt = articleService.getById(id);
		if (articleOpt.isPresent()) {
			GetArticleDTO dto = new GetArticleDTO(articleOpt.get());
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Récupère les commentaires associés à un article.
	 *
	 * @param id identifiant de l'article
	 * @return une {@link ResponseEntity} contenant la liste des commentaires
	 */
	@GetMapping({ "/articles/{id}/comments", "/articles/{id}/comments/" })
	public ResponseEntity<List<GetCommentDTO>> getCommentsByArticle(@PathVariable Long id) {
		List<GetCommentDTO> comments = articleService.getCommentsByArticle(id);
		return ResponseEntity.ok(comments);
	}

	/**
	 * Publie un nouveau commentaire sur un article.
	 *
	 * @param articleId  identifiant de l'article
	 * @param dto        DTO contenant le contenu du commentaire
	 *                   {@link PostCommentDTO}
	 * @param authHeader en-tête HTTP contenant le jeton <code>Bearer</code>
	 * @return le commentaire sauvegardé encapsulé dans une {@link ResponseEntity}
	 */
	@PostMapping({ "/articles/{id}/comments", "/articles/{id}/comments/" })
	public ResponseEntity<GetCommentDTO> postComment(@PathVariable("id") Long articleId, @RequestBody PostCommentDTO dto,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		Comment saved = articleService.postComment(articleId, dto, token);
		return ResponseEntity.ok(new GetCommentDTO(saved));
	}
}