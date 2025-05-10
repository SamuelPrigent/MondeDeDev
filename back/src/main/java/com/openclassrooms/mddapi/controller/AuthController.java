package com.openclassrooms.mddapi.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// dto
import com.openclassrooms.mddapi.dto.CreateUserDTO;
import com.openclassrooms.mddapi.dto.LoginRequestDTO;
import com.openclassrooms.mddapi.dto.GetUserDTO;
import com.openclassrooms.mddapi.dto.LoginResponseDTO;
// service / security / exception
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.security.JwtUtil;

/**
 * Contrôleur REST responsable de l'authentification et de l'inscription des
 * utilisateurs.
 * <p>
 * Il fournit des endpoints pour :
 * <ul>
 * <li>l'inscription d'un nouvel utilisateur ;</li>
 * <li>la connexion et la génération d'un jeton JWT ;</li>
 * <li>la récupération des informations de l'utilisateur courant.</li>
 * </ul>
 * Les routes sont préfixées par <code>/api</code>.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

	/** Service métier gérant les opérations sur les utilisateurs. */
	@Autowired
	private UserService userService;

	/** Gestionnaire Spring Security pour l'authentification. */
	@Autowired
	private AuthenticationManager authenticationManager;

	/** Utilitaire de création et de validation des JWT. */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Inscrit un nouvel utilisateur.
	 *
	 * @param createUserDTO informations du nouvel utilisateur {@link CreateUserDTO}
	 * @return l'utilisateur créé au format {@link GetUserDTO}
	 */
	@PostMapping(value = { "/auth/register", "/auth/register/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
		GetUserDTO createdUser = userService.createUser(createUserDTO); // Créer l'utilisateur
		return ResponseEntity.ok(createdUser);
	}

	/**
	 * Authentifie un utilisateur et génère un jeton JWT.
	 *
	 * @param loginRequest identifiants de connexion {@link LoginRequestDTO}
	 * @return un {@link LoginResponseDTO} contenant le token JWT et l'identifiant
	 *         utilisateur
	 */
	@PostMapping({ "/auth/login", "/auth/login/" })
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

			// get user details
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			// String token = jwtUtil.generateToken(userDetails);
			Long userId = userService.getUserId(userDetails.getUsername());

			// Generate Token
			String token = jwtUtil.generateToken(userDetails, userId);

			// getUserId to send it in response
			// Long userId = userService.getUserId(userDetails.getUsername());

			return ResponseEntity.ok(new LoginResponseDTO(token, userId));
		} catch (Exception e) {
			System.out.println("Erreur lors de la connexion: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Récupère l'utilisateur actuellement authentifié.
	 *
	 * @param authHeader en-tête HTTP contenant le jeton <code>Bearer</code>
	 * @return les informations de l'utilisateur courant {@link GetUserDTO}
	 */
	@GetMapping({ "/auth/me", "/auth/me/" })
	public ResponseEntity<GetUserDTO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
		// Extrait email du token
		String email = jwtUtil.extractClaim(token, claims -> claims.get("email", String.class));
		// Récupérer l'utilisateur depuis la base de données
		return ResponseEntity.ok(userService.getByEmail(email));
	}

}