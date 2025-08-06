package com.francinjr.rentalbusiness.components.users;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    User fetchByEmail(String email);
    //User save(User user);
    //void requestChangePassword(String email);
    //void verifyToken(String token);
    //void changePassword(String email, String novaSenha);
    void login(User user, HttpServletResponse response);
    //void changePasswordWithoutToken(String email, String novaSenha);
    //void validateUserOperation(User user);
}
