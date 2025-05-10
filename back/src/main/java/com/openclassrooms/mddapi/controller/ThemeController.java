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

/**
 * Contrôleur REST dédié à la gestion des thèmes et aux abonnements de l'utilisateur.
 * <p>
 * Fonctions proposées :
 * <ul>
 *   <li>Liste des thèmes ;</li>
 *   <li>Liste des thèmes avec information d'abonnement ;</li>
 *   <li>Abonnement/désabonnement à un thème.</li>
 * </ul>
 * Toutes les routes sont préfixées par <code>/api</code>.
 */
@RestController
@RequestMapping("/api")
public class ThemeController {
	/** Service de gestion des thèmes. */
	@Autowired
	private ThemeService themeService;
	/** Service gérant les relations utilisateur-thème (abonnements). */
	@Autowired
	private ThemeUserService themeUserService;

	/**
	 * Récupère la liste de tous les thèmes (pour la liste déroulante).
	 *
	 * @return une {@link java.util.List} de {@link GetThemesDTO}
	 */
	@GetMapping({ "/themes", "/themes/" })
	public List<GetThemesDTO> getAllThemes() {
		return themeService.getAllThemes();
	}

	/**
	 * Récupère la liste des thèmes avec information d'abonnement pour l'utilisateur courant.
	 *
	 * @param authHeader jeton <code>Bearer</code> de l'utilisateur
	 * @return une {@link java.util.List} de {@link GetThemesWithSubsInfoDTO}
	 */
	@GetMapping({ "/themesSubsInfo", "/themesSubsInfo/" })
	public List<GetThemesWithSubsInfoDTO> getAllThemesWithSubsInfo(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		return themeUserService.getAllThemesWithSubsInfo(token);
	}

	/**
	 * Récupère les thèmes auxquels un utilisateur est abonné.
	 *
	 * @param userId identifiant de l'utilisateur
	 * @return une {@link java.util.List} de {@link GetThemesDTO}
	 */
	@GetMapping({ "/themes/{userId}", "/themes/{userId}/" })
	public List<GetThemesDTO> getThemesByUserId(@PathVariable Long userId) {
		return themeUserService.getThemesByUserId(userId);
	}

	// ==== Subscribe & Unsubscribe to a theme ====

	/**
	 * Abonne l'utilisateur courant à un thème et renvoie la liste mise à jour avec info d'abonnement.
	 *
	 * @param body       DTO contenant l'id du thème
	 * @param authHeader jeton <code>Bearer</code>
	 * @return une {@link java.util.List} de {@link GetThemesWithSubsInfoDTO} ou erreur 400
	 */
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

	/**
	 * Désabonne l'utilisateur courant d'un thème et renvoie la liste des thèmes restants.
	 *
	 * @param body       DTO contenant l'id du thème
	 * @param authHeader jeton <code>Bearer</code>
	 * @return une {@link java.util.List} de {@link GetThemesDTO} ou erreur 400
	 */
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
