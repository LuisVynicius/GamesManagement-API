package com.mevy.gamesapi.entities;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_game", indexes = {
    @Index(name = "IDX_Name", columnList = "name")
})
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Game implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 75)
    private String name;

    private String description;

    @Column(nullable = false, updatable = false)
    private Instant date;

    @Column(nullable = false)
    private Short ageGroup;

}
