package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.openclassrooms.mddapi.models.User; // model
import com.openclassrooms.mddapi.repository.UserRepository; // repo
// dto
import com.openclassrooms.mddapi.dto.CreateUserDTO;
import com.openclassrooms.mddapi.dto.GetUserDTO;
import com.openclassrooms.mddapi.dto.UpdateUserDTO;
// spring security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // hash password
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service pour la gestion des utilisateurs.
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Vérifie l'existence d'un utilisateur par son email.
	 *
	 * @param email adresse email
	 * @return {@code true} si un utilisateur possède cet email, sinon {@code false}
	 */
	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	/**
	 * Persiste un utilisateur déjà construit (usage interne).
	 *
	 * @param user entité {@link User} à sauvegarder
	 * @return l'entité {@link User} persistée
	 */
	public User createUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Crée un utilisateur à partir d'un {@link CreateUserDTO}.
	 *
	 * @param createUserDTO données de création
	 * @return DTO de l'utilisateur créé
	 * @throws IllegalArgumentException si l'email est déjà utilisé
	 */
	public GetUserDTO createUser(CreateUserDTO createUserDTO) {
		createUserDTO.validate(); // Validation des données
		if (existsByEmail(createUserDTO.getEmail())) {
			throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
		}
		User user = new User();
		user.setEmail(createUserDTO.getEmail());
		user.setUsername(createUserDTO.getUsername());
		user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

		User savedUser = userRepository.save(user);
		return new GetUserDTO(savedUser);
	}

	/**
	 * Récupère un utilisateur via son identifiant.
	 *
	 * @param id identifiant de l'utilisateur
	 * @return {@link Optional} contenant le {@link GetUserDTO} si trouvé, sinon
	 *         vide
	 */
	public Optional<GetUserDTO> getById(Long id) {
		return userRepository.findById(id).map(user -> new GetUserDTO(user));
	}

	/**
	 * Récupère un utilisateur via son email.
	 *
	 * @param email adresse email
	 * @return {@link GetUserDTO} de l'utilisateur si trouvé, sinon lève une
	 *         exception
	 */
	public GetUserDTO getByEmail(String email) {
		return findByEmail(email).map(user -> new GetUserDTO(user))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}

	/**
	 * Cherche un utilisateur par email.
	 *
	 * @param email adresse email
	 * @return {@link Optional} contenant l'{@link User} si trouvé, sinon vide
	 */
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Met à jour un utilisateur via son identifiant.
	 *
	 * @param id            identifiant de l'utilisateur à modifier
	 * @param updateUserDTO données à mettre à jour ({@link UpdateUserDTO})
	 * @return l'utilisateur mis à jour sous forme de {@link GetUserDTO}
	 */
	public GetUserDTO updateUserById(Long id, UpdateUserDTO updateUserDTO) {
		updateUserDTO.validate(); // Validation des données
		// check is user exist
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

		// Mettre à jour les champs de l'utilisateur saisis
		if (updateUserDTO.getEmail() != null) {
			user.setEmail(updateUserDTO.getEmail());
		}
		if (updateUserDTO.getUsername() != null) {
			user.setUsername(updateUserDTO.getUsername());
		}
		if (updateUserDTO.getPassword() != null && updateUserDTO.getPassword() != "") {
			user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
		}
		// Sauvegarder l'utilisateur mis à jour
		User updatedUser = userRepository.save(user);

		return new GetUserDTO(updatedUser);
	}

	/**
	 * Récupère l'id d'un utilisateur à partir de son email ou de son username.
	 *
	 * @param identifier email ou username
	 * @return identifiant technique de l'utilisateur
	 */
	public Long getUserId(String identifier) {
		Optional<User> userOptional = findByEmail(identifier);
		if (userOptional.isEmpty()) {
			userOptional = userRepository.findByUsername(identifier);
		}
		return userOptional.map(User::getId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}
}