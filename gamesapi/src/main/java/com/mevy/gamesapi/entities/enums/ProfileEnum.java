package com.mevy.gamesapi.entities.enums;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileEnum {
        ADMIN(1, "ROLE_ADMIN"),
        USER(2, "ROLE_USER"),
        GAME_DEVELOPER(3, "ROLE_GAME_DEVELOPER")
    ;

    private int code;
    private String description;

    public static ProfileEnum toEnum(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        for (ProfileEnum profile : ProfileEnum.values()) {
            if (code.equals(profile.getCode())) {
                return profile;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
