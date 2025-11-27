package com.example.project.user.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    private final String accessToken;

    public static LoginResponse from(String accessToken) {
        return new LoginResponse(accessToken);
    }
}
