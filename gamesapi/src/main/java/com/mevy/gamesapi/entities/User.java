package com.mevy.gamesapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mevy.gamesapi.entities.enums.ProfileEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user", indexes = {
    @Index(name = "IDX_UserEmail", columnList = "email")
})
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private Long id;

    @Column(
        nullable = false,
        unique = true,
        length = 25
    )
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private String email;

    private Instant deleteDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        joinColumns        = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<Game> games = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInformations userInformations;

    public User(
            Long id,
            String username,
            String password,
            String email
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Set<ProfileEnum> getProfiles() {
        return profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        profiles.add(profileEnum.getCode());
    }
    
}
