package com.dev.kioki.global.security.util;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String phone;
    private final String code;
    private final String userId;
    private final String userRole;

    public CustomAuthenticationToken(String phone, String code, String userId, String userRole) {
        super(null);
        this.phone = phone;
        this.code = code;
        this.userId = userId;
        this.userRole = userRole;
        setAuthenticated(false);
    }

    @Override
    public String getPrincipal() {return phone;}

    @Override
    public String getCredentials() {return code;}

    @Override
    public String getName() {return userId;}

    public String getRole() {return userRole;}
}
