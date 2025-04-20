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

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public boolean existsByEmail(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	// Create user
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

	// get by id
	public Optional<GetUserDTO> getById(Long id) {
		return userRepository.findById(id).map(user -> new GetUserDTO(user));
	}

	// get by email
	public GetUserDTO getByEmail(String email) {
		return findByEmail(email).map(user -> new GetUserDTO(user))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}

	// dependency for getByEmail
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// Update by id
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

	public Long getUserId(String identifier) {
		Optional<User> userOptional = findByEmail(identifier);
		if (userOptional.isEmpty()) {
			userOptional = userRepository.findByUsername(identifier);
		}
		return userOptional.map(User::getId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}
}