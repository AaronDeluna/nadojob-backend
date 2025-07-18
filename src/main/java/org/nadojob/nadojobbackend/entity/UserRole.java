package org.nadojob.nadojobbackend.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CANDIDATE,
    COMPANY_OWNER,
    EMPLOYER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
