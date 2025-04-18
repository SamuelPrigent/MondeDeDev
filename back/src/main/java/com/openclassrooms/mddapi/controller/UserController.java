package com.openclassrooms.mddapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
// dto
import com.openclassrooms.mddapi.dto.GetUserDTO;
import com.openclassrooms.mddapi.dto.UpdateUserDTO;
// service / security / exception
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * GET /api/user/{id}
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
		return userService.getById(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}

	/**
	 * PUT /api/user/{id}
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<GetUserDTO> putUserById(@PathVariable Long id, @RequestHeader("Authorization") String token,
			@RequestBody UpdateUserDTO updateUserDTO) {
		// Extraire l'email de l'utilisateur à partir du token
		String email = jwtUtil.extractUsername(token.substring(7));
		System.out.println("Email extrait: " + email);

		// Récupérer l'utilisateur par email
		GetUserDTO userFromToken = userService.getByEmail(email);
		System.out.println("Utilisateur récupéré par email: " + userFromToken);

		// Vérifier si l'ID extrait correspond à l'ID de la requête
		if (!userFromToken.getId().equals(id)) {
			System.out.println("ID de l'utilisateur ne correspond pas à l'ID de la requête.");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

		// Retourner la réponse
		return ResponseEntity.ok(updatedUser);
	}
}