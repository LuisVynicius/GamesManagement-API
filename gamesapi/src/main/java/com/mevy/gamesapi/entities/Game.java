package com.mevy.gamesapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_game", indexes = {
    @Index(name = "IDX_GameName", columnList = "name")
})
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Game implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 75)
    private String name;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private Instant date;

    @Column(nullable = false)
    private Short ageGroup;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    public Game(Long id, String name, Float price, String description, Instant date, Short ageGroup) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.date = date;
        this.ageGroup = ageGroup;
    }

}
