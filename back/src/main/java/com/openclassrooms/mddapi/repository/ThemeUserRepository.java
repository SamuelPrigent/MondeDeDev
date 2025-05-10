package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Themes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository Spring Data JPA pour la relation many-to-many thème ↔ utilisateur.
 * Permet notamment de récupérer la liste des {@link Themes} d'un utilisateur
 * via une requête JPQL personnalisée.
 */
@Repository
public interface ThemeUserRepository extends JpaRepository<Themes, Long> {
	@Query("SELECT t FROM Themes t JOIN t.users u WHERE u.id = :userId")
	List<Themes> findThemesByUserId(@Param("userId") Long userId);
}
