package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;

public class GetUserDTO {
    private Long id;
    private String username;
    private String email;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    // Constructeur par défaut
    public GetUserDTO() {
    }

    // Constructeur à partir d'une entité User
    public GetUserDTO(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        
        // Gestion des dates nulles
        if (user.getCreatedAt() != null) {
            this.createdAt = user.getCreatedAt().format(formatter);
        } else {
            this.createdAt = "non défini";
        }
        
        if (user.getUpdatedAt() != null) {
            this.updatedAt = user.getUpdatedAt().format(formatter);
        } else {
            this.updatedAt = "non défini";
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}