package com.smartarch.user.token;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.smartarch.user.model.User;

public class TokenService {

    public static String getToken(User user) {
        Date start = new Date ();
        long currentTime = System.currentTimeMillis () + 60 * 60 * 1000;//一小时有效时间
        Date end = new Date (currentTime);
        return JWT.create().withAudience(user.getId()).withIssuedAt(start)
                .withExpiresAt (end)
                .sign(Algorithm.HMAC256(user.getPasswords()));
    }
}