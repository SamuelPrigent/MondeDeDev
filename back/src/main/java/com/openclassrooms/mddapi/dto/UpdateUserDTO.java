package com.openclassrooms.mddapi.dto;

public class UpdateUserDTO {
	private String email;
	private String username;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Validation
	public void validate() {
		// need all to reconnect user with new user
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("L'email est obligatoire");
		}
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire");
		}
		// password
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Le mot de passe est obligatoire");
		}

		// 8 caractères
		if (this.password != null && this.password != "") {
			if (password.length() < 8) {
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères");
			}
			// Vérification de la présence d'au moins un chiffre
			if (!password.matches(".*[0-9].*")) {
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins un chiffre");
			}
			// Vérification de la présence d'au moins une lettre minuscule
			if (!password.matches(".*[a-z].*")) {
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins une lettre minuscule");
			}
			// Vérification de la présence d'au moins une lettre majuscule
			if (!password.matches(".*[A-Z].*")) {
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins une lettre majuscule");
			}
			// Vérification de la présence d'au moins un caractère spécial
			if (!password.matches(".*[^a-zA-Z0-9].*")) {
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins un caractère spécial");
			}
		}
	}

}