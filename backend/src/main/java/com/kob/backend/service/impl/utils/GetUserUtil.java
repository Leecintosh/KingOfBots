package com.kob.backend.service.impl.utils;

import com.kob.backend.pojo.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetUserUtil {
    public static User getUserWithAccountInfo(AuthenticationManager authenticationManager, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(token); // automatically throws exception if authentication fails
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        return loginUser.getUser();
    }

    public static User getUserWithToken() {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) token.getPrincipal();
        return loginUser.getUser();
    }
}
