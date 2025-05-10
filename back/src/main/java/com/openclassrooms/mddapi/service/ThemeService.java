package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Themes;
import com.openclassrooms.mddapi.dto.GetThemesDTO;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
	@Autowired
	private ThemeRepository themeRepository;

	/**
	 * Récupère l'ensemble des thèmes disponibles dans la base de données.
	 *
	 * @return liste des thèmes exposés sous forme de {@link GetThemesDTO}
	 */
	public List<GetThemesDTO> getAllThemes() {
		List<Themes> themes = themeRepository.findAll();
		return themes.stream()
				.map(theme -> new GetThemesDTO(theme.getId(), theme.getThemeName(), theme.getThemeDescription()))
				.collect(Collectors.toList());
	}
}
