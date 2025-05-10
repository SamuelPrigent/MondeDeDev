package com.openclassrooms.mddapi.service;

import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.CreateArticleDTO;
import com.openclassrooms.mddapi.dto.GetArticleDTO;
import com.openclassrooms.mddapi.dto.GetCommentDTO;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.dto.PostCommentDTO;
import com.openclassrooms.mddapi.models.Comment;

import java.util.Optional;
import java.util.List;

import com.openclassrooms.mddapi.security.JwtUtil;
import com.openclassrooms.mddapi.dto.GetThemesDTO;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private ThemeUserService themeUserService;

	@Autowired
	private ThemeService themeService;

	/**
	 * Récupère un article par son identifiant.
	 *
	 * @param id identifiant de l'article
	 * @return {@link Optional} contenant l'{@link Article} si trouvé, sinon vide
	 */
	public Optional<Article> getById(Long id) {
		return articleRepository.findById(id);
	}

	/**
	 * Récupère l'ensemble des articles disponibles.
	 *
	 * @return liste d'articles {@link Article}
	 */
	public List<Article> getArticles() {
		return articleRepository.findAll();
	}

	/**
	 * Récupère les articles liés aux thèmes auxquels l'utilisateur est abonné.
	 *
	 * @param token JWT de l'utilisateur (préfixe "Bearer " retiré en amont)
	 * @return liste des articles convertis en {@link GetArticleDTO}
	 */
	public List<GetArticleDTO> getSubscribedArticles(String token) {
		// Extraire l'email du token
		String email = jwtUtil.extractEmail(token);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		if (user == null) {
			return List.of();
		}
		// Récupérer les thèmes auxquels l'utilisateur est abonné
		List<GetThemesDTO> userThemes = themeUserService.getThemesByUserId(user.getId());
		List<String> themeNames = userThemes.stream().map(GetThemesDTO::getThemeName).toList();
		// Récupérer les articles filtrés par nom de thème et transformer en DTO
		return articleRepository.findAll().stream().filter(article -> themeNames.contains(article.getTheme()))
				.map(GetArticleDTO::new).toList();
	}

	/**
	 * Crée un nouvel article.
	 *
	 * @param request données de l'article à créer ({@link CreateArticleDTO})
	 * @param token   JWT de l'auteur
	 * @return DTO de l'article créé
	 * @throws IllegalArgumentException si le thème n'existe pas ou si l'utilisateur n'est pas trouvé
	 */
	public GetArticleDTO createArticle(CreateArticleDTO request, String token) {
		// validation données request
		request.validate();

		// Vérification que le thème existe
		List<GetThemesDTO> allThemes = themeService.getAllThemes();
		boolean themeExists = allThemes.stream().anyMatch(theme -> theme.getThemeName().equals(request.getTheme()));
		if (!themeExists) {
			throw new IllegalArgumentException("Le thème spécifié n'existe pas : " + request.getTheme());
		}

		// Récupère l'id utilisateur depuis le token
		Long userIdFromToken = jwtUtil.extractUserId(token);
		if (userIdFromToken == null) {
			throw new IllegalArgumentException("Utilisateur authentifié introuvable dans le token");
		}
		User author = userRepository.findById(userIdFromToken)
				.orElseThrow(() -> new IllegalArgumentException("Auteur non trouvé"));

		// create article object
		Article article = new Article();
		article.setTitle(request.getTitle());
		article.setDescription(request.getDescription());
		article.setTheme(request.getTheme());
		article.setAuthor(author); // objet User filtré par le DTO => id
		// save
		Article savedArticle = articleRepository.save(article);
		return new GetArticleDTO(savedArticle);
	}

	/**
	 * Récupère les commentaires associés à un article.
	 *
	 * @param articleId identifiant de l'article
	 * @return liste des commentaires en {@link GetCommentDTO}
	 */
	public List<GetCommentDTO> getCommentsByArticle(Long articleId) {
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
		List<Comment> comments = commentRepository.findByArticle(article);
		return comments.stream().map(GetCommentDTO::new).toList();
	}

	/**
	 * Publie un commentaire sur un article.
	 *
	 * @param articleId identifiant de l'article
	 * @param dto       contenu du commentaire ({@link PostCommentDTO})
	 * @param token     JWT de l'utilisateur
	 * @return entité {@link Comment} sauvegardée
	 * @throws IllegalArgumentException si l'article ou l'utilisateur est introuvable
	 */
	public Comment postComment(Long articleId, PostCommentDTO dto, String token) {
		// Vérifier que l'article existe
		Article article = articleRepository.findById(articleId)
				.orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
		// Extraire l'id utilisateur du token
		Long userId = jwtUtil.extractUserId(token);
		// Vérifier que l'utilisateur existe
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

		Comment comment = new Comment();
		comment.setArticle(article);
		comment.setUser(user);
		comment.setComment(dto.getComment());
		return commentRepository.save(comment);
	}

}