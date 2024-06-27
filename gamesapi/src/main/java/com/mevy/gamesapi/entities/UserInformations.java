package com.mevy.gamesapi.entities;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_User_Informations")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "user")
public class UserInformations implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        nullable = false,
        unique = true,
        updatable = false
    )
    private Long id;

    @JsonIgnore
    @OneToOne
    @MapsId
    private User user;

    @Column(length = 30)
    private String name;

    @Column(length = 30)
    private String lastName;

    @JsonFormat(
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timezone = "UTC",
        shape = JsonFormat.Shape.STRING
    )
    @Column(
        nullable = false,
        updatable = false
    )
    private Instant createAt;

    public UserInformations(User user) {
        this.user = user;
    }

}
