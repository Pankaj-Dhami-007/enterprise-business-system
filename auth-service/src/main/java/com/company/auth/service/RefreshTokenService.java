package com.company.auth.service;

import com.company.auth.entity.RefreshToken;
import com.company.auth.entity.UserAuth;

public interface RefreshTokenService {

    RefreshToken save(UserAuth user, String refreshToken, long ttlSeconds);

    RefreshToken validate(String refreshToken);

    void revoke(RefreshToken refreshToken);

    void revokeAll(UserAuth user);
}
