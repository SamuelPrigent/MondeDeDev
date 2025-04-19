package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Dans notre implémentation, le username est en fait l'email. C'est une
	 * pratique courante d'utiliser l'email comme identifiant unique.
	 */
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		String normalized = usernameOrEmail.toLowerCase().trim();
		System.out.println("Recherche d'utilisateur avec identifiant (email ou username) normalisé : " + normalized);

		User user = null;
		try {
			if (normalized.contains("@")) {
				// Recherche par email
				user = userRepository.findByEmailIgnoreCase(normalized).orElseThrow(() -> {
					System.out.println("Aucun utilisateur trouvé avec cet email (insensible à la casse) : " + normalized);
					return new UsernameNotFoundException("User not found with email: " + normalized);
				});
				System.out.println("Utilisateur trouvé avec email : " + normalized + ", ID : " + user.getId());
			} else {
				// Recherche par username
				user = userRepository.findByUsernameIgnoreCase(normalized).orElseThrow(() -> {
					System.out.println("Aucun utilisateur trouvé avec ce username (insensible à la casse) : " + normalized);
					return new UsernameNotFoundException("User not found with username: " + normalized);
				});
				System.out.println("Utilisateur trouvé avec username : " + normalized + ", ID : " + user.getId());
			}
			System.out.println("Mot de passe haché récupéré de la BDD : " + user.getPassword());
			return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
					.password(user.getPassword()).authorities(new ArrayList<>()).build();
		} catch (Exception e) {
			System.out.println("Exception lors de la recherche de l'utilisateur : " + e.getMessage());
			throw e;
		}
	}
}