package com.francinjr.rentalbusiness.components.users;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

public interface TokenService {

    String generateToken(User user) throws JWTCreationException;
    String validateToken(String token) throws JWTVerificationException;

}
