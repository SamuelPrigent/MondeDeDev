package com.openclassrooms.mddapi.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
import com.openclassrooms.mddapi.exception.EmailExistException;
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
    // public ResponseEntity<LoginResponseDTO> register(@RequestBody CreateUserDTO
    //
    // createUserDTO) {
    // Vérifier si l'utilisateur existe déjà
    if (userService.existsByEmail(createUserDTO.getEmail())) {
      throw new EmailExistException();
    }
    // Créer l'utilisateur
    userService.createUser(createUserDTO);
    System.out.println("Utilisateur créé avec succès");

    // Récupérer l'utilisateur créé pour le renvoyer dans la réponse
    GetUserDTO createdUser = userService.getByEmail(createUserDTO.getEmail());

    return ResponseEntity.ok(createdUser);
  }

  // /**
  // * POST /api/auth/login
  // */
  // @PostMapping({ "/auth/login", "/auth/login/" })
  // public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO
  // loginRequest) {
  // // Authentification
  // Authentication authentication = authenticationManager
  // .authenticate(new
  // UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
  // loginRequest.getPassword()));
  // // get user details
  // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
  // String token = jwtUtil.generateToken(userDetails);
  // return ResponseEntity.ok(new LoginResponseDTO(token));
  // }

  @PostMapping({ "/auth/login", "/auth/login/" })
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
    // System.out.println("Tentative de connexion pour l'email: " +
    // loginRequest.getEmail());
    try {
      // Authentification
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
      // System.out.println("Authentification réussie");

      // get user details
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String token = jwtUtil.generateToken(userDetails);
      // System.out.println("Token généré avec succès");

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

  /**
   * GET /api/user/{id} we dont create a UserController just for one route ?
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
    return userService.getById(id).map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
  }

}