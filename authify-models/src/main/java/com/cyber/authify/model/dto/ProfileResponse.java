package com.cyber.authify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private long id;

    private String email;

    private String firstName;

    private String lastName;

    private boolean emailVerified;

    private Set<String> permissions;
}
