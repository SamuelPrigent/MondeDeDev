package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	// find User in BDD by email
	Optional<User> findByEmail(String email);

	// find User by email ignoring case
	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
	Optional<User> findByEmailIgnoreCase(@Param("email") String email);

	// find User by username ignoring case
	@Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
	Optional<User> findByUsernameIgnoreCase(@Param("username") String username);

	// check if User exist with email
	Boolean existsByEmail(String email);
}