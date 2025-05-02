package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.GetThemesDTO;
import com.openclassrooms.mddapi.dto.GetThemesWithSubsInfoDTO;
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

	// GET /api/themes (for dropdown possibility)
	@GetMapping({ "/themes", "/themes/" })
	public List<GetThemesDTO> getAllThemes() {
		return themeService.getAllThemes();
	}

	// GET theme with Sub Info (theme page)
	@GetMapping({ "/themesSubsInfo", "/themesSubsInfo/" })
	public List<GetThemesWithSubsInfoDTO> getAllThemesWithSubsInfo(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		return themeUserService.getAllThemesWithSubsInfo(token);
	}

	// GET /api/themes/{userId} => pour filtrer les thèmes d'un utilisateur
	@GetMapping({ "/themes/{userId}", "/themes/{userId}/" })
	public List<GetThemesDTO> getThemesByUserId(@PathVariable Long userId) {
		return themeUserService.getThemesByUserId(userId);
	}

	// ==== Subscribe & Unsubscribe to a theme ====

	// Subscribe
	@PostMapping({ "/themes/subscribe", "/themes/subscribe/" })
	public ResponseEntity<List<GetThemesWithSubsInfoDTO>> subscribeThemeWithList(@RequestBody ThemeSubscribeDTO body,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		boolean result = themeUserService.subscribeTheme(body.getThemeId(), token);
		if (result) {
			return ResponseEntity.ok(themeUserService.getAllThemesWithSubsInfo(token));
		}
		return ResponseEntity.badRequest().build();
	}

	// Unsubscribe
	@DeleteMapping({ "/themes/unsubscribe", "/themes/unsubscribe/" })
	public ResponseEntity<List<GetThemesDTO>> unsubscribeTheme(@RequestBody ThemeSubscribeDTO body,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		boolean result = themeUserService.unsubscribeTheme(body.getThemeId(), token);
		if (result) {
			List<GetThemesDTO> themes = themeUserService.getThemesForMe(token);
			return ResponseEntity.ok(themes);
		}
		return ResponseEntity.badRequest().build();
	}
}
