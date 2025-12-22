package com.sadps.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils(){}
        public static String getCurrentUserEmail(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth.getName();

    }
}
