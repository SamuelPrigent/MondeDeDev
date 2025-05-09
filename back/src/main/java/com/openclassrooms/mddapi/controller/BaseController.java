package com.openclassrooms.mddapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseController {

	@GetMapping({ "/", "" })
	public ResponseEntity<String> getResponse() {
		return ResponseEntity.ok("Monde De Dev (API)");
	}
}