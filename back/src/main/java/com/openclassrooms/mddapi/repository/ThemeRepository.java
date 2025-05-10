package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Themes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité {@link Themes}.
 */
@Repository
public interface ThemeRepository extends JpaRepository<Themes, Long> {
}
