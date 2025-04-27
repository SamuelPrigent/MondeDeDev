package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Themes;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.dto.GetThemesDTO;
import com.openclassrooms.mddapi.dto.GetThemesWithSubsInfoDTO;
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

	//
	public List<GetThemesWithSubsInfoDTO> getAllThemesWithSubsInfo(String token) {
		String email = jwtUtil.extractEmail(token);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		List<Themes> allThemes = themeRepository.findAll();
		return allThemes.stream().map(theme -> {
			boolean isSubscribed = user != null && theme.getUsers().contains(user);
			return new GetThemesWithSubsInfoDTO(theme.getId(), theme.getThemeName(), theme.getThemeDescription(),
					isSubscribed);
		}).collect(Collectors.toList());
	}

	// Retourne la liste des thèmes de l'utilisateur à partir du token
	public List<GetThemesDTO> getThemesForMe(String token) {
		String email = jwtUtil.extractEmail(token);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		if (user == null) {
			return List.of();
		}
		return getThemesByUserId(user.getId());
	}

	// Abonnement à un thème
	public boolean subscribeTheme(Long themeId, String token) {
		String email = jwtUtil.extractEmail(token);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		Themes theme = themeRepository.findById(themeId).orElse(null);
		// user est déjà récupéré par email ci-dessus
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
		String email = jwtUtil.extractEmail(token);
		Optional<User> userOpt = userRepository.findByEmail(email);
		User user = userOpt.orElse(null);
		Themes theme = themeRepository.findById(themeId).orElse(null);
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

	//
	public List<GetThemesDTO> getThemesByUserId(Long userId) {
		List<Themes> themes = themeUserRepository.findThemesByUserId(userId);
		return themes.stream()
				.map(theme -> new GetThemesDTO(theme.getId(), theme.getThemeName(), theme.getThemeDescription()))
				.collect(Collectors.toList());
	}
}
