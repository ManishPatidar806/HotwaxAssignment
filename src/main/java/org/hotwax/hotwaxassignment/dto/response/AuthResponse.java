package org.hotwax.hotwaxassignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private String message;
}

