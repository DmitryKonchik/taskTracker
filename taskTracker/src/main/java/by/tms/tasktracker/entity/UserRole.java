package by.tms.tasktracker.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    PERFORMER,MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
