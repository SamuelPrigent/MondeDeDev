package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.openclassrooms.mddapi.models.User; // model
import com.openclassrooms.mddapi.repository.UserRepository; // repo
// dto
import com.openclassrooms.mddapi.dto.CreateUserDTO;
import com.openclassrooms.mddapi.dto.GetUserDTO;
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

	public Optional<GetUserDTO> getById(Long id) {
		return userRepository.findById(id).map(user -> new GetUserDTO(user));
	}

	public GetUserDTO getByEmail(String email) {
		return findByEmail(email).map(user -> new GetUserDTO(user))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}