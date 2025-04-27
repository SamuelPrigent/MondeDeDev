package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.GetThemesDTO;
import com.openclassrooms.mddapi.dto.ThemeSubscribeDTO;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.ThemeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ThemeController {
	@Autowired
	private ThemeService themeService;
	@Autowired
	private ThemeUserService themeUserService;

	// GET /api/themes => used in a theme dropdown for article creation
	@GetMapping({ "/themes", "/themes/" })
	public List<GetThemesDTO> getAllThemes() {
		return themeService.getAllThemes();
	}

	// GET /api/themes/{userId} => pour filtrer les thèmes d'un utilisateur
	@GetMapping({ "/themes/{userId}", "/themes/{userId}/" })
	public List<GetThemesDTO> getThemesByUserId(@PathVariable Long userId) {
		return themeUserService.getThemesByUserId(userId);
	}

	// ==== Subscribe & Unsubscribe to a theme ====
	// S'abonner à un thème via body JSON
	@PostMapping("/themes/subscribe")
	public ResponseEntity<String> subscribeTheme(@RequestBody ThemeSubscribeDTO body,
			@RequestHeader("Authorization") String authHeader) {
		System.out
				.println("[ThemeController][subscribeTheme] themeId=" + body.getThemeId() + ", authHeader=" + authHeader);
		String token = authHeader.replace("Bearer ", "");
		boolean result = themeUserService.subscribeTheme(body.getThemeId(), token);
		System.out.println("[ThemeController][subscribeTheme] result=" + result);
		if (result)
			return ResponseEntity.ok("Subscribe to themeId : " + body.getThemeId());
		return ResponseEntity.badRequest().body("Déjà abonné ou erreur");
	}

	// Se désabonner d'un thème via body JSON
	@DeleteMapping("/themes/unsubscribe")
	public ResponseEntity<String> unsubscribeTheme(@RequestBody ThemeSubscribeDTO body,
			@RequestHeader("Authorization") String authHeader) {
		System.out
				.println("[ThemeController][unsubscribeTheme] themeId=" + body.getThemeId() + ", authHeader=" + authHeader);
		String token = authHeader.replace("Bearer ", "");
		boolean result = themeUserService.unsubscribeTheme(body.getThemeId(), token);
		System.out.println("[ThemeController][unsubscribeTheme] result=" + result);
		if (result)
			return ResponseEntity.ok("Unsubscribe from themeId : " + body.getThemeId());
		return ResponseEntity.badRequest().body("Non abonné ou erreur");
	}
}
