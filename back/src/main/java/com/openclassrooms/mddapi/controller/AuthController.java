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

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * POST /api/auth/register
	 */
	@PostMapping(value = { "/auth/register", "/auth/register/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserDTO> register(@RequestBody CreateUserDTO createUserDTO) {
		GetUserDTO createdUser = userService.createUser(createUserDTO); // Créer l'utilisateur
		return ResponseEntity.ok(createdUser);
	}

	@PostMapping({ "/auth/login", "/auth/login/" })
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
		try {
			// Authentification
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			// get user details
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtil.generateToken(userDetails);

			return ResponseEntity.ok(new LoginResponseDTO(token));
		} catch (Exception e) {
			System.out.println("Erreur lors de la connexion: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * GET /api/auth/me
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