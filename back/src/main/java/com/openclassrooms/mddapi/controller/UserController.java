package com.openclassrooms.mddapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
// dto
import com.openclassrooms.mddapi.dto.GetUserDTO;
import com.openclassrooms.mddapi.dto.LoginResponseDTO;
import com.openclassrooms.mddapi.dto.UpdateUserDTO;
// service / security / exception
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.security.JwtUtil;

/**
 * Contrôleur REST pour la consultation et la mise à jour d'un utilisateur.
 * <p>
 * Principales fonctionnalités :
 * <ul>
 *   <li>Récupération d'un utilisateur par son identifiant ;</li>
 *   <li>Mise à jour sécurisée du profil utilisateur avec génération d'un nouveau JWT.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Récupère un utilisateur par son identifiant.
	 *
	 * @param id identifiant de l'utilisateur
	 * @return l'utilisateur au format {@link GetUserDTO}
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
		return userService.getById(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}

	/**
	 * Met à jour un utilisateur après vérification de l'identité via le JWT.
	 *
	 * @param id            identifiant de l'utilisateur à mettre à jour
	 * @param token         en-tête <code>Bearer</code> contenant le JWT
	 * @param updateUserDTO données de mise à jour {@link UpdateUserDTO}
	 * @return un {@link LoginResponseDTO} contenant un nouveau token et l'id utilisateur
	 */
	@PutMapping({ "/user/{id}", "/user/{id}/" })
	public ResponseEntity<?> putUserById(@PathVariable Long id, @RequestHeader("Authorization") String token,
			@RequestBody UpdateUserDTO updateUserDTO) {
		// Extraire l'id utilisateur à partir du token
		Long userIdFromToken = jwtUtil.extractUserId(token.substring(7));

		// Vérifier si l'ID extrait correspond à l'ID de la requête
		if (userIdFromToken == null || !userIdFromToken.equals(id)) {
			System.out.println("ID de l'utilisateur ne correspond pas à l'ID de la requête.");
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(java.util.Map.of("error", "Vous n'êtes pas autorisé à modifier cet utilisateur."));
		}

		// Vérifier si l'utilisateur existe
		Optional<GetUserDTO> existingUser = userService.getById(id);
		if (!existingUser.isPresent()) {
			System.out.println("Utilisateur non trouvé avec l'ID: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		// Mettre à jour l'utilisateur
		GetUserDTO updatedUser = userService.updateUserById(id, updateUserDTO);
		System.out.println("Utilisateur mis à jour: " + updatedUser);

		// Renvoie d'un nouveau token mis à jour
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(updateUserDTO.getEmail(), updateUserDTO.getPassword()));

		// get user details
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String newToken = jwtUtil.generateToken(userDetails, id);

		return ResponseEntity.ok(new LoginResponseDTO(newToken, id));
	}

}