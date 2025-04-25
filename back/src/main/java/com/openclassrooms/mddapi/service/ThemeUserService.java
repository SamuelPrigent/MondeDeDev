package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Themes;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.dto.GetUserThemesDTO;
import com.openclassrooms.mddapi.repository.ThemeUserRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeUserService {
	@Autowired
	private ThemeUserRepository themeUserRepository;
	@Autowired
	private ThemeRepository themeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;

	// Abonnement à un thème
	public boolean subscribeTheme(Long themeId, String token) {
		System.out.println("[subscribeTheme] themeId=" + themeId + ", token=" + token);
		String email = jwtUtil.extractEmail(token);
		System.out.println("[subscribeTheme] email extrait du token: " + email);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		Long userId = user != null ? user.getId() : null;
		System.out.println("[subscribeTheme] userId retrouvé par email: " + userId);
		Themes theme = themeRepository.findById(themeId).orElse(null);
		System.out.println("[subscribeTheme] theme trouvé: " + (theme != null));
		// user est déjà récupéré par email ci-dessus
		System.out.println("[subscribeTheme] user trouvé: " + (user != null));
		if (theme == null || user == null) {
			System.out.println("[subscribeTheme] ERREUR: theme ou user null");
			return false;
		}
		if (theme.getUsers().contains(user)) {
			System.out.println("[subscribeTheme] Déjà abonné");
			return false;
		}
		theme.getUsers().add(user);
		themeRepository.save(theme);
		System.out.println("[subscribeTheme] Abonnement réussi");
		return true;
	}

	// Désabonnement d'un thème
	public boolean unsubscribeTheme(Long themeId, String token) {
		System.out.println("[unsubscribeTheme] themeId=" + themeId + ", token=" + token);
		String email = jwtUtil.extractEmail(token);
		System.out.println("[unsubscribeTheme] email extrait du token: " + email);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		Long userId = user != null ? user.getId() : null;
		System.out.println("[unsubscribeTheme] userId retrouvé par email: " + userId);
		Themes theme = themeRepository.findById(themeId).orElse(null);
		System.out.println("[unsubscribeTheme] theme trouvé: " + (theme != null));
		// user est déjà récupéré par email ci-dessus
		System.out.println("[unsubscribeTheme] user trouvé: " + (user != null));
		if (theme == null || user == null) {
			System.out.println("[unsubscribeTheme] ERREUR: theme ou user null");
			return false;
		}
		if (!theme.getUsers().contains(user)) {
			System.out.println("[unsubscribeTheme] Pas abonné");
			return false;
		}
		theme.getUsers().remove(user);
		themeRepository.save(theme);
		System.out.println("[unsubscribeTheme] Désabonnement réussi");
		return true;
	}

	public List<GetUserThemesDTO> getThemesByUserId(Long userId) {
		List<Themes> themes = themeUserRepository.findThemesByUserId(userId);
		return themes.stream().map(theme -> new GetUserThemesDTO(theme.getThemeName())).collect(Collectors.toList());
	}
}
